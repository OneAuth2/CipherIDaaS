package cipher.console.oidc.domain.subapp;

import java.io.Serializable;
import java.util.List;

/**
 * 获取网易邮箱未读邮件数量的请求实体
 * @Author: zt
 * @Date: 2018/12/3 17:38
 */
public class WangyiUnReadMailCountDomain implements Serializable {

    /**
     * 帐号名	必填 匹配项
     */
    private String account_name;

    /**
     * 域名	必填 匹配项
     */
    private String domain;

    /**
     * 返回格式	选填	返回结果格式，支持json、xml、text三种格式，默认返回json
     */
    private String format;

    /**
     * 请求参数	必填 匹配项	params参数json格式:
     * {
     * fids:[1,5],
     * //1-收件箱;2-草稿箱;3-已发送;4-已删除;5-垃圾邮件
     * }
     */
    private String params;

    /**
     * 企业标识	必填 匹配项
     */
    private String product;

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }
}
