package cipher.console.oidc.service.impl;

import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.web.AcSetInfo;
import cipher.console.oidc.domain.web.WifiPortalSetInfoDomain;
import cipher.console.oidc.enums.AcDeviceEnum;
import cipher.console.oidc.mapper.AcSetInfoMapper;
import cipher.console.oidc.service.AcSetInfoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 95744 on 2018/9/17.
 */
@Service
public class AcSetInfoServiceImpl implements AcSetInfoService {

    @Autowired
    private AcSetInfoMapper acSetInfoMapper;

    @Override
    public Map<String, Object> deleteAc(AcSetInfo acSetInfo) {
        Map<String,Object> map=new HashMap<>();
        try{
            acSetInfoMapper.deleteByPrimaryKey(acSetInfo.getId());
            map.put("code",0);
            map.put("msg","删除成功");
        }catch (Exception e){
            map.put("code",1);
            map.put("msg","内部服务器错误");
        }

        return map;
    }

    @Override
    public int insertSelective(WifiPortalSetInfoDomain record) {
        return acSetInfoMapper.insertSelective(record);
    }

    @Override
    public AcSetInfo selectByPrimaryKey(Integer id) {
        return acSetInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(AcSetInfo record) {
        return acSetInfoMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public Map<String, Object> getAcSetPageList(AcSetInfo form, DataGridModel pageModel) {
        Map<String, Object> map = new HashMap<>();
        form = (form == null ? new AcSetInfo() : form);
        form.setPageData(pageModel);
        List<AcSetInfo> list = acSetInfoMapper.selectAcSetInfoList(form);
        if(null!=list&&list.size()>0) {
            for (AcSetInfo acSetInfo : list) {
                if(StringUtils.isNotEmpty(String.valueOf(acSetInfo.getAcDeviceId())) &&null!=acSetInfo.getAcDeviceId()){
                    acSetInfo.setAcDeviceName(AcDeviceEnum.getAcDevice(acSetInfo.getAcDeviceId()));
                }
            }
        }
        int total = acSetInfoMapper.selectAcSetInfoCount(form);
        map.put("rows", list);
        map.put("total", total);
        return map;
    }

    @Override
    public int deleteAcInfo(Integer id) {
        return acSetInfoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insertWifiPortalSetInfo(AcSetInfo record) {
        return acSetInfoMapper.insertWifiPortalSetInfo(record);
    }

    @Override
    public void updateAcSetInfo(WifiPortalSetInfoDomain form) {
        acSetInfoMapper.updateAcSetInfo(form);
    }
}
