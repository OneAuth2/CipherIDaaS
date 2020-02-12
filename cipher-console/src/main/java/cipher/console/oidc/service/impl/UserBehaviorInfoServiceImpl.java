package cipher.console.oidc.service.impl;

import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.exceldomain.UserBehaviorExcle;
import cipher.console.oidc.domain.web.UserBehaviorInfo;
import cipher.console.oidc.enums.UserBehaviorEnum;
import cipher.console.oidc.mapper.UserBehaviorInfoMapper;
import cipher.console.oidc.service.UserBehaviorInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 95744 on 2018/6/4.
 */
@Service
public class UserBehaviorInfoServiceImpl implements UserBehaviorInfoService {

    @Autowired
    private UserBehaviorInfoMapper userBehaviorInfoMapper;

    @Override
    public Map<String, Object> getUserBehaviorPageList(UserBehaviorInfo form, DataGridModel pageModel) {
        Map<String, Object> map = new HashMap<>();
        form = (form == null ? new UserBehaviorInfo() : form);
        form.setPageData(pageModel);
        List<UserBehaviorInfo> list = userBehaviorInfoMapper.selectUserBehaviorList(form);
        for(UserBehaviorInfo userBehaviorInfo:list){
            userBehaviorInfo.setTypeStr(UserBehaviorEnum.getUserBehaviorEnum(Integer.valueOf(userBehaviorInfo.getType())));
        }
        int total = userBehaviorInfoMapper.selectUserBehaviorCount(form);
        map.put("rows", list);
        map.put("total", total);
        return map;
    }

    @Override
    public int insertUserBehaviorInfo(UserBehaviorInfo userBehaviorInfo) {

        return userBehaviorInfoMapper.insertSelective(userBehaviorInfo);

    }

    @Override
    public List<UserBehaviorExcle> exportExcle(UserBehaviorInfo form) {
        return userBehaviorInfoMapper.exportExcle(form);
    }

}
