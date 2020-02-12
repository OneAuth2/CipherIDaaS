package cipher.console.oidc.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 验证：手机号，邮箱，身份证号等工具类
 * @author cozi
 * @2019-07-22
 */
public class VerifyUtil {

    /**
     * 验证手机号
     * @param telephone
     * @return
     */
    public static boolean verifyPhoneNumber(String telephone){
        //因为手机号段变化太快，只能尽量少做限制
        String PHONE_NUMBER_REG = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$";
        //简化限制
        String PHONE_NUMBER_REG1 = "^(1[3-9])\\d{9}$";
        Pattern pattern = Pattern.compile(PHONE_NUMBER_REG1);
        Matcher matcher = pattern.matcher(telephone);
        return matcher.matches();
    }

    /**
     * 验证邮箱
     * @param mail
     * @return
     */
    public static boolean verifyMail(String mail){
        String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(regEx1);
        Matcher matcher = pattern.matcher(mail);
        return matcher.matches();
    }

    /**
     * 验证身份证号
     * @param idNum
     * @return
     */
    public static boolean verifyIdNum(String idNum){
            if (idNum == null || "".equals(idNum)) {
                return false;
            }
            // 定义判别用户身份证号的正则表达式（15位或者18位，最后一位可以为字母）
            String regularExpression = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|" +
                    "(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";
            //假设18位身份证号码:41000119910101123X  410001 19910101 123X
            //^开头
            //[1-9] 第一位1-9中的一个      4
            //\\d{5} 五位数字           10001（前六位省市县地区）
            //(18|19|20)                19（现阶段可能取值范围18xx-20xx年）
            //\\d{2}                    91（年份）
            //((0[1-9])|(10|11|12))     01（月份）
            //(([0-2][1-9])|10|20|30|31)01（日期）
            //\\d{3} 三位数字            123（第十七位奇数代表男，偶数代表女）
            //[0-9Xx] 0123456789Xx其中的一个 X（第十八位为校验值）
            //$结尾

            //假设15位身份证号码:410001910101123  410001 910101 123
            //^开头
            //[1-9] 第一位1-9中的一个      4
            //\\d{5} 五位数字           10001（前六位省市县地区）
            //\\d{2}                    91（年份）
            //((0[1-9])|(10|11|12))     01（月份）
            //(([0-2][1-9])|10|20|30|31)01（日期）
            //\\d{3} 三位数字            123（第十五位奇数代表男，偶数代表女），15位身份证不含X
            //$结尾


            boolean matches = idNum.matches(regularExpression);

            //判断第18位校验值
            if (matches) {

                if (idNum.length() == 18) {
                    try {
                        char[] charArray = idNum.toCharArray();
                        //前十七位加权因子
                        int[] idCardWi = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
                        //这是除以11后，可能产生的11位余数对应的验证码
                        String[] idCardY = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};
                        int sum = 0;
                        for (int i = 0; i < idCardWi.length; i++) {
                            int current = Integer.parseInt(String.valueOf(charArray[i]));
                            int count = current * idCardWi[i];
                            sum += count;
                        }
                        char idCardLast = charArray[17];
                        int idCardMod = sum % 11;
                        if (idCardY[idCardMod].toUpperCase().equals(String.valueOf(idCardLast).toUpperCase())) {
                            return true;
                        } else {
                            return false;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        return false;
                    }
                }
            }
            return matches;
        }

    public static void main(String[] args) {
        System.out.println(verifyPhoneNumber("13186102014"));
        System.out.println(verifyMail("123@163.com"));
        System.out.println(verifyIdNum("421"));
    }
}
