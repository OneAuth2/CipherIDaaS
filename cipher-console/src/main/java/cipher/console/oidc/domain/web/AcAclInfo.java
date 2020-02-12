package cipher.console.oidc.domain.web;

import cipher.console.oidc.domain.BaseDomain;
import org.apache.commons.lang.StringUtils;

import java.util.Date;

public class AcAclInfo extends BaseDomain {

    private int id;

    private String name;

    private String application;

    private String ip;

    private String mac;

    private String createTime;

    private Date modifiedTime;

    private Date workStartTime;

    private Date workEndTime;

    private int offLine;


    public int getOffLine() {
        return offLine;
    }

    public void setOffLine(int offLine) {
        this.offLine = offLine;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMac() {
        return mac;
    }

    public String getFormatMac() {
        if (StringUtils.isEmpty(mac)){
            return mac;
        }
        return mac.replace(".","")
                .replace(",","")
                .replace("-","")
                .replace("/","")
                .replace(":","")
                .replace(" ","");
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public Date getWorkStartTime() {
        return workStartTime;
    }

    public void setWorkStartTime(Date workStartTime) {
        this.workStartTime = workStartTime;
    }

    public Date getWorkEndTime() {
        return workEndTime;
    }

    public void setWorkEndTime(Date workEndTime) {
        this.workEndTime = workEndTime;
    }

    public void setTime(String createTime,Date modifiedTime,Date workStartTime,Date workEndTime){
        setCreateTime(createTime);
        setModifiedTime(modifiedTime);
        setWorkStartTime(workStartTime);
        setWorkEndTime(workEndTime);
    }

    @Override
    public String toString() {
        return super.toString() + "AcAclInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", application='" + application + '\'' +
                ", ip='" + ip + '\'' +
                ", mac='" + mac + '\'' +
                ", createTime=" + createTime +
                ", modifiedTime=" + modifiedTime +
                ", workStartTime=" + workStartTime +
                ", workEndTime=" + workEndTime +
                '}';
    }
}
