package cipher.console.oidc.service.impl;

import cipher.console.oidc.domain.web.MacDeviceBinding;
import cipher.console.oidc.domain.web.StaffInfoDomain;
import cipher.console.oidc.domain.web.VisitorInfoDomain;
import cipher.console.oidc.domain.web.WifiStrategyInfo;
import cipher.console.oidc.mapper.WifiStrategyInfoMapper;
import cipher.console.oidc.service.WifiStrategyInfoService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 95744 on 2018/9/19.
 */
@Service
public class WifiStrategyInfoServiceImpl implements WifiStrategyInfoService {
   @Autowired
   private WifiStrategyInfoMapper wifiStrategyInfoMapper;


    @Override
    public int insertSelective(WifiStrategyInfo record) {
        return wifiStrategyInfoMapper.insertSelective(record);
    }

    @Override
    public WifiStrategyInfo selectByPrimaryKey(Integer id) {
        return wifiStrategyInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(WifiStrategyInfo record) {
        return wifiStrategyInfoMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<WifiStrategyInfo> getWifiStategyInfoList() {
        return wifiStrategyInfoMapper.getWifiStategyInfo();
    }

    @Override
    public Integer getStrategyId(String companyUuid, String type) {
        return wifiStrategyInfoMapper.getStategyId(companyUuid,type);
    }

    @Override
    public StaffInfoDomain getStaffStrategy(String companyUuid) {
        WifiStrategyInfo strategyInfo = wifiStrategyInfoMapper.getStaffWifiStrategyInfo(companyUuid);

        //员工策略为空
        if (strategyInfo == null){
            return null;
        }

        //对策略进行Json转换
        try {
            return new Gson().fromJson(strategyInfo.getValue(),StaffInfoDomain.class);
        }catch (Exception e){
            //策略转换失败
            return null;
        }
    }

    @Override
    public VisitorInfoDomain getVisitorStrategy(String companyUuid) {
        WifiStrategyInfo strategyInfo = wifiStrategyInfoMapper.getVisitorWifiStrategyInfo(companyUuid);

        //员工策略为空
        if (strategyInfo == null){
            return null;
        }

        //对策略进行Json转换
        try {
            return new Gson().fromJson(strategyInfo.getValue(),VisitorInfoDomain.class);
        }catch (Exception e){
            //策略转换失败
            return null;
        }
    }

    @Override
    public boolean insertStrategy(WifiStrategyInfo wifiStrategyInfo) {
        try {
            wifiStrategyInfoMapper.insertIntoWifiStrategy(wifiStrategyInfo);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public MacDeviceBinding getMacDeviceBinding(String companyId) {
        WifiStrategyInfo strategyInfo = wifiStrategyInfoMapper.getMacDeviceBinding(companyId);

        //员工策略为空
        if (strategyInfo == null){
            return null;
        }

        //对策略进行Json转换
        try {
            return new Gson().fromJson(strategyInfo.getValue(),MacDeviceBinding.class);
        }catch (Exception e){
            //策略转换失败
            return null;
        }
    }

}



















