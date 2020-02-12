package cipher.console.oidc.domain.authsettingsdomain;



public class IdentityAuthentication {
     private String authMethod;
     private int otherAuthDd;//钉钉扫码
     private int otherAuthDb;//大白扫码
     private int otherAuthNum;//手机随机码
    private int otherAuthWx;//企业微信扫码
    private int otherAuthDt;//动态密码
  //   private int otherAuthSf;
    // private int otherAuthDingDt;
    private int firstLogin;
    private int infoCollection;



    public int getOtherAuthDt() {
        return otherAuthDt;
    }

    public void setOtherAuthDt(int otherAuthDt) {
        this.otherAuthDt = otherAuthDt;
    }

    private int infoCollectionMail;
    private int DpSend;//动态密码下发

    public int getDpSend() {
        return DpSend;
    }

    public void setDpSend(int dpSend) {
        DpSend = dpSend;
    }

    public int getOtherAuthWx() {
        return otherAuthWx;
    }

    public void setOtherAuthWx(int otherAuthWx) {
        this.otherAuthWx = otherAuthWx;
    }

    public String getAuthMethod() {
        return authMethod;
    }

    public void setAuthMethod(String authMethod) {
        this.authMethod = authMethod;
    }


    public int getOtherAuthDd() {
        return otherAuthDd;
    }

    public void setOtherAuthDd(int otherAuthDd) {
        this.otherAuthDd = otherAuthDd;
    }

    public int getOtherAuthDb() {
        return otherAuthDb;
    }

    public void setOtherAuthDb(int otherAuthDb) {
        this.otherAuthDb = otherAuthDb;
    }

    public int getOtherAuthNum() {
        return otherAuthNum;
    }

    public void setOtherAuthNum(int otherAuthNum) {
        this.otherAuthNum = otherAuthNum;
    }


    public int getFirstLogin() {
        return firstLogin;
    }

    public void setFirstLogin(int firstLogin) {
        this.firstLogin = firstLogin;
    }

    public int getInfoCollection() {
        return infoCollection;
    }

    public void setInfoCollection(int infoCollection) {
        this.infoCollection = infoCollection;
    }

    public int getInfoCollectionMail() {
        return infoCollectionMail;
    }

    public void setInfoCollectionMail(int infoCollectionMail) {
        this.infoCollectionMail = infoCollectionMail;
    }

    @Override
    public String toString() {
        return "IdentityAuthentication{" +
                "authMethod='" + authMethod + '\'' +
                ", otherAuthDd=" + otherAuthDd +
                ", otherAuthDb=" + otherAuthDb +
                ", otherAuthNum=" + otherAuthNum +
                ", otherAuthWx=" + otherAuthWx +
                ", firstLogin=" + firstLogin +
                ", infoCollection=" + infoCollection +
                ", infoCollectionMail=" + infoCollectionMail +
                '}';
    }
}
