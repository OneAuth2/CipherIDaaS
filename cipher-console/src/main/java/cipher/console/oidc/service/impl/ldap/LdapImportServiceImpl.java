package cipher.console.oidc.service.impl.ldap;

import cipher.console.oidc.config.LdapMultiSourceConfig;
import cipher.console.oidc.domain.web.*;
import cipher.console.oidc.entity.LdapUser;
import cipher.console.oidc.model.AdMap2LocalConfigModel;
import cipher.console.oidc.service.AdUserBufferService;
import cipher.console.oidc.service.ldap.AdUpdateTimeService;
import cipher.console.oidc.service.ldap.LdapImportService;
import com.alibaba.fastjson.JSON;
import com.unboundid.ldap.sdk.*;
import com.unboundid.ldap.sdk.controls.SimplePagedResultsControl;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

/**
 * @Author: zt
 * @Date: 2019-04-13 16:36
 */
@Service
public class LdapImportServiceImpl implements LdapImportService {

    private Logger logger = LoggerFactory.getLogger(LdapImportServiceImpl.class);

    @Autowired
    private AdUpdateTimeService adUpdateTimeService;


    @Override
    public List<SearchResultEntry> importUserFromLdap(AdInfoDomain adInfoDomain, String timeStamp) throws Exception {


        //AD到缓冲表中的映射关系
        String kvConfigJson = adInfoDomain.getKvConfig();

        List<AdMap2LocalConfigModel> kvList = JSON.parseArray(kvConfigJson, AdMap2LocalConfigModel.class);

        //获取要导入的AD域属性
        List<String> adKeys = kvList.stream()
                .map(AdMap2LocalConfigModel::getAdKey)
                .collect(Collectors.toList());

        LDAPConnection ldapConnection = LdapMultiSourceConfig.getLdapSdkConnection(adInfoDomain);

        if (ldapConnection == null) {
            return null;
        }

        Filter personFilter = Filter.create("objectClass=person");
        Filter timeFilter = Filter.create("whenChanged>=" + timeStamp);
        Filter resFilter = Filter.createANDFilter(personFilter, timeFilter);

        String rootDc = getRootDc(adInfoDomain.getUserName());


        SearchRequest searchRequest = new SearchRequest(rootDc, SearchScope.SUB, resFilter, (String[]) adKeys.toArray(new String[0]));
        searchRequest.setControls(new SimplePagedResultsControl(50000, null));
        //0代表不限制
        searchRequest.setSizeLimit(100000);

        String ous = adInfoDomain.getOus();
        if (StringUtils.isEmpty(ous)) {
            SearchResult searchResult = ldapConnection.search(searchRequest);

            List<SearchResultEntry> entryList = searchResult.getSearchEntries();
            logger.info("总共扫描到:" + entryList.size() + " 条数据");

            return entryList;
        }


        List<SearchResultEntry> resultEntryList = new ArrayList<>();

        List<String> ouList = JSON.parseArray(adInfoDomain.getOus(), String.class);

        for (String ou : ouList) {
            //设置baseDn
            searchRequest.setBaseDN(ou);
            SearchResult searchResult = ldapConnection.search(searchRequest);
            List<SearchResultEntry> entryList = searchResult.getSearchEntries();
            if (CollectionUtils.isNotEmpty(entryList)) {
                resultEntryList.addAll(entryList);
            }
        }

        logger.info("总共扫描到:" + resultEntryList.size() + " 条数据");

        return resultEntryList;
    }

    /**
     * 获取当前cpu的核数
     *
     * @return
     */
    private int getCurrentCpuHe() {
        return Runtime.getRuntime().availableProcessors();
    }


