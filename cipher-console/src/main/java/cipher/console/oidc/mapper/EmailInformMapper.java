package cipher.console.oidc.mapper;

import cipher.console.oidc.domain.EmailInformDomain.CreatEmailDomain;
import cipher.console.oidc.domain.EmailInformDomain.EmailBasicDomain;
import cipher.console.oidc.domain.EmailInformDomain.RandomEmailDomain;
import cipher.console.oidc.domain.EmailInformDomain.SeedkeyEmailDomain;
import cipher.console.oidc.domain.web.EmailInfoDomain;

public interface EmailInformMapper {
    //邮件通知基本配置
    int companyUuidCount(String companyUuid);
    void insertEmailInform(EmailBasicDomain emailBasicDomain) throws Exception;
    void updateEmailInform(EmailBasicDomain emailBasicDomain) throws Exception;
    EmailBasicDomain selectEmailInformBycompanyUuid(String companyUuid);

    //邮件验证码通知测试
    EmailInfoDomain EmailInformByCompanyUuid(String companyUuid);

    //邮件通知随机码配置
    void insertRandomEmail(RandomEmailDomain randomEmailDomain) throws Exception;
    void updateRandomEmail(RandomEmailDomain randomEmailDomain) throws Exception;
    RandomEmailDomain selectRandomEmailBycompanyUuid(String companyUuid);

    //邮件通知开通账号配置
    void insertCreatEmail(CreatEmailDomain creatEmailDomain) throws Exception;
    void updateCreatEmail(CreatEmailDomain creatEmailDomain) throws Exception;
    CreatEmailDomain selectCreatEmailBycompanyUuid(String companyUuid);

    //下发种子密钥邮箱配置
    void insertSeedkeyEmail(SeedkeyEmailDomain seedkeyEmailDomain) throws Exception;
    void updateSeedkeyEmail(SeedkeyEmailDomain seedkeyEmailDomain) throws Exception;
    SeedkeyEmailDomain selectSeedkeyEmailBycompanyUuid(String companyUuid);

}
