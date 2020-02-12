package cipher.console.oidc.domain.web;/**
 * @author lqgzj
 * @date 2019-10-11
 */

import cipher.console.oidc.domain.BaseDomain;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

/**
 * @Author qiaoxi
 * @Date 2019-10-1115:11
 **/
public class WxGroupDomain extends BaseDomain implements Serializable {

    private Long id;
    private String name;
    private Long parentid;
    private Integer order;
    private String companyId;
    private String status;
    private String path = "";
    private Integer parentGroupId;


    @Override
    public String getCompanyId() {
        return companyId;
    }

    @Override
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentid() {
        return parentid;
    }

    public void setParentid(Long parentid) {
        this.parentid = parentid;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getParentGroupId() {
        return parentGroupId;
    }

    public void setParentGroupId(Integer parentGroupId) {
        this.parentGroupId = parentGroupId;
    }

    public void constructPath(String tmpPath) {
        if (StringUtils.isEmpty(tmpPath)) {
            return;
        }
        if (StringUtils.isEmpty(this.path)) {
            this.path = tmpPath;
        } else {
            this.path = tmpPath + "/" + this.path;
        }
    }
}
