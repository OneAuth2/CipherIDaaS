package cipher.console.oidc.service.subapp;

import cipher.console.oidc.model.ZhOaUserModel;

/**
 * 众合oa 插入子账号
 *
 * @Author: zt
 * @Date: 2019-4-10 16:17
 */
public interface ZhOaInsertUserService {

    /**
     * Oa子账号下发
     * @param zhOaUserModel
     * @return
     */
    public boolean insertAccount(ZhOaUserModel zhOaUserModel);

}
