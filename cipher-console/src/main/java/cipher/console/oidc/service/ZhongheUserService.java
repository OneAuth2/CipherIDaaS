package cipher.console.oidc.service;

import cipher.console.oidc.domain.zhonghe.ZhongheUser;
import cipher.console.oidc.domain.zhonghe.ZhongheUserDB;


import java.util.List;

public interface ZhongheUserService {

    /**
     * 批量插入众合用户信息
     * @param userList
     */
    public void insertZhUser(List<ZhongheUser> userList);

    /**
     * 将众合用户映射到自己的用户体系
     */
    public void zhonghe2cipheruser() throws Exception;

    public List<ZhongheUserDB> queryZhAllUser();

}
