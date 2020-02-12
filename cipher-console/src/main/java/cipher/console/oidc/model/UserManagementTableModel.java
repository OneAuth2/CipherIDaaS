package cipher.console.oidc.model;

import cipher.console.oidc.domain.BaseDomain;
import cipher.console.oidc.domain.web.*;

import java.util.List;

public class UserManagementTableModel extends BaseDomain {
    private int id;
    private String accountNumber;
    private int groupId;

    private UserInfoDomain user;
    private GroupInfoDomain group;
    private List<ApplicationInfoDomain> applications;
    private List<UserAuthorizationMapDomain> methods;
    private TableData tableData;

    public UserManagementTableModel(int id, String accountNumber, int groupId, UserInfoDomain user, GroupInfoDomain group, List<ApplicationInfoDomain> applications, List<UserAuthorizationMapDomain> methods) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.groupId = groupId;
        this.user = user;
        this.group = group;
        this.applications = applications;
        this.methods = methods;
    }

    public UserManagementTableModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public UserInfoDomain getUser() {
        return user;
    }

    public void setUser(UserInfoDomain user) {
        this.user = user;
    }

    public GroupInfoDomain getGroup() {
        return group;
    }

    public void setGroup(GroupInfoDomain group) {
        this.group = group;
    }

    public List<ApplicationInfoDomain> getApplications() {
        return applications;
    }

    public void setApplications(List<ApplicationInfoDomain> applications) {
        this.applications = applications;
    }

    public List<UserAuthorizationMapDomain> getMethods() {
        return methods;
    }

    public void setMethods(List<UserAuthorizationMapDomain> methods) {
        this.methods = methods;
    }

    public TableData getMainTableData(){
        tableData = new TableData();
        tableData.setId(id);
        tableData.setName(user.getUserName());
        tableData.setNickName(user.getNickname());
        tableData.setAdGroup("AD组");
        tableData.setMail(user.getMail());
        tableData.setPhone(user.getPhoneNumber());
        if (group == null){
            tableData.setGroup("");
        }else {
            tableData.setGroup(group.getGroupName());
        }
        tableData.setApplications(getApplicationsName());
        tableData.setMethod(getMethodsName());
        tableData.setStatus(user.getAccountStatus());
        return tableData;
    }

    private String getApplicationsName(){
        String appName = "";
        if (applications == null){
            return appName;
        }
        for (ApplicationInfoDomain applicationInfoDomain : applications){
            appName += applicationInfoDomain.getApplicationName()+' ';
        }
        return appName;
    }

    private String getMethodsName(){
        String methodName = "";
        for (UserAuthorizationMapDomain userAuthorizationMapDomain : methods){
            methodName += userAuthorizationMapDomain.getAccountAuthorizedMethod()+' ';
        }
        return methodName;
    }

    public class TableData extends BaseDomain{
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

        public TableData(int id, String name, String nickName, String adGroup, String mail, String phone, String group, String applications, String method, String status, String edit, String operation) {
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

        public TableData() {
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

}
