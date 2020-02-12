package cipher.console.oidc.domain.zhonghe;

import java.io.Serializable;

public class ZhongheDepartment implements Serializable {


    /**
     * is_active : true
     * pk : 467
     * master_post : 536
     * name : 达康环境（本级）
     * parent_pk : 68
     */

    private boolean is_active;
    private int pk;
    private int master_post;
    private String name;
    private int parent_pk;

    public boolean getIsActive() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public int getPk() {
        return pk;
    }

    public void setPk(int pk) {
        this.pk = pk;
    }

    public int getMaster_post() {
        return master_post;
    }

    public void setMaster_post(int master_post) {
        this.master_post = master_post;
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
