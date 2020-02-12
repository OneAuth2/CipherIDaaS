package cipher.console.oidc.service.impl;/**
 * @author lqgzj
 * @date 2019-10-12
 */

import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.common.HttpKey;
import cipher.console.oidc.common.NewReturnUtils;
import cipher.console.oidc.common.WxUrlConstants;
import cipher.console.oidc.domain.web.*;
import cipher.console.oidc.mapper.*;
import cipher.console.oidc.model.LocalWxMapModel;
import cipher.console.oidc.service.UUIDService;
import cipher.console.oidc.service.WxConfigService;
import cipher.console.oidc.service.WxUserService;
import cipher.console.oidc.util.HttpsClientUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @Author qiaoxi
 * @Date 2019-10-1210:50
 **/

@Service
public class WxUserServiceImpl implements WxUserService {

    @Autowired
    private WxConfigService wxConfigService;

    @Autowired
    private WxUserBufferMapper wxUserBufferMapper;

    @Autowired
    private WxLocalUserBindingMapper wxLocalUserBindingMapper;

    @Autowired
    private UUIDService uuidService;
    @Autowired
    private GroupMapper groupMapper;


    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserGroupMapper userGroupMapper;


    private Logger logger = LoggerFactory.getLogger(WxUserServiceImpl.class);


    @Override
    public Map<String, Object> syncAllWxUser(String companyId) {
        logger.info("wx同步用户开始++++++++++++++++");
        WxConfigDomain config = wxConfigService.queryConfig(companyId);
        Integer matchRule = config.getMatchRule();
        if (matchRule == null) {
            return NewReturnUtils.failureResponse("匹配规则配置信息缺失!");
        }

        //1.查询到列表中的用户，并确定是待绑定状态还是新增状态
        List<WxUserDomain> wxUserDomainList = wxUserBufferMapper.queryListToSync(null, companyId, matchRule);
        Map<String, Object> map = syncWxUser2Local(wxUserDomainList, config, companyId);
        //5.删除已经同步的用户
        wxUserBufferMapper.deleteByCompanyIdList(companyId);
        return map;
    }

    @Override
    @Transactional
    public Map<String, Object> syncDepartWxUser(String companyId, List<String> useridList) throws Exception {
        WxConfigDomain config = wxConfigService.queryConfig(companyId);
        Integer matchRule = config.getMatchRule();
        if (matchRule == null) {
            return NewReturnUtils.failureResponse("匹配规则配置信息缺失!");
        }
        //1.查询到列表中的用户，并确定是待绑定状态还是新增状态
        List<WxUserDomain> wxUserDomainList = wxUserBufferMapper.queryListToSync(useridList, companyId, matchRule);
        Map<String, Object> map = syncWxUser2Local(wxUserDomainList, config, companyId);
        //5.删除已经同步的用户
        wxUserBufferMapper.deleteByIdList(useridList, companyId);
        return map;
    }

