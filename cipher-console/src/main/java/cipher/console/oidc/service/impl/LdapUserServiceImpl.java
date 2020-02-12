package cipher.console.oidc.service.impl;

import cipher.console.oidc.domain.web.AdUserBufferDomain;
import cipher.console.oidc.domain.web.UserInfoDomain;
import cipher.console.oidc.entity.LdapUser;
import cipher.console.oidc.mapper.LdapUserMapper;
import cipher.console.oidc.model.AdMap2LocalConfigModel;
import cipher.console.oidc.service.AdUserBufferService;
import cipher.console.oidc.service.LdapUserService;
import edu.hziee.common.queue.DelayExecuteBuffer;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @Author: zt
 * @Date: 2018/10/25 10:56
 */
@Service
public class LdapUserServiceImpl implements LdapUserService {

    private static final Logger logger = LoggerFactory.getLogger(LdapUserServiceImpl.class);

    @Autowired
    private LdapUserMapper ldapUserMapper;

//    @Autowired
//    private DelayExecuteBuffer<LdapUser> inserLdapUserBuffer;

    @Autowired
    private AdUserBufferService adUserBufferService;

    @Override
    public void insertLdapUserList(List<LdapUser> ldapUserList) throws Exception {
        if(CollectionUtils.isEmpty(ldapUserList)){
            return;
        }

//        for (LdapUser ldapUser : ldapUserList) {
//            inserLdapUserBuffer.add(ldapUser);
//        }

        ldapUserMapper.insertLdapUserList(ldapUserList);
    }

    @Override
    @Transactional
    public void insertLdapUserGuidList(List<LdapUser> ldapUserList,String companyId) {
        if(CollectionUtils.isEmpty(ldapUserList)){
            return;
        }

        try {
            // 先删除后插入
            ldapUserMapper.truncateLdapUserGuidList(companyId);
            ldapUserMapper.insertLdapUserGuidList(ldapUserList);
        } catch (Exception e) {
            logger.error("Enter LdapUserServiceImpl.insertLdapUserGuidList() but failed..==");
           logger.error(e.getMessage(), e);
        }

    }

    @Override
    public void updateList(List<LdapUser> ldapUserList) {
        ldapUserMapper.updateList(ldapUserList);
    }

    @Override
    public String queryLatestChangeTime(String source) {
        return ldapUserMapper.queryLatestChangeTime(source);
    }

    @Override
    public List<LdapUser> queryAlredyExistsUser(List<LdapUser> ldapUserList,String companyId) {
        return ldapUserMapper.queryAlredyExistsUser(ldapUserList,companyId);
    }

    @Override
    public List<LdapUser> queryAlredyExistsUser() {
        return ldapUserMapper.queryAlredyExistsUserExtend();
    }

    @Override
    public String queryLatestCreatedTime() {
        return ldapUserMapper.queryLatestCreatedTime();
    }

    @Override
    @Transactional
    public Map<String, Integer> allSyncAdUser2Buffer(List<LdapUser> ldapUserList, List<AdMap2LocalConfigModel> adMap2LocalConfigModelList, Map<String, Integer> map,String companyId) {
        //查出已经存在于备份表的数据（只有一个字段），区分新增和字段更改
        List<LdapUser> alredyExistUsesr = this.queryAlredyExistsUser();

        //只存在字段更新的用户，不区分是在cipher_user_info还是buffer
        List<LdapUser> alredyExistUserList = new ArrayList<>();

        Iterator<LdapUser> ldapUserIterator = ldapUserList.iterator();

        //执行完后，ldapUserList只剩新增的用户
        while (ldapUserIterator.hasNext()) {
            LdapUser ldapUser = ldapUserIterator.next();
            for (LdapUser ldapUser1 : alredyExistUsesr) {
                ldapUser1.setCompanyId(companyId);
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
            justInBufferList = adUserBufferService.queryInBufferListByBuffer(existsList,companyId);
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
                this.insertLdapUserList(ldapUserList);
            }

            //字段更新，但已经存在于缓冲表中的列表
            if (CollectionUtils.isNotEmpty(justInBufferUserList)) {

                //查出已经存在于缓冲表的列表，并对比，
                List<AdUserBufferDomain> userInfoDomainList = adUserBufferService.queryUserByBufferList(justInBufferUserList,companyId);

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
                List<UserInfoDomain> userInfoDomainList = adUserBufferService.queryUserInfoByBufferList(existsList,companyId);
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

            map.put("add", addList.size());
            map.put("update", justInBufferUserList.size());

            return map;
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        return map;
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

        /*    //手机号不同
            if (!StringUtils.equals(dbBufferUser.getPhoneNumber(), adUserBufferDomain.getPhoneNumber())) {
                return false;
            }
*/
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

}
