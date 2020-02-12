package cipher.console.oidc.service;

import cipher.console.oidc.model.ModifyAccountNumberModel;

import java.util.List;

/**
 * @Author: zt
 * @Date: 2018/11/13 16:26
 */
public interface ModifyAccountNumberService {

    /**
     * 更改cipher_group_user_map表的account_number
     * @param modifyAccountNumberModelList
     */
    public void updateCipherGroupUserMapAccountNumber(List<ModifyAccountNumberModel> modifyAccountNumberModelList);

    /**
     * 更改cipher_role_user_map表的account_number
     * @param modifyAccountNumberModelList
     */
    public void updateCipherRoleUserMapAccountNumber(List<ModifyAccountNumberModel> modifyAccountNumberModelList);

    /**
     * 更改cipher_authorization_map表的account_number
     * @param modifyAccountNumberModelList
     */
    public void updateCipherUserAuthorizationMapAccountNumber(List<ModifyAccountNumberModel> modifyAccountNumberModelList);


}
