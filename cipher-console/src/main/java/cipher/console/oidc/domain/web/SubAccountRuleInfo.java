package cipher.console.oidc.domain.web;


import java.io.Serializable;

public class SubAccountRuleInfo implements Serializable {
    private int assManual;
    private int assPrimaryAccount;
    private int assEmail;
    private int assEmailPrefix;
    private int assTelephone;
    private int assWorkers;
    private int assPwdManual;
    private int assPwdPrimaryAccount;

    public int getAssManual() {
        return assManual;
    }

    public void setAssManual(int assManual) {
        this.assManual = assManual;
    }

    public int getAssPrimaryAccount() {
        return assPrimaryAccount;
    }

    public void setAssPrimaryAccount(int assPrimaryAccount) {
        this.assPrimaryAccount = assPrimaryAccount;
    }

    public int getAssEmail() {
        return assEmail;
    }

    public void setAssEmail(int assEmail) {
        this.assEmail = assEmail;
    }

    public int getAssEmailPrefix() {
        return assEmailPrefix;
    }

    public void setAssEmailPrefix(int assEmailPrefix) {
        this.assEmailPrefix = assEmailPrefix;
    }

    public int getAssTelephone() {
        return assTelephone;
    }

    public void setAssTelephone(int assTelephone) {
        this.assTelephone = assTelephone;
    }

    public int getAssWorkers() {
        return assWorkers;
    }

    public void setAssWorkers(int assWorkers) {
        this.assWorkers = assWorkers;
    }

    public int getAssPwdManual() {
        return assPwdManual;
    }

    public void setAssPwdManual(int assPwdManual) {
        this.assPwdManual = assPwdManual;
    }

    public int getAssPwdPrimaryAccount() {
        return assPwdPrimaryAccount;
    }

    public void setAssPwdPrimaryAccount(int assPwdPrimaryAccount) {
        this.assPwdPrimaryAccount = assPwdPrimaryAccount;
    }

    @Override
    public String toString() {
        return "SubAccountRuleInfo{" +
                "assManual=" + assManual +
                ", assPrimaryAccount=" + assPrimaryAccount +
                ", assEmail=" + assEmail +
                ", assEmailPrefix=" + assEmailPrefix +
                ", assTelephone=" + assTelephone +
                ", assWorkers=" + assWorkers +
                ", assPwdManual=" + assPwdManual +
                ", assPwdPrimaryAccount=" + assPwdPrimaryAccount +
                '}';
    }
}
