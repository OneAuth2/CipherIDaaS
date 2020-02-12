package cipher.console.oidc.mapper;


import cipher.console.oidc.domain.zhonghe.ZhongheUser;
import cipher.console.oidc.domain.zhonghe.ZhongheUserDB;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ZhongheUserDAO {


    /**
     * 批量插入众合用户信息
     * @param userList
     */
    public void insertZhUser(@Param("userList") List<ZhongheUser> userList);

    /**
     * 将众合用户映射到自己的用户体系
     */
    public void zhonghe2cipheruser() throws Exception;

    /**
     * 查询众合所有的用户
     * @return
     */
    public List<ZhongheUserDB> queryAllZhUser();




}
