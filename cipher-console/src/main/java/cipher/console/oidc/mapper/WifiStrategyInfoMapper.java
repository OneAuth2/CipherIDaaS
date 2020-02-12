package cipher.console.oidc.mapper;

import cipher.console.oidc.domain.web.WifiStrategyInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WifiStrategyInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WifiStrategyInfo record);

    int insertSelective(WifiStrategyInfo record);

    WifiStrategyInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WifiStrategyInfo record);

    int updateByPrimaryKey(WifiStrategyInfo record);

    List<WifiStrategyInfo> getWifiStategyInfo();

    Integer getStategyId(@Param(value = "companyUuid") String companyUuid,@Param(value = "type") String type);

    /**
     * 查询员工wifi访问策略
     * */
    WifiStrategyInfo getStaffWifiStrategyInfo(@Param(value = "companyUuid") String companyUuid);

    /**
     * 查询访客wifi访问策略
     * */
    WifiStrategyInfo getVisitorWifiStrategyInfo(@Param(value = "companyUuid") String companyUuid);


    WifiStrategyInfo getMacDeviceBinding(String companyId);

    /**
     * 向数据库插入wifi策略
     * */
    void insertIntoWifiStrategy(WifiStrategyInfo wifiStrategyInfo);

}