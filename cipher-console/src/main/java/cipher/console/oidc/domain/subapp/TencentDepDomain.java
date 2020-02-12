package cipher.console.oidc.domain.subapp;

import java.io.Serializable;
import java.util.List;

/**
 * 获取腾讯邮箱部门信息
 * @Author: zt
 * @Date: 2018/11/29 15:16
 */
public class TencentDepDomain implements Serializable {


    /**
     * errcode : 0
     * errmsg : ok
     * department : [{"id":2,"name":"广州研发中心","parentid":1,"order":10},{"id":3,"name":"邮箱产品部","parentid":2,"order":40}]
     */

    private int errcode;
    private String errmsg;
    private List<DepartmentBean> department;

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

    public List<DepartmentBean> getDepartment() {
        return department;
    }

    public void setDepartment(List<DepartmentBean> department) {
        this.department = department;
    }

    public static class DepartmentBean {
        /**
         * id : 2
         * name : 广州研发中心
         * parentid : 1
         * order : 10
         */

        private Long id;
        private String name;
        private int parentid;
        private int order;

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

        public int getParentid() {
            return parentid;
        }

        public void setParentid(int parentid) {
            this.parentid = parentid;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }
    }
}
