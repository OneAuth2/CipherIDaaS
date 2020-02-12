package cipher.console.oidc.mapper;

import cipher.console.oidc.domain.web.UserCompanyMapDomain;

import java.util.List;

public interface UserCompanyMapMapper {

    String queryCompanyId(String userName);

    List<UserCompanyMapDomain> getInvitAndUserInfo(UserCompanyMapDomain userCompanyMapDomain);

    Integer getUsedInvitCount(String companyId);
}
