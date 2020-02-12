package cipher.console.oidc.enums;

public enum TeamRuleEnum {

    GROUP(1,"部门"),
    USERNAME(2,"账号"),
    TELEPHONE(3,"手机"),
    MAIL(4,"邮箱"),
    JOB(5,"职位"),
    SEX(6,"性别");


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

    TeamRuleEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static String getTeamRuleEnum(int type) {
        for (TeamRuleEnum teamRuleEnum : TeamRuleEnum.values()) {
            if (teamRuleEnum.getType() == type) {
                return teamRuleEnum.getDesc();
            }
        }
        return null;
    }





}
