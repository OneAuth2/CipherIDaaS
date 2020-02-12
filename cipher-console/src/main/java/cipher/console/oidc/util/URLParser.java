package cipher.console.oidc.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URLParser {
    protected byte type;
    protected static final byte TYPE_URL = 1;
    protected static final byte TYPE_QUERY_STRING = 2;
    protected String url;
    protected String baseUrl;
    protected String queryString;
    protected String label;
    protected String charset = "utf-8";

    protected boolean compiled = false;
    public Map<String, String> parsedParams;
    protected URLDecoder urld = new URLDecoder();

    public static URLParser fromURL(String url) {
        URLParser parser = new URLParser();

        parser.type = 1;
        parser.url = url;

        String[] split = url.split("\\?", 2);
        parser.baseUrl = split[0];
        parser.queryString = (split.length > 1 ? split[1] : "");

        String[] split2 = url.split("#", 2);
        parser.label = (split2.length > 1 ? split2[1] : null);

        return parser;
    }

    public static URLParser fromQueryString(String queryString) {
        URLParser parser = new URLParser();

        parser.type = 2;
        parser.queryString = queryString;

        return parser;
    }

    public URLParser useCharset(String charset) {
        this.charset = charset;
        return this;
    }

    public URLParser compile() throws UnsupportedEncodingException {
        if (this.compiled) {
            return this;
        }
        String paramString = this.queryString.split("#")[0];
        String[] params = paramString.split("&");

        this.parsedParams = new HashMap<String, String>(params.length);
        for (String p : params) {
            String[] kv = p.split("=");
            if (kv.length == 2) {
                this.parsedParams.put(kv[0], URLDecoder.decode(kv[1], this.charset));
            }
        }
        this.compiled = true;

        return this;
    }

    public String getParameter(String name) {
        if (this.compiled) {
            return (String) this.parsedParams.get(name);
        }
        String paramString = this.queryString.split("#")[0];
        Matcher match = Pattern.compile("(^|&)" + name + "=([^&]*)").matcher(paramString);
        match.lookingAt();

        return match.group(2);
    }

    public URLParser setParameter(String name, String value) throws UnsupportedEncodingException {
        if (!this.compiled) {
            compile();
        }
        this.parsedParams.put(name, value);

        return this;
    }

    public String getParameterFromUrl(String urlName,String name) throws UnsupportedEncodingException {
        String subUrl = getParameter(urlName);
        String decodeUrl = null;
        String parameter = null;
        decodeUrl = URLDecoder.decode(subUrl,"UTF-8");
        parameter = fromURL(decodeUrl).compile().getParameter(name);
        return parameter;
    }






    public static class ParseDomainName {
        InetAddress myServer = null;
        InetAddress myIPaddress = null;
        String domainName = null;

        public ParseDomainName(String domainName) {
            this.domainName = domainName;
        }

        public InetAddress getServerIP() {
            try {
                myServer = InetAddress.getByName(domainName);
            } catch (UnknownHostException e) {
            }
            return (myServer);
        }

        // 取得LOCALHOST的IP地址
        public InetAddress getMyIP() {
            try {
                myIPaddress = InetAddress.getLocalHost();
            } catch (UnknownHostException e) {
            }
            return (myIPaddress);
        }
    }

        public static void main(String[] args) {
          /*  ParseDomainName pdn = new ParseDomainName("139.196.143.107");
            System.out.println("Your host IP is: " + pdn.getMyIP().getHostAddress());
            System.out.println("The Server IP is :" + pdn.getServerIP().getHostAddress());*/

            String tosign="page=1&rows=10&ticket=YpMU4CX0ILMmgRWoxHQ3DelTRUJkr21PBgfxzRgIUR2dBUxssRzPAg1THeT2DHiUrouANQAXmgvqw9utXXno5kZTrGRU2PFTmR8mqqu2jeJ1aFypWfaNZaLV8rrdYUz";
            String md5Str = DigestUtils.md5Hex(tosign);
            System.out.println(md5Str);


        }

    }


