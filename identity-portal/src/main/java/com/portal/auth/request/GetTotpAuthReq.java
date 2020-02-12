package com.portal.auth.request;


import com.portal.auth.response.GetTotpAuthResp;
import com.portal.ctid.uri.RequestUrl;

import java.util.HashMap;
import java.util.Map;

@RequestUrl(uri ="/totp/checkreq" )
public class GetTotpAuthReq extends GetCipherAuthReq implements GetRequest {

    private String certToken;
    private String phoneNumber;
    private String mail;
    private String totp;
    private Long timestamp;
    private int uuid;

    public int getUuid() {
        return uuid;
    }

    public void setUuid(int uuid) {
        this.uuid = uuid;
    }

    public GetTotpAuthReq(String certToken, String phoneNumber, String mail, String totp, Long timestamp, int uuid) {
        this.certToken = certToken;
        this.phoneNumber = phoneNumber;
        this.mail = mail;
        this.totp = totp;
        this.timestamp = timestamp;
        this.uuid=uuid;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getCertToken() {
        return certToken;
    }

    public void setCertToken(String certToken) {
        this.certToken = certToken;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTotp() {
        return totp;
    }

    public void setTotp(String totp) {
        this.totp = totp;
    }


    @Override
    public GetTotpAuthResp request() {
        Map<String,Object> map = new HashMap<>();
        map.put("certToken",certToken);
        map.put("phoneNumber",phoneNumber);
        map.put("mail",mail);
        map.put("totp",totp);
        map.put("timestamp",timestamp);
        map.put("uuid",uuid);
        GetTotpAuthResp resp = postMethodRequest(map, GetTotpAuthResp.class);
        return resp;
    }
}
