package cipher.console.oidc.service.impl;

import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.common.DingUrlConstants;
import cipher.console.oidc.common.HttpKey;
import cipher.console.oidc.common.NewReturnUtils;
import cipher.console.oidc.domain.web.*;
import cipher.console.oidc.mapper.*;
import cipher.console.oidc.model.LocalDingMapModel;
import cipher.console.oidc.service.DingConfigService;
import cipher.console.oidc.service.DingUserService;
import cipher.console.oidc.service.UUIDService;
import cipher.console.oidc.service.UserService;
import cipher.console.oidc.util.HttpsClientUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
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


@Service
public class DingUserServiceImpl implements DingUserService {
    @Autowired
    private DingUserMapper dingUserMapper;

    @Autowired
    private DingConfigService dingConfigService;

    @Autowired
    private DingLocalUserBindingMapper dingLocalUserBindingMapper;

    @Autowired
    private DingUserBufferMapper dingUserBufferMapper;

    @Autowired
    private UUIDService uuidService;


    private Logger logger = LoggerFactory.getLogger(DingUserServiceImpl.class);

    /**
     * add by ly
     *
     * @param uuid
     */
    @Override
    public void deleteUserMap(String uuid) {
        dingUserMapper.deleteUserMap(uuid);
    }

    @Override
    public List<DingUserDomain> getUserFromDing(String companyId, Long departMentId) {

        Integer offset = 0;

        String json = doGetUserForDing(companyId, departMentId, offset);
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        JSONObject jsonObject = JSON.parseObject(json);
        Integer errorCode = jsonObject.getInteger(DingUrlConstants.ERR_CODE_KEY);
        //返回状态码不正确
        if (!errorCode.equals(DingUrlConstants.REQUEST_SUCESS)) {
            return null;
        }

        String userList = jsonObject.getString(DingUrlConstants.USER_LIST_KEY);

        if (StringUtils.isEmpty(userList)) {
            return null;
        }

        List<DingUserDomain> userDomainList = JSON.parseArray(userList, DingUserDomain.class);

        //某部门下所有用户的集合
        List<DingUserDomain> dingUserList = new ArrayList<>(userDomainList);

        while (jsonObject.getBoolean(DingUrlConstants.HAS_MORE_KEY)) {

            String s = doGetUserForDing(companyId, departMentId, offset++);
            if (StringUtils.isEmpty(s)) {
                break;
            }
            jsonObject = JSON.parseObject(s);
            errorCode = jsonObject.getInteger(DingUrlConstants.ERR_CODE_KEY);

            if (!errorCode.equals(DingUrlConstants.REQUEST_SUCESS)) {
                break;
            }
            userList = jsonObject.getString(DingUrlConstants.USER_LIST_KEY);

            if (StringUtils.isEmpty(userList)) {
                break;
            }

            userDomainList = JSON.parseArray(userList, DingUserDomain.class);
            dingUserList.addAll(userDomainList);
        }

        return dingUserList;
    }


    private String doGetUserForDing(String companyId, Long departMentId, Integer offset) {
        String accessToken = dingConfigService.getAccessTokenByCompanyUUid(companyId);

        Map<String, Object> map = HttpsClientUtils.doGetWithNoHeader(DingUrlConstants.DING_USER_URL, null, null
                , getParam(accessToken, departMentId, DingUrlConstants.OFFSET, DingUrlConstants.SIZE));
        return (String) map.get(HttpKey.RES);
    }

    @Override
    public List<DingSimpleGroup> getAllDingGroup(String companyId) {
        String accessToken = dingConfigService.getAccessTokenByCompanyUUid(companyId);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("access_token=").append(accessToken)
                .append("&id=").append(DingUrlConstants.DING_ROOT_DEPT_ID);

        Map<String, Object> map = HttpsClientUtils.doGetWithNoHeader(DingUrlConstants.DEPT_URL, null, null, stringBuilder.toString());
        String json = (String) map.get(HttpKey.RES);
        JSONObject jsonObject = JSON.parseObject(json);
        String department = jsonObject.getString("department");
        if (StringUtils.isEmpty(department)) {
            return null;
        }

        return JSON.parseArray(department, DingSimpleGroup.class);
    }

