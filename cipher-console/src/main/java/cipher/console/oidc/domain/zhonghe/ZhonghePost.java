package cipher.console.oidc.domain.zhonghe;

import java.io.Serializable;

public class ZhonghePost implements Serializable {


    /**
     * department : 353
     * pk : 7
     * is_active : true
     * name : 待调整员工
     * parent_pk : 40
     */

    private int department;
    private int pk;
    private boolean is_active;
    private String name;
    private int parent_pk;

    public int getDepartment() {
        return department;
    }

    public void setDepartment(int department) {
        this.department = department;
    }

    public int getPk() {
        return pk;
    }

    public void setPk(int pk) {
        this.pk = pk;
    }

    public boolean getIsActive() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParent_pk() {
        return parent_pk;
    }

    public void setParent_pk(int parent_pk) {
        this.parent_pk = parent_pk;
    }
}
