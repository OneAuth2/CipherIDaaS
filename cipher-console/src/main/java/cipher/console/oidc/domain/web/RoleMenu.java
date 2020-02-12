package cipher.console.oidc.domain.web;

import cipher.console.oidc.domain.BaseDomain;


public class RoleMenu extends BaseDomain {

        private Integer           roleId;
        private Integer           resourceId;
    private String            authList;
    private String resourceName;
    private String text;
    private String roleName;
    private String type;
    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

   

    public RoleMenu() {
        this.setType();
    }

    private void setType() {
        this.type = "呵呵";
    }


    public String getAuthList() {
        return authList;
    }

    public void setAuthList(String authList) {
        this.authList = authList;
    }

  



        public Integer getRoleId() {
            return roleId;
        }
        public void setRoleId(Integer roleId) {
            this.roleId = roleId;
        }
        public Integer getResourceId() {
            return resourceId;
        }
        public void setResourceId(Integer resourceId) {
            this.resourceId = resourceId;
        }



}
