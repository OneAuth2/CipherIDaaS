package cipher.console.oidc.service;

import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.web.DeviceInfo;
import cipher.console.oidc.domain.web.StaffDeviceBindingInfo;

import java.util.Map;

public interface StaffDeviceBindingService {

    public Map<String,Object> selectStaffDeviceBindingInfoList(StaffDeviceBindingInfo staffDeviceBindingInfo, DataGridModel pageModel);

    public  int deleteStaffDeviceBindingInfo(int id);
}
