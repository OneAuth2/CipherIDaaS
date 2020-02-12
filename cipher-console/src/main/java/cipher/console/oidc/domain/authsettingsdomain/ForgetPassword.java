package cipher.console.oidc.domain.authsettingsdomain;

public class ForgetPassword {
     private int isOpenForgetPwd;
     private int isOpenMailForget;
     private int isOpenNumForget;
    private int twoAuthForgetDd;//钉钉
    private int twoAuthForgetDb;//大白
    private int twoAuthForgetWx;//微信
   //  private int twoAuthForgetSf;

    public int getIsOpenForgetPwd() {
        return isOpenForgetPwd;
    }

    public void setIsOpenForgetPwd(int isOpenForgetPwd) {
        this.isOpenForgetPwd = isOpenForgetPwd;
    }

    public int getIsOpenMailForget() {
        return isOpenMailForget;
    }

    public void setIsOpenMailForget(int isOpenMailForget) {
        this.isOpenMailForget = isOpenMailForget;
    }

    public int getIsOpenNumForget() {
        return isOpenNumForget;
    }

    public void setIsOpenNumForget(int isOpenNumForget) {
        this.isOpenNumForget = isOpenNumForget;
    }

    public int getTwoAuthForgetDd() {
        return twoAuthForgetDd;
    }

    public void setTwoAuthForgetDd(int twoAuthForgetDd) {
        this.twoAuthForgetDd = twoAuthForgetDd;
    }

    public int getTwoAuthForgetDb() {
        return twoAuthForgetDb;
    }

    public void setTwoAuthForgetDb(int twoAuthForgetDb) {
        this.twoAuthForgetDb = twoAuthForgetDb;
    }

    public int getTwoAuthForgetWx() {
        return twoAuthForgetWx;
    }

    public void setTwoAuthForgetWx(int twoAuthForgetWx) {
        this.twoAuthForgetWx = twoAuthForgetWx;
    }

    @Override
    public String toString() {
        return "ForgetPassword{" +
                "isOpenForgetPwd=" + isOpenForgetPwd +
                ", isOpenMailForget=" + isOpenMailForget +
                ", isOpenNumForget=" + isOpenNumForget +
                ", twoAuthForgetDd=" + twoAuthForgetDd +
                ", twoAuthForgetDb=" + twoAuthForgetDb +
                '}';
    }
}
