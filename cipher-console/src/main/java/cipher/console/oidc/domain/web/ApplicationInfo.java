package cipher.console.oidc.domain.web;

import cipher.console.oidc.domain.BaseDomain;

/**
 * Created by 95744 on 2018/6/4.
 */
public class ApplicationInfo extends BaseDomain{

    private Integer id;
    private String applicationName;
    private String applicationStatus;
    private Integer visitTimes;
    private Integer recently;
    private String proportion;//占比
    private String startTime;
    private String endTime;
    private String sidx;
    private String sord;

    private Integer countBySev;
    private Integer countByMon;


    public String getSidx() {
        return sidx;
    }

    public void setSidx(String sidx) {
        this.sidx = sidx;
    }

    public String getSord() {
        return sord;
    }

    public void setSord(String sord) {
        this.sord = sord;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRecently() {
        return recently;
    }

    public void setRecently(Integer recently) {
        this.recently = recently;
    }

    public String getProportion() {
        return proportion;
    }

    public void setProportion(String proportion) {
        this.proportion = proportion;
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

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public Integer getVisitTimes() {
        return visitTimes;
    }

    public void setVisitTimes(Integer visitTimes) {
        this.visitTimes = visitTimes;
    }

    public Integer getCountBySev() {
        return countBySev;
    }

    public void setCountBySev(Integer countBySev) {
        this.countBySev = countBySev;
    }

    public Integer getCountByMon() {
        return countByMon;
    }

    public void setCountByMon(Integer countByMon) {
        this.countByMon = countByMon;
    }

    @Override
    public String toString() {
        return "ApplicationInfo{" +
                "id=" + id +
                ", applicationName='" + applicationName + '\'' +
                ", applicationStatus='" + applicationStatus + '\'' +
                ", visitTimes=" + visitTimes +
                ", recently=" + recently +
                ", proportion='" + proportion + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", sidx='" + sidx + '\'' +
                ", sord='" + sord + '\'' +
                '}';
    }
}
