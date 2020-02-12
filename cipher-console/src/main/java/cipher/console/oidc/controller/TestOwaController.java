package cipher.console.oidc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author: zt
 * @Date: 2018/12/6 15:46
 */
@Controller
@RequestMapping(value = "/test")
public class TestOwaController {

    @RequestMapping(value = "/owa")
    public void loginOwa(HttpServletRequest request, HttpServletResponse response) {
        try {
            PrintWriter out = response.getWriter();
            out.write(getOwaForm(action,userName,pwd,dest));
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String action = "https://192.168.1.170/owa/auth.owa";

    private String userName = "administrator";

    private String pwd = "Cipher170!";

    private String dest="https://192.168.1.170/owa/auth";

    public String getOwaForm(String action, String userName, String pwd,String dest) {

        return "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "<head>\n" +
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
                "</head>\n" +
                "<body style=\"display='none'\">\n" +
                "<form id=\"form1\" name=\"form1\" method=\"post\" action=\"" + action + "\">\n" +
                "    <input type=\"text\" name=\"destination\" value=\"" + dest + "\">\n" +
                "    <input type=\"text\" name=\"flags\" value=\"4\">\n" +
                "    <input type=\"text\" name=\"forcedownlevel\" value=\"0\">\n" +
                "    <input type=\"text\" name=\"username\" value=\"" + userName + "\">\n" +
                "    <input type=\"text\" name=\"password\" value=\"" + pwd + "\">\n" +
                "    <input type=\"text\" name=\"passwordText\" value=\"\">\n" +
                "    <input type=\"text\" name=\"isUtf8\" value=\"1\">\n" +
                "</form>\n" +
                "<script>\n" +
                "\tdocument.getElementById(\"form1\").submit();\n" +
                "</script>\n" +
                "</body>\n" +
                "</html>\n";
    }


    public String getOwaFormZh() {
        return "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "<head style=\"display='none'\">\n" +
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
                "</head>\n" +
                "<body>\n" +
                "<form id=\"form1\" name=\"form1\" method=\"post\" action=\"https://mail.unittec.com/owa/auth.owa\">\n" +
                "    <input type=\"text\" name=\"destination\" value=\"https://mail.unittec.com/ecp/?tdsourcetag=s_pcqq_aiomsg\">\n" +
                "    <input type=\"text\" name=\"flags\" value=\"4\">\n" +
                "    <input type=\"text\" name=\"forcedownlevel\" value=\"0\">\n" +
                "    <input type=\"text\" name=\"username\" value=\"administrator\">\n" +
                "    <input type=\"text\" name=\"password\" value=\"zhkj@000925.NET\">\n" +
                "    <input type=\"text\" name=\"passwordText\" value=\"\">\n" +
                "    <input type=\"text\" name=\"isUtf8\" value=\"1\">\n" +
                "</form>\n" +
                "<script>\n" +
                "\tdocument.getElementById(\"form1\").submit();\n" +
                "</script>\n" +
                "</body>\n" +
                "</html>\n";
    }


    private String oaAction="http://oa.bangcle.com/login/VerifyLogin.jsp";

    private String oaUserName="ysqtest";

    private String oaPwd="12345678";

    public String getFanwiOa(String action,String userName,String pwd){
        return "<html>\n" +
                "<head>\n" +
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
                "</head>\n" +
                "<body style=\"display='none'\">\n" +
                "<form id=\"form1\" name=\"form1\" method=\"post\" action=\"http://oa.bangcle.com/login/VerifyLogin.jsp\">\n" +
                "    <input type=\"text\" name=\"loginid\" value=\"ysqtest\">\n" +
                "    <input type=\"text\" name=\"userpassword\" value=\"12345678\">\n" +
                "    <input type=\"text\" name=\"logintype\" value=\"1\">\n" +
                "    <input type=\"text\" name=\"frommail\" value=\"0\">\n" +
                "    <input type=\"submit\" name=\"ddd\" id=\"ddd\" value=\"提交\" />\n" +
                "</form>\n" +
                "<script>\n" +
                "\tdocument.getElementById(\"form1\").submit();\n" +
                "</script>\n" +
                "</body>\n" +
                "</html>";
    }

}
