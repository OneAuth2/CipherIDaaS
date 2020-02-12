package cipher.jms.consumer;


import cipher.jms.consumer.jms.base.BaseMessage;


public class EmailCodeInfoDomain extends BaseMessage {


    private String mail;

    public EmailCodeInfoDomain() {
    }

    public EmailCodeInfoDomain(String mail) {
        this.mail = mail;
    }


    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Override
    public String toString() {
        return "EmailCodeInfoDomain{" +
                "mail='" + mail + '\'' +
                '}';
    }
}
