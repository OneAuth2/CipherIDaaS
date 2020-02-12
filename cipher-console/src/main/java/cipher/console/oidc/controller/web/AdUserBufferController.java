package cipher.console.oidc.controller.web;

import cipher.console.oidc.common.ConstantsCMP;
import cipher.console.oidc.config.LdapMultiSourceConfig;
import cipher.console.oidc.domain.web.AdInfoDomain;
import cipher.console.oidc.domain.web.AdMap2LocalDomain;
import cipher.console.oidc.domain.web.AdUserBufferDomain;
import cipher.console.oidc.domain.web.UserInfoDomain;
import cipher.console.oidc.entity.LdapUser;
import cipher.console.oidc.model.AdMap2LocalConfigModel;
import cipher.console.oidc.service.AdInfoService;
import cipher.console.oidc.service.AdMap2LocalService;
import cipher.console.oidc.service.AdUserBufferService;
import cipher.console.oidc.service.LdapUserService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.naming.*;
import javax.naming.directory.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.util.*;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

/**
 * @Author: zt
 * @Date: 2018/10/24 19:37
 */
@Controller
@RequestMapping(value = "/cipher-back-19-04-12")
public class AdUserBufferController {


    private LdapTemplate ldapTemplate = null;

//    @Autowired
//    private AdMap2LocalService adMap2LocalService;

    @Autowired
    private AdUserBufferService adUserBufferService;

    @Autowired
    private LdapUserService ldapUserService;

    @Autowired
    private AdInfoService adInfoService;

    private static final Logger LOGGER = LoggerFactory.getLogger(AdUserBufferController.class);


    /**
     * 将AD域的用户导入缓冲表中
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/depart_sync")
    @ResponseBody
    public Map<String, Integer> syncAdUser2Buffer(HttpServletRequest request, HttpServletResponse response,
                                                  @RequestParam(value = "adId") Integer adId) {
        LOGGER.info("Enter the AdUserBufferController.syncAdUser2Buffer 进入增量同步AD域用户");
        String companyId = ConstantsCMP.getSessionCompanyId(request);
        AdInfoDomain adInfoDomain = adInfoService.queryAdInfoById(adId);
        ldapTemplate = LdapMultiSourceConfig.getLdapTemplate(adInfoDomain);

        String latestTimeStamp = ldapUserService.queryLatestChangeTime(adInfoDomain.getIp());

        LOGGER.info("上次更新的时间:{}", latestTimeStamp);

        if (StringUtils.isEmpty(latestTimeStamp)) {
            latestTimeStamp = "19700916080801.0Z";
        }

        Map<String, Integer> map = new HashMap<>();

        //获取字段映射配置
        List<AdMap2LocalConfigModel> adMap2LocalConfigModelList = getAdMapConfig(adInfoDomain);


        LdapQuery ldapQuery = query().where("whenChanged").gte(latestTimeStamp);
        List<LdapUser> ldapUserList = ldapTemplate.find(ldapQuery, LdapUser.class);

        //添加来源
        if (CollectionUtils.isNotEmpty(ldapUserList)) {
            for (LdapUser ldapUser : ldapUserList) {
                ldapUser.setSource(adInfoDomain.getIp());
            }
        }


        //从AD域导出的数据，并转换成缓冲的数据结构
        List<AdUserBufferDomain> adUserBufferDomainList = convertAdUserToBuffer(ldapUserList, adMap2LocalConfigModelList);

        //扫描到的所有的用户数
        int allUser = adUserBufferDomainList.size();
        map.put("allData", allUser);
        //已经存在于cipher_user_info,或者cipher_user_ad_buffer表中的数据
        List<UserInfoDomain> dbAdRepeatList = adUserBufferService.queryUserByBufferUser(adUserBufferDomainList, companyId);

        //所有数据都是新增数据
        if (CollectionUtils.isEmpty(dbAdRepeatList)) {
            LOGGER.info("AdUserBufferController.syncAdUser2Buffer 所有用户都是新增用户");
            //扫描到的所有数据量
            map.put("allData", adUserBufferDomainList.size());
            //更新的数据条数
            map.put("update", 0);
            //新增的数据条数
            map.put("add", adUserBufferDomainList.size());
            try {
                //插入缓冲表
                adUserBufferService.insertBufferList(adUserBufferDomainList);
                //插入备份表
                ldapUserService.insertLdapUserList(ldapUserList);
            } catch (Exception e) {
                e.printStackTrace();
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
            return map;
        }

        List<LdapUser> updateLdapUserList = ldapUserGuoLv(ldapUserList, dbAdRepeatList);
        try {
            if (CollectionUtils.isNotEmpty(updateLdapUserList)) {
                //更新备份表中已经存在的数据
                ldapUserService.updateList(updateLdapUserList);
            }
            if (CollectionUtils.isNotEmpty(ldapUserList)) {
                //插入新增的数据
                ldapUserService.insertLdapUserList(ldapUserList);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        /***字段更新和新增共存的情况，对于存在于cipher_user_ad_buffer中的数据，update,其余的，insert进buffer**/


