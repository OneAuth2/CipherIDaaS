package cipher.console.oidc.domain.web;


import cipher.console.oidc.jms.base.BaseMessage;

/**
 * Wifi操作信息实体类
 * create by shizhao at 2019/4/18
 * @author shizhao
 * @since  2019/4/18
 * */
public class WifiActionMessage extends BaseMessage {

    //操作的IP
    private String ip;

    //操作的mac地震
    private String mac;

    //操作的用户名
    private String userName;


    //操作的类型
    //Login-1
    //Logout-0
    private int action;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "WifiActionMessage{" +
                "ip='" + ip + '\'' +
                ", mac='" + mac + '\'' +
                ", userName='" + userName + '\'' +
                ", action=" + action +
                '}';
    }
}
