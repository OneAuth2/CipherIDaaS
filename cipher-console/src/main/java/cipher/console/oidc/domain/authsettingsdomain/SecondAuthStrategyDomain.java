package cipher.console.oidc.domain.authsettingsdomain;

public class SecondAuthStrategyDomain {
     private int twoAuthDd;//钉钉扫码
     private int twoAuthDb;//大白扫码
     private int twoAuthNum;//手机随机码
     private int twoAuthMail;//邮件随机码
     private int twoAuthDingPush;//钉钉push认证
     private int twoAuthDt;//动态密码
    private int twoAuthWx;//微信认证
     private int twoAuthSf;
    private int twoAuthDingDt;


    public int getTwoAuthSf() {
        return twoAuthSf;
    }

    public void setTwoAuthSf(int twoAuthSf) {
        this.twoAuthSf = twoAuthSf;
    }

    public int getTwoAuthDingDt() {
        return twoAuthDingDt;
    }

    public void setTwoAuthDingDt(int twoAuthDingDt) {
        this.twoAuthDingDt = twoAuthDingDt;
    }

    public int getTwoAuthDd() {
        return twoAuthDd;
    }

    public void setTwoAuthDd(int twoAuthDd) {
        this.twoAuthDd = twoAuthDd;
    }

    public int getTwoAuthDb() {
        return twoAuthDb;
    }

    public void setTwoAuthDb(int twoAuthDb) {
        this.twoAuthDb = twoAuthDb;
    }

    public int getTwoAuthNum() {
        return twoAuthNum;
    }

    public void setTwoAuthNum(int twoAuthNum) {
        this.twoAuthNum = twoAuthNum;
    }

    public int getTwoAuthMail() {
        return twoAuthMail;
    }

    public void setTwoAuthMail(int twoAuthMail) {
        this.twoAuthMail = twoAuthMail;
    }


    public int getTwoAuthDingPush() {
        return twoAuthDingPush;
    }

    public void setTwoAuthDingPush(int twoAuthDingPush) {
        this.twoAuthDingPush = twoAuthDingPush;
    }

    @Override
    public String toString() {
        return "SecondAuthStrategyDomain{" +
                "twoAuthDd=" + twoAuthDd +
                ", twoAuthDb=" + twoAuthDb +
                ", twoAuthNum=" + twoAuthNum +
                ", twoAuthMail=" + twoAuthMail +
                ", twoAuthDingPush=" + twoAuthDingPush +
                ", twoAuthDt=" + twoAuthDt +
                ", twoAuthWx=" + twoAuthWx +
                '}';
    }

    public int getTwoAuthDt() {
        return twoAuthDt;
    }

    public void setTwoAuthDt(int twoAuthDt) {
        this.twoAuthDt = twoAuthDt;
    }

    public int getTwoAuthWx() {
        return twoAuthWx;
    }

    public void setTwoAuthWx(int twoAuthWx) {
        this.twoAuthWx = twoAuthWx;
    }
}
