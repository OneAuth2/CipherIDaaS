package cipher.console.oidc.domain.web;/**
 * @author lqgzj
 * @date 2019-10-12
 */

import java.io.Serializable;

/**
 * @Author qiaoxi
 * @Date 2019-10-1211:31
 **/
public class WxSimpleGroup implements Serializable {
    private Long id;
    private String name;

    private Long parentid;


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

    public Long getParentid() {
        return parentid;
    }

    public void setParentid(Long parentid) {
        this.parentid = parentid;
    }
}
