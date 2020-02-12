package cipher.console.oidc.service.impl.subapp;

import cipher.console.oidc.common.HttpKey;
import cipher.console.oidc.model.ZhOaUserModel;
import cipher.console.oidc.service.subapp.ZhOaInsertUserService;
import cipher.console.oidc.util.HttpClientUtils;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * import requests
 * url = 'http://oa.unittec.com:9002/hr/api/staff/new/?unittec_api_key=FhN0sYVv9n'
 * d = {'username': 'vicalloyx', 'profile_name': 'vicalloy', 'mobile': '123'}
 * r = requests.post(url, data=d)
 * print (r.content)
 *
 * @Author: zt
 * @Date: 2019-4-11 16:56
 */
@Service
public class ZhOaInsertUserServiceImpl implements ZhOaInsertUserService {

    Logger logger = LoggerFactory.getLogger(ZhOaInsertUserServiceImpl.class);

   // String url = "http://oa.unittec.com:9002/hr/api/staff/new/?unittec_api_key=FhN0sYVv9n";

    String url = "http://oa.unittec.com/hr/api/staff/new/?unittec_api_key=FhN0sYVv9n";

    String unittecApiKey = "FhN0sYVv9n";


    @Override
    public boolean insertAccount(ZhOaUserModel zhOaUserModel) {
        logger.info("Enter the zhonghe oa distribute account=====>");
        logger.info("the param is:" + zhOaUserModel);
        Map<String, Object> map = HttpClientUtils.doPost(url, constrctParam(zhOaUserModel), null);
        if (!map.containsKey(HttpKey.RES)) {
            return false;
        }
        String res = (String) map.get(HttpKey.RES);
        if (StringUtils.isEmpty(res)) {
            return false;
        }

        try {
            JSON.parse(res);
        }catch (Exception e){
            logger.error("该用户已经存在!");
            return false;
        }


        if (!res.contains("uid")) {
            return false;
        }
        logger.info("the distibute oa user result is=====>" + res);
        return true;
    }


    private List<NameValuePair> constrctParam(ZhOaUserModel zhOaUserModel) {
        List<NameValuePair> list = new ArrayList<>();
        list.add(new BasicNameValuePair("username", zhOaUserModel.getUsername()));
        list.add(new BasicNameValuePair("profile_name", zhOaUserModel.getProfileName()));
        list.add(new BasicNameValuePair("mobile", zhOaUserModel.getMobile()));
        return list;
    }




}