    @Override
    public List<DingUserDomain> getAllDingUser(String companyId) {

        //1.获取钉钉所有部门
        List<DingSimpleGroup> allDingGroup = getAllDingGroup(companyId);

        //获取根部门的用户
        List<DingUserDomain> userDomainList = getUserFromDing(companyId, DingUrlConstants.DING_ROOT_DEPT_ID);


        //将根部门用户保存
        List<DingUserDomain> dingUserList = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(userDomainList)) {
            dingUserList.addAll(userDomainList);
        }

        if (CollectionUtils.isEmpty(allDingGroup)) {
            return dingUserList;
        }

        /************引入线程池优化******************/
        ExecutorService executorService = Executors.newFixedThreadPool(50);


        List<Future<List<DingUserDomain>>> futureList = new ArrayList<>();

        //递归获取所有部门的用户
        for (DingSimpleGroup dingSimpleGroup : allDingGroup) {

            futureList.add(executorService.submit(() -> getUserFromDing(companyId, dingSimpleGroup.getId())));

//            List<DingUserDomain> userFromDing = getUserFromDing(companyId, dingSimpleGroup.getId());
//            if (CollectionUtils.isEmpty(userFromDing)) {
//                continue;
//            }
//            dingUserList.addAll(userFromDing);
        }

        if(CollectionUtils.isEmpty(futureList)){
            return dingUserList;
        }

        for(Future<List<DingUserDomain>> future:futureList){
            List<DingUserDomain> dingUserDomainList = null;
            try {
                dingUserDomainList = future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            if(CollectionUtils.isNotEmpty(dingUserDomainList)){
                dingUserList.addAll(dingUserDomainList);
            }
        }

        executorService.shutdown();

        return dingUserList;

    }


    /**
     * @param accessToken accessToken
     * @param depId       部门id
     * @param offset      偏移量
     * @param size        页码
     * @return 返回获取钉钉的用户列表的get参数
     */
    private String getParam(String accessToken, Long depId, Integer offset, Integer size) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("access_token=").append(accessToken)
                .append("&department_id=").append(depId)
                .append("&offset=").append(offset)
                .append("&size=").append(size);

