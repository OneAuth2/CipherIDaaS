package cipher.console.oidc.service;

import cipher.console.oidc.domain.web.UserAuthorizationMapDomain;

import java.util.List;

/**
 * 用户授权service
 * @author  sizhao
 * */
public interface UserAuthorizationService {

   /**
    * 获取用户授权方式
    * @return 用户授权列表
    * */
   public List<UserAuthorizationMapDomain> getUserAuthorizationMethods();

   /**
    * 根据用户主账号删除用户验证方式
    * @param accountNumber 用户的主账号
    * @return 删除的结果 0-删除失败  1-删除成功
    * */
   public int deleteByAccountNumber(String accountNumber);

   /**
    * 插入用户的授权信息
    * @param userAuthorizationMapDomain 用户授权信息
    * @return 插入结果 0-插入失败  1-插入成功
    * */
   public int insertInto(UserAuthorizationMapDomain userAuthorizationMapDomain);
}