        //查出存在于buffer的数据
        List<AdUserBufferDomain> inBufferUserList = adUserBufferService.queryInBufferList(dbAdRepeatList, companyId);
        //从所有字段更新的数据中，拆分出已经存在于buffer的数据，和已经存在于用户的数据
        List<AdUserBufferDomain> inBufferList = chaifen(adUserBufferDomainList, inBufferUserList);

        try {
            //对于已经存在于缓冲表中的数据，直接更新
            if (CollectionUtils.isNotEmpty(inBufferList)) {

                adUserBufferService.updateBufferList(inBufferList);
            }

            //新增的数据直接插入
            if (CollectionUtils.isNotEmpty(adUserBufferDomainList)) {
                List<UserInfoDomain> userInfoDomainList = adUserBufferService.queryUserInfoByBufferList(adUserBufferDomainList, companyId);
                Iterator<AdUserBufferDomain> iterator = adUserBufferDomainList.iterator();
                //对于相同的，不在插入
                while (iterator.hasNext()) {
                    AdUserBufferDomain adUserBufferDomain = iterator.next();
                    for (UserInfoDomain userInfoDomain : userInfoDomainList) {
                        if (adUserBufferDomain.getObjectGUID().equals(userInfoDomain.getObjectGUID())) {
                            if (checkIsEqualBufferAndUser(adUserBufferDomain, userInfoDomain)) {
                                iterator.remove();
                                break;
                            }
                        }

                    }
                }


                if (CollectionUtils.isNotEmpty(adUserBufferDomainList)) {
                    adUserBufferService.insertBufferList(adUserBufferDomainList);
                }
            }

            map.put("add", adUserBufferDomainList.size());
            map.put("update", inBufferList.size());
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return map;
    }


    /**
     * 获取AD映射到数据库的配置
     *
     * @return
     */
    private List<AdMap2LocalConfigModel> getAdMapConfig(AdInfoDomain adInfoDomain) {
//        AdInfoDomain adInfoDomain = adInfoService.queryAdInfo(form);
        String kvJson = adInfoDomain.getKvConfig();
        List<AdMap2LocalConfigModel> list = new Gson().fromJson(kvJson, new TypeToken<List<AdMap2LocalConfigModel>>() {
        }.getType());
        list.add(new AdMap2LocalConfigModel("objectGUID", "objectGUID"));
        list.add(new AdMap2LocalConfigModel("dn", "dn"));
        list.add(new AdMap2LocalConfigModel("source", "source"));
        return list;
    }

    /**
     * @param allUser    从AD域导入的所有的用户，
     * @param bufferList 已经存在的用户
     * @return 已经存在的用户列表，allUser中剩下新增的用户
     */
    private List<LdapUser> ldapUserGuoLv(List<LdapUser> allUser, List<UserInfoDomain> bufferList) {
        List<LdapUser> ldapUserExistsList = new ArrayList<>();
        Iterator<LdapUser> iterator = allUser.iterator();
        while (iterator.hasNext()) {
            LdapUser ldapUser = iterator.next();
            if (ldapUser.getUserPrincipalName() == null) {
                iterator.remove();
                continue;
            }

            for (UserInfoDomain userInfoDomain : bufferList) {
                if (userInfoDomain.getObjectGUID().equals(ldapUser.getObjectGUID())) {
                    ldapUserExistsList.add(ldapUser);
                    iterator.remove();
                    break;
                }
            }
        }

        return ldapUserExistsList;
    }

