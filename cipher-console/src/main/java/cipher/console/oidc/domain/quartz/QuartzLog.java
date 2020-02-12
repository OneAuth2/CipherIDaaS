package cipher.console.oidc.domain.quartz;

import java.io.Serializable;
import java.util.Date;

public class QuartzLog implements Serializable{
    private Integer id;

    private Long quartzId;

    private Long time;

    private String result;

    private String remark;

    private Date createTime;

    private Date startTime;


    public Long getQuartzId() {
        return quartzId;
    }

    public void setQuartzId(Long quartzId) {
        this.quartzId = quartzId;
    }

    public Long getTime() {
        return time;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result == null ? null : result.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        return "QuartzLog{" +
                "id=" + id +
                ", quartzId=" + quartzId +
                ", time=" + time +
                ", result='" + result + '\'' +
                ", remark='" + remark + '\'' +
                ", createTime=" + createTime +
                ", startTime=" + startTime +
                '}';
    }
}