package cipher.console.oidc.domain.subapp;

import java.io.Serializable;

/**
 * @Author: zt
 * @Date: 2018/12/13 16:41
 */
public class WpsQueryUserListDomain implements Serializable {

    /**
     * 用户账号 可选
     */
    private String unique_name;

    /**
     *分页起始下标	是，默认0 可选
     */
    private int offset;

    /**
     *返回的数据条数，≤0不限制返回条数	是，默认10 可选
     */
    private int limit;

    public String getUnique_name() {
        return unique_name;
    }

    public void setUnique_name(String unique_name) {
        this.unique_name = unique_name;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
