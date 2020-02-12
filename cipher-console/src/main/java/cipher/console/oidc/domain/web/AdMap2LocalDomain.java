package cipher.console.oidc.domain.web;

import cipher.console.oidc.domain.BaseDomain;

import java.util.Date;

/**
 * @Author: zt
 * @Date: 2018/10/22 17:08
 */
public class AdMap2LocalDomain extends BaseDomain {

    private Integer id;

    /**
     * ad域当中的属性
     */
    private String adKey;

    /**
     * 对应本地数据库当中的属性
     */
    private String localVal;

    private Date createTime;

    private Date modifyTime;


    private String displayName;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public AdMap2LocalDomain() {
    }

    public AdMap2LocalDomain(String adKey, String localVal) {
        this.adKey = adKey;
        this.localVal = localVal;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAdKey() {
        return adKey;
    }

    public void setAdKey(String adKey) {
        this.adKey = adKey;
    }

    public String getLocalVal() {
        return localVal;
    }

    public void setLocalVal(String localVal) {
        this.localVal = localVal;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Override
    public String toString() {
        return "AdMap2LocalDomain{" +
                "id=" + id +
                ", adKey='" + adKey + '\'' +
                ", localVal='" + localVal + '\'' +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                '}';
    }
}
