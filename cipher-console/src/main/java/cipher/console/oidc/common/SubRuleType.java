package cipher.console.oidc.common;

public enum SubRuleType {

    SAIFU_NUMBER(1,"平台账号"),
    PHONE(4,"电话号码"),
    MAIL(2,"邮箱"),
    MAIL_PRIX(3,"邮箱前缀"),
    JOB_NO(5,"工号");


    private int code;

    private String type;

    SubRuleType(int code, String type) {
        this.code = code;
        this.type = type;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }



}
