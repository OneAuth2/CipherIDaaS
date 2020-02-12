package cipher.console.oidc.mapper;

import cipher.console.oidc.domain.web.AdUserBufferDomain;
import cipher.console.oidc.domain.web.UserInfoDomain;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 对应Ad导入数据缓冲表的操作,cipher_ad_user_buffer
 * @Author: zt
 * @Date: 2018/10/25 9:43
 */
public interface AdUserBufferMapper {


    /**
     * 从Ad域导出的数据和已有的数据(用户表和缓冲表)进行对比，确定是新增还是更改。
     * @param bufferDomainList
     * @return
     */
    public List<UserInfoDomain> queryUserByBufferUser(@Param("bufferDomainList") List<AdUserBufferDomain> bufferDomainList,@Param("companyId")String companyId);


    /**
     * 批量插入缓冲表
     * @param bufferDomainList
     * @throws Exception
     */
    public void insertBufferList(@Param("bufferDomainList") List<AdUserBufferDomain> bufferDomainList) throws Exception;

    /**
     * 查询字段更新的数据中已经存在于缓冲表中的数据
     * @param userInfoDomainList
     * @return
     */
    public List<AdUserBufferDomain> queryInBufferList(@Param("userInfoDomainList") List<UserInfoDomain> userInfoDomainList,@Param("companyId") String companyId);

    /**
     * 批量更新
     * @param bufferList
     */
    public void updateBufferList(@Param("bufferList") List<AdUserBufferDomain> bufferList);

    /**
     * 查询已经存在于缓冲表的用户
     * @param bufferDomainList
     */
    public List<AdUserBufferDomain> queryInBufferListByByffer(@Param("bufferDomainList") List<AdUserBufferDomain> bufferDomainList,@Param("companyId") String companyId);


    /**
     * 查询完整的用户信息
     * @param bufferDomainList
     * @return
     */
    public List<AdUserBufferDomain> queryUserByBufferList(@Param("bufferDomainList") List<AdUserBufferDomain> bufferDomainList,@Param("companyId") String companyId);


    /**
     * 根据id列表查询对应在缓冲表中的用户
     * @param idList
     * @return
     */
    public List<AdUserBufferDomain> queryListByIdList(@Param("idList") List<Integer> idList);

    /**
     * 查询缓冲表中的所有用户
     * @return
     */
    public List<AdUserBufferDomain> queryAllBufferUser(@Param("companyId") String companyId);


    /**
     * 批量将缓冲表中的用户，插入cipher_user_info表中
     * @param bufferDomainList
     * @throws Exception
     */
    public void insertIntoCipherUser(@Param("bufferDomainList") List<AdUserBufferDomain> bufferDomainList) throws Exception;

    /**
     * 批量更新cipher_user_info
     * @param bufferDomainList
     * @throws Exception
     */
    public void updateCipherUserByBuffer(@Param("bufferList") List<AdUserBufferDomain> bufferDomainList) throws Exception;

    /**
     * 根据id列表删除缓冲表中的用户
     * @param idList
     */
    public void deleteByIdList(@Param("idList")List<Integer> idList) throws Exception;

    /**
     * 根据缓冲表中的列表，查询用户表中的列表
     * @param bufferDomainList
     * @return
     */
    public List<UserInfoDomain> queryUserInfoByBufferList(@Param("bufferDomainList") List<AdUserBufferDomain> bufferDomainList,@Param("companyId")String companyId);




}
