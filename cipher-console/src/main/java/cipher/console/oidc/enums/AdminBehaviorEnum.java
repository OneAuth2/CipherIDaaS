package cipher.console.oidc.enums;

/**
 * Created by 95744 on 2018/6/6.
 */
public enum AdminBehaviorEnum {


      ACCOUNT_MAINTENANCE(1,"账号维护"),
      HIT_STRATEGY(2,"命中策略"),
      APPLICATION_MAINTENANCE(3,"应用维护"),
      AUTHORIZED_MAINTENANC(4,"授权维护"),
      ORIGINATION_UPDATE(5,"组织结构更新"),
      TEAM_UPDATE(6,"安全组更新"),
      SUBACCOUNT_MAINTENANCE(7,"子账号维护"),
      STRATEGY_MAINTENANCE(8,"策略维护"),
      APPLICATION_VISITOR(9,"应用访问"),
      WIFI_MANAGER(10,"无线网络管理"),
      SYSTEM_MANAGER(11,"系统设置");

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

    AdminBehaviorEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static String getAdminBehaviorEnum(int type) {
        for (AdminBehaviorEnum adminBehaviorEnum : AdminBehaviorEnum.values()) {
            if (adminBehaviorEnum.getType() == type) {
                return adminBehaviorEnum.getDesc();
            }
        }
        return null;
    }

}
