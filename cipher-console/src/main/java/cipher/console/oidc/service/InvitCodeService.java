package cipher.console.oidc.service;
import cipher.console.oidc.domain.web.InvitCodeInfo;
import java.util.List;
import java.util.Map;

public interface InvitCodeService {

    List<InvitCodeInfo> queryInvitCodeList(String userName);

    Map<String,Object> createInvitCode(String userName);
}