    private Map<String, Object> syncWxUser2Local(List<WxUserDomain> wxUserDomainList, WxConfigDomain config, String companyId) {
        if (CollectionUtils.isEmpty(wxUserDomainList)) {
            return NewReturnUtils.failureResponse("无数据!");
        }

        int syncLen = wxUserDomainList.size();
        //2.根据属性映射，确定字段对应关系
        List<WxLocalUserMapDomain> mapList = new ArrayList<>();

        //对于匹配到的(待绑定)数据，只需建立关联关系
        Iterator<WxUserDomain> iterator = wxUserDomainList.iterator();
        while (iterator.hasNext()) {
            WxUserDomain wxUserDomain = iterator.next();
            if (StringUtils.isNotEmpty(wxUserDomain.getStatus())) {
                mapList.add(new WxLocalUserMapDomain(wxUserDomain.getUserid(), wxUserDomain.getStatus(), companyId));
                iterator.remove();
            }
        }

        //企业微信用户和本地用户的字段映射
        String attrMap = config.getAttrMap();
        if (StringUtils.isEmpty(attrMap)) {
            return NewReturnUtils.failureResponse("字段映射配置缺失!");
        }

        List<LocalWxMapModel> localWxMapModels = JSON.parseArray(attrMap, LocalWxMapModel.class);
        logger.info(localWxMapModels.toString());
        localWxMapModels.add(new LocalWxMapModel("wxUserId", "userid"));
        localWxMapModels.add(new LocalWxMapModel("groupNames", "departmentStr"));
       // localWxMapModels.add(new LocalWxMapModel("accountNumber", "mobile"));

        //将缓冲表中的数据结构转换为cipher_user_info中的数据结构
        List<UserInfoDomain> userInfoDomains = convertWxUserToLocalUser(localWxMapModels, wxUserDomainList);

        if (CollectionUtils.isEmpty(userInfoDomains)) {
            //没有新增的，全是待绑定的，直接建立映射关系
            wxLocalUserBindingMapper.insertList(mapList);
            return NewReturnUtils.successResponse("sync_length", syncLen);
        }

        //用于记录导入的企业微信用户的组织结构关系
        List<GroupUserMapDomain> groupUserMapDomainList = new ArrayList<>();

        //3.建立企业微信和本地用户的关联关系，以及组织结构的映射
        userInfoDomains.forEach(userInfoDomain -> {
            userInfoDomain.setCompanyId(companyId);
            //uuid作为本地用户的主键
            String uuid = uuidService.getUUid();
            userInfoDomain.setUuid(uuid);
            mapList.add(new WxLocalUserMapDomain(userInfoDomain.getWxUserId(), uuid, companyId));
            if (StringUtils.isEmpty(userInfoDomain.getGroupNames())) {
                userInfoDomain.setGroupNames(WxUrlConstants.WX_ROOT_DEPT_ID.toString());
            }

            String departmentStr = userInfoDomain.getGroupNames();
            String[] wxGroups = departmentStr.split(",");
            for (String wxGroupId : wxGroups) {
                Integer groupId = groupMapper.getGroupIdByWxGroupId(wxGroupId, companyId);
                //如果找不到对应的组，则放根目录
                if (groupId == null) {
                    groupId = 0;
                }
                groupUserMapDomainList.add(new GroupUserMapDomain(groupId, uuid));
            }
        });

        //4.将新增的用户导入本地用户库
        userMapper.insertUserList(userInfoDomains);

        wxLocalUserBindingMapper.insertList(mapList);
        //   4.建立组织结构关系
        userGroupMapper.insertListWithUUid(groupUserMapDomainList);


        return NewReturnUtils.successResponse("sync_length", syncLen);
    }

