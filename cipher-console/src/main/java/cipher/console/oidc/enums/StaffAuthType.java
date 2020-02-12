package cipher.console.oidc.enums;

public enum StaffAuthType {

    USER_NAME_ONLY(1,"用户名"),

    USER_NAME_AND_PASSWORD(2,"用户名密码认证");

    private int authTypeId;

    private String authDescription;


    StaffAuthType(int authType, String authDescription){
        this.authTypeId=authType;
        this.authDescription=authDescription;
    }

    public static StaffAuthType getAuthType(int typeId){
        for (StaffAuthType staffAuthType : StaffAuthType.values()){
            if (staffAuthType.getAuthTypeId() == typeId){
                return staffAuthType;
            }
        }
        return null;
    }

    public String getAuthDescription() {
        return authDescription;
    }

    public int getAuthTypeId() {
        return authTypeId;
    }
}
