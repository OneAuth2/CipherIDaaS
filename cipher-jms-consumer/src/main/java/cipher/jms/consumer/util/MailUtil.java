package cipher.jms.consumer.util;

import cipher.jms.consumer.domain.EmailInfoDomain;
import com.sun.mail.util.MailSSLSocketFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.Properties;

public class MailUtil implements Runnable {
    private String email;// 收件人邮箱
    private String code;// 激活码
    private String account;//注册用户账号
    private String password;//注册用户密码

    private EmailInfoDomain emailInfoDomain;


    public EmailInfoDomain getEmailInfoDomain() {
        return emailInfoDomain;
    }

    public void setEmailInfoDomain(EmailInfoDomain emailInfoDomain) {
        this.emailInfoDomain = emailInfoDomain;
    }

    public MailUtil(String email, String code, EmailInfoDomain emailInfoDomain) {
        this.email = email;
        this.code = code;
        this.emailInfoDomain = emailInfoDomain;
    }

    public MailUtil(String email, String account, String password, EmailInfoDomain emailInfoDomain) {
        this.email = email;
        this.account = account;
        this.password = password;
        this.emailInfoDomain = emailInfoDomain;
    }

    public void run() {
        // 1.创建连接对象javax.mail.Session
        // 2.创建邮件对象 javax.mail.Message
        // 3.发送一封激活邮件
        String from = "sizhao_ch@qq.com";// 发件人电子邮箱
        String host = "smtp.qq.com"; // 指定发送邮件的主机smtp.qq.com(QQ)|smtp.163.com(网易)

        Properties properties = System.getProperties();// 获取系统属性

        properties.setProperty("mail.smtp.host", host);// 设置邮件服务器
        properties.setProperty("mail.smtp.auth", "true");// 打开认证

        try {
            //QQ邮箱需要下面这段代码，163邮箱不需要
            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.ssl.socketFactory", sf);


            // 1.获取默认session对象
            Session session = Session.getDefaultInstance(properties, new Authenticator() {
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("sizhao_ch@qq.com", "kwhkpegwryprbfej"); // 发件人邮箱账号、授权码
                }
            });

            // 2.创建邮件对象
            Message message = new MimeMessage(session);
            // 2.1设置发件人
            message.setFrom(new InternetAddress(from));
            // 2.2设置接收人
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            // 2.3设置邮件主题
            message.setSubject("【赛赋访客】临时验证码");
            // 2.4设置邮件内容
            String content = "<html><head></head><body><h1>您登记了访问赛赋科技,"
                    + "您的临时验证码为:</h1><h3><a href='"
                    + "'>" + code
                    + "</href></h3></body></html>";
            message.setContent(content, "text/html;charset=UTF-8");
            // 3.发送邮件
            Transport.send(message);
            System.out.println("邮件成功发送!");
        } catch (Exception e) {

        }
    }


    public void sendTenxungEmail(EmailInfoDomain domain) throws UnsupportedEncodingException {
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
        Session s = Session.getInstance(prop, new Authenticator() {
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
            mimeMessage.setText(domain.getDescribes());
            mimeMessage.saveChanges();
            //发送
            Transport.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    public void sendImageEmail(EmailInfoDomain domain) throws UnsupportedEncodingException {
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
        Session s = Session.getInstance(prop, new Authenticator() {
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
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(mimeMessage, true);

            mimeMessage.setFrom(new InternetAddress(domain.getAccount(), domain.getAccount()));
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(domain.getEmailAddress()));
            //设置主题
            mimeMessage.setSubject(domain.getSeedkeyTitle());
            mimeMessage.setSentDate(new Date());
            String htmlContent = "";
            Multipart multipart = new MimeMultipart("alternative");
            BodyPart htmlPart = new MimeBodyPart();

            String[] split = domain.getSeedkeyDescribes().split("\\{QR Code\\}");
            if(split.length==2){
                htmlContent = "<p>"+split[0]+"</p><img src=\"cid:image\"><p>"+split[1]+"</p>";
            }else if(split.length==1){
                htmlContent = "<p>"+split[0]+"</p><img src=\"cid:image\">";
            }else {
                htmlContent = "<p>你好！\n" +
                        "\n" +
                        "IT管理员根据公司的安全要求，已启用了双因素动态密码认证。\n" +
                        "请各位员工用户使用微信小程序「赛赋认证器」扫描下方二维码，绑定动态密码。</p><img src=\"cid:image\"><p>请保存好该二维码，以免泄露。\n" +
                        "如有问题请联系IT管理员。</p>";
            }

            htmlPart.setContent(htmlContent, "text/html;charset=utf-8");
            multipart.addBodyPart(htmlPart);
            BodyPart imgPart = new MimeBodyPart();
            DataSource fds = new FileDataSource(domain.getImgPath() + File.separator + domain.getImgId());

            imgPart.setDataHandler(new DataHandler(fds));
            imgPart.setHeader("Content-ID", "<image>");

            multipart.addBodyPart(imgPart);
            mimeMessage.setContent(multipart);
            Transport.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }


}
