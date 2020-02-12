package cipher.console.oidc.service.impl;

import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.web.DeviceInfo;
import cipher.console.oidc.domain.web.StaffDeviceBindingInfo;
import cipher.console.oidc.mapper.StaffDeviceBindingMapper;
import cipher.console.oidc.service.StaffDeviceBindingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StaffDeviceBindingServiceImpl implements StaffDeviceBindingService {

    @Autowired
    private StaffDeviceBindingMapper staffDeviceBindingMapper;
    @Override
    public Map<String, Object> selectStaffDeviceBindingInfoList(StaffDeviceBindingInfo form, DataGridModel pageModel) {
        Map<String, Object> map = new HashMap<>();
        form = (form == null ? new StaffDeviceBindingInfo() : form);
        form.setPageData(pageModel);
        List<StaffDeviceBindingInfo> list = staffDeviceBindingMapper.selectStaffDeviceBindingList(form);
        int total = staffDeviceBindingMapper.selecttaffDeviceBindingCount(form);
        map.put("rows", list);
        map.put("total", total);
        return map;
    }

    @Override
    public int deleteStaffDeviceBindingInfo(int id) {
        return staffDeviceBindingMapper.deleteStaffDeviceBindingInfo(id);
    }
}
