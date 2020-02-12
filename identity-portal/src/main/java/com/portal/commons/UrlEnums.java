package com.portal.commons;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 95744 on 2018/9/7.
 */
public enum UrlEnums {

    URL1(1,"/login"),

    URL2(2,"/callBack"),

    URL3(3,"/checkUser"),

    URL4(4,"/longpoll"),

    URL5(5,"/sccanImg"),

    URL6(6,"/getLongPollRes"),

    URL7(7,"/addUser/toSession"),

    URL8(8,"/portal/wait"),

    URL9(9,"/cipher/aliding"),


    URL12(12,"/error"),

    URL13(13,"/oidc/redirectUri"),

    URL14(14,"/cipher/abnormal"),

    URL15(15,"/cipher/sso"),

    URL16(16,"/cipher/dabby/qrcode"),

    URL17(17,"/cipher/dabby/pooling"),

    URL18(18,"/dabby/auth"),

    URL19(18,"/cipher/auth"),

    URL20(20,"/cipher/authQrCode/callback"),

    URL21(21,"/cipher/bandAuth"),

    URL22(22,"/cipher/user/sendPhoneCode"),
    URL23(23,"/cipher/user/sendMailCode"),
    URL24(24,"/cipher/user/phoneCodeChecked"),
    URL25(25,"/cipher/user/mailCodeChecked"),
    URL26(26,"/cipher/user/getContactInfo"),
    URL27(27,"/cipher/user/passwordSet"),
    URL28(28,"/cipher/user/bindingFlow"),
    URL29(29,"/cipher/user/pwdChecked"),
    URL30(30,"/cipher/user/daBaiAutoMatch"),
    URL31(31,"/cipher/user/saiFuAutoMatch"),
    URL32(32,"/cipher/user/dingTalkAutoMatch"),
    URL33(33,"/cipher/user/daBaiBinding"),
    URL34(34,"/cipher/user/cipherBinding"),
    URL35(35,"/cipher/user/dingTalkBinding"),
    URL36(36,"/cipher/user/scanLogin "),
    URL37(37,"/cipher/user/pwdLoginChecked"),
    URL38(38,"/cipher/user/pwdTotpLoginChecked"),
    URL39(39,"/cipher/user/saiFuPolling"),
    URL40(40,"/cipher/user/daBaiPolling"),
    URL41(41,"/cipher/user/dingTalkLogin"),
    URL42(42,"/cipher/user/phoneCodeLogin"),
    URL43(43,"/cipher/user/collectPhoneNumber"),
    URL44(44,"/cipher/user/saifuSecondPolling"),
    URL45(45,"/cipher/user/daBaiSecondPolling"),
    URL46(46,"/cipher/user/dingTalkSecondAuth"),
    URL47(47,"/cipher/user/totpSecondCheck"),
    URL48(48,"/cipher/user/mailCodeSecondChecked"),
    URL49(49,"/cipher/user/phoneCodeSecondChecked"),
    URL50(50,"/cipher/index/service"),
    URL51(51,"/cipher/login/saiFuQrcode"),
    URL52(52,"/cipher/login/daBaiQrcode"),
    URL53(53,"/cipher/regist/registFlow"),
    URL54(54,"/cipher/user/regist"),
    URL55(55,"/cipher/user/phoneNumberExist"),
    URL56(56,"/cipher/forget/forgetPwdFlow"),
    URL57(57,"/cipher/forget/phoneCodeChecked"),
    URL58(58,"/cipher/forget/emailChecked"),
    URL59(59,"/cipher/forget/reset"),
    URL61(61,"/cipher/reset/checkpwd"),
    URL62(62,"/cipher/reset/password"),
    URL63(63,"/logout"),
    URL64(64,"101.132.145.69:9999"),
    URL65(65,"/cipher/cas/login"),
    URL66(66,"/cipher/cas/logout"),
    URL67(67,"/cipher/cas/serviceValidate"),
    URL68(68,"101.132.145.69:9999"),
    URL69(69,"/cipher/user/getDingTalkId"),
    URL70(70,"/cipher/lua/login"),
    URL71(71,"/cipher/lua/getTicket"),
    URL72(72,"/cipher/index/login"),
    URL73(73,"/cipher/portal/getUserAppList"),
    URL74(74,"/cipher/user/dingPushSecondCheck"),
    URL75(75,"/cipher/cas/login/serviceValidate"),
    URL76(76,"/cipher/user/dingPushPolling"),
    URL77(77,"/cipher/portal/test"),
    URL78(78,"/cipher/user/otpLoginChecked"),
    URL79(79,"/cipher/user/otpDynamicSecondCheck"),
    URL80(80,"/cipher/user/weixinBinding"),
    URL81(81,"/cipher/user/weixinAutoMatch"),
    URL82(82,"/cipher/user/weixinSecondAuth"),
    URL83(83,"/cipher/user/weixinPolling"),
    URL84(84,"/cipher/user/getWeiXinAppId"),
    URL85(85,"/cipher/user/collectMailNumber"),
    URL86(86,"/cipher/user/collectPhoneNumber"),



    URL88(88,"/cipher/oauth/authorize"),
    URL89(89,"/cipher/oauth/access_token"),
    URL90(90,"/cipher/oauth/refreshToken"),
    URL91(91,"/cipher/oauth/user"),
    URL92(92,"/cipher/saml/sso"),
    URL93(93,"/cipher/saml/login"),
   // URL94(94,"/cipher/oauth/login"),
    ;








    UrlEnums(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    private int type;
    private String desc;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    public static String getUrlEnums(int type) {
        for(UrlEnums urlEnums:UrlEnums.values()){
            if(type==urlEnums.getType()){
                return urlEnums.getDesc();
            }
        }
        return null;
    }


     public static List<EnumUrlTypeVo> getEuumUrlList(){
     List<EnumUrlTypeVo> list=new ArrayList<EnumUrlTypeVo>();
         for(UrlEnums urlEnums : UrlEnums .values()){
             EnumUrlTypeVo enumUrlTypeVo=new EnumUrlTypeVo();
             enumUrlTypeVo.setDesc(urlEnums.getDesc());
             list.add(enumUrlTypeVo);
         }
     return list;
    }
}










