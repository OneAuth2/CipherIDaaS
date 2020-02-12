package cipher.console.oidc.enums;

/**
 * Created by 95744 on 2018/6/6.
 */
public enum UserBehaviorEnum {


      /*LOGON_LOG(1,"登陆日志"),
      ACCESS_PATH(2,"访问路径"),
      ACCOUNT_UPDATE(3,"账号更新"),
      ACCOUNT_SECURITY(4,"账号安全与状态");*/

      APPLICATION_VISIT(1,"应用访问"),
      HIT_STRATEGY(2,"命中策略"),
      ACCOUNT_MAINTAIN(3,"账号维护");






    /*LOGIN_IN_OUT(1, "登入登出"),
    APPLY_LOGIN(2, "访问应用"),
    PERSONAL_CHANGE(3, "修改密码"),
    BIND_APP(4,"绑定手机app"),
    BIND_ACOUNT(5,"账号锁定和解锁"),
    RESET_PASSWORD(6,"重置密码"),
    NICKNAME_UPDATE(7, "修改昵称"),
    TELEPHONE_UPDATE(8,"修改手机号");*/


    private int type;
    private String desc;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    UserBehaviorEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static String getUserBehaviorEnum(int type) {
        for (UserBehaviorEnum userBehaviorEnum : UserBehaviorEnum.values()) {
            if (userBehaviorEnum.getType() == type) {
                return userBehaviorEnum.getDesc();
            }
        }
        return null;
    }

}
