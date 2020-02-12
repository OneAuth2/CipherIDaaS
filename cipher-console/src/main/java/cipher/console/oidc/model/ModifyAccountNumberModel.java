package cipher.console.oidc.model;

/**
 * @Author: zt
 * @Date: 2018/11/13 15:37
 */
public class ModifyAccountNumberModel {

    private String oldAccountNumber;

    private String newAccountNumber;

    public ModifyAccountNumberModel() {
    }

    public ModifyAccountNumberModel(String oldAccountNumber, String newAccountNumber) {
        this.oldAccountNumber = oldAccountNumber;
        this.newAccountNumber = newAccountNumber;
    }

    public String getOldAccountNumber() {
        return oldAccountNumber;
    }

    public void setOldAccountNumber(String oldAccountNumber) {
        this.oldAccountNumber = oldAccountNumber;
    }



    public String getNewAccountNumber() {
        return newAccountNumber;
    }

    public void setNewAccountNumber(String newAccountNumber) {
        this.newAccountNumber = newAccountNumber;
    }

    @Override
    public String toString() {
        return "ModifyAccountNumberModel{" +
                "oldAccountNumber='" + oldAccountNumber + '\'' +
                ", newAccountNumber='" + newAccountNumber + '\'' +
                '}';
    }
}
