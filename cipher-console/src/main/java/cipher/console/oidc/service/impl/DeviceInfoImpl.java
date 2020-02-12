package cipher.console.oidc.service.impl;

import cipher.console.oidc.common.ConstansUMP;
import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.web.DeviceInfo;
import cipher.console.oidc.domain.web.UserBehaviorInfo;
import cipher.console.oidc.mapper.DeviceInfoMapper;
import cipher.console.oidc.service.DeviceInfoService;
import cipher.console.oidc.service.UserBehaviorInfoService;
import cipher.console.oidc.util.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DeviceInfoImpl implements DeviceInfoService {

    @Autowired
    private DeviceInfoMapper deviceInfoMapper;
    @Autowired
    private UserBehaviorInfoService userBehaviorInfoService;

    @Override
    public int accountBundledCount(String accountNumber) {
        return deviceInfoMapper.accountBundledCount(accountNumber);
    }

    @Override
    public boolean clearBundleInfo(String accountNumber) {
        if (deviceInfoMapper.clearBundleInfo(accountNumber) >= 0) {
            return true;
        }
        return false;
    }

    @Override
    public Map<String, Object> selectDeviceInfoList(DeviceInfo form, DataGridModel pageModel) {
        Map<String, Object> map = new HashMap<>();
        form = (form == null ? new DeviceInfo() : form);
        form.setPageData(pageModel);
        List<DeviceInfo> list = deviceInfoMapper.selectDeviceInfoList(form);
        if (null != list && list.size() > 0) {
            for (DeviceInfo deviceInfo : list) {
                List<String> hmans = deviceInfoMapper.getHmansList(deviceInfo.getAccountNumber());
                deviceInfo.setHman(getStr(hmans));
            }
        }
        int total = deviceInfoMapper.selectDeviceCount(form);
        map.put("rows", list);
        map.put("total", total);
        return map;
    }


    public static String getStr(List<String> list) {
        String str = String.join(",", list);
        return str;
    }

    @Override
    public Map<String, Object> selectDeviceInfoListByAccountNumber(DeviceInfo form, DataGridModel pageModel) {
        Map<String, Object> map = new HashMap<>();
        form = (form == null ? new DeviceInfo() : form);
        form.setPageData(pageModel);
        List<DeviceInfo> list = deviceInfoMapper.selectDeviceInfoListByAccountNumber(form);
        int total = deviceInfoMapper.selectDeviceCountByAccountNumber(form);
        map.put("rows", list);
        map.put("total", total);
        return map;
    }

    @Override
    public int deleteDevice(int id) {
        try {
            String accountName =getAccountNumber(id);
           // UserBehaviorInfo userBehaviorInfo = new UserBehaviorInfo(accountName, null, ConstansUMP.ACCOUNT_SECURITY, IpUtil.getIp(), "用户设备解绑成功", accountName + "设备解绑", new Date());
           // userBehaviorInfoService.insertUserBehaviorInfo(userBehaviorInfo);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return deviceInfoMapper.deleteDevice(id);
    }

    @Override
    public Integer getCount(DeviceInfo deviceInfo) {
        return deviceInfoMapper.selectDeviceCountByAccountNumber(deviceInfo);
    }

    @Override
    public Integer updateDevice(int number, int openSelect,String companyId) {
        return deviceInfoMapper.updateDevice(number, openSelect,companyId);
    }

    @Override
    public String getAccountNumber(int id) {
        return deviceInfoMapper.getAccountNumber(id);
    }

    @Override
    public Integer getDeviceNum(String companyId) {
        return deviceInfoMapper.getDeviceNum(companyId);
    }
}





