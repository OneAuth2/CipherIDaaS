package cipher.console.oidc.mapper;


import cipher.console.oidc.domain.web.CompanyInfoDomain;
import cipher.console.oidc.domain.web.InvitCodeInfo;

import java.util.List;

public interface InvitCodeMapper {

    int findInvitCodeCount(String companyId);

    void addInvitCodeInfo(InvitCodeInfo invitCodeInfo);

    List<InvitCodeInfo> queryInvitCodeList(String companyId);


}
