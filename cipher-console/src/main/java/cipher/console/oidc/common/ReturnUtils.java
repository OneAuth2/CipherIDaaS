package cipher.console.oidc.common;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zt
 * @Date: 2018/12/28 15:06
 */
public class ReturnUtils {

    public static Map<String, Object> failureResponse(String msg) {
        Map<String, Object> map = new HashMap<>();
        map.put(GlobalReturnCode.RETURN_CODE, GlobalReturnCode.MsgCodeEnum.FAILURE.getCode());
        map.put(GlobalReturnCode.RETURN_MSG, msg);
        return map;
    }

    public static Map<String, Object> failureResponse(int code, String msg) {
        Map<String, Object> map = new HashMap<>();
        map.put(GlobalReturnCode.RETURN_CODE, code);
        map.put(GlobalReturnCode.RETURN_MSG, msg);
        return map;
    }

    public static Map<String, Object> successResponse() {
        Map<String, Object> map = new HashMap<>();
        map.put(GlobalReturnCode.RETURN_CODE, GlobalReturnCode.MsgCodeEnum.SUCCESS.getCode());
        map.put(GlobalReturnCode.RETURN_MSG, GlobalReturnCode.MsgCodeEnum.SUCCESS.getMsg());
        return map;
    }

    public static Map<String, Object> successResponse(GlobalReturnCode.MsgCodeEnum msgCodeEnum) {
        Map<String, Object> map = new HashMap<>();
        map.put(GlobalReturnCode.RETURN_CODE, msgCodeEnum.getCode());
        map.put(GlobalReturnCode.RETURN_MSG, msgCodeEnum.getMsg());
        return map;
    }


    public static Map<String, Object> successResponse(String msg) {
        Map<String, Object> map = new HashMap<>();
        map.put(GlobalReturnCode.RETURN_CODE, GlobalReturnCode.MsgCodeEnum.SUCCESS.getCode());
        map.put(GlobalReturnCode.RETURN_MSG, msg);
        return map;
    }

    public static Map<String, Object> successResponse(String key, Object val) {
        Map<String, Object> map = new HashMap<>();
        map.put(GlobalReturnCode.RETURN_CODE, GlobalReturnCode.MsgCodeEnum.SUCCESS.getCode());
        map.put(GlobalReturnCode.RETURN_MSG, GlobalReturnCode.MsgCodeEnum.SUCCESS.getMsg());
        map.put(key, val);
        return map;
    }



}
