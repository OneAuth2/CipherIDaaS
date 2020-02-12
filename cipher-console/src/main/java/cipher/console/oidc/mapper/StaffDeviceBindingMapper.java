package cipher.console.oidc.mapper;

import cipher.console.oidc.domain.web.StaffDeviceBindingInfo;

import java.util.List;

public interface   StaffDeviceBindingMapper {

    List<StaffDeviceBindingInfo>  selectStaffDeviceBindingList(StaffDeviceBindingInfo form);

    int selecttaffDeviceBindingCount(StaffDeviceBindingInfo form);

    int  deleteStaffDeviceBindingInfo(int id);
}
