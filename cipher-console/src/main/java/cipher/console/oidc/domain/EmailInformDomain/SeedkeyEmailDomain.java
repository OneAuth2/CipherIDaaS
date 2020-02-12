package cipher.console.oidc.domain.EmailInformDomain;

public class SeedkeyEmailDomain {
    private Integer id;
    private String companyUuid;
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
        return "SeedkeyEmailDomain{" +
                "id=" + id +
                ", companyUuid='" + companyUuid + '\'' +
                ", title='" + title + '\'' +
                ", writer='" + writer + '\'' +
                '}';
    }
}