        return stringBuilder.toString();
    }


    @Override
    public Map<String, Object> syncDingUser(String companyId) {
        logger.info("钉钉扫描用户开始++++++++++++++++");
        List<DingUserDomain> allDingUser = getAllDingUser(companyId);

        if (CollectionUtils.isEmpty(allDingUser)) {
            return NewReturnUtils.successResponse("add", 0);
        }

        //TODO 属性编辑本期(1.5.4)未处理

        //1.去掉已经存在于用户表(通过关联表确定)的用户
        List<String> inMappingUser = dingLocalUserBindingMapper.queryAlreDyInBindUser(allDingUser);
        allDingUser = deleteFromList(allDingUser, inMappingUser);


        if (!CollectionUtils.isEmpty(allDingUser)) {
            //2.去掉已经存在于缓冲表的用户
            List<String> inBufferUser = dingUserBufferMapper.queryAlredyInBufferUser(allDingUser, companyId);
            allDingUser = deleteFromList(allDingUser, inBufferUser);
        }

        //3.入库
        allDingUser.forEach(dingUserDomain -> dingUserDomain.setCompanyId(companyId));
        if (CollectionUtils.isEmpty(allDingUser)) {
            return NewReturnUtils.successResponse("add", 0);
        }
        dingUserBufferMapper.insert(allDingUser);

        return NewReturnUtils.successResponse("add", allDingUser.size());
    }


    /**
     * @param list       钉钉用户列表
     * @param targetList 已经存在的部分
     * @return 去掉存在的部分后的数据
     */
    private List<DingUserDomain> deleteFromList(List<DingUserDomain> list, List<String> targetList) {

        if (CollectionUtils.isEmpty(list) || CollectionUtils.isEmpty(targetList)) {
            return list;
        }

        Iterator<DingUserDomain> iterator = list.iterator();
        while (iterator.hasNext()) {
            DingUserDomain dingUserDomain = iterator.next();
            for (String userId : targetList) {
                if (userId.equals(dingUserDomain.getUserid())) {
                    iterator.remove();
                    break;
                }
            }
        }
        return list;
    }


    /**
     * @param dataGridModel 分页参数
     * @param companyId     公司id
     * @param status        筛选条件:0-全部，1-新增，2-待绑定，默认全部
     * @param queryStr      根据姓名参数
     * @return
     */
    @Override
    public Map<String, Object> queryBufferDingList(DataGridModel dataGridModel,
                                                   String companyId,
                                                   Integer status,
                                                   String queryStr) {

        DingConfigDomain dingConfigDomain = dingConfigService.queryConfig(companyId);
        List<DingUserDomain> dingUserDomainList = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        int count = 0;
        if(dingConfigDomain!=null){
            Integer matchRule = dingConfigDomain.getMatchRule();
            if (matchRule == null) {
                logger.error("配置信息缺失!");
                return null;
            }
            //TODO 编辑本期(1.5.4)未考虑

            dingUserDomainList = dingUserBufferMapper.queryList(status, companyId, dataGridModel, queryStr, matchRule);
            //设置状态
            dingUserDomainList.forEach(dingUserDomain -> {
                if (StringUtils.isEmpty(dingUserDomain.getStatus())) {
                    dingUserDomain.setStatus("新增");
                } else {
                    dingUserDomain.setStatus("待绑定");
                }
            });
            count = dingUserBufferMapper.queryListCount(status, companyId, dataGridModel, queryStr, matchRule);
        }
        map.put("rows", dingUserDomainList);
        map.put("total", count);
        return map;

    }


    @Autowired
    private GroupMapper groupMapper;


    @Autowired
    private UserGroupMapper userGroupMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional
    public Map<String, Object> syncDepartDingUser(String companyId, List<String> userIdList) throws Exception {

        DingConfigDomain config = dingConfigService.queryConfig(companyId);
        Integer matchRule = config.getMatchRule();
        if (matchRule == null) {
            return NewReturnUtils.failureResponse("匹配规则配置信息缺失!");
        }
        //1.查询到列表中的用户，并确定是待绑定状态还是新增状态
        List<DingUserDomain> dingUserDomainList = dingUserBufferMapper.queryListToSync(userIdList, companyId, matchRule);
        Map<String, Object> map = syncDingUser2Local(dingUserDomainList, config, companyId);
        //5.删除已经同步的用户
        dingUserBufferMapper.deleteByIdList(userIdList, companyId);
        return map;
    }

    @Override
    public Map<String, Object> syncAllDingUser(String companyId) throws Exception {
        logger.info("钉钉同步用户开始++++++++++++++++");
        DingConfigDomain config = dingConfigService.queryConfig(companyId);
        Integer matchRule = config.getMatchRule();
        if (matchRule == null) {
            return NewReturnUtils.failureResponse("匹配规则配置信息缺失!");
        }

        //1.查询到列表中的用户，并确定是待绑定状态还是新增状态
        List<DingUserDomain> dingUserDomainList = dingUserBufferMapper.queryListToSync(null, companyId, matchRule);
        Map<String, Object> map = syncDingUser2Local(dingUserDomainList, config, companyId);
        //5.删除已经同步的用户
        dingUserBufferMapper.deleteByCompanyIdList(companyId);
        return map;
    }


    private Map<String, Object> syncDingUser2Local(List<DingUserDomain> dingUserDomainList, DingConfigDomain config, String companyId) {
        if (CollectionUtils.isEmpty(dingUserDomainList)) {
            return NewReturnUtils.failureResponse("无数据!");
        }

        int syncLen = dingUserDomainList.size();
        //2.根据属性映射，确定字段对应关系
        List<DingLocalUserMapDomain> mapList = new ArrayList<>();

        //对于匹配到的(待绑定)数据，只需建立关联关系
        Iterator<DingUserDomain> iterator = dingUserDomainList.iterator();
        while (iterator.hasNext()) {
            DingUserDomain dingUserDomain = iterator.next();
            if (StringUtils.isNotEmpty(dingUserDomain.getStatus())) {
                mapList.add(new DingLocalUserMapDomain(dingUserDomain.getUserid(), dingUserDomain.getStatus(), dingUserDomain.getUnionid(), companyId));
                iterator.remove();
            }
        }

        //钉钉用户和本地用户的字段映射
        String attrMap = config.getAttrMap();
        if (StringUtils.isEmpty(attrMap)) {
            return NewReturnUtils.failureResponse("字段映射配置缺失!");
        }

        List<LocalDingMapModel> localDingMapModels = JSON.parseArray(attrMap, LocalDingMapModel.class);
        localDingMapModels.add(new LocalDingMapModel("dingUserId", "userid"));
        localDingMapModels.add(new LocalDingMapModel("groupNames", "departmentStr"));
        localDingMapModels.add(new LocalDingMapModel("accountNumber", "mobile"));
        localDingMapModels.add(new LocalDingMapModel("dingUnionId", "unionid"));

        //将缓冲表中的数据结构转换为cipher_user_info中的数据结构
        List<UserInfoDomain> userInfoDomains = convertDingUserToLocalUser(localDingMapModels, dingUserDomainList);

        if (CollectionUtils.isEmpty(userInfoDomains)) {
            //没有新增的，全是待绑定的，直接建立映射关系
            dingLocalUserBindingMapper.insertList(mapList);
            return NewReturnUtils.successResponse("sync_length", syncLen);
        }

        //用于记录导入的钉钉用户的组织结构关系
        List<GroupUserMapDomain> groupUserMapDomainList = new ArrayList<>();

        //3.建立钉钉和本地用户的关联关系，以及组织结构的映射
        userInfoDomains.forEach(userInfoDomain -> {
            userInfoDomain.setCompanyId(companyId);
            //uuid作为本地用户的主键
            String uuid = uuidService.getUUid();
            userInfoDomain.setUuid(uuid);
            mapList.add(new DingLocalUserMapDomain(userInfoDomain.getDingUserId(), uuid, userInfoDomain.getDingUnionId(), companyId));
            if (StringUtils.isEmpty(userInfoDomain.getGroupNames())) {
                userInfoDomain.setGroupNames(DingUrlConstants.DING_ROOT_DEPT_ID.toString());
            }

            String departmentStr = userInfoDomain.getGroupNames();


            String[] dingGroups = departmentStr.split(",");
            for (String dingGroupId : dingGroups) {
                Integer groupId = groupMapper.getGroupIdByDingGroupId(dingGroupId, companyId);
                //如果找不到对应的组，则放根目录
                if (groupId == null) {
                    groupId = 0;
                }
                groupUserMapDomainList.add(new GroupUserMapDomain(groupId, uuid));
            }
        });

        //4.将新增的用户导入本地用户库
        userMapper.insertUserList(userInfoDomains);

        dingLocalUserBindingMapper.insertList(mapList);
        //4.建立组织结构关系
        userGroupMapper.insertListWithUUid(groupUserMapDomainList);


        return NewReturnUtils.successResponse("sync_length", syncLen);
    }


    /**
     * @param configList 钉钉用户和本地用户的字段映射关系
     * @param userList   钉钉的用户列表
     * @return 本地用户列表
     */
    private List<UserInfoDomain> convertDingUserToLocalUser(List<LocalDingMapModel> configList, List<DingUserDomain> userList) {
        if (CollectionUtils.isEmpty(userList) || CollectionUtils.isEmpty(configList)) {
            return null;
        }

        List<UserInfoDomain> result = new ArrayList<>();

        for (DingUserDomain dingUserDomain : userList) {

            UserInfoDomain userInfoDomain = new UserInfoDomain();
            for (LocalDingMapModel dingMapModel : configList) {

                try {
                    Field dingField = dingUserDomain.getClass().getDeclaredField(dingMapModel.getDingKey());
                    dingField.setAccessible(true);
                    if (dingField.get(dingUserDomain) != null) {
                        Field localField = userInfoDomain.getClass().getDeclaredField(dingMapModel.getLocalKey());
                        localField.setAccessible(true);
                        localField.set(userInfoDomain, dingField.get(dingUserDomain));
                    }

                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            result.add(userInfoDomain);

        }

        return result;
    }


}
