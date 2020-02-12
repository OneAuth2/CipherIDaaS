package cipher.console.oidc.enums;

public enum UserImportEnum {

    USER_NAME("姓名","userName"),
    ACCOUNT_NUMBER("主账号（用户名）","accountNumber"),
    MAIL("邮箱","mail"),
    PHONE_NUMBER("手机号","phoneNumber"),
    NICKNAME("昵称","nickname"),
    JOB_NO("工号","jobNo"),
    JOB("职位","job"),
    GENDER("性别","gender");


    private String type;
    private String desc;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    UserImportEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }



    public static String getPoliceEnum(String type) {
        for (UserImportEnum policeEnum : UserImportEnum.values()) {
            if (policeEnum.getType().equals(type)) {
                return policeEnum.getDesc();
            }
        }
        return null;
    }


}
