package cipher.console.oidc.service;

import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.model.TotpAuthorizationModel;

import java.util.List;
import java.util.Map;

/**Totp授权管理的service
* @author sizhao
* */
public interface TotpAuthorizationService {

    /**
     * 获取Totp授权表格的全部信息
     * @param name 用户主账号
     * @param pageModel 分页信息
     * @return 用户信息的数据
     * */
    Map<String,Object> queryAllTotpTable(TotpAuthorizationModel name, DataGridModel pageModel);

    /**
     * 获取用户的临时校验码
     * @param accountNumber 用户主账号
     * @return 用户的临时验证码列表
     * */
    List<Integer> queryScratchCodeByAccountNumber(String accountNumber);
}
