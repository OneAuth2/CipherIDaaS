package cipher.console.oidc.mapper;

import cipher.console.oidc.domain.web.AdActivityDomain;
import cipher.console.oidc.domain.web.AdUserBufferDomain;

import java.util.List;

/**
 * @Author: tk
 * @Date: 2018/10/25 20:27
 */
public interface AdActivityMapper {

    List<AdUserBufferDomain> selectAllUsers(AdActivityDomain adActivityDomain);

    int getAllUsersCount(AdActivityDomain adActivityDomain);

    AdActivityDomain getUserBufferInfo(String accountNumber);//获取缓冲表中的信息

    AdActivityDomain getUserInfo(String accountNumber);//通过主账号获取我们的账户信息
}
