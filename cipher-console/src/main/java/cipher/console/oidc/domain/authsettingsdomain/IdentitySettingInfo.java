package cipher.console.oidc.domain.authsettingsdomain;

import java.util.Date;

public class IdentitySettingInfo {
    private int id;
    private String company_uuid;
    private int type;
    private String auth_mode;
    private String binding_flow;
    private String regist_flow;
    private String forget_flow;
    private Date create_time;
    private Date modify_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompany_uuid() {
        return company_uuid;
    }

    public void setCompany_uuid(String company_uuid) {
        this.company_uuid = company_uuid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAuth_mode() {
        return auth_mode;
    }

    public void setAuth_mode(String auth_mode) {
        this.auth_mode = auth_mode;
    }

    public String getBinding_flow() {
        return binding_flow;
    }

    public void setBinding_flow(String binding_flow) {
        this.binding_flow = binding_flow;
    }

    public String getRegist_flow() {
        return regist_flow;
    }

    public void setRegist_flow(String regist_flow) {
        this.regist_flow = regist_flow;
    }

    public String getForget_flow() {
        return forget_flow;
    }

    public void setForget_flow(String forget_flow) {
        this.forget_flow = forget_flow;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getModify_time() {
        return modify_time;
    }

    public void setModify_time(Date modify_time) {
        this.modify_time = modify_time;
    }

    @Override
    public String toString() {
        return "IdentitySettingInfo{" +
                "id=" + id +
                ", company_uuid='" + company_uuid + '\'' +
                ", type=" + type +
                ", auth_mode='" + auth_mode + '\'' +
                ", binding_flow='" + binding_flow + '\'' +
                ", regist_flow='" + regist_flow + '\'' +
                ", forget_flow='" + forget_flow + '\'' +
                ", create_time=" + create_time +
                ", modify_time=" + modify_time +
                '}';
    }
}
