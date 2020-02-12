package com.portal;/**
 * @author lqgzj
 * @date 2019-09-27
 */


import com.portal.license.VerifyLicense;

/**
 * @Author qiaoxi
 * @Date 2019-09-2711:51
 **/
public class licenseVerifyTest {

    public static void main(String[] args) {
        VerifyLicense vLicense = new VerifyLicense();
        //获取参数
        vLicense.setParam("/config/param.properties");
        //验证证书
        vLicense.verify();
    }
}
