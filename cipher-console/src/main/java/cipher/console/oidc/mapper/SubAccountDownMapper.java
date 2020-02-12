package cipher.console.oidc.mapper;

import cipher.console.oidc.domain.subapp.SubAccountDownDomain;

/**
 * TODO:
 * create by liuying at 2019/8/5
 *
 * @author liuying
 * @since 2019/8/5 9:32
 */
public interface SubAccountDownMapper {

    public SubAccountDownDomain getSubAccountDownInfo(SubAccountDownDomain form);

    public void insertSubAccountDownInfo(SubAccountDownDomain form);


}
