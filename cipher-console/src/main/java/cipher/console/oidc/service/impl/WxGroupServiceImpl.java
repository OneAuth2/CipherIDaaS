package cipher.console.oidc.service.impl;/**
 * @author lqgzj
 * @date 2019-10-11
 */

import cipher.console.oidc.common.*;
import cipher.console.oidc.domain.web.WxGroupDomain;
import cipher.console.oidc.mapper.GroupMapper;
import cipher.console.oidc.mapper.WxGroupBufferMapper;
import cipher.console.oidc.mapper.WxGroupMapper;
import cipher.console.oidc.service.WxConfigService;
import cipher.console.oidc.service.WxGroupService;
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

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @Author qiaoxi
 * @Date 2019-10-1115:06
 **/
@Service
public class WxGroupServiceImpl implements WxGroupService {

    @Autowired
    private WxGroupBufferMapper wxGroupBufferMapper;

    @Autowired
    private WxGroupMapper wxGroupMapper;

    @Autowired
    private WxConfigService wxConfigService;

    @Autowired
    private GroupMapper groupMapper;

    private Logger logger = LoggerFactory.getLogger(WxGroupServiceImpl.class);

    /*
    同步企业微信部门
     */
    @Override
    @Transactional
    public Map<String, Object> sync(String companyId)throws Exception {
        List<WxGroupDomain> list = wxGroupBufferMapper.queryGroupByCompany(companyId);
        int syncLen = list.size();
        insertGroup(list, companyId);
        //清空已经同步的数据
        wxGroupBufferMapper.delete(companyId);

        return NewReturnUtils.successResponse("sync_length", syncLen);
    }

    /*
        从企业微信组织结构中获取group
         */
    @Override
    public List<WxGroupDomain> getGroupFromWx(String companyUUid) {
        String token = wxConfigService.getAccessTokenByCompanyUUid(companyUUid);
        if (StringUtils.isEmpty(token)) {
            logger.error("配置信息缺失！");
            return null;
        }

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("access_token=").append(token)
                //父部门id（如果不传，默认部门为根部门，根部门ID为1
                .append("&id=").append("1");

        Map<String, Object> map = HttpsClientUtils.doGetWithNoHeader(WxUrlConstants.GROUP_SYNC_URL, null, null, stringBuilder.toString());
        String json = (String) map.get(HttpKey.RES);
        JSONObject jsonObject = JSON.parseObject(json);
        if (!jsonObject.getInteger(WxUrlConstants.ERR_CODE_KEY).equals(0)) {
            logger.error("获取部门出错!:" + jsonObject.getString(WxUrlConstants.ERR_MSG_KEY));
            return null;
        }
        String department = jsonObject.getString("department");
        return JSON.parseArray(department, WxGroupDomain.class);
    }

    /**
     * 扫描企业微信组织机构
     *
     * @param companyUUid
     * @return
     */
    @Override
    public Map<String, Object> scanWxGroup(String companyUUid) {

        //从企业微信导入的组织结构
        List<WxGroupDomain> list = getGroupFromWx(companyUUid);

        if (CollectionUtils.isEmpty(list)) {
            return NewReturnUtils.successResponse("add", 0);
        }

        list.forEach(wxGroupDomain -> wxGroupDomain.setCompanyId(companyUUid));

        //从数据库中查询出已经存在于cipher_group_info的组
        List<Long> inDbGroupList = wxGroupMapper.queryAlredyExistsGroup(list);


        //TODO 对于编辑的过的group在这处理
        list = deleteFromList(list, inDbGroupList);

        if (CollectionUtils.isEmpty(list)) {
            return NewReturnUtils.successResponse("add", 0);
        }

        //查出已经存在于缓冲表的数据
        List<Long> idList = wxGroupBufferMapper.queryIdList(list);
        list = deleteFromList(list, idList);

        //新增了0个企业微信的group
        if (CollectionUtils.isEmpty(list)) {
            return NewReturnUtils.successResponse("add", 0);
        }

        wxGroupBufferMapper.insert(list);

        return NewReturnUtils.successResponse("add", list.size());
    }

    /**
     * 获取企业微信列表
     *
     * @param dataGridModel
     * @param companyId
     * @return
     */
    @Override
    public Map<String, Object> getWxGroupList(DataGridModel dataGridModel, String companyId) {
        Map<String, Object> map = new HashMap<>();

        List<WxGroupDomain> list = wxGroupBufferMapper.queryList(dataGridModel, companyId);

        list.forEach(wxGroupDomain -> wxGroupDomain.setStatus("新增"));

        list = constructPath(list);
        int count = wxGroupBufferMapper.queryCountByCompany(companyId);

        map.put("rows", list);
        map.put("total", count);
        return map;
    }

    /**
     * 从wxGroupDomainList中取出已经存在于idList中的数据
     *
     * @param wxGroupDomainList
     * @param idList
     */
    private List<WxGroupDomain> deleteFromList(List<WxGroupDomain> wxGroupDomainList, List<Long> idList) {
        if (CollectionUtils.isEmpty(wxGroupDomainList) || CollectionUtils.isEmpty(idList)) {
            return wxGroupDomainList;
        }

        Iterator<WxGroupDomain> iterator = wxGroupDomainList.iterator();
        while (iterator.hasNext()) {
            WxGroupDomain wxGroupDomain = iterator.next();
            for (Long id : idList) {
                if (id.equals(wxGroupDomain.getId())) {
                    iterator.remove();
                    break;
                }
            }
        }
        return wxGroupDomainList;
    }

    /**
     * 构造路径
     *
     * @param list
     * @return
     */
    private List<WxGroupDomain> constructPath(List<WxGroupDomain> list) {

        for (WxGroupDomain wxGroupDomain : list) {
            Long groupId = wxGroupDomain.getId();
            while (!groupId.equals(1L)) {
                WxGroupDomain tmpWx = queryParentName(groupId, list);
                if (tmpWx == null) {
                    break;
                }
                groupId = tmpWx.getParentid();
                wxGroupDomain.constructPath(tmpWx.getName());
            }
        }

        return list;
    }

    private WxGroupDomain queryParentName(Long parentId, List<WxGroupDomain> list) {
        for (WxGroupDomain wxGroupDomain : list) {
            if (wxGroupDomain.getId().equals(parentId)) {
                return wxGroupDomain;
            }
        }
        return null;
    }


    private void insertGroup(List<WxGroupDomain> list, String comapnyId) {

        int flag = 0;

        while (list.size() > 0) {

            Iterator<WxGroupDomain> iterator = list.iterator();

            while (iterator.hasNext()) {
                WxGroupDomain wxGroupDomain = iterator.next();

                if (wxGroupDomain.getParentid().equals(0L)) {
                    wxGroupDomain.setParentGroupId(0);
                    groupMapper.insertGroupByWxGroup(wxGroupDomain);
                    iterator.remove();
                }
                //从数据库获取dingGroupDomain.的parentGroupId
//                Integer parentGroupId = queryParentGroupId(dingGroupDomain.getParentid(), allGroup);
                Integer parentGroupId = groupMapper.queryGroupIdBYWxId(wxGroupDomain.getParentid());
                if (parentGroupId != null) {
                    wxGroupDomain.setParentGroupId(parentGroupId);
                    groupMapper.insertGroupByWxGroup(wxGroupDomain);
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

}
