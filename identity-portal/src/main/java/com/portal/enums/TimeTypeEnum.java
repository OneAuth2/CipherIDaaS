package com.portal.enums;

public enum TimeTypeEnum {

    YEAR(1, "年"), MONTH(2, "月"), DAY(3, "日"), HOUR(4, "小时"), MINUTE(5, "分"), SECOND(6, "秒");

    private int    type;
    private String desc;

    private TimeTypeEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

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

}