    /**
     * @param configList 企业微信用户和本地用户的字段映射关系
     * @param userList   企业微信的用户列表
     * @return 本地用户列表
     */
    private List<UserInfoDomain> convertWxUserToLocalUser(List<LocalWxMapModel> configList, List<WxUserDomain> userList) {
        if (CollectionUtils.isEmpty(userList) || CollectionUtils.isEmpty(configList)) {
            return null;
        }

        List<UserInfoDomain> result = new ArrayList<>();

        for (WxUserDomain wxUserDomain : userList) {

            UserInfoDomain userInfoDomain = new UserInfoDomain();
            for (LocalWxMapModel wxMapModel : configList) {

                try {
                    Field wxField = wxUserDomain.getClass().getDeclaredField(wxMapModel.getWxKey());
                    wxField.setAccessible(true);
                    if (wxField.get(wxUserDomain) != null) {
                        Field localField = userInfoDomain.getClass().getDeclaredField(wxMapModel.getLocalKey());
                        localField.setAccessible(true);
                        localField.set(userInfoDomain, wxField.get(wxUserDomain));
                    }
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            result.add(userInfoDomain);

        }

        return result;
    }

    /**
     * 扫描企业微信用户信息
     *
     * @param companyUUid
     * @return
     */
    @Override
    public Map<String, Object> scanWxUser(String companyUUid) {
        logger.info("wx扫描用户开始+++++++++++");
        List<WxUserDomain> allWxUser = getAllWxUser(companyUUid);

        if (CollectionUtils.isEmpty(allWxUser)) {
            return NewReturnUtils.successResponse("add", 0);
        }

        //1.去掉已经存在于用户表(通过关联表确定)的用户
        List<String> inMappingUser = wxLocalUserBindingMapper.queryAlreDyInBindUser(allWxUser);
        allWxUser = deleteFromList(allWxUser, inMappingUser);

        if (!CollectionUtils.isEmpty(allWxUser)) {
            //2.去掉已经存在于缓冲表的用户
            List<String> inBufferUser = wxUserBufferMapper.queryAlredyInBufferUser(allWxUser, companyUUid);
            allWxUser = deleteFromList(allWxUser, inBufferUser);
        }

        //3.入库
        allWxUser.forEach(wxUserDomain -> {
            wxUserDomain.setCompanyId(companyUUid);
            //部门数据处理，将[]去掉
            wxUserDomain.setDepartment(StringUtils.strip(wxUserDomain.getDepartment(), "[]"));
        });
        if (CollectionUtils.isEmpty(allWxUser)) {
            return NewReturnUtils.successResponse("add", 0);
        }
        wxUserBufferMapper.insert(allWxUser);

        return NewReturnUtils.successResponse("add", allWxUser.size());
    }


    /**
     * 获取企业微信用户
     *
     * @param companyId 公司id
     * @return
     */
    @Override
    public List<WxUserDomain> getAllWxUser(String companyId) {
        //1.获取企业微信所有部门
        List<WxSimpleGroup> allWxGroup = getAllWxGroup(companyId);

        //获取根部门的用户
        List<WxUserDomain> userDomainList = getUserFromWx(companyId, WxUrlConstants.WX_ROOT_DEPT_ID);

        //将根部门用户保存
        List<WxUserDomain> wxUserList = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(userDomainList)) {
            wxUserList.addAll(userDomainList);
        }

        if (CollectionUtils.isEmpty(allWxGroup)) {
            return wxUserList;
        }

        /************引入线程池优化******************/
        ExecutorService executorService = Executors.newFixedThreadPool(50);


        List<Future<List<WxUserDomain>>> futureList = new ArrayList<>();

        //递归获取所有部门的用户
        for (WxSimpleGroup wxSimpleGroup : allWxGroup) {

            futureList.add(executorService.submit(() -> getUserFromWx(companyId, wxSimpleGroup.getId())));

//            List<DingUserDomain> userFromDing = getUserFromDing(companyId, dingSimpleGroup.getId());
//            if (CollectionUtils.isEmpty(userFromDing)) {
//                continue;
//            }
//            dingUserList.addAll(userFromDing);
        }

        if (CollectionUtils.isEmpty(futureList)) {
            return wxUserList;
        }

        for (Future<List<WxUserDomain>> future : futureList) {
            List<WxUserDomain> wxUserDomainList = null;
            try {
                wxUserDomainList = future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            if (CollectionUtils.isNotEmpty(wxUserDomainList)) {
                wxUserList.addAll(wxUserDomainList);
            }
        }

        executorService.shutdown();

        return wxUserList;
    }

    /**
     * API请求企业微信用户
     *
     * @param companyId 公司id
     * @return
     */
    @Override
    public List<WxSimpleGroup> getAllWxGroup(String companyId) {
        String accessToken = wxConfigService.getAccessTokenByCompanyUUid(companyId);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("access_token=").append(accessToken)
                .append("&id=").append(WxUrlConstants.WX_ROOT_DEPT_ID);
        Map<String, Object> map = HttpsClientUtils.doGetWithNoHeader(WxUrlConstants.DEPT_URL, null, null, stringBuilder.toString());
        String json = (String) map.get(HttpKey.RES);
        JSONObject jsonObject = JSON.parseObject(json);
        String department = jsonObject.getString("department");
        if (StringUtils.isEmpty(department)) {
            return null;
        }

        return JSON.parseArray(department, WxSimpleGroup.class);
    }

    /**
     * 获取企业微信某个部门下所有的用户
     *
     * @param companyId
     * @param departMentId 部门Id
     * @return
     */
    @Override
    public List<WxUserDomain> getUserFromWx(String companyId, Long departMentId) {
        String json = doGetUserForWx(companyId, departMentId);
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        JSONObject jsonObject = JSON.parseObject(json);
        Integer errorCode = jsonObject.getInteger(WxUrlConstants.ERR_CODE_KEY);
        //返回状态码不正确
        if (!errorCode.equals(WxUrlConstants.REQUEST_SUCESS)) {
            return null;
        }

        String userList = jsonObject.getString(WxUrlConstants.USER_LIST_KEY);

        if (StringUtils.isEmpty(userList)) {
            return null;
        }

        List<WxUserDomain> userDomainList = JSON.parseArray(userList, WxUserDomain.class);

        //某部门下所有用户的集合
        List<WxUserDomain> wxUserList = new ArrayList<>(userDomainList);

//        while (jsonObject.getBoolean(WxUrlConstants.HAS_MORE_KEY)) {
//
//            String s = doGetUserForWx(companyId, departMentId,);
//            if (StringUtils.isEmpty(s)) {
//                break;
//            }
//            jsonObject = JSON.parseObject(s);
//            errorCode = jsonObject.getInteger(WxUrlConstants.ERR_CODE_KEY);
//
//            if (!errorCode.equals(WxUrlConstants.REQUEST_SUCESS)) {
//                break;
//            }
//            userList = jsonObject.getString(WxUrlConstants.USER_LIST_KEY);
//
//            if (StringUtils.isEmpty(userList)) {
//                break;
//            }
//
//            userDomainList = JSON.parseArray(userList, WxUserDomain.class);
//            wxUserList.addAll(userDomainList);
//        }

        return wxUserList;
    }

    private String doGetUserForWx(String companyId, Long departMentId) {
        String accessToken = wxConfigService.getAccessTokenByCompanyUUid(companyId);

        Map<String, Object> map = HttpsClientUtils.doGetWithNoHeader(WxUrlConstants.WX_USER_URL, null, null
                , getParam(accessToken, departMentId));
        return (String) map.get(HttpKey.RES);
    }

    /**
     * @param accessToken accessToken
     * @param depId       部门id
     * @return 返回获取企业微信的用户列表的get参数
     */
    private String getParam(String accessToken, Long depId) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("access_token=").append(accessToken)
                .append("&department_id=").append(depId).append("&fetch_child").append(0);
        return stringBuilder.toString();
    }


    /**
     * @param dataGridModel 分页参数
     * @param companyUUid   公司id
     * @param status        筛选条件:0-全部，1-新增，2-待绑定，默认全部
     * @param queryStr      根据姓名参数
     * @return
     */
    @Override
    public Map<String, Object> queryBufferWxList(DataGridModel dataGridModel, String companyUUid, Integer status, String queryStr) {
        WxConfigDomain wxConfigDomain = wxConfigService.queryConfig(companyUUid);
        List<WxUserDomain> wxUserDomainList = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        int count = 0;
        if (wxConfigDomain != null) {
            Integer matchRule = wxConfigDomain.getMatchRule();
            if (matchRule == null) {
                logger.error("配置信息缺失!");
                return null;
            }

            wxUserDomainList = wxUserBufferMapper.queryList(status, companyUUid, dataGridModel, queryStr, matchRule);
            //设置状态
            wxUserDomainList.forEach(wxUserDomain -> {
                if (StringUtils.isEmpty(wxUserDomain.getStatus())) {
                    wxUserDomain.setStatus("新增");
                } else {
                    wxUserDomain.setStatus("待绑定");
                }
            });
            count = wxUserBufferMapper.queryListCount(status, companyUUid, dataGridModel, queryStr, matchRule);
        }
        map.put("rows", wxUserDomainList);
        map.put("total", count);
        return map;

    }

    /**
     * @param list       企业微信用户列表
     * @param targetList 已经存在的部分
     * @return 去掉存在的部分后的数据
     */
    private List<WxUserDomain> deleteFromList(List<WxUserDomain> list, List<String> targetList) {

        if (CollectionUtils.isEmpty(list) || CollectionUtils.isEmpty(targetList)) {
            return list;
        }

        Iterator<WxUserDomain> iterator = list.iterator();
        while (iterator.hasNext()) {
            WxUserDomain wxUserDomain = iterator.next();
            for (String userId : targetList) {
                if (userId.equals(wxUserDomain.getUserid())) {
                    iterator.remove();
                    break;
                }
            }
        }
        return list;
    }
}
