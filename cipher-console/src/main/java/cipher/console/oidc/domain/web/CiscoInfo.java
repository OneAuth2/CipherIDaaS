package cipher.console.oidc.domain.web;

import java.io.Serializable;

public class CiscoInfo implements Serializable {

    private String buttonClicked;
    private String errFlag;
    private String errMsg;
    private String infoFlag;
    private String infoMsg;
    private String redirectUrl;
    private String networkName;
    private String switchUrl;


    public String getButtonClicked() {
        return buttonClicked;
    }

    public void setButtonClicked(String buttonClicked) {
        this.buttonClicked = buttonClicked;
    }

    public String getErrFlag() {
        return errFlag;
    }

    public void setErrFlag(String errFlag) {
        this.errFlag = errFlag;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getInfoFlag() {
        return infoFlag;
    }

    public void setInfoFlag(String infoFlag) {
        this.infoFlag = infoFlag;
    }

    public String getInfoMsg() {
        return infoMsg;
    }

    public void setInfoMsg(String infoMsg) {
        this.infoMsg = infoMsg;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getNetworkName() {
        return networkName;
    }

    public void setNetworkName(String networkName) {
        this.networkName = networkName;
    }

    public String getSwitchUrl() {
        return switchUrl;
    }

    public void setSwitchUrl(String switchUrl) {
        this.switchUrl = switchUrl;
    }
}
