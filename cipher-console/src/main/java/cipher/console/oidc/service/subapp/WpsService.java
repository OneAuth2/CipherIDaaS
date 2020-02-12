package cipher.console.oidc.service.subapp;

import cipher.console.oidc.domain.subapp.*;

import java.util.List;
import java.util.Map;

/**
 * 金山云协作文档
 *
 * @Author: zt
 * @Date: 2018/12/7 14:10
 */
public interface WpsService {

    /**
     * @param wpsCommonReqDomin appid不需要
     * @param userId   用户信息
     */
    public Map<String, Object> createSession(WpsCommonReqDomin wpsCommonReqDomin, String userId);


    /**
     * 根据第三方系统用户的userId换取云协作用户的userId
     * @param wpsCommonReqDomin 都需要
     * @param userId 本系统用户的id
     * @return 云协作用户的userId
     */
    public Long getWpsUserIdBy3rdUser(WpsCommonReqDomin wpsCommonReqDomin,String userId);

    /**
     * 根据公司id和部门id创建用户
     * @param wpsCommonReqDomin
     * @param wpsCreateUserReqDomain
     * @param deptId
     * @return
     */
    public Map<String,Object> createUser(WpsCommonReqDomin wpsCommonReqDomin, WpsCreateUserReqDomain wpsCreateUserReqDomain,long deptId );

    /**
     * 在某个部门下创建子部门
     * @param wpsCommonReqDomin
     * @param deptId
     * @return
     */
    public Map<String,Object> createDept(WpsCommonReqDomin wpsCommonReqDomin,Long deptId);


    /**
     *
     * @param wpsCommonReqDomin
     * @param offset 分页起始下标	是，默认0
     * @param limit 返回的数据条数，≤0不限制返回条数	是，默认10
     * @return
     */
    public List<WpsDeptDomain> queryWpsDeptList(WpsCommonReqDomin wpsCommonReqDomin,int offset,int limit);


    /**
     * 根据公司id获取用户列表
     * /v1/companies/:company_id/members
     * GET
     * @param wpsCommonReqDomin
     * @param wpsQueryUserListDomain
     */
    public List<WpsUserDomain> queryUserByConpanyId(WpsCommonReqDomin wpsCommonReqDomin, WpsQueryUserListDomain wpsQueryUserListDomain);



}
