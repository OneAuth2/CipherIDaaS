package cipher.console.oidc.model;

public class UserSubAccountModel  {

    private int applicationId;

    private String applicationName;

    private String subAccount;

    public int getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getSubAccount() {
        if (subAccount == null){
            subAccount = "";
        }
        return subAccount;
    }

    public void setSubAccount(String subAccount) {
        this.subAccount = subAccount;
    }
}
