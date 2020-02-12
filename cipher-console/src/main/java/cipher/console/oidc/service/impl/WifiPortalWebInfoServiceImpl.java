package cipher.console.oidc.service.impl;

import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.web.WifiPortalWebInfo;
import cipher.console.oidc.domain.web.WifiPotalPageSettingInfo;
import cipher.console.oidc.mapper.WifiPortalWebInfoMapper;
import cipher.console.oidc.service.WifiPortalWebInfoService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 95744 on 2018/9/17.
 */
@Service

public class WifiPortalWebInfoServiceImpl implements WifiPortalWebInfoService {

    @Autowired
    private WifiPortalWebInfoMapper wifiPortalWebInfoMapper;

    @Override
    public Map<String, Object> getEditProtal(String ip) {
        Map<String,Object> map=new HashMap<>();
        Map<String,Object> msg=new HashMap<>();
        try{
            WifiPotalPageSettingInfo wifiPotalPageSettingInfo=  wifiPortalWebInfoMapper.getEditProtal(ip);
            map.put("code",0);
            msg.put("wifiPotalPageSettingInfo",wifiPotalPageSettingInfo);
            map.put("msg",wifiPotalPageSettingInfo);
        }catch (Exception e){
            map.put("code",1);
            map.put("msg","内部服务器失败");
            e.printStackTrace();
            return map;
        }
        return map;
    }

    @Override
    public int insertSelective(WifiPortalWebInfo record) {
        return wifiPortalWebInfoMapper.insertSelective(record);
    }

    @Override
    public WifiPortalWebInfo selectByPrimaryKey(Integer id) {
        return wifiPortalWebInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public int CountByPortalNum(Integer id, String portalNum) {
        return wifiPortalWebInfoMapper.selectCountByPortalNum(id,portalNum);
    }

    /**
     * modify by 田扛
     * @param record
     * date 2019/3/7
     * @return  更新wifi配置界面
     */
    @Override
    public int updateByPrimaryKeySelective(WifiPotalPageSettingInfo record) {
        return wifiPortalWebInfoMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * modify 田扛
     * data 2019/3/7
     *
     * @param form
     * @param pageModel
     * @return
     */
    @Override
    public Map<String, Object> getWifiPortWebInfoPageList(WifiPotalPageSettingInfo form, DataGridModel pageModel) {
        Map<String, Object> map = new HashMap<>();
        try {
            Map<String, Object> msg = new HashedMap();
            form = (form == null ? new WifiPotalPageSettingInfo() : form);
            form.setPageData(pageModel);
            List<WifiPotalPageSettingInfo> list = wifiPortalWebInfoMapper.selectWifiPortalWebInfoList(form);
            int total = wifiPortalWebInfoMapper.selectWifiPortalWebInfoCount(form);
            map.put("code", 0);
            msg.put("rows", list);
            msg.put("total", total);
            map.put("msg", msg);

        } catch (Exception e) {
            map.put("code", 1);
            map.put("msg", "内部服务器错误");
            e.printStackTrace();
        }
//        map.put("rows", list);
//        map.put("total", total);
        return map;
    }

    @Override
    public int deleteInfo(Integer id) {
        return wifiPortalWebInfoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public WifiPotalPageSettingInfo getWifiPotalPageSettingInfo(String id) {
        return wifiPortalWebInfoMapper.getEditProtal(id);
    }

    @Override
    public void insertWifiPotalPageSettingInfo(WifiPotalPageSettingInfo wifiPotalPageSettingInfo) {
        wifiPortalWebInfoMapper.insertWifiPotalPageSettingInfo(wifiPotalPageSettingInfo);
    }


}
