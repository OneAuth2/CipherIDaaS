package cipher.console.oidc.domain.web;

import java.io.Serializable;

/**
 * @Author: zt
 * @Date: 2019-05-14 11:17
 */
public class DingSimpleGroup implements Serializable {

    private Long id;
    private String name;

    private Long parentid;

    private boolean createDeptGroup;
    private boolean autoAddUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public boolean isCreateDeptGroup() {
        return createDeptGroup;
    }

    public void setCreateDeptGroup(boolean createDeptGroup) {
        this.createDeptGroup = createDeptGroup;
    }

    public boolean isAutoAddUser() {
        return autoAddUser;
    }

    public void setAutoAddUser(boolean autoAddUser) {
        this.autoAddUser = autoAddUser;
    }

    public Long getParentid() {
        return parentid;
    }

    public void setParentid(Long parentid) {
        this.parentid = parentid;
    }
}
