package cipher.console.oidc.service;

import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.exceldomain.UserBehaviorExcle;
import cipher.console.oidc.domain.web.UserBehaviorInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by 95744 on 2018/6/4.
 */

public interface UserBehaviorInfoService {

    public Map<String,Object> getUserBehaviorPageList(UserBehaviorInfo form, DataGridModel pageModel);

    public  int  insertUserBehaviorInfo(UserBehaviorInfo userBehaviorInfo);//插入行为日志

    List<UserBehaviorExcle> exportExcle(UserBehaviorInfo form);

}
