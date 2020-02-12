package cipher.console.oidc.enums;

public enum  VisitorAuthSource {

    LOCAL_SOURCE(0,"本地添加"),
    VISITOR_REGISTER(1,"自注册");


    private int sourceId;

    private String description;

    VisitorAuthSource(int sourceId,String description){
        this.sourceId = sourceId;
        this.description = description;
    }

    public static VisitorAuthSource getSource(int typeId){
        for (VisitorAuthSource authSource:VisitorAuthSource.values()){
            if (authSource.sourceId == typeId){
                return authSource;
            }
        }
        return null;
    }

    public int getSourceId() {
        return sourceId;
    }

    public String getDescription() {
        return description;
    }
}
