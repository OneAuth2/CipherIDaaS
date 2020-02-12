package cipher.console.oidc.domain.subapp;

import java.io.Serializable;

/**
 * @Author: zt
 * @Date: 2018/12/12 15:00
 */
public class WpsDeptDomain implements Serializable {


    /**
     * company_id : 1
     * created_at : 1494907068
     * display_name : test
     * group_id : 1
     * id : 1
     * name : WPS
     * parent_id : 0
     * path :
     * path_name : WPS
     * sort_key : 1
     * source :
     * status : 1
     * updated_at : 1512948645
     */

    private Long company_id;
    private Long created_at;
    private String display_name;
    private Long group_id;
    private String id;
    private String name;
    private String parent_id;
    private String path;
    private String path_name;
    private int sort_key;
    private String source;
    private int status;
    private Long updated_at;

    public Long getCompany_id() {
        return company_id;
    }

    public void setCompany_id(Long company_id) {
        this.company_id = company_id;
    }

    public Long getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Long created_at) {
        this.created_at = created_at;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public Long getGroup_id() {
        return group_id;
    }

    public void setGroup_id(Long group_id) {
        this.group_id = group_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath_name() {
        return path_name;
    }

    public void setPath_name(String path_name) {
        this.path_name = path_name;
    }

    public int getSort_key() {
        return sort_key;
    }

    public void setSort_key(int sort_key) {
        this.sort_key = sort_key;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Long getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Long updated_at) {
        this.updated_at = updated_at;
    }
}
