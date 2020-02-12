package cipher.console.oidc.mapper;

import cipher.console.oidc.model.TotpAuthorizationModel;

import java.util.List;

public interface TotpAuthorizationMapper {

    List<TotpAuthorizationModel> queryAllTotpTable(TotpAuthorizationModel model);

    List<Integer> queryScratchCodeByAccountNumber(String accountNumber);

    int queryPageCount(TotpAuthorizationModel model);
}
