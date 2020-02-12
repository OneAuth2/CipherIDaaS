package cipher.console.oidc.service.impl;

import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.web.AdActivityDomain;
import cipher.console.oidc.domain.web.AdUserBufferDomain;
import cipher.console.oidc.mapper.AdActivityMapper;
import cipher.console.oidc.mapper.AdUserBufferMapper;
import cipher.console.oidc.service.AdActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: tk
 * @Date: 2018/10/25 20:18
 */
@Service
public class AdActivityServiceImpl implements AdActivityService {
    @Autowired
    private AdActivityMapper adActivityMapper;

    /*获得所有缓冲表中的内容并返回map
     *
     * @param adUserBufferDomain
     * @param dataGridModel
     * @return
     */
    @Override
    public Map<String, Object> getAllUserBufferList(AdActivityDomain adActivityDomain, DataGridModel dataGridModel) {
        adActivityDomain = (adActivityDomain == null) ? new AdActivityDomain() : adActivityDomain;
        Map<String, Object> map = new HashMap<>();
        adActivityDomain.setPageData(dataGridModel);

        List<AdUserBufferDomain> list = adActivityMapper.selectAllUsers(adActivityDomain);
        map.put("rows", list);
        map.put("total", adActivityMapper.getAllUsersCount(adActivityDomain));
        return map;
    }


    /**
     * 获取用户数据
     * @param accountNumber
     * @return
     */
    @Override
    public Map<String, Object> getUserInfo(String accountNumber) {
        Map<String,Object> map=new HashMap<>();
        AdActivityDomain adUserBuffer= adActivityMapper.getUserBufferInfo(accountNumber);//获取缓冲表数据
        AdActivityDomain userInfo=adActivityMapper.getUserInfo(accountNumber);//获取userinfo的数据
        map.put("adUserBuffer",adUserBuffer);
        map.put("userInfo",userInfo);
        return map;
    }

}
