package cipher.console.oidc.mapper;

import cipher.console.oidc.model.ModifyAccountNumberModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 更改account_number的入口
 * @Author: zt
 * @Date: 2018/11/13 16:18
 */
public interface ModifyAccountNumberMapper {

    /**
     * 更改cipher_group_user_map表的account_number
     * @param modifyAccountNumberModelList
     */
    public void updateCipherGroupUserMapAccountNumber(@Param("modelList") List<ModifyAccountNumberModel> modifyAccountNumberModelList);

    /**
     * 更改cipher_role_user_map表的account_number
     * @param modifyAccountNumberModelList
     */
    public void updateCipherRoleUserMapAccountNumber(@Param("modelList")List<ModifyAccountNumberModel> modifyAccountNumberModelList);

    /**
     * 更改cipher_authorization_map表的account_number
     * @param modifyAccountNumberModelList
     */
    public void updateCipherUserAuthorizationMapAccountNumber(@Param("modelList")List<ModifyAccountNumberModel> modifyAccountNumberModelList);


}
