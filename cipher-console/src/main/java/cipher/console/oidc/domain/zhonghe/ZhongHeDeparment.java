package cipher.console.oidc.domain.zhonghe;

/**
 * @Author: zt
 * @Date: 2018/9/27 14:37
 */
public class ZhongHeDeparment {


    /**
     * is_active : true
     * pk : 37
     * master_post : 1049
     * name : 众合本级
     * parent_pk : 1
     */
    private boolean is_active;
    private int pk;
    private int master_post;
    private String name;
    private int parent_pk;

    public boolean isIs_active() {
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

    @Override
    public String toString() {
        return "ZhongHeDeparment{" +
                "is_active=" + is_active +
                ", pk=" + pk +
                ", master_post=" + master_post +
                ", name='" + name + '\'' +
                ", parent_pk=" + parent_pk +
                '}';
    }
}
