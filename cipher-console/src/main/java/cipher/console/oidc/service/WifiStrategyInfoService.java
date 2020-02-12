package cipher.console.oidc.service;

import cipher.console.oidc.domain.web.MacDeviceBinding;
import cipher.console.oidc.domain.web.StaffInfoDomain;
import cipher.console.oidc.domain.web.VisitorInfoDomain;
import cipher.console.oidc.domain.web.WifiStrategyInfo;

import java.util.List;

/**
 * Created by 95744 on 2018/9/19.
 */
public interface WifiStrategyInfoService {

    int insertSelective(WifiStrategyInfo record);

    WifiStrategyInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WifiStrategyInfo record);

    List<WifiStrategyInfo> getWifiStategyInfoList();

    Integer getStrategyId(String companyUuid,String type);

    /**
     * 获取员工wifi访问策略
     * */
    StaffInfoDomain getStaffStrategy(String compaUuid);

    /**
     * 获取访客wifi访问策略
     * */
    VisitorInfoDomain getVisitorStrategy(String companyUuid);

    /**
     * 插入wifi策略
     * */
    boolean insertStrategy(WifiStrategyInfo wifiStrategyInfo);

    /**
     * 获取访客设备绑定数量限制数量
     * */
    MacDeviceBinding getMacDeviceBinding(String companyId);



}
