package cipher.console.oidc.controller.WpsController;

import cipher.console.oidc.common.HttpKey;
import cipher.console.oidc.util.HttpClientUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 亚太机电
 * http://mail.apg.cn:3000
 * 账号:nhx@apg.cn
 * 密码:System123
 *
 * @Author: zt
 * @Date: 2018/12/19 15:57
 */
@Controller
@RequestMapping(value = "/testMail")
public class TestMail {

    private static final String loginUrl = "http://mail.apg.cn:3000/webmail/index.php?module=view&action=login";

    private static final String userName = "nhx@apg.cn";

    private static final String pwd = "System123";

    private static String key_hash = "1234567812345678";

    private static String action = "http://mail.apg.cn:3000/webmail/index.php?module=operate&action=login&web=1";

    private static final String baseUrl="http://mail.apg.cn:3000/webmail/index.php";

    @RequestMapping(value = "/sso", method = RequestMethod.GET)
    public void mailSSO(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.write(getMailSSoUrl(baseUrl, userName, pwd));
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }

    }


    public static void main(String[] args) {
        String baseUrl = "http://mail.apg.cn:3000/webmail/index.php";
        getMailSSoUrl(baseUrl, userName, pwd);
//        System.out.println(sccess);
    }



    private static String getMailSSoUrl(String baseUrl, String userName, String pwd) {
        try {
            URL url1 = new URL(baseUrl + "?module=view&action=login");
            Document document = Jsoup.parse(url1, 3000);
            String token = document.getElementsByAttributeValue("name", "token").get(0).val();
            String stime = document.getElementsByAttributeValue("name", "stime").get(0).val();
            String nonce = document.getElementsByAttributeValue("name", "nonce").get(0).val();
            String domain = document.getElementsByAttributeValue("name", "domain").get(0).val();
            String body = document.toString();
            String key = StringUtils.substringBetween(body, "CryptoJS.MD5('", "');");
            String iv = StringUtils.substringBetween(body, "CryptoJS.enc.Utf8.parse('", "');");
            System.out.println("token=" + token + " stime=" + stime + " nonce=" + nonce + " domain=" + domain + " key=" + key + " iv=" + iv);
            pwd = encryptAES(pwd, key, iv);
            String res = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                    "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                    "<head>\n" +
                    "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
                    "</head>\n" +
                    "<body style=\"display: none;\">\n" +
                    "<form id=\"form1\" name=\"form1\" method=\"post\" action=\"" + baseUrl + "?module=view&action=login&web=1" + "\">\n" +
                    "    <input type=\"text\" name=\"username\" value=\"" + userName + "\">\n" +
                    "    <input type=\"text\" name=\"token\" value=\"" + token + "\">\n" +
                    "    <input type=\"text\" name=\"stime\" value=\"" + stime + "\">\n" +
                    "    <input type=\"text\" name=\"remuser\" value=\"1\">\n" +
                    "\t<input type=\"text\" name=\"password\" value=\"" + pwd + "\">\n" +
                    "    <input type=\"text\" name=\"nonce\" value=\"" + nonce + "\">\n" +
                    "    <input type=\"text\" name=\"language\" value=\"zh_CN\">\n" +
                    "    <input type=\"text\" name=\"domain\" value=\"" + domain + "\">\n" +
                    "    <input type=\"submit\" name=\"ddd\" id=\"ddd\" value=\"提交\" />\n" +
                    "</form>\n" +
                    "<script>\n" +
                    "\t document.getElementById(\"form1\").submit();\n" +
                    "</script>\n" +
                    "</body>\n" +
                    "</html>\n";
            return res;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String encryptAES(String data, String key, String iv) throws Exception {
        key = md5Password(key);
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/NOPadding");   //参数分别代表 算法名称/加密模式/数据填充方式
            int blockSize = cipher.getBlockSize();
            byte[] dataBytes = data.getBytes();
            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }

            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);

            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            byte[] encrypted = cipher.doFinal(plaintext);

            return new sun.misc.BASE64Encoder().encode(encrypted);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String decryptAES(String data, String key, String iv) throws Exception {
        key = md5Password(key);
        try {
            byte[] encrypted1 = new BASE64Decoder().decodeBuffer(data);

            Cipher cipher = Cipher.getInstance("AES/CBC/NOPadding");
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);

            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original);
            return originalString;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String md5Password(String password) {
        try {
            // 得到一个信息摘要器
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(password.getBytes());
            StringBuffer buffer = new StringBuffer();
            // 把每一个byte 做一个与运算 0xff;
            for (byte b : result) {
                // 与运算
                int number = b & 0xff;// 加盐
                String str = Integer.toHexString(number);
                if (str.length() == 1) {
                    buffer.append("0");
                }
                buffer.append(str);
            }

            // 标准的md5加密后的结果
            return buffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }

    }



    private static boolean checkMailUserNameAndPwd(String baseUrl, String userName, String pwd) {
        URL url1 = null;
        try {
            url1 = new URL(baseUrl + "?module=view&action=login");
            Document document = Jsoup.parse(url1, 3000);
            String token = document.getElementsByAttributeValue("name", "token").get(0).val();
            String stime = document.getElementsByAttributeValue("name", "stime").get(0).val();
            String nonce = document.getElementsByAttributeValue("name", "nonce").get(0).val();
            String domain = document.getElementsByAttributeValue("name", "domain").get(0).val();

            String body = document.toString();
            String key = StringUtils.substringBetween(body, "CryptoJS.MD5('", "');");
            String iv = StringUtils.substringBetween(body, "CryptoJS.enc.Utf8.parse('", "');");
            System.out.println("token=" + token + " stime=" + stime + " nonce=" + nonce + " domain=" + domain + " key=" + key + " iv=" + iv);
            pwd = encryptAES(pwd, key, iv);
            List<NameValuePair> paramList = new ArrayList<>();
            paramList.add(new BasicNameValuePair("username", userName));
            paramList.add(new BasicNameValuePair("token", token));
            paramList.add(new BasicNameValuePair("stime", stime));
            paramList.add(new BasicNameValuePair("remuser", "1"));
            paramList.add(new BasicNameValuePair("password", pwd));
            paramList.add(new BasicNameValuePair("nonce", nonce));
            paramList.add(new BasicNameValuePair("language", "zh_CN"));
            paramList.add(new BasicNameValuePair("domain", domain));

            Map<String, Object> resMap = HttpClientUtils.doPost(baseUrl + "?module=operate&action=login&web=1", paramList, null, null, null, null);

            String res = (String) resMap.get(HttpKey.RES);
            int status = (int) resMap.get(HttpKey.STATUS_CODE);
//            System.out.println("status=" + status + " res=" + res);
            if (status == 302 && "<meta http-equiv=\"refresh\" content=\"0; URL=index.php\">".equals(res)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private Map<String, String> getHeaderMap() {
        Map<String, String> map = new HashMap<>();
        map.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        map.put("Accept-Encoding", "gzip, deflate");
        map.put("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8,la;q=0.7");
        map.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36");
        map.put("Referer", "http://mail.apg.cn:3000/webmail/index.php?module=view&action=login");
        return map;
    }


}
