package cipher.console.oidc.domain.web;

public class AppAuditInfo {
    private Integer id;
    private String userId;//被操作人id
    private Integer applicationId;//应用id
    private String userName;//操作人姓名
    private Integer type;//事件类型
    private String msg;//事件简介
    private String companyId;//公司id
    private String createTime;

    public AppAuditInfo(Integer applicationId,String userName,Integer type,String msg,String companyId){
        this.applicationId=applicationId;
        this.userName=userName;
        this.type=type;
        this.msg=msg;
        this.companyId=companyId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Integer applicationId) {
        this.applicationId = applicationId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "AppAuditInfo{" +
                "id=" + id +
                ", applicationId=" + applicationId +
                ", userName='" + userName + '\'' +
                ", type=" + type +
                ", msg='" + msg + '\'' +
                ", companyId='" + companyId + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
