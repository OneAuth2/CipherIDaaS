package cipher.console.oidc.enums;

/**
 * Created by 95744 on 2018/9/17.
 */
public enum AcDeviceEnum {

    HUA_WEI(1, "Huawei"),
    H3C(2, "H3C"),
    ARUBA(3, "Aruba"),
    CISCO(4, "Cisco"),
    RUIJIE(5,"Ruijie");


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

    AcDeviceEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }


    public static String getAcDevice(Integer code) {
        for(AcDeviceEnum acDeviceEnum: AcDeviceEnum.values()){
            if(code==acDeviceEnum.getType()){
                return acDeviceEnum.getDesc();
            }
        }
        return null;
    }
}
