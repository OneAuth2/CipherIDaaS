package cipher.console.oidc.domain.authsettingsdomain;

public class AccountBinding {
     //private int bindingAppSf;
     //private int autoBindingSf;
    private int bindingAppDd;
    private int autoBindingDd;
    private int bindingAppDb;
    private int autoBindingDb;
    private int bindingAppWx;
    private int autoBindingWx;

    private int isOpenMailBind;
    private int isOpenNumBind;

    public int getBindingAppDd() {
        return bindingAppDd;
    }

    public void setBindingAppDd(int bindingAppDd) {
        this.bindingAppDd = bindingAppDd;
    }

    public int getAutoBindingDd() {
        return autoBindingDd;
    }

    public void setAutoBindingDd(int autoBindingDd) {
        this.autoBindingDd = autoBindingDd;
    }

    public int getBindingAppDb() {
        return bindingAppDb;
    }

    public void setBindingAppDb(int bindingAppDb) {
        this.bindingAppDb = bindingAppDb;
    }

    public int getAutoBindingDb() {
        return autoBindingDb;
    }

    public void setAutoBindingDb(int autoBindingDb) {
        this.autoBindingDb = autoBindingDb;
    }

    public int getIsOpenMailBind() {
        return isOpenMailBind;
    }

    public void setIsOpenMailBind(int isOpenMailBind) {
        this.isOpenMailBind = isOpenMailBind;
    }

    public int getIsOpenNumBind() {
        return isOpenNumBind;
    }

    public void setIsOpenNumBind(int isOpenNumBind) {
        this.isOpenNumBind = isOpenNumBind;
    }
    public int getBindingAppWx() {
        return bindingAppWx;
    }

    public void setBindingAppWx(int bindingAppWx) {
        this.bindingAppWx = bindingAppWx;
    }

    public int getAutoBindingWx() {
        return autoBindingWx;
    }

    public void setAutoBindingWx(int autoBindingWx) {
        this.autoBindingWx = autoBindingWx;
    }

    @Override
    public String toString() {
        return "AccountBinding{" +
                ", bindingAppDd=" + bindingAppDd +
                ", autoBindingDd=" + autoBindingDd +
                ", bindingAppDb=" + bindingAppDb +
                ", autoBindingDb=" + autoBindingDb +
                ", isOpenMailBind=" + isOpenMailBind +
                ", isOpenNumBind=" + isOpenNumBind +
                '}';
    }
}
