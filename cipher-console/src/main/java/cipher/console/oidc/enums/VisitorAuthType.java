package cipher.console.oidc.enums;

public enum  VisitorAuthType {

    SMS_CODE_TO_STAFF(1,"员工接收短信码"),

    SMS_CODE_TO_VISITOR(2,"访客接收短信码"),

    MAIL_CODE_TO_STAFF(3,"员工接收邮箱码"),

    MAIL_CODE_TO_VISITOR(4,"访客接收邮箱码");


    int authType;

    private String description;

    VisitorAuthType(int authType,String description){
        this.authType = authType;
        this.description = description;
    }


    public static VisitorAuthType getVisitorAuthType(int typeId){
        for (VisitorAuthType visitorAuthType:VisitorAuthType.values()){
            if (visitorAuthType.authType == typeId){
                return visitorAuthType;
            }
        }

        return null;
    }

    public int getAuthType() {
        return authType;
    }

    public String getDescription() {
        return description;
    }
}
