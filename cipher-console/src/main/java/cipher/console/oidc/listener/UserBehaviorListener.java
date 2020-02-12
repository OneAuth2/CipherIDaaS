package cipher.console.oidc.listener;

import cipher.console.oidc.domain.web.UserBehaviorInfo;
import cipher.console.oidc.event.UserBehaviorEvent;
import cipher.console.oidc.mapper.UserBehaviorInfoMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 插入用户日志监听器
 * @author cozi
 * @date 2019-07-02
 **/
@Component
public class UserBehaviorListener {

    @Autowired
    private UserBehaviorInfoMapper userBehaviorInfoMapper;

    @EventListener
    public void onAplicationEvent(UserBehaviorEvent userBehaviorEvent){
        //处理插入日志逻辑
        if(userBehaviorEvent.getUserBehaviorInfo()!=null){
            UserBehaviorInfo userBehaviorInfo = userBehaviorEvent.getUserBehaviorInfo();
            if(StringUtils.isNotEmpty(userBehaviorInfo.getUserId())){
                //获取用户信息
                String userInfo = userBehaviorInfoMapper.selectUserInfoByUserId(userBehaviorInfo.getUserId());
                //添加用户信息
                userBehaviorInfo.setUserInfo(userInfo);
                //添加事件简介
                userBehaviorInfo.setMsg(userInfo+userBehaviorInfo.getMsg());
                userBehaviorInfoMapper.insertSelective(userBehaviorInfo);
            }

        }

    }
}
