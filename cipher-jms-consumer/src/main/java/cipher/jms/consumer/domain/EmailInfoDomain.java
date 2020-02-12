package cipher.jms.consumer.domain;

import java.io.Serializable;

/**
 * Created by 95744 on 2018/8/23.
 */
public class EmailInfoDomain implements Serializable {

    private Integer id;
    private String smtp;
    private int port;
    private String title;
    private String account;
    private String pwd;
    private String describes;
    private String dredgeTitle;
    private String dredgeDescribes;
    private String seedkeyTitle;
    private String seedkeyDescribes;
    private String imgPath;
    private String imgId;
    private int isSSL;
    private int sendTime;
    private int intervalTime;
    private int extendTime;
    private int effectiveTime;
    private String companyId;


    private String informMsg;

    public String getInformMsg() {
        return informMsg;
    }

    public void setInformMsg(String informMsg) {
        this.informMsg = informMsg;
    }

    public String getDredgeTitle() {
        return dredgeTitle;
    }

    public void setDredgeTitle(String dredgeTitle) {
        this.dredgeTitle = dredgeTitle;
    }

    public String getDredgeDescribes() {
        return dredgeDescribes;
    }

    public void setDredgeDescribes(String dredgeDescribes) {
        this.dredgeDescribes = dredgeDescribes;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public int getSendTime() {
        return sendTime;
    }

    public void setSendTime(int sendTime) {
        this.sendTime = sendTime;
    }

    private String kapataCode;


    public String getKapataCode() {
        return kapataCode;
    }

    public void setKapataCode(String kapataCode) {
        this.kapataCode = kapataCode;
    }

    public int getIsSSL() {
        return isSSL;
    }

    public void setIsSSL(int isSSL) {
        this.isSSL = isSSL;
    }

    private String emailAddress;


    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSmtp() {
        return smtp;
    }

    public void setSmtp(String smtp) {
        this.smtp = smtp;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getDescribes() {
        return describes;
    }

    public void setDescribes(String describes) {
        this.describes = describes;
    }

    public int getIntervalTime() {
        return intervalTime;
    }

    public void setIntervalTime(int intervalTime) {
        this.intervalTime = intervalTime;
    }

    public int getExtendTime() {
        return extendTime;
    }

    public void setExtendTime(int extendTime) {
        this.extendTime = extendTime;
    }

    public int getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(int effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getImgId() {
        return imgId;
    }

    public void setImgId(String imgId) {
        this.imgId = imgId;
    }

    public String getSeedkeyTitle() {
        return seedkeyTitle;
    }

    public void setSeedkeyTitle(String seedkeyTitle) {
        this.seedkeyTitle = seedkeyTitle;
    }

    public String getSeedkeyDescribes() {
        return seedkeyDescribes;
    }

    public void setSeedkeyDescribes(String seedkeyDescribes) {
        this.seedkeyDescribes = seedkeyDescribes;
    }
}
