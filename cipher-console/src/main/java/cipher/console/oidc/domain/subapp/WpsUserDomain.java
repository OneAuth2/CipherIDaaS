package cipher.console.oidc.domain.subapp;

import java.io.Serializable;

/**
 * @Author: zt
 * @Date: 2018/12/13 17:21
 */
public class WpsUserDomain implements Serializable {


    /**
     * company_id : 2
     * created_at : 1544685977
     * employee_id :
     * ext_phone_number :
     * sort_key : 0
     * status : 1
     * title :
     * unique_name : zt
     * updated_at : 1544685977
     * user_id : 6
     */

    private int company_id;
    private long created_at;
    private String employee_id;
    private String ext_phone_number;
    private int sort_key;
    private int status;
    private String title;
    private String unique_name;
    private long updated_at;
    private int user_id;

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public long getCreated_at() {
        return created_at;
    }

    public void setCreated_at(long created_at) {
        this.created_at = created_at;
    }

    public String getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
    }

    public String getExt_phone_number() {
        return ext_phone_number;
    }

    public void setExt_phone_number(String ext_phone_number) {
        this.ext_phone_number = ext_phone_number;
    }

    public int getSort_key() {
        return sort_key;
    }

    public void setSort_key(int sort_key) {
        this.sort_key = sort_key;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUnique_name() {
        return unique_name;
    }

    public void setUnique_name(String unique_name) {
        this.unique_name = unique_name;
    }

    public long getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(long updated_at) {
        this.updated_at = updated_at;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
