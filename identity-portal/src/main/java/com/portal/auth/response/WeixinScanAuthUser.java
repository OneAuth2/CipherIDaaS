package com.portal.auth.response;



import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * TODO:
 * create by liuying at 2019/9/29
 *
 * @author liuying
 * @since 2019/9/29 15:50
 */
public class WeixinScanAuthUser implements Serializable {

    private int errcode;
    private String errmsg;
    private String userid;
    private String name;
    private Date department;
    private Date order;
    private String position;
    private String mobile;
    private String gender;
    private String email;
    private List<Integer> is_leader_in_dept;
    private String avatar;
    private String telephone;
    private int enable;
    private String alias;
    private String address;
    private Extattr extattr;
    private int status;
    private String qr_code;
    private String external_position;
    private External_profile external_profile;

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDepartment() {
        return department;
    }

    public void setDepartment(Date department) {
        this.department = department;
    }

    public Date getOrder() {
        return order;
    }

    public void setOrder(Date order) {
        this.order = order;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Integer> getIs_leader_in_dept() {
        return is_leader_in_dept;
    }

    public void setIs_leader_in_dept(List<Integer> is_leader_in_dept) {
        this.is_leader_in_dept = is_leader_in_dept;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getEnable() {
        return enable;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Extattr getExtattr() {
        return extattr;
    }

    public void setExtattr(Extattr extattr) {
        this.extattr = extattr;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getQr_code() {
        return qr_code;
    }

    public void setQr_code(String qr_code) {
        this.qr_code = qr_code;
    }

    public String getExternal_position() {
        return external_position;
    }

    public void setExternal_position(String external_position) {
        this.external_position = external_position;
    }

    public External_profile getExternal_profile() {
        return external_profile;
    }

    public void setExternal_profile(External_profile external_profile) {
        this.external_profile = external_profile;
    }

    public class Text {

        private String value;
        public void setValue(String value) {
            this.value = value;
        }
        public String getValue() {
            return value;
        }

    }

    public class Attrs {

        private int type;
        private String name;
        private Text text;
        public void setType(int type) {
            this.type = type;
        }
        public int getType() {
            return type;
        }

        public void setName(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }

        public void setText(Text text) {
            this.text = text;
        }
        public Text getText() {
            return text;
        }

    }

    public class Extattr {

        private List<Attrs> attrs;
        public void setAttrs(List<Attrs> attrs) {
            this.attrs = attrs;
        }
        public List<Attrs> getAttrs() {
            return attrs;
        }

    }
    public class External_attr {

        private int type;
        private String name;
        private Text text;
        public void setType(int type) {
            this.type = type;
        }
        public int getType() {
            return type;
        }

        public void setName(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }

        public void setText(Text text) {
            this.text = text;
        }
        public Text getText() {
            return text;
        }

    }

    public class External_profile {

        private String external_corp_name;
        private List<External_attr> external_attr;
        public void setExternal_corp_name(String external_corp_name) {
            this.external_corp_name = external_corp_name;
        }
        public String getExternal_corp_name() {
            return external_corp_name;
        }

        public void setExternal_attr(List<External_attr> external_attr) {
            this.external_attr = external_attr;
        }
        public List<External_attr> getExternal_attr() {
            return external_attr;
        }

    }

    @Override
    public String toString() {
        return "GetWeixinCallBackResp{" +
                "errcode=" + errcode +
                ", errmsg='" + errmsg + '\'' +
                ", userid='" + userid + '\'' +
                ", name='" + name + '\'' +
                ", department=" + department +
                ", order=" + order +
                ", position='" + position + '\'' +
                ", mobile='" + mobile + '\'' +
                ", gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                ", is_leader_in_dept=" + is_leader_in_dept +
                ", avatar='" + avatar + '\'' +
                ", telephone='" + telephone + '\'' +
                ", enable=" + enable +
                ", alias='" + alias + '\'' +
                ", address='" + address + '\'' +
                ", extattr=" + extattr +
                ", status=" + status +
                ", qr_code='" + qr_code + '\'' +
                ", external_position='" + external_position + '\'' +
                ", external_profile=" + external_profile +
                '}';
    }
}
