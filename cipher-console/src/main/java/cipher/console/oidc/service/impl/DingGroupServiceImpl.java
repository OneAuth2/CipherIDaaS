package cipher.console.oidc.service.impl;

import cipher.console.oidc.common.*;
import cipher.console.oidc.domain.web.DingGroupDomain;
import cipher.console.oidc.domain.web.GroupInfoDomain;
import cipher.console.oidc.domain.web.GroupTreeDomain;
import cipher.console.oidc.mapper.DingGroupBufferMapper;
import cipher.console.oidc.mapper.DingGroupMapper;
import cipher.console.oidc.mapper.GroupMapper;
import cipher.console.oidc.service.DingConfigService;
import cipher.console.oidc.service.DingGroupService;
import cipher.console.oidc.util.HttpsClientUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.omg.PortableServer.LIFESPAN_POLICY_ID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Author: zt
 * @Date: 2019-05-11 15:34
 */
@Service
public class DingGroupServiceImpl implements DingGroupService {

    @Autowired
    private GroupMapper groupMapper;

    @Autowired
    private DingConfigService dingConfigService;

    @Autowired
    private DingGroupMapper dingGroupMapper;

    @Autowired
    private DingGroupBufferMapper dingGroupBufferMapper;

    private Logger logger = LoggerFactory.getLogger(DingGroupServiceImpl.class);

    @Override
    public List<GroupTreeDomain> getGroupTreeList(String companyId) {
        List<GroupTreeDomain> list = groupMapper.queryAllGroupToTree(companyId);
        return constructGroupTreeList(list);
    }

    @Override
    public Map<String, Object> scanDingGroup(String companyId) {

        //从钉钉导入的组织结构
        List<DingGroupDomain> list = getGroupFromDing(companyId);

        if (CollectionUtils.isEmpty(list)) {
            return NewReturnUtils.successResponse("add", 0);
        }

        list.forEach(dingGroupDomain -> dingGroupDomain.setCompanyId(companyId));

        //从数据库中查询出已经存在于cipher_group_info的组
        List<Long> inDbGroupList = dingGroupMapper.queryAlredyExistsGroup(list);

        //TODO 对于编辑的过的group在这处理
        list = deleteFromList(list, inDbGroupList);

        if (CollectionUtils.isEmpty(list)) {
            return NewReturnUtils.successResponse("add", 0);
        }

        //查出已经存在于缓冲表的数据
        List<Long> idList = dingGroupBufferMapper.queryIdList(list);
        list = deleteFromList(list, idList);

        //新增了0个钉钉的group
        if (CollectionUtils.isEmpty(list)) {
            return NewReturnUtils.successResponse("add", 0);
        }

        dingGroupBufferMapper.insert(list);

        return NewReturnUtils.successResponse("add", list.size());
    }

    /**
     * 从dingGroupDomainList中取出已经存在于idList中的数据
     *
     * @param dingGroupDomainList
     * @param idList
     */
    private List<DingGroupDomain> deleteFromList(List<DingGroupDomain> dingGroupDomainList, List<Long> idList) {
        if (CollectionUtils.isEmpty(dingGroupDomainList) || CollectionUtils.isEmpty(idList)) {
            return dingGroupDomainList;
        }

        Iterator<DingGroupDomain> iterator = dingGroupDomainList.iterator();
        while (iterator.hasNext()) {
            DingGroupDomain dingGroupDomain = iterator.next();
            for (Long id : idList) {
                if (id.equals(dingGroupDomain.getId())) {
                    iterator.remove();
                    break;
                }
            }
        }
        return dingGroupDomainList;
    }

    @Override
    public List<DingGroupDomain> getGroupFromDing(String companyId) {
        String token = dingConfigService.getAccessTokenByCompanyUUid(companyId);
        if (StringUtils.isEmpty(token)) {
            logger.error("配置信息缺失！");
            return null;
        }

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("access_token=").append(token)
                //是否递归获取所有部门
                .append("&fetch_child=").append(true)
                //父部门id（如果不传，默认部门为根部门，根部门ID为1
                .append("&id=").append("1");

        Map<String, Object> map = HttpsClientUtils.doGetWithNoHeader(DingUrlConstants.GROUP_SYNC_URL, null, null, stringBuilder.toString());
        String json = (String) map.get(HttpKey.RES);
        JSONObject jsonObject = JSON.parseObject(json);
        if (!jsonObject.getInteger(DingUrlConstants.ERR_CODE_KEY).equals(0)) {
            logger.error("获取部门出错!:" + jsonObject.getString(DingUrlConstants.ERR_MSG_KEY));
            return null;
        }
        String department = jsonObject.getString("department");
        return JSON.parseArray(department, DingGroupDomain.class);
    }

    @Override
    public Map<String, Object> getDingGroupList(DataGridModel dataGridModel, String companyId) {
        Map<String, Object> map = new HashMap<>();

        List<DingGroupDomain> list = dingGroupBufferMapper.queryList(dataGridModel, companyId);
        //TODO 编辑的状态，这期（1.5.4）未处理
        list.forEach(dingGroupDomain -> dingGroupDomain.setStatus("新增"));

        list = constructPath(list);
        int count = dingGroupBufferMapper.queryCountByCompany(companyId);

        map.put("rows", list);
        map.put("total", count);
        return map;
    }


