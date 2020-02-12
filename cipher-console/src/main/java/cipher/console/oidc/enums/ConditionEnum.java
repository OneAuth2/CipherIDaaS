package cipher.console.oidc.enums;

public enum ConditionEnum {

    EQUAL(1,"等于"),
    NOT_EQUAL(2,"不等于"),
    CONTANT(3,"包含"),
    NOT_CONTAN(4,"不包含");


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

    ConditionEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static String getConditionEnum(int type) {
        for (ConditionEnum conditionEnum : ConditionEnum.values()) {
            if (conditionEnum.getType() == type) {
                return conditionEnum.getDesc();
            }
        }
        return null;
    }

}
