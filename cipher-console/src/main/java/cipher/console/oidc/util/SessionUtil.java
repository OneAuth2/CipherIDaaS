package cipher.console.oidc.util;


import cipher.console.oidc.domain.web.UserInfoDomain;

import java.util.ArrayList;
import java.util.List;


/**
 * @Author: zt
 * @Date: 2018/5/30 8:47
 */
public class SessionUtil {
    //根据sessionId判断当前用户是否存在在集合中  如果存在 返回当前用户  否则返回null
    public static UserInfoDomain getUserBySessionId(List<UserInfoDomain> userList, String sessionId) {
      /* if(null!=userList && userList.size()>0){
           for (UserInfoDomain user : userList) {
               if(null!=user) {
                   if (sessionId.equals(user.getSessionId())) {
                       return user;
                   }
               }
           }
       }
*/
        return null;
    }
}