package cipher.console.oidc.mapper;

import cipher.console.oidc.domain.web.DeviceInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DeviceInfoMapper {
    public String getAccountNumber(int id);

    public int accountBundledCount(String accountNumber);

    public int clearBundleInfo(String accountNumber);

    public List<DeviceInfo> selectDeviceInfoList(DeviceInfo form);

    public int selectDeviceCount(DeviceInfo form);

    public List<DeviceInfo> selectDeviceInfoListByAccountNumber(DeviceInfo form);

    public Integer selectDeviceCountByAccountNumber(DeviceInfo form);

    public int deleteDevice(@Param(value = "id") int id);

    public Integer updateDevice(@Param(value = "number") int number,
                                @Param(value = "openSelect") int openSelect,
                                @Param(value = "companyId") String companyId);


    public List<String> getHmansList(String accountNumber);

    public Integer getDeviceNum(String companyId);
}
