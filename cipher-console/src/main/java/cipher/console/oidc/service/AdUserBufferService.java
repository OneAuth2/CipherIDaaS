package cipher.console.oidc.service;

import cipher.console.oidc.domain.web.AdUserBufferDomain;
import cipher.console.oidc.domain.web.UserInfoDomain;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: zt
 * @Date: 2018/10/25 9:44
 */
public interface AdUserBufferService {

    /**
     * 从Ad域导出的数据和已有的数据进行对比，确定是新增还是更改。
     * @param bufferDomainList
     * @return
     */
    public List<UserInfoDomain> queryUserByBufferUser( List<AdUserBufferDomain> bufferDomainList,String companyId);

    /**
     * 批量插入缓冲表
     * @param bufferDomainList
     * @throws Exception
     */
    public void insertBufferList(List<AdUserBufferDomain> bufferDomainList) throws Exception;

    /**
     * 查询字段更新的数据中已经存在于缓冲表中的数据
     * @param userInfoDomainList
     * @return
     */
    public List<AdUserBufferDomain> queryInBufferList(List<UserInfoDomain> userInfoDomainList,String companyId);

    /**
     * 批量更新
     * @param bufferList
     */
    public void updateBufferList(List<AdUserBufferDomain> bufferList);


    public List<AdUserBufferDomain> queryInBufferListByBuffer(List<AdUserBufferDomain> existsList,String companyId);

    /**
     * 查询完整的用户信息
     * @param bufferDomainList
     * @return
     */
    public List<AdUserBufferDomain> queryUserByBufferList(List<AdUserBufferDomain> bufferDomainList,String companyId);

    /**
     * 根据id列表查询对应在缓冲表中的用户
     * @param idList
     * @return
     */
    public List<AdUserBufferDomain> queryListByIdList(List<Integer> idList);

    /**
     * 批量将缓冲表中的用户，插入cipher_user_info表中
     * @param bufferDomainList
     * @throws Exception
     */
    public void insertIntoCipherUser(List<AdUserBufferDomain> bufferDomainList) throws Exception;

    /**
     * 批量更新cipher_user_info
     * @param bufferDomainList
     * @throws Exception
     */
    public void updateCipherUserByBuffer(List<AdUserBufferDomain> bufferDomainList) throws Exception;

    /**
     * 根据id列表删除缓冲表中的用户
     * @param idList
     */
    public void deleteByIdList(List<Integer> idList) throws Exception;

    /**
     * 根据缓冲表中的列表，查询用户表中的列表
     * @param bufferDomainList
     * @return
     */
    public List<UserInfoDomain> queryUserInfoByBufferList(List<AdUserBufferDomain> bufferDomainList,String companyId);

    public List<AdUserBufferDomain> queryAllBufferUser(String comapnyId);


}
