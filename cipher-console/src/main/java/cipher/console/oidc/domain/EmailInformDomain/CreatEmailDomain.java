package cipher.console.oidc.domain.EmailInformDomain;

public class CreatEmailDomain {
    private Integer id;
    private String companyUuid;
    private Integer serviceState;
    private String title;
    private String writer;

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

    public Integer getServiceState() {
        return serviceState;
    }

    public void setServiceState(Integer serviceState) {
        this.serviceState = serviceState;
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

    @Override
    public String toString() {
        return "CreatEmailDomain{" +
                "id=" + id +
                ", companyUuid='" + companyUuid + '\'' +
                ", serviceState=" + serviceState +
                ", title='" + title + '\'' +
                ", writer='" + writer + '\'' +
                '}';
    }
}
