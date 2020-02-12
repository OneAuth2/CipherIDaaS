package cipher.console.oidc.listener;

import cipher.console.oidc.domain.web.EquipBehaviorInfo;
import cipher.console.oidc.event.EquipBehaviorEvent;
import cipher.console.oidc.mapper.EquipBehaviorInfoMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 插入设备审计日志监听器
 * @author cozi
 * @date 2019-07-02
 **/
@Component
public class EquipBehaviorListener {

    @Autowired
    private EquipBehaviorInfoMapper equipBehaviorInfoMapper;


    @EventListener
    public void onAplicationEvent(EquipBehaviorEvent equipBehaviorEvent){
        //处理插入日志逻辑
        if(equipBehaviorEvent.getEquipBehaviorInfo()!=null){
            EquipBehaviorInfo equipBehaviorInfo = equipBehaviorEvent.getEquipBehaviorInfo();
            if(StringUtils.isNotEmpty(equipBehaviorInfo.getUserId())){
                //获取用户信息
                String userInfo = equipBehaviorInfoMapper.selectUserInfoByUserId(equipBehaviorInfo.getUserId());
                //添加用户信息
                equipBehaviorInfo.setUserInfo(userInfo);
                try {
                    equipBehaviorInfoMapper.insertEquipBehavior(equipBehaviorInfo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

    }
}
