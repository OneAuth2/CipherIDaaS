package cipher.console.oidc.domain.web;

import cipher.console.oidc.domain.BaseParamDomain;
import cipher.console.oidc.enums.VisitorAuthSource;
import cipher.console.oidc.enums.VisitorAuthType;

import java.io.Serializable;

/**
 * Created by 95744 on 2018/9/19.
 */
public class VisitorInfoDomain extends BaseParamDomain {

    /**
     * 访客来源
     * @see VisitorAuthSource
     */
    private int sourceType;

    //访客有效时间范围
    private int timeRange;

    /**
     * 访客认证方式
     * @see VisitorAuthType
     * */
    private int authType;

    //是否需要员工信息
    //0-否，1-是
    private int needStuffInfo;

    //是否开启员工扫码
    //0-否，1-是
    private int needStuffScan;

    //赛赋扫码
    private int authSf;

    //钉钉扫码
    private int authDd;

    //大白认证
    private int authDb;

    public VisitorInfoDomain() {
        this.sourceType = VisitorAuthSource.VISITOR_REGISTER.getSourceId();
        this.timeRange = 24;
        this.authType = VisitorAuthType.SMS_CODE_TO_VISITOR.getAuthType();
    }

    public VisitorInfoDomain(int sourceType, int timeRange, int authType, int needStuffInfo, int needStuffScan,int authSf,int authDd,int authDb) {
        this.sourceType = sourceType;
        this.timeRange = timeRange;
        this.authType = authType;
        this.needStuffInfo = needStuffInfo;
        this.needStuffScan = needStuffScan;
        this.authSf = authSf;
        this.authDd = authDd;
        this.authDb = authDb;
    }

    public int getSourceType() {
        return sourceType;
    }

    public void setSourceType(int sourceType) {
        this.sourceType = sourceType;
    }

    public int getTimeRange() {
        return timeRange;
    }

    public void setTimeRange(int timeRange) {
        this.timeRange = timeRange;
    }


    public int getAuthType() {
        return authType;
    }

    public void setAuthType(int authType) {
        this.authType = authType;
    }

    public int getNeedStuffInfo() {
        return needStuffInfo;
    }

    public void setNeedStuffInfo(int needStuffInfo) {
        this.needStuffInfo = needStuffInfo;
    }

    public int getNeedStuffScan() {
        return needStuffScan;
    }

    public void setNeedStuffScan(int needStuffScan) {
        this.needStuffScan = needStuffScan;
    }

    public int getAuthSf() {
        return authSf;
    }

    public void setAuthSf(int authSf) {
        this.authSf = authSf;
    }

    public int getAuthDd() {
        return authDd;
    }

    public void setAuthDd(int authDd) {
        this.authDd = authDd;
    }

    public int getAuthDb() {
        return authDb;
    }

    public void setAuthDb(int authDb) {
        this.authDb = authDb;
    }
}
