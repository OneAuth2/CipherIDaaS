package cipher.console.oidc.filter;

import cipher.console.oidc.domain.web.UserInfoDomain;
import cipher.console.oidc.util.SessionUtil;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


/**
 * @Author: zt
 *  ServletRequestListener 监听器
 * @Date: 2018/5/30 8:55
 */
@WebListener
public class UserCountServletRequestListener implements ServletRequestListener {


    @Override
    public void requestDestroyed(ServletRequestEvent arg0) {

    }


    @SuppressWarnings("unchecked")
    @Override
    public void requestInitialized(ServletRequestEvent arg0) {
        //从servletContext中获的userList
        List<UserInfoDomain> userList = (List<UserInfoDomain>) arg0.getServletContext().getAttribute("userList");
        //如果servletContext中没有userList对象  初始化userList
        if(userList ==null){
            userList =new ArrayList<UserInfoDomain>();
        }
        HttpServletRequest request=(HttpServletRequest) arg0.getServletRequest();

        //sessionId
        String sessionId=request.getSession().getId();
        //如果当前sessionId不存在集合中  创建当前user对象
        if(SessionUtil.getUserBySessionId(userList,sessionId)==null){
            UserInfoDomain user=new UserInfoDomain();
            user.setSessionId(sessionId);
            user.setIp(request.getRemoteAddr());
            userList.add(user);
        }
        //将userList集合 保存到ServletContext
        arg0.getServletContext().setAttribute("userList", userList);
    }
}
