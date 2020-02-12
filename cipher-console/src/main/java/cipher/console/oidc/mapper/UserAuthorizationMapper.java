package cipher.console.oidc.mapper;

import cipher.console.oidc.domain.web.UserAuthorizationMapDomain;

import java.util.List;

public interface UserAuthorizationMapper {
    public List<UserAuthorizationMapDomain> getUserAuthorizationMethods();

    /**
     * 拼接每个用户的所有认证方式
     * @return
     */
    public List<UserAuthorizationMapDomain> qeryUserAuthType();

    public int deleteByAccountNumber(String accountNumber);

    public int insertInto(UserAuthorizationMapDomain userAuthorizationMapDomain);
}
