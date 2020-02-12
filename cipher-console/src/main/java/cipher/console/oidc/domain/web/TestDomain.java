package cipher.console.oidc.domain.web;

import cipher.console.oidc.domain.BaseDomain;

public class TestDomain extends BaseDomain{
    int id;
    String name;
    String nickName;
    String adGroup;
    String mail;
    String phone;
    String group;
    String applications;
    String method;
    String status;
    String edit;
    String operation;

    public TestDomain(int id, String name, String nickName, String adGroup, String mail, String phone, String group, String applications, String method, String status, String edit, String operation) {
        this.id = id;
        this.name = name;
        this.nickName = nickName;
        this.adGroup = adGroup;
        this.mail = mail;
        this.phone = phone;
        this.group = group;
        this.applications = applications;
        this.method = method;
        this.status = status;
        this.edit = edit;
        this.operation = operation;
    }

    public TestDomain() {
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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAdGroup() {
        return adGroup;
    }

    public void setAdGroup(String adGroup) {
        this.adGroup = adGroup;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getApplications() {
        return applications;
    }

    public void setApplications(String applications) {
        this.applications = applications;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEdit() {
        return edit;
    }

    public void setEdit(String edit) {
        this.edit = edit;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
}