    /**
     * @param allUser    所有字段更新的用户
     * @param bufferList 已经存在于缓冲表的accountNumber列表
     * @return 返回，已经存在于buffer的所有用户的信息,allUser只剩下未在缓冲中的用户
     */
    private List<AdUserBufferDomain> chaifen(List<AdUserBufferDomain> allUser, List<AdUserBufferDomain> bufferList) {
        List<AdUserBufferDomain> inBuffferUserList = new ArrayList<>();

        Iterator<AdUserBufferDomain> iterator = allUser.iterator();
        while (iterator.hasNext()) {
            AdUserBufferDomain adUserBufferDomain = iterator.next();

            for (AdUserBufferDomain bufferDomain : bufferList) {
                if (adUserBufferDomain.getObjectGUID().equals(bufferDomain.getObjectGUID())) {
                    inBuffferUserList.add(adUserBufferDomain);
                    iterator.remove();
                    break;
                }
            }
        }
        return inBuffferUserList;
    }


    /**
     * 将Ad域中的用户转换为缓冲表中的用户
     *
     * @param ldapUserList               Ad域中查出来的用户列表
     * @param adMap2LocalConfigModelList 字段映射配置
     * @return 缓冲表中的用户
     */
    private List<AdUserBufferDomain> convertAdUserToBuffer(List<LdapUser> ldapUserList, List<AdMap2LocalConfigModel> adMap2LocalConfigModelList) {
        List<AdUserBufferDomain> adUserBufferDomainList = new ArrayList<>();

        for (LdapUser ldapUser : ldapUserList) {
            AdUserBufferDomain adUserBufferDomain = new AdUserBufferDomain();
            for (AdMap2LocalConfigModel adMap2LocalDomain : adMap2LocalConfigModelList) {
                try {
                    Field adField = ldapUser.getClass().getDeclaredField(adMap2LocalDomain.getAdKey());
                    adField.setAccessible(true);
//                    LOGGER.info("字段名：" + adField.getName() + "<=====>值：" + adField.get(ldapUser));
                    if (adField.get(ldapUser) != null) {
                        Field userField = adUserBufferDomain.getClass().getDeclaredField(adMap2LocalDomain.getLocalVal());
                        userField.setAccessible(true);
                        userField.set(adUserBufferDomain, adField.get(ldapUser));
                    }
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            //过滤掉没有userPrincipalName的账号，如电脑主机
            if (adUserBufferDomain.getAccountNumber() != null) {
                adUserBufferDomainList.add(adUserBufferDomain);
            }

        }
        return adUserBufferDomainList;
    }

    /**
     * 全量扫描
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/all_sync_old")
    @ResponseBody
    public Map<String, Integer> allSyncAdUser2Buffer(HttpServletRequest request,
                                                     HttpServletResponse response,
                                                     @RequestParam(required = false) String companyIdParam,
                                                     @RequestParam(value = "adId") Integer adId) {
        String companyId = null;
        try {
            companyId = ConstantsCMP.getSessionCompanyId(request);
        }catch (Exception e){
            LOGGER.error("定时任务不能获取到session");
        }
        if (StringUtils.isEmpty(companyId)) {
            companyId = companyIdParam;
        }

        AdInfoDomain adInfoDomain = adInfoService.queryAdInfoById(adId);
        ldapTemplate = LdapMultiSourceConfig.getLdapTemplate(adInfoDomain);

        Map<String, Integer> map = new HashMap<>();
        String latestTimeStamp = "19700916080801.0Z";

        //获取字段映射配置
        List<AdMap2LocalConfigModel> adMap2LocalConfigModelList = getAdMapConfig(adInfoDomain);

        LdapQuery ldapQuery = query().where("whenChanged").gte(latestTimeStamp);

        List<LdapUser> ldapUserList = ldapTemplate.find(ldapQuery, LdapUser.class);

        //添加来源
        if (CollectionUtils.isNotEmpty(ldapUserList)) {
            for (LdapUser ldapUser : ldapUserList) {
                ldapUser.setSource(adInfoDomain.getIp());
            }
        }


        //清洗数据，去掉userPrincipalName为空的数据
        ldapUserList.removeIf(ldapUser -> ldapUser.getUserPrincipalName() == null);

        int allData = ldapUserList.size();
        //该AD域没有有效数据
        if (CollectionUtils.isEmpty(ldapUserList)) {
            map.put("allData", 0);
            map.put("update", 0);
            map.put("add", 0);
            return map;
        }

        // 将ldapUserList插入到数据库
        ldapUserService.insertLdapUserGuidList(ldapUserList, companyId);

        map = ldapUserService.allSyncAdUser2Buffer(ldapUserList, adMap2LocalConfigModelList, map, companyId);


        if (map != null) {
            map.put("allData", allData);
        }

        return map;
    }


    /**
     * 判断个别字段是否相同
     *
     * @param adUserBufferDomain
     * @param dbBufferUser
     * @return
     */
    private Boolean checkIsEqual(AdUserBufferDomain adUserBufferDomain, AdUserBufferDomain dbBufferUser) {
        try {
            //昵称不同
            if (!StringUtils.equals(dbBufferUser.getNickname(), adUserBufferDomain.getNickname())) {
                return false;
            }
            //名不同
            if (!StringUtils.equals(dbBufferUser.getUserName(), adUserBufferDomain.getUserName())) {
                return false;
            }

            //职位不同
            if (!StringUtils.equals(dbBufferUser.getJob(), adUserBufferDomain.getJob())) {
                return false;
            }

            //手机号不同
            if (!StringUtils.equals(dbBufferUser.getPhoneNumber(), adUserBufferDomain.getPhoneNumber())) {
                return false;
            }

            //dn不同
            if (!StringUtils.equals(dbBufferUser.getDn(), adUserBufferDomain.getDn())) {
                return false;
            }

            //account_number不同
            if (!StringUtils.equals(dbBufferUser.getAccountNumber(), adUserBufferDomain.getAccountNumber())) {
                return false;
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 判断新导入的用户和已有的是否相同
     *
     * @param adUserBufferDomain
     * @param userInfoDomain
     * @return
     */
    private Boolean checkIsEqualBufferAndUser(AdUserBufferDomain adUserBufferDomain, UserInfoDomain userInfoDomain) {
        try {
            //昵称不同
            if (!StringUtils.equals(userInfoDomain.getNickname(), adUserBufferDomain.getNickname())) {
                return false;
            }
            //名不同
            if (!StringUtils.equals(userInfoDomain.getUserName(), adUserBufferDomain.getUserName())) {
                return false;
            }

            //职位不同
            if (!StringUtils.equals(userInfoDomain.getJob(), adUserBufferDomain.getJob())) {
                return false;
            }

            //手机号不同
            if (!StringUtils.equals(userInfoDomain.getPhoneNumber(), adUserBufferDomain.getPhoneNumber())) {
                return false;
            }

            //account_number不同
            if (!StringUtils.equals(userInfoDomain.getAccountNumber(), adUserBufferDomain.getAccountNumber())) {
                return false;
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 全量扫描
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/all_sync")
    @ResponseBody
    @Transactional
    @Deprecated
    public Map<String, Integer> allSyncAdUser2BufferOld(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "adId") Integer adId) {
        String companyId = ConstantsCMP.getSessionCompanyId(request);
        AdInfoDomain adInfoDomain = adInfoService.queryAdInfoById(adId);
        ldapTemplate = LdapMultiSourceConfig.getLdapTemplate(adInfoDomain);


        Map<String, Integer> map = new HashMap<>();
        String latestTimeStamp = "19700916080801.0Z";

        //获取字段映射配置
        List<AdMap2LocalConfigModel> adMap2LocalConfigModelList = getAdMapConfig(adInfoDomain);

        LdapQuery ldapQuery = query().where("whenChanged").gte(latestTimeStamp);
        List<LdapUser> ldapUserList = ldapTemplate.find(ldapQuery, LdapUser.class);

        //清洗数据，去掉userPrincipalName为空的数据
        ldapUserList.removeIf(ldapUser -> ldapUser.getUserPrincipalName() == null);

        int allData = ldapUserList.size();
        //该AD域没有有效数据
        if (CollectionUtils.isEmpty(ldapUserList)) {
            map.put("allData", 0);
            map.put("update", 0);
            map.put("add", 0);
            return map;
        }

        //查出已经存在于备份表的数据（只有一个字段），区分新增和字段更改
        List<LdapUser> alredyExistUsesr = ldapUserService.queryAlredyExistsUser(ldapUserList, companyId);

        //只存在字段更新的用户，不区分是在cipher_user_info还是buffer
        List<LdapUser> alredyExistUserList = new ArrayList<>();

        Iterator<LdapUser> ldapUserIterator = ldapUserList.iterator();

        //执行完后，ldapUserList只剩新增的用户
        while (ldapUserIterator.hasNext()) {
            LdapUser ldapUser = ldapUserIterator.next();
            for (LdapUser ldapUser1 : alredyExistUsesr) {
                if (ldapUser1.getObjectGUID().equals(ldapUser.getObjectGUID())) {
                    alredyExistUserList.add(ldapUser);
                    ldapUserIterator.remove();
                    break;
                }
            }
        }

        //新增的用户
        List<AdUserBufferDomain> addList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(ldapUserList)) {
            addList = convertAdUserToBuffer(ldapUserList, adMap2LocalConfigModelList);
        }


        //从只存在字段更新的列表中，拆出，分别在cippher_user_info和buffer的list
        //只存在字段更新的用户
        List<AdUserBufferDomain> existsList = convertAdUserToBuffer(alredyExistUserList, adMap2LocalConfigModelList);

        //只存在于缓冲表的列表,只有accountNumber
        List<AdUserBufferDomain> justInBufferList = null;
        if (CollectionUtils.isNotEmpty(existsList)) {
            justInBufferList = adUserBufferService.queryInBufferListByBuffer(existsList, companyId);
        }


        List<AdUserBufferDomain> justInBufferUserList = new ArrayList<>();

        Iterator<AdUserBufferDomain> adUserBufferDomainIterator = existsList.iterator();

//        执行完后existsList中只剩已经存在于cipher_user的列表
        while (adUserBufferDomainIterator.hasNext()) {
            AdUserBufferDomain adUserBufferDomain = adUserBufferDomainIterator.next();
            for (AdUserBufferDomain bufferDomain : justInBufferList) {
                if (bufferDomain.getObjectGUID().equals(adUserBufferDomain.getObjectGUID())) {
                    justInBufferUserList.add(adUserBufferDomain);
                    adUserBufferDomainIterator.remove();
                    break;
                }
            }
        }

        try {
            //新增的用户，插入备份表
            if (CollectionUtils.isNotEmpty(ldapUserList)) {
                ldapUserService.insertLdapUserList(ldapUserList);
            }

            //字段更新，但已经存在于缓冲表中的列表
            if (CollectionUtils.isNotEmpty(justInBufferUserList)) {

                //查出已经存在于缓冲表的列表，并对比，
                List<AdUserBufferDomain> userInfoDomainList = adUserBufferService.queryUserByBufferList(justInBufferUserList, companyId);

                Iterator<AdUserBufferDomain> iterator = justInBufferUserList.iterator();
                while (iterator.hasNext()) {
                    AdUserBufferDomain adUserBufferDomain = iterator.next();
                    for (AdUserBufferDomain userInfoDomain : userInfoDomainList) {
                        if (adUserBufferDomain.getObjectGUID().equals(userInfoDomain.getObjectGUID())) {
                            if (checkIsEqual(adUserBufferDomain, userInfoDomain)) {
                                iterator.remove();
                                break;
                            }
                        }
                    }

                }

                if (CollectionUtils.isNotEmpty(justInBufferUserList)) {
                    adUserBufferService.updateBufferList(justInBufferUserList);
                }
            }

            //existsList，已经存在于cipher_user表的用户，确认是字段更新还是未改变
            if (CollectionUtils.isNotEmpty(existsList)) {
                List<UserInfoDomain> userInfoDomainList = adUserBufferService.queryUserInfoByBufferList(existsList, companyId);
                Iterator<AdUserBufferDomain> adIterator = existsList.iterator();

                if (CollectionUtils.isNotEmpty(userInfoDomainList)) {
                    while (adIterator.hasNext()) {
                        AdUserBufferDomain adUserBufferDomain = adIterator.next();
                        for (UserInfoDomain userInfoDomain : userInfoDomainList) {
                            if (adUserBufferDomain.getObjectGUID().equals(userInfoDomain.getObjectGUID())) {
                                if (checkIsEqualBufferAndUser(adUserBufferDomain, userInfoDomain)) {
                                    adIterator.remove();
                                    break;
                                }
                            }
                        }
                    }
                }

                if (CollectionUtils.isNotEmpty(existsList)) {
                    adUserBufferService.updateBufferList(existsList);
//                    adUserBufferService.insertBufferList(existsList);
                }
            }

            //新增的用户列表
            if (CollectionUtils.isNotEmpty(addList)) {
                adUserBufferService.insertBufferList(addList);
            }

            map.put("allData", allData);
            map.put("add", addList.size());
            map.put("update", justInBufferUserList.size());
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        return null;
    }

}
