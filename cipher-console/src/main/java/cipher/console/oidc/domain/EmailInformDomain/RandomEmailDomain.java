package cipher.console.oidc.domain.EmailInformDomain;

public class RandomEmailDomain {
    private Integer id;
    private String companyUuid;
    private String title;
    private String writer;
    private Integer effectiveTime;//验证码倒计时
    private Integer intervalTime;//间隔时间
    private Integer sendTime;//发送次数
    private Integer extendTime;//延长时间

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompanyUuid() {
        return companyUuid;
    }

    public void setCompanyUuid(String companyUuid) {
        this.companyUuid = companyUuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public Integer getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(Integer effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public Integer getIntervalTime() {
        return intervalTime;
    }

    public void setIntervalTime(Integer intervalTime) {
        this.intervalTime = intervalTime;
    }

    public Integer getSendTime() {
        return sendTime;
    }

    public void setSendTime(Integer sendTime) {
        this.sendTime = sendTime;
    }

    public Integer getExtendTime() {
        return extendTime;
    }

    public void setExtendTime(Integer extendTime) {
        this.extendTime = extendTime;
    }

    @Override
    public String toString() {
        return "RandomEmailDomain{" +
                "id=" + id +
                ", companyUuid='" + companyUuid + '\'' +
                ", title='" + title + '\'' +
                ", writer='" + writer + '\'' +
                ", effectiveTime=" + effectiveTime +
                ", intervalTime=" + intervalTime +
                ", sendTime=" + sendTime +
                ", extendTime=" + extendTime +
                '}';
    }
}
