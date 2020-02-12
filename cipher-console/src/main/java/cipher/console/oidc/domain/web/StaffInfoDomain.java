package cipher.console.oidc.domain.web;

import cipher.console.oidc.domain.BaseParamDomain;
import cipher.console.oidc.enums.StaffAuthSource;
import cipher.console.oidc.enums.StaffAuthType;

import java.io.Serializable;

/**
 * Created by 95744 on 2018/9/19.
 */

/**
 * modify by cozi
 * 2019-5-8 11:18
 * 删除 Totp因子认证 和  赛赋因子认证 1.5.4
 */

public class StaffInfoDomain extends BaseParamDomain {

    /**
     * 认证方式编号
     * @see StaffAuthType
     * */
    private int authType;

    /**
     * 认证源编号
     * @see StaffAuthSource
     * */
    private int sourceType;

    //MAC认证
    //0关闭，1-开启
    private int macFactor;

    //时间单位
    //0-小时，1-天
    private int timeUnits;

    //时间范围
    private int timeRanges;

    public StaffInfoDomain() {
        authType = StaffAuthType.USER_NAME_AND_PASSWORD.getAuthTypeId();
        timeRanges = 7;
        timeUnits = 1;
    }

    public StaffInfoDomain(int authType,int sourceType,int macFactor, int timeUnits, int timeRanges) {
        this.authType = authType;
        this.sourceType = sourceType;
        this.macFactor = macFactor;
        this.timeUnits = timeUnits;
        this.timeRanges = timeRanges;

    }

    public int getAuthType() {
        return authType;
    }

    public void setAuthType(int authType) {
        this.authType = authType;
    }

    public int getSourceType() {
        return sourceType;
    }

    public void setSourceType(int sourceType) {
        this.sourceType = sourceType;
    }

    public int getMacFactor() {
        return macFactor;
    }

    public void setMacFactor(int macFactor) {
        this.macFactor = macFactor;
    }

    public int getTimeUnits() {
        return timeUnits;
    }

    public void setTimeUnits(int timeUnits) {
        this.timeUnits = timeUnits;
    }

    public int getTimeRanges() {
        return timeRanges;
    }

    public void setTimeRanges(int timeRanges) {
        this.timeRanges = timeRanges;
    }
}
