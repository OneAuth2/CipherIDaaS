package com.portal.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.*;

public class MapUtil {


    private static final Logger logger = LoggerFactory.getLogger(MapUtil.class);


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
        //根据逗号截取字符串数组
        String[] str1 = str.split(",");
        //创建Map对象
        Map<String, Object> map = new HashMap<>();
        //循环加入map集合
        for (int i = 0; i < str1.length; i++) {
            //根据":"截取字符串数组
            String[] str2 = str1[i].split(":");
            //str2[0]为KEY,str2[1]为值
            map.put(str2[0], str2[1]);
        }
        return map;
    }



    public static Map objectToMap(Object object) {
        if (object == null) {
            return new HashMap();
        }

        if (object instanceof Map) {
            return (Map) object;
        }

        Map map = new HashMap();

        /**
         * IBatis的默认对象参数名
         */
        map.put("value", object);

        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            field.setAccessible(true);
            try {
                map.put(fieldName, field.get(object));
            } catch (Exception e) {
                logger.error("objectToMap error: ", e);
            }
        }

        return map;
    }

    /**
     * @param bean
     * @param parameters
     * @throws Exception
     */
    @SuppressWarnings({ "rawtypes" })
    public static void populate(Object bean, Map parameters) {
        for (Iterator i = parameters.entrySet().iterator(); i.hasNext();) {
            Map.Entry entry = (Map.Entry) i.next();
            Object key = entry.getKey();
            Object value = entry.getValue();
            String strKey;

            if (key != null) {
                if (!(key instanceof String)) {
                    strKey = key.toString();
                } else {
                    strKey = (String) key;
                }

                FieldUtil.setFieldValue(bean, strKey, value);
            }
        }
    }



}
