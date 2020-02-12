package cipher.console.oidc.mapper;

import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.web.DynamicStateDomain;
import cipher.console.oidc.domain.web.DynamicStateInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DynamicStateMapper {

    //全部
    List<DynamicStateDomain> selectDynamicStateList(DynamicStateInfo dsi);
    int selectDynamicStateListCount(DynamicStateInfo dsi);

    //已下发
    List<DynamicStateDomain> selectDynamicStateFinishList(DynamicStateInfo dsi);
    int selectDynamicStateFinishListCount(DynamicStateInfo dsi);

    //未下发
    List<DynamicStateDomain> selectDynamicStateUndoneList(DynamicStateInfo dsi);
    int selectDynamicStateUndoneListCount(DynamicStateInfo dsi);

    /**
     * 通过userId 获取邮箱地址
     * @param userId 用户id
     * @return
     */
    String getMailByuserId(String userId);

    /**
     * 通过用户id删除动态密码-密钥
     * @param userId 用户id
     */
    void delDynamicPassword(String userId);

    /**
     * 通过用户id获取account_number
     * @param userId
     * @return
     */
    String selectAccountNumByUserId(String userId);

    /**
     * 修改下发状态
     * @param userId
     */
    void updateIssueStatus(String userId);

    /**
     * 保存动态密码-密钥
     * @param uuid
     * @param userId
     * @param dynamicPassword
     */
    void insertDynamicPassword(@Param(value = "uuid") String uuid,
                               @Param(value = "userId")String userId,
                               @Param(value = "companyId")String companyId,
                               @Param(value = "dynamicPassword")String dynamicPassword);

    /**
     * 通过公司Id找到已下发种子密钥的用户Id
     * @param companyId 公司Id
     * @return
     */
    List<String> selectedUserId(String companyId);


}
