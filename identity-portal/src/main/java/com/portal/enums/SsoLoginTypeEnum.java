package com.portal.enums;

/**
 * TODO:
 * create by liuying at 2019/12/10
 *
 * @author liuying
 * @since 2019/12/10 14:53
 */
public enum SsoLoginTypeEnum {

    CAS("CAS","casSsoService"),
    RDSG("RDSG","rdsgSsoService"),
    AUTH2("AUTH2","authSsoService"),
    SAML("SAML","smmlSsoService");

    private String type;

    private String impl;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImpl() {
        return impl;
    }

    public void setImpl(String impl) {
        this.impl = impl;
    }

    SsoLoginTypeEnum(String type, String impl) {
        this.type = type;
        this.impl = impl;
    }


    public static String getSsoLoginType(String type){
        for(SsoLoginTypeEnum ssoLoginTypeEnum: SsoLoginTypeEnum.values()){
            if(type.equals(ssoLoginTypeEnum.getType())){
                return ssoLoginTypeEnum.impl;
            }
        }
        return null;
    }






}
