package cipher.console.oidc.enums;

/**
 * Created by 95744 on 2018/6/6.
 */
public enum ApplicationAuditEnum {

    APPLICATION_VISIT(1, "应用访问"),
    APPLICATION_AUTH(2, "应用维护"),
    AUTH_MAINTENANCE(3,"授权维护"),
    SUB_ACCOUNT_AUTH(4,"子账号维护");


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

    ApplicationAuditEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static String getApplicationAuditEnum(int type) {
        for (ApplicationAuditEnum applicationAuditEnum : ApplicationAuditEnum.values()) {
            if (applicationAuditEnum.getType() == type) {
                return applicationAuditEnum.getDesc();
            }
        }
        return null;
    }

}
