package cipher.console.oidc.domain.subapp;

import java.io.Serializable;
import java.util.List;

/**
 * 每刻报销系统创建账号实体
 *
 * @Author: zt
 * @Date: 2018/12/4 16:45
 */
public class MaycurCreateSubReqDomain implements Serializable {

    private long timestamp;

    private List<DataBean> data;


    public long getTimestamp() {
        if (timestamp == 0L) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {

        /**
         *  Y 手机号码（也可提供邮箱）
         */
        private String mobile;

        /**
         *  Y 邮箱(也可提供手机号码）
         */
        private String email;

        /**
         * Y 员工姓名
         */
        private String name;

        /**
         *  Y 主键 员工工号
         */
        private String employeeId;

        /**
         *  员工职级名称，如果系统无该职级，
         */
        private String rank;

        /**
         * 员工职务编码
         */
        private String position;

        /**
         *  默认：ENABLE  员工在职状态，	可以选择ENABLE,	DIS
         */
        private String status;

        /**
         * 姓
         */
        private String firstName;

        /**
         * 名
         */
        private String lastName;

        /**
         *  入职时间
         */
        private long hireDate;

        /**
         * 属性，可被用于流程
         */
        private String tag;


        /**
         *  外联平台，可选值	WEIXIN、DING_TA
         */
        private String source;

        /**
         * 外联平台key	格式:外联平台的企业ID+" ex:dingCorpId+""+dingUserId,weixinCo
         */
        private String sourceId;

        /**
         * 常驻地，传入每刻地址编码，具体见备
         */
        private String residenceCode;

        /***
         * 备注
         */
        private String note;

        /**
         *  Y 部门编码
         */
        private String departmentBizCode;

        /**
         * N 部门内上级工号
         */
        private String managerId;

        /**
         *  非必须 默认值Y 是否承担部门，如果不是承担部 门，请输入	N
         */
        private String cover;


        private List<DepartmentsBean> departments;


        public long getHireDate() {
            return hireDate;
        }

        public void setHireDate(long hireDate) {
            this.hireDate = hireDate;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getSourceId() {
            return sourceId;
        }

        public void setSourceId(String sourceId) {
            this.sourceId = sourceId;
        }

        public String getResidenceCode() {
            return residenceCode;
        }

        public void setResidenceCode(String residenceCode) {
            this.residenceCode = residenceCode;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public String getDepartmentBizCode() {
            return departmentBizCode;
        }

        public void setDepartmentBizCode(String departmentBizCode) {
            this.departmentBizCode = departmentBizCode;
        }

        public String getManagerId() {
            return managerId;
        }

        public void setManagerId(String managerId) {
            this.managerId = managerId;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmployeeId() {
            return employeeId;
        }

        public void setEmployeeId(String employeeId) {
            this.employeeId = employeeId;
        }

        public String getRank() {
            return rank;
        }

        public void setRank(String rank) {
            this.rank = rank;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getStatus() {
            if(null==status){
                status="ENABLE";
            }
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public List<DepartmentsBean> getDepartments() {
            return departments;
        }

        public void setDepartments(List<DepartmentsBean> departments) {
            this.departments = departments;
        }

        /**
         * 员工默认业务实体
         */
        public static class DepartmentsBean {
            /**
             * departmentBizCode : YFA
             * managerId : 000
             */

            private String departmentBizCode;
            private String managerId;

            public String getDepartmentBizCode() {
                return departmentBizCode;
            }

            public void setDepartmentBizCode(String departmentBizCode) {
                this.departmentBizCode = departmentBizCode;
            }

            public String getManagerId() {
                return managerId;
            }

            public void setManagerId(String managerId) {
                this.managerId = managerId;
            }
        }
    }
}
