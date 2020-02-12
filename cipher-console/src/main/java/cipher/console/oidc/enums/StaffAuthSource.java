package cipher.console.oidc.enums;

public enum StaffAuthSource {

    LOCAL_AUTH(0,"本地认证"),
    AD_AUTH(1,"AD认证"),
    GOLD_MANTIS_AUTH(2,"金螳螂ERP认证");

    private int sourceType;

    private String sourceDescription;

    StaffAuthSource(int sourceType, String sourceDescription){
        this.sourceType=sourceType;
        this.sourceDescription=sourceDescription;
    }

    public static StaffAuthSource getAuthSource(int typeId){
        for (StaffAuthSource staffAuthSource : StaffAuthSource.values()){
            if (staffAuthSource.getSourceType() == typeId){
                return staffAuthSource;
            }
        }

        return null;
    }


    public int getSourceType() {
        return sourceType;
    }

    public String getSourceDescription() {
        return sourceDescription;
    }
}
