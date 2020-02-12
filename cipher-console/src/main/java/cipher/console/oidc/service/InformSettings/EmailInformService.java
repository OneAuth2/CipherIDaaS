package cipher.console.oidc.service.InformSettings;

import cipher.console.oidc.domain.EmailInformDomain.CreatEmailDomain;
import cipher.console.oidc.domain.EmailInformDomain.EmailBasicDomain;
import cipher.console.oidc.domain.EmailInformDomain.RandomEmailDomain;
import cipher.console.oidc.domain.EmailInformDomain.SeedkeyEmailDomain;

import java.util.Map;

public interface EmailInformService {
    Map<String,Object> compileEmailInform(EmailBasicDomain emailBasicDomain);
    Map<String,Object> echoEmailInform(String companyUuid);

    Map<String,Object> emailInformByCompanyUuid(String companyUuid, String title, String writer, String emailAddr);

    Map<String,Object> compileRandomEmail(RandomEmailDomain randomEmailDomain);
    Map<String,Object> echoRandomEmail(String companyUuid);

    Map<String,Object> compileCreatEmail(CreatEmailDomain creatEmailDomain);
    Map<String,Object> echoCreatEmail(String companyUuid);

    Map<String,Object> compileSeedkeyEmail(SeedkeyEmailDomain seedkeyEmailDomain);
    Map<String,Object> echoSeedkeyEmail(String companyUuid);

}
