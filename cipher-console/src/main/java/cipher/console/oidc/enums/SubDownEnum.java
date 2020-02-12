package cipher.console.oidc.enums;

public enum SubDownEnum {

    TENGXUN(1,"tengxun"),
    WANGYI(2,"wangyi");


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

    SubDownEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static String getSubDownEnum(int type) {
        for (SubDownEnum subDownEnum : SubDownEnum.values()) {
            if (subDownEnum.getType()==type) {
                return subDownEnum.getDesc();
            }
        }
        return null;
    }


    public static Integer getSubDownTypeEnum(String desc) {
        for (SubDownEnum subDownEnum : SubDownEnum.values()) {
            if (subDownEnum.getDesc().equals(desc)) {
                return subDownEnum.getType();
            }
        }
        return 999;
    }

}
