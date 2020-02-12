package cipher.console.oidc.util;

public class ReturnJsonCode {

  public static final String RETURN_CODE = "return_code";
  public static final String RETURN_MSG  = "return_msg";

  public static final int SUCCESS  = 1;
  public static final int FAIL  = 0;

  public enum MsgCodeEnum {
    FAILURE(0, "操作失败！"),
    SUCCESS(1, "操作成功！"),
    UPLOAD_TYPE(3, "上传文件类型不匹配"),
    BEEN_APPLIED(4, "记录已被引用，不能删除！"),
    AUTHORITY(5, "没有权限！"),
    APK_ON_LINE(6, "有相同包名的应用已上线,请先将其下线！"),
    APPNAME_EXISTS(7, "该数据已存在！"),
    INFO_EXISTS(2, "信息已存在！");

    private int    code;
    private String msg;

    MsgCodeEnum(int _code, String _msg) {
      this.code = _code;
      this.msg = _msg;
    }

    public int getCode() {
      return code;
    }

    public void setCode(int code) {
      this.code = code;
    }

    public String getMsg() {
      return msg;
    }

    public void setMsg(String msg) {
      this.msg = msg;
    }

  }

}
