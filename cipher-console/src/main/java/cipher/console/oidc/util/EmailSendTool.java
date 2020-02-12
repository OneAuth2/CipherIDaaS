package cipher.console.oidc.util;
import cipher.console.oidc.domain.web.EmailInfoDomain;
import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class EmailSendTool {
    // 邮箱服务器
    private String host;
    // 这个是你的邮箱用户名
    private String username;
    // 你的邮箱密码
    private String password;

    private String mail_head_name = "this is head of this mail";

    private String mail_head_value = "this is head of this mail";

    private String mail_to;

    private String mail_from;

    private String mail_subject = "this is the subject of this test mail";

    private String mail_body = "this is the mail_body of this test mail";

    private String personalName = "";


    public EmailSendTool() {
    }

    public EmailSendTool(String host, String username, String password,
                         String mailto, String subject, String text, String name,
                         String head_name, String head_value) {
        this.host = host;
        this.username = username;
        this.mail_from = username;
        this.password = password;
        this.mail_to = mailto;
        this.mail_subject = subject;
        this.mail_body = text;
        this.personalName = name;
        this.mail_head_name = head_name;
        this.mail_head_value = head_value;
    }

    /**
     * 此段代码用来发送普通电子邮件
     *
     * @throws MessagingException
     * @throws UnsupportedEncodingException
     * @throws GeneralSecurityException
     */
    public void send() throws MessagingException, UnsupportedEncodingException, GeneralSecurityException {
        Properties props = new Properties();
        Authenticator auth = new Email_Autherticator(); // 进行邮件服务器用户认证
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.auth", "true");
        props.put("mail.transport.protocol", "smtp");
        MailSSLSocketFactory sf = new MailSSLSocketFactory();//ssl设置
        sf.setTrustAllHosts(true);
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.ssl.socketFactory", sf);
        Session session = Session.getDefaultInstance(props, auth);
        // 设置session,和邮件服务器进行通讯。
        MimeMessage message = new MimeMessage(session);
        // message.setContent("foobar, "application/x-foobar"); // 设置邮件格式
        message.setContent(mail_body, "text/html;charset=utf-8");
        message.setSubject(mail_subject); // 设置邮件主题
        //message.setText(mail_body); // 设置邮件正文
        message.setHeader(mail_head_name, mail_head_value); // 设置邮件标题

        message.setSentDate(new Date()); // 设置邮件发送日期
        Address address = new InternetAddress(mail_from, personalName);
        message.setFrom(address); // 设置邮件发送者的地址
        Address toAddress = new InternetAddress(mail_to); // 设置邮件接收方的地址
        message.addRecipient(Message.RecipientType.TO, toAddress);


        Transport.send(message); // 发送邮件
    }

    /**
     * 用来进行服务器对用户的认证
     */
    public class Email_Autherticator extends Authenticator {
        public Email_Autherticator() {
            super();
        }

        public Email_Autherticator(String user, String pwd) {
            super();
            username = user;
            password = pwd;
        }

        @Override
        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(username, password);
        }
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail_head_name() {
        return mail_head_name;
    }

    public void setMail_head_name(String mail_head_name) {
        this.mail_head_name = mail_head_name;
    }

    public String getMail_head_value() {
        return mail_head_value;
    }

    public void setMail_head_value(String mail_head_value) {
        this.mail_head_value = mail_head_value;
    }

    public String getMail_to() {
        return mail_to;
    }

    public void setMail_to(String mail_to) {
        this.mail_to = mail_to;
    }

    public String getMail_from() {
        return mail_from;
    }

    public void setMail_from(String mail_from) {
        this.mail_from = mail_from;
    }

    public String getMail_subject() {
        return mail_subject;
    }

    public void setMail_subject(String mail_subject) {
        this.mail_subject = mail_subject;
    }

    public String getMail_body() {
        return mail_body;
    }

    public void setMail_body(String mail_body) {
        this.mail_body = mail_body;
    }

    public String getPersonalName() {
        return personalName;
    }

    public void setPersonalName(String personalName) {
        this.personalName = personalName;
    }

   public static void main(String[] args) throws UnsupportedEncodingException {
       /* EmailSendTool sendEmail = new EmailSendTool("smtp.qq.com",
                "957444517@qq.com", "ccikyvxmzxaxbbjh", "619598856@qq.com",
                "主题", "邮件内容", "发送人名称", "", "");
        System.out.println("main");
        try {
            sendEmail.send();
        } catch (Exception e) {
            e.printStackTrace();
        }*/


       Properties prop = new Properties();
       //协议
       prop.setProperty("mail.transport.protocol", "smtp");
       //服务器
       prop.setProperty("mail.smtp.host", "smtp.exmail.qq.com");
       //端口
       prop.setProperty("mail.smtp.port", "465");
       //使用smtp身份验证
       prop.setProperty("mail.smtp.auth", "true");
       //使用SSL，企业邮箱必需！
       //开启安全协议
       MailSSLSocketFactory sf = null;
       try {
           sf = new MailSSLSocketFactory();
           sf.setTrustAllHosts(true);
       } catch (GeneralSecurityException e1) {
           e1.printStackTrace();
       }
       prop.put("mail.smtp.ssl.enable", "true");
       prop.put("mail.smtp.ssl.socketFactory", sf);
       //
       //获取Session对象
       Session s = Session.getDefaultInstance(prop, new Authenticator() {
           //此访求返回用户和密码的对象
           @Override
           protected PasswordAuthentication getPasswordAuthentication() {
               PasswordAuthentication pa = new PasswordAuthentication("ying.liu@cipherchina.com", "Ly28783973");
               return pa;
           }
       });
       //设置session的调试模式，发布时取消
       //s.setDebug(true);
       MimeMessage mimeMessage = new MimeMessage(s);
       try {
           mimeMessage.setFrom(new InternetAddress("ying.liu@cipherchina.com", "ying.liu@cipherchina.com"));
           mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress("tao.zhou@cipherchina.com"));
           //设置主题
           mimeMessage.setSubject("测试邮件");
           mimeMessage.setSentDate(new Date());
           //设置内容
           mimeMessage.setText("测试邮件");
           mimeMessage.saveChanges();
           //发送
           Transport.send(mimeMessage);
       } catch (MessagingException e) {
           e.printStackTrace();
       }

   }





    public static void sendTenxungEmail(EmailInfoDomain domain) throws UnsupportedEncodingException {
        Properties prop = new Properties();
        //协议
        prop.setProperty("mail.transport.protocol", "smtp");
        //服务器
        prop.setProperty("mail.smtp.host", domain.getSmtp());
        //端口
        prop.setProperty("mail.smtp.port", String.valueOf(domain.getPort()));
        //使用smtp身份验证
        prop.setProperty("mail.smtp.auth", "true");
        //使用SSL，企业邮箱必需！
        //开启安全协议
        MailSSLSocketFactory sf = null;
        try {
            sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
        } catch (GeneralSecurityException e1) {
            e1.printStackTrace();
        }
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.ssl.socketFactory", sf);
        //
       //获取Session对象
        Session s = Session.getDefaultInstance(prop, new Authenticator() {
            //此访求返回用户和密码的对象
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                PasswordAuthentication pa = new PasswordAuthentication(domain.getAccount(), domain.getPwd());
                return pa;
            }
        });
        //设置session的调试模式，发布时取消
        //s.setDebug(true);
        MimeMessage mimeMessage = new MimeMessage(s);
        try {
            mimeMessage.setFrom(new InternetAddress(domain.getAccount(), domain.getAccount()));
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(domain.getEmailAddress()));
            //设置主题
            mimeMessage.setSubject(domain.getTitle());
            mimeMessage.setSentDate(new Date());
            //设置内容
            String kapataCode=NumberUtil.createData(6);
            Map<String, String> param = new HashMap<String, String>();
            param.put("code", kapataCode);
            String newMsg=EmailSendTool.convertMessage(domain.getDescribes(),param);
            domain.setDescribes(newMsg);
            mimeMessage.setText(domain.getDescribes());
            mimeMessage.saveChanges();
            //发送
            Transport.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }


    /**
     * @param template 尊敬的用户,您在{platform}的验证码已发送！
     * @param param    键值对
     * @return
     */
    public static String convertMessage(String template, Map<String, String> param) {
        if (template != null && param != null && !param.isEmpty()) {
            for (Map.Entry<String, String> entry : param.entrySet()) {
                template = template.replace("{" + entry.getKey() + "}", entry.getValue());
            }
        }
        return template;
    }

}