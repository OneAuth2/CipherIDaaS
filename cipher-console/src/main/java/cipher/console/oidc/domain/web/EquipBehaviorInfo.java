package cipher.console.oidc.domain.web;

import cipher.console.oidc.domain.BaseDomain;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class EquipBehaviorInfo extends BaseDomain{
    private Integer id;
    private String userId;
    private String companyId;
    private String vpnId;
    private String userInfo;
    private Integer type;
    private String intro;
    private String msg;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private String startTime;
    private String endTime;

    public EquipBehaviorInfo(){
    }

    public EquipBehaviorInfo(String userId,String companyId,String vpnId,Integer type,String intro,String msg){
        this.userId = userId;
        this.companyId = companyId;
        this.vpnId = vpnId;
        this.type = type;
        this.intro = intro;
        this.msg = msg;
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

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getVpnId() {
        return vpnId;
    }

    public void setVpnId(String vpnId) {
        this.vpnId = vpnId;
    }

    public String getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "EquipBehaviorInfo{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", companyId='" + companyId + '\'' +
                ", vpnId='" + vpnId + '\'' +
                ", userInfo='" + userInfo + '\'' +
                ", type=" + type +
                ", intro='" + intro + '\'' +
                ", msg='" + msg + '\'' +
                ", createTime=" + createTime +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }
}
