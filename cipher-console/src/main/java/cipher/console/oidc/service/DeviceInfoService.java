package cipher.console.oidc.service;

import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.web.DeviceInfo;

import java.util.Map;

public interface DeviceInfoService {

    public int accountBundledCount(String accountNumber);

    public boolean clearBundleInfo(String accountNumber);


    public Map<String,Object> selectDeviceInfoList(DeviceInfo deviceInfo, DataGridModel pageModel);

    public Map<String,Object> selectDeviceInfoListByAccountNumber(DeviceInfo deviceInfo, DataGridModel pageModel);

    public int deleteDevice(int id);

    public Integer getCount(DeviceInfo deviceInfo);

    public Integer updateDevice(int number,int openSelect,String companyId);

    public String getAccountNumber(int id);
    public Integer getDeviceNum(String companyId);
}