    @Override
    public List<AdUserBufferDomain> importLdapUserWithTemplate(AdInfoDomain adInfoDomain, String timeStamp) throws Exception {

        LdapTemplate ldapTemplate = LdapMultiSourceConfig.getLdapTemplate(adInfoDomain);
        if (ldapTemplate == null) {
            return null;
        }

        List<AdMap2LocalConfigModel> adMap2LocalConfigModelList = JSON.parseArray(adInfoDomain.getKvConfig(), AdMap2LocalConfigModel.class);

        LdapQuery ldapQuery = query().where("whenChanged").gte(timeStamp);
        List<LdapUser> ldapUserList = ldapTemplate.find(ldapQuery, LdapUser.class);

        //从AD域导出的数据，并转换成缓冲的数据结构
        List<AdUserBufferDomain> adUserBufferDomainList = convertAdUserToBuffer(ldapUserList, adMap2LocalConfigModelList);
        logger.info("scen user total :" + adUserBufferDomainList.size());

        //维护一个AD域增量更新的时间戳
        String latestChange = "19700916080801.0Z";
        for (AdUserBufferDomain adUserBufferDomain : adUserBufferDomainList) {
            adUserBufferDomain.setSource(adInfoDomain.getIp());
            String whenChange = adUserBufferDomain.getWhenChanged();
            if (whenChange.compareTo(latestChange) > 0) {
                latestChange = whenChange;
            }
        }
        if (!"19700916080801.0Z".equals(latestChange)) {
            //插入或者更新
            adUpdateTimeService.updateOrInsert(new AdUpdateTimeDomain(adInfoDomain.getIp(), latestChange));
        }
        return adUserBufferDomainList;
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
                } catch (Exception e) {
//                    logger.error("No such filed :" + e.getMessage());
                }
            }
            //过滤掉没有userPrincipalName的账号，如电脑主机
            if (adUserBufferDomain.getAccountNumber() != null) {
                adUserBufferDomainList.add(adUserBufferDomain);
            }

        }
        return adUserBufferDomainList;
    }


    private String getRootDc(String userName) {

        String s = userName.substring(userName.lastIndexOf("@") + 1, userName.length());
        String[] dcs = s.split("\\.");
        StringBuilder baseDc = new StringBuilder();
        for (int i = 0; i < dcs.length; i++) {
            if (i == dcs.length - 1) {
                baseDc.append("dc=").append(dcs[i]);
            } else {
                baseDc.append("dc=").append(dcs[i]).append(",");
            }
        }
        return baseDc.toString();
    }

    @Override
    public List<AdUserBufferDomain> convertToAdUser(List<SearchResultEntry> ldapUserList, AdInfoDomain adInfoDomain) throws Exception {
        List<AdUserBufferDomain> list = new ArrayList<>();
        List<AdMap2LocalConfigModel> kvList = JSON.parseArray(adInfoDomain.getKvConfig(), AdMap2LocalConfigModel.class);
        List<AdUserBufferDomain> adUserBufferDomainList = new ArrayList<>();

        //数据结构转换
        ldapUserList.forEach(searchResultEntry -> {

            AdUserBufferDomain adUserBufferDomain = new AdUserBufferDomain();

            kvList.forEach(adMap2LocalConfigModel -> {
                Field userField;
                try {
                    userField = adUserBufferDomain.getClass().getDeclaredField(adMap2LocalConfigModel.getLocalVal());
                    userField.setAccessible(true);
                    String val = searchResultEntry.getAttribute(adMap2LocalConfigModel.getAdKey()).getValue();
                    if ("userAccountControl".equals(adMap2LocalConfigModel.getAdKey())) {
                        userField.set(adUserBufferDomain, Integer.parseInt(val));
                    }
                    if ("objectGUID".equals(adMap2LocalConfigModel.getAdKey())) {
                        userField.set(adUserBufferDomain, getGUID(val.getBytes()));
                    } else {
                        userField.set(adUserBufferDomain, val);
                    }

                } catch (NoSuchFieldException | NullPointerException e) {
//                    logger.error("No such field :" + e.getMessage());
                } catch (IllegalAccessException e) {
                    logger.error("Illegal access exception:" + e.getMessage());
                } catch (Exception e) {
                    logger.error("Server exception :" + e.getMessage());
                }
            });
            adUserBufferDomainList.add(adUserBufferDomain);
        });
        //去掉accountNumber为空的数据
        adUserBufferDomainList.removeIf(adUserBufferDomain -> adUserBufferDomain.getAccountNumber() == null);

        //维护一个AD域增量更新的时间戳
        String latestChange = "19700916080801.0Z";
        for (AdUserBufferDomain adUserBufferDomain : adUserBufferDomainList) {
            adUserBufferDomain.setCompanyId(adInfoDomain.getCompanyId());
            adUserBufferDomain.setSource(adInfoDomain.getIp());
            String whenChange = adUserBufferDomain.getWhenChanged();
            if (whenChange.compareTo(latestChange) > 0) {
                latestChange = whenChange;
            }
        }


        if (!"19700916080801.0Z".equals(latestChange)) {
            //插入或者更新
            adUpdateTimeService.updateOrInsert(new AdUpdateTimeDomain(adInfoDomain.getIp(), latestChange));
        }



        //去重
        Set<String> set = new HashSet<String>();
        for (AdUserBufferDomain adUserBufferDomain : adUserBufferDomainList) {
            if (adUserBufferDomain == null) {
                continue;
            }
            String objectGUID = adUserBufferDomain.getObjectGUID();
            if (objectGUID != null) {
                if (!set.contains(objectGUID)) { //set中不包含重复的
                    set.add(objectGUID);
                    list.add(adUserBufferDomain);
                } else {
                    continue;
                }
            }
        }
        set.clear();
        return list;
    }


    private String getGUID(byte[] inArr) {
        StringBuilder guid = new StringBuilder();

        for (byte b : inArr) {
            StringBuilder dblByte = new StringBuilder(
                    Integer.toHexString(b & 0xff));
            if (dblByte.length() == 1) {
                guid.append("0");
            }
            guid.append(dblByte);
        }
        return guid.toString();
    }


    @Autowired
    private AdUserBufferService adUserBufferService;


    @Override
    public Map<String, Integer> handleLogic(List<AdUserBufferDomain> adUserBufferDomainList, String companyId) throws Exception {

        Map<String, Integer> map = new HashMap<>();
        //扫描到的所有数据量
        map.put("allData", adUserBufferDomainList.size());

        //已经存在于cipher_user_info,或者cipher_user_ad_buffer表中的数据
        List<UserInfoDomain> dbAdRepeatList = adUserBufferService.queryUserByBufferUser(adUserBufferDomainList, companyId);
         System.out.println("dbAdRepeatList====="+dbAdRepeatList);
        //所有数据都是新增数据
        if (CollectionUtils.isEmpty(dbAdRepeatList)) {
            logger.info("所有用户都是新增用户");
            //更新的数据条数
            map.put("update", 0);
            //新增的数据条数
            map.put("add", adUserBufferDomainList.size());
            //插入缓冲表
            adUserBufferService.insertBufferList(adUserBufferDomainList);
            return map;
        }


        /*字段更新和新增共存的情况，对于存在于cipher_user_ad_buffer中的数据，update,其余的，insert进buffer**/

        //查出存在于buffer的数据
        List<AdUserBufferDomain> inBufferUserList = adUserBufferService.queryInBufferList(dbAdRepeatList, companyId);
        //从所有字段更新的数据中，拆分出已经存在于buffer的数据，和已经存在于用户的数据
        List<AdUserBufferDomain> inBufferList = chaifen(adUserBufferDomainList, inBufferUserList);


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
        return map;
    }

    /**
     * @param allUser    所有字段更新的用户
     * @param bufferList 已经存在于缓冲表的accountNumber列表
     * @return 返回，已经存在于buffer的所有用户的信息,allUser只剩下未在缓冲中的用户
     */
    private List<AdUserBufferDomain> chaifen(List<AdUserBufferDomain> allUser, List<AdUserBufferDomain> bufferList) {
        List<AdUserBufferDomain> inBuffferUserList =  new ArrayList<AdUserBufferDomain>();
        Iterator<AdUserBufferDomain> iterator = allUser.iterator();
        while (iterator.hasNext()) {
            AdUserBufferDomain adUserBufferDomain = iterator.next();
            for (AdUserBufferDomain bufferDomain : bufferList) {
                if (adUserBufferDomain.getObjectGUID().equals(bufferDomain.getObjectGUID())) {
                    iterator.remove();
                    inBuffferUserList.add(adUserBufferDomain);
                    break;
                }
            }
        }


        return inBuffferUserList;
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
            if (StringUtils.isNotEmpty(userInfoDomain.getJob())&&StringUtils.isNotEmpty(adUserBufferDomain.getJob())&&!StringUtils.equals(userInfoDomain.getJob(), adUserBufferDomain.getJob())) {
                return false;
            }

          /*  //手机号不同
            if (!StringUtils.equals(userInfoDomain.getPhoneNumber(), adUserBufferDomain.getPhoneNumber())) {
                return false;
            }
*/
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




