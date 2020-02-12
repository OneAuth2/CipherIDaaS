package cipher.console.oidc.enums;

/**
 * @Author: zt
 * Ad域的搜索范围
 * @Date: 2018/6/8 16:42
 */
public enum SearchScopeEnum {
    OBJECT_SCOPE(0, "OBJECT_SCOPE"),
    ONELEVEL_SCOPE(1, "ONELEVEL_SCOPE"),
    SUBTREE_SCOPE(2, "SUBTREE_SCOPE");

    private int code;

    private String desc;

    public static int getCodeByDesc(String desc) {
        for (SearchScopeEnum searchScopeEnum : SearchScopeEnum.values()) {
            if(searchScopeEnum.getDesc().equals(desc)){
                return searchScopeEnum.getCode();
            }
        }

        return 2;
    }

    SearchScopeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
