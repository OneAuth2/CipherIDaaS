package cipher.console.oidc.util;

import java.util.*;

public class MapUtil {

    /**
     * Map转String
     *
     * @param map
     * @return
     */
    public static String getMapToString(Map<String, Object> map) {
        Set<String> keySet = map.keySet();
        //将set集合转换为数组
        String[] keyArray = keySet.toArray(new String[keySet.size()]);
        //给数组排序(升序)
        Arrays.sort(keyArray);
        //因为String拼接效率会很低的，所以转用StringBuilder
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < keyArray.length; i++) {
            // 参数值为空，则不参与签名 这个方法trim()是去空格
            if ((String.valueOf(map.get(keyArray[i]))).trim().length() > 0) {
                sb.append(keyArray[i]).append(":").append(String.valueOf(map.get(keyArray[i])).trim());
            }
            if (i != keyArray.length - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    /**
     * String转map
     *
     * @param str
     * @return
     */
    public static Map<String, Object> getStringToMap(String str) {
        //创建Map对象
        Map<String, Object> map = new HashMap<>();
        try {
            //根据逗号截取字符串数组
            String[] str1 = str.split(",");
            //循环加入map集合
            for (int i = 0; i < str1.length; i++) {
                //根据":"截取字符串数组
                String[] str2 = str1[i].split(":");
                //str2[0]为KEY,str2[1]为值
                map.put(str2[0], str2[1]);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return map;
    }

  public  static void main(String arg[]){
       Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("uuid", "1233456");
        parameterMap.put("companyUUid", "123456");
        parameterMap.put("timestamp", "12233333");
        System.out.println(getMapToString(parameterMap));

        String ss="companyUUid:123456,timestamp:1558357344122,uuid:be22b31d6b1411e9b5af00163e00cc6a";
        System.out.println(getStringToMap(ss));


    }


}
