package cipher.console.oidc.service;

import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.exceldomain.AdminBehaviorExcle;
import cipher.console.oidc.domain.exceldomain.NewAdminBehaviorExcle;
import cipher.console.oidc.domain.web.AdminBehaviorInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * Created by 95744 on 2018/6/4.
 */

public interface AdminBehaviorInfoService {
    public Map<String,Object> getAdminBehaviorPageList(AdminBehaviorInfo form, DataGridModel pageModel);

    List<NewAdminBehaviorExcle> exportExcle(AdminBehaviorInfo form);

    void insertSelective(AdminBehaviorInfo record, HttpSession session);
}