    @Override
    @Transactional
    public Map<String, Object> sync(String companyId) throws Exception {
        List<DingGroupDomain> list = dingGroupBufferMapper.queryGroupByCompany(companyId);
        int syncLen = list.size();
        insertGroup(list, companyId);
        //清空已经同步的数据
        dingGroupBufferMapper.delete(companyId);

        return NewReturnUtils.successResponse("sync_length", syncLen);
    }

    private void insertGroup(List<DingGroupDomain> list, String comapnyId) {

        int flag = 0;

        while (list.size() > 0) {

            Iterator<DingGroupDomain> iterator = list.iterator();

            while (iterator.hasNext()) {
                DingGroupDomain dingGroupDomain = iterator.next();

                if (dingGroupDomain.getParentid().equals(1L)) {
                    dingGroupDomain.setParentGroupId(0);
                    groupMapper.insertGroupByDingGroup(dingGroupDomain);
                    iterator.remove();
                    continue;
                }
                //从数据库获取dingGroupDomain.的parentGroupId
//                Integer parentGroupId = queryParentGroupId(dingGroupDomain.getParentid(), allGroup);
                Integer parentGroupId = groupMapper.queryGroupIdBYDingId(dingGroupDomain.getParentid());
                if (parentGroupId != null) {
                    dingGroupDomain.setParentGroupId(parentGroupId);
                    groupMapper.insertGroupByDingGroup(dingGroupDomain);
                    iterator.remove();
                }
            }

            flag++;

            /**
             * 防止异常清空，遍历超过10级还没结束，则认为是异常清空
             */
            if (flag >= 10) {
                logger.error("手动终止同步过程");
                throw new RuntimeException("手动终止，结构超过10级");
            }
        }

    }


    /**
     * 构造路径
     *
     * @param list
     * @return
     */
    private List<DingGroupDomain> constructPath(List<DingGroupDomain> list) {

        for (DingGroupDomain dingGroupDomain : list) {

            Long groupId = dingGroupDomain.getId();

            while (!groupId.equals(1L)) {
                DingGroupDomain tmpDing = queryParentName(groupId, list);
                if (tmpDing == null) {
                    break;
                }
                groupId = tmpDing.getParentid();
                dingGroupDomain.constructPath(tmpDing.getName());
            }
        }

        return list;
    }

    private DingGroupDomain queryParentName(Long parentId, List<DingGroupDomain> list) {
        for (DingGroupDomain dingGroupDomain : list) {
            if (dingGroupDomain.getId().equals(parentId)) {
                return dingGroupDomain;
            }
        }
        return null;
    }


    /**
     * 构造部门树结构
     *
     * @param list
     * @return
     */
    private static List<GroupTreeDomain> constructGroupTreeList(List<GroupTreeDomain> list) {
        List<GroupTreeDomain> result = new ArrayList<>();

        //1.查找到根节点
        list.forEach(groupTreeDomain -> {
            if (groupTreeDomain.getParentGroupId() == 0) {
                result.add(groupTreeDomain);
            }
        });

        //2.递归查找子节点
        for (GroupTreeDomain parent : result) {
            queryChildNodeByParent(parent, list);
        }

        return result;
    }

    /**
     * 根据部门查找子部门
     *
     * @param parent
     * @param list
     */
    private static void queryChildNodeByParent(GroupTreeDomain parent, List<GroupTreeDomain> list) {

        for (GroupTreeDomain groupTreeDomain : list) {
            if (parent.getGroupId().equals(groupTreeDomain.getParentGroupId())) {
                queryChildNodeByParent(groupTreeDomain,list);
                parent.getNodes().add(groupTreeDomain);
            }
        }
    }


    public static void main(String[] args) {
        List<GroupTreeDomain> list = new ArrayList<>();
        GroupTreeDomain groupTreeDomain1 = new GroupTreeDomain();
        groupTreeDomain1.setParentGroupId(0);
        groupTreeDomain1.setGroupId(10);
        groupTreeDomain1.setGroupName("顶级部门");
        list.add(groupTreeDomain1);


        GroupTreeDomain groupTreeDomain2 = new GroupTreeDomain();
        groupTreeDomain2.setParentGroupId(10);
        groupTreeDomain2.setGroupId(20);
        groupTreeDomain2.setGroupName("2部门");
        list.add(groupTreeDomain2);

        GroupTreeDomain groupTreeDomain3 = new GroupTreeDomain();
        groupTreeDomain3.setParentGroupId(20);
        groupTreeDomain3.setGroupId(30);
        groupTreeDomain3.setGroupName("3级部门");
        list.add(groupTreeDomain3);

        List<GroupTreeDomain> res = constructGroupTreeList(list);
        System.err.println(JSON.toJSONString(res));



    }


}
