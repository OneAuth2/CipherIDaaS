package cipher.console.oidc.filter;

import cipher.console.oidc.domain.web.UserInfoDomain;
import cipher.console.oidc.util.SessionUtil;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.ArrayList;

/**
 * @Author: zt
 * @Date: 2018/5/29 21:10
 */
@WebListener
public class UserCountHttpSessionListener implements HttpSessionListener {
     //当前用户数
     private int userCounts = 0;
    /**
     * sessionCreated  用户数+1
     *
     * @param se
     */
    @Override
    public void sessionCreated(HttpSessionEvent se) {
//        System.err.println("用户名:" + se.getSession().getAttribute("userName"));
        userCounts++;
        //重新在servletContext中保存userCounts
        se.getSession().getServletContext().setAttribute("userCounts", userCounts);
    }

    /**
     * sessionDestroyed  用户数-1
     *
     * @param se
     */
    @Override
    @SuppressWarnings("unchecked")
    public void sessionDestroyed(HttpSessionEvent se) {
        userCounts--;
        if (userCounts < 0) {
            userCounts = 1;
        }
        //重新在servletContext中保存userCounts
        se.getSession().getServletContext().setAttribute("userCounts", userCounts);
        ArrayList<UserInfoDomain> userList = (ArrayList<UserInfoDomain>) se.getSession().getServletContext().getAttribute("userList");
        String sessionId = se.getSession().getId();
        //如果当前用户在userList中  在session销毁时  将当前用户移出userList
        if (SessionUtil.getUserBySessionId(userList, sessionId) != null) {
            userList.remove(SessionUtil.getUserBySessionId(userList, sessionId));
        }
        //将userList集合  重新保存到servletContext
        se.getSession().getServletContext().setAttribute("userList", userList);
    }


}
