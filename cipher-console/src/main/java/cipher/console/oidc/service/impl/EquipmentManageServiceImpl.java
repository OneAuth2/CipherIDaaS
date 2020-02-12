package cipher.console.oidc.service.impl;

import cipher.console.oidc.common.NewReturnUtils;
import cipher.console.oidc.common.ReturnMsg;
import cipher.console.oidc.domain.web.EquipBehaviorInfo;
import cipher.console.oidc.domain.web.UserInfoDomain;
import cipher.console.oidc.domain.web.VpnConfigurationDomain;
import cipher.console.oidc.mapper.EquipmentManageMapper;
import cipher.console.oidc.publistener.EquipBehaviorPublistener;
import cipher.console.oidc.service.EquipmentManageService;
import cipher.console.oidc.service.UUIDService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EquipmentManageServiceImpl implements EquipmentManageService {

    private static final Logger logger = LoggerFactory.getLogger(EquipmentManageServiceImpl.class.getSimpleName());

    @Autowired
    private EquipmentManageMapper equipmentManageMapper;

    @Autowired
    private UUIDService uuidService;

    @Autowired
    private EquipBehaviorPublistener equipBehaviorPublistener;

    @Override
    public List<VpnConfigurationDomain> getVpnConfigList(String companyId,VpnConfigurationDomain vpnConfigurationDomain) {
        List<VpnConfigurationDomain> list = new ArrayList<>();
        if(StringUtils.isNotEmpty(companyId)){
            vpnConfigurationDomain.setCompanyId(companyId);
            list = equipmentManageMapper.selectVpnConfigList(vpnConfigurationDomain);
        }
        return list;
    }

    @Override
    public int getVpnConfigCount(String companyId) {
        return equipmentManageMapper.obtainVpnConfigCount(companyId);
    }

    @Override
    public Map<String, Object> modifyVpnConfig(VpnConfigurationDomain vpnConfigurationDomain, UserInfoDomain userInfoDomain) {
        if(vpnConfigurationDomain!=null){
            //修改vpn设备信息
            if(StringUtils.isNotEmpty(vpnConfigurationDomain.getUuid())){
                try {
                    equipmentManageMapper.updateVpnConfig(vpnConfigurationDomain);
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("enter EquipmentManageServiceImpl.modifyVpnConfig() but update failed,vpnConfigurationDomain=[{}]==",new Object[]{
                            vpnConfigurationDomain});
                    return NewReturnUtils.failureResponse(ReturnMsg.getVpnConfigListMsg(2));
                }
                try{
                    EquipBehaviorInfo equipBehaviorInfo = new EquipBehaviorInfo(userInfoDomain.getUuid(),vpnConfigurationDomain.getCompanyId(),
                            vpnConfigurationDomain.getUuid(),1,"修改设备配置",
                            "使用账号:"+userInfoDomain.getAccountNumber()+"修改\""+vpnConfigurationDomain.getDevicename()+"\"的配置信息");
                    equipBehaviorPublistener.publish(equipBehaviorInfo);
                }catch (Exception e){
                    e.printStackTrace();
                }
                return NewReturnUtils.successResponse(ReturnMsg.getVpnConfigListMsg(0));
                //添加vpn设备信息
            }else{
                String uuid = uuidService.getUUid();
                vpnConfigurationDomain.setUuid(uuid);
                try {
                    equipmentManageMapper.insertVpnConfig(vpnConfigurationDomain);
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("enter EquipmentManageServiceImpl.modifyVpnConfig() but insert failed,vpnConfigurationDomain=[{}]==",new Object[]{
                            vpnConfigurationDomain});
                    return NewReturnUtils.failureResponse(ReturnMsg.getVpnConfigListMsg(3));
                }
                try{
                    EquipBehaviorInfo equipBehaviorInfo = new EquipBehaviorInfo(userInfoDomain.getUuid(),vpnConfigurationDomain.getCompanyId(),
                            vpnConfigurationDomain.getUuid(),1,"添加设备配置",
                            "使用账号:"+userInfoDomain.getAccountNumber()+"添加\""+vpnConfigurationDomain.getDevicename()+"\"配置");
                    equipBehaviorPublistener.publish(equipBehaviorInfo);
                }catch (Exception e){
                    e.printStackTrace();
                }
                return NewReturnUtils.successResponse(ReturnMsg.getVpnConfigListMsg(0));
            }
        }
        return NewReturnUtils.failureResponse(ReturnMsg.getVpnConfigListMsg(1));
    }

    @Override
    public Map<String, Object> vpnConfigByUuid(String uuid, String companyId) {
        Map<String, Object> map = new HashMap<>();
        VpnConfigurationDomain vpnConfigurationDomain = equipmentManageMapper.vpnConfigByUuid(uuid, companyId);
        map = NewReturnUtils.successResponse(ReturnMsg.getVpnConfigListMsg(4));
        map.put("return_result",vpnConfigurationDomain);
        return map;
    }

    @Override
    public Map<String, Object> vpnConfigDel(String uuid, String companyId,UserInfoDomain userInfoDomain) {
        Map<String, Object> map = new HashMap<>();
        if(StringUtils.isNotEmpty(uuid)){
            try{
                String devicename = equipmentManageMapper.selectDevicenameByUuid(uuid);
                if(StringUtils.isEmpty(devicename)){
                    devicename = "无设备名称";
                }
                EquipBehaviorInfo equipBehaviorInfo = new EquipBehaviorInfo(userInfoDomain.getUuid(),companyId,
                        uuid,1,"删除设备配置",
                        "使用账号:"+userInfoDomain.getAccountNumber()+"删除\""+devicename+"\"配置");
                equipBehaviorPublistener.publish(equipBehaviorInfo);
            }catch (Exception e){
                e.printStackTrace();
            }
            equipmentManageMapper.vpnConfigDel(uuid,companyId);
            return NewReturnUtils.successResponse("vpn设备信息删除成功");
        }
        return NewReturnUtils.successResponse("vpn设备信息删除失败");
    }
}
