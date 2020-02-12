package cipher.console.oidc.service.impl;

import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.common.NewReturnUtils;
import cipher.console.oidc.common.ReturnMsg;
import cipher.console.oidc.domain.exceldomain.EquipBehaviorExcle;
import cipher.console.oidc.domain.web.EquipBehaviorDomain;
import cipher.console.oidc.domain.web.EquipBehaviorInfo;
import cipher.console.oidc.mapper.EquipBehaviorMapper;
import cipher.console.oidc.service.EquipBehaviorService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EquipBehaviorServiceImpl implements EquipBehaviorService {

    @Autowired
    private EquipBehaviorMapper equipBehaviorMapper;

    @Override
    public Map<String, Object> getEquipPageList(EquipBehaviorDomain equipBehaviorDomain, DataGridModel pageModel) {
        Map<String, Object> map = new HashMap<>();
        int total = 0;
        if(equipBehaviorDomain!=null&&pageModel!=null){
            equipBehaviorDomain.setPageData(pageModel);
            List<EquipBehaviorDomain> vpnList = equipBehaviorMapper.getVpnList(equipBehaviorDomain);
            total = equipBehaviorMapper.selectVpnCount(equipBehaviorDomain);
            if(vpnList!=null&&vpnList.size()>0){
                for(EquipBehaviorDomain equipBehaviorDomain1:vpnList){
                    String vpnId = equipBehaviorDomain1.getVpnId();
                    if(StringUtils.isNotEmpty(vpnId)&&StringUtils.isNotEmpty(equipBehaviorDomain.getCompanyId())){
                        //查询最近七天的日志
                        equipBehaviorDomain1.setRecentlySeven(equipBehaviorMapper.selectRecentlySeven(vpnId,equipBehaviorDomain.getCompanyId()));
                        //查询最近七天的日志
                        equipBehaviorDomain1.setRecentlyThirty(equipBehaviorMapper.selectRecentlyThirty(vpnId,equipBehaviorDomain.getCompanyId()));
                    }
                }
            }
            map = NewReturnUtils.successResponse(ReturnMsg.EQUIPLISTSUCCESS);
            map.put("rows", vpnList);
            map.put("total", total);
        }else {
            map = NewReturnUtils.failureResponse(ReturnMsg.EQUIPPARAMETERSMISS);
        }
        return map;
    }

    @Override
    public Map<String, Object> getEquipLogList(EquipBehaviorInfo equipBehaviorInfo, DataGridModel pageModel) {
        Map<String, Object> map = new HashMap<>();
        int total = 0;
        if(equipBehaviorInfo!=null&&pageModel!=null){
            equipBehaviorInfo.setPageData(pageModel);
            List<EquipBehaviorInfo> equipLogList = equipBehaviorMapper.getEquipLogList(equipBehaviorInfo);
            total = equipBehaviorMapper.selectEquipLogCount(equipBehaviorInfo);
            map = NewReturnUtils.successResponse(ReturnMsg.EQUIPLISTSUCCESS);
            map.put("rows",equipLogList);
            map.put("total", total);
        }else {
            map = NewReturnUtils.failureResponse(ReturnMsg.EQUIPPARAMETERSMISS);
        }
        return map;
    }

    @Override
    public List<EquipBehaviorExcle> getEquipBehaviorExcle(EquipBehaviorInfo equipBehaviorInfo) {
        return equipBehaviorMapper.selectEquipBehaviorExcle(equipBehaviorInfo);
    }
}
