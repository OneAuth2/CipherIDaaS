package cipher.console.oidc.controller.web;

import cipher.console.oidc.common.CacheKey;
import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.common.NewReturnUtils;
import cipher.console.oidc.domain.web.AdminBehaviorInfo;
import cipher.console.oidc.domain.web.GroupTreeDomain;
import cipher.console.oidc.enums.AdminBehaviorEnum;
import cipher.console.oidc.redis.RedisClient;
import cipher.console.oidc.service.AdminBehaviorInfoService;
import cipher.console.oidc.service.DingGroupService;
import cipher.console.oidc.service.SessionService;
import cipher.console.oidc.token.CheckToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 钉钉组织架构相关
 *
 * @Author: zt
 * @Date: 2019-05-11 15:26
 */
@RestController
@RequestMapping(value = "/cipher/dingGroup")
public class DingGroupController {

    @Autowired
    private DingGroupService dingGroupService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private RedisClient<String,Object> redisClient;


    /**
     * 部门的树结构
     *
     * @param request
     * @param session
     * @return
     */
    @CheckToken
    @RequestMapping(value = "/getGroupTreeList")
    public List<GroupTreeDomain> getGroupTreeList(HttpServletRequest request, HttpSession session) {
        return dingGroupService.getGroupTreeList(sessionService.getCompanyUUid(session));
    }

    /**
     * 扫描钉钉组织结构
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "/scanDingGroup")
    public Map<String, Object> scanDingGroup(HttpSession session, HttpServletResponse response) {
        return dingGroupService.scanDingGroup(sessionService.getCompanyUUid(session));
    }

    /**
     * 获取钉钉的group列表
     *
     * @param dataGridModel
     * @param session
     * @return
     */
    @CheckToken
    @RequestMapping(value = "/getGroupList")
    public Map<String, Object> getGroupList(DataGridModel dataGridModel, HttpSession session) {
        String companyId = sessionService.getCompanyUUid(session);
        return dingGroupService.getDingGroupList(dataGridModel, companyId);
    }

    /**
     * 将钉钉的组织结构从缓冲表(cipher_ding_group_buffer)同步到cipher_group_info
     *
     * @param session
     * @return
     */
    @CheckToken
    @RequestMapping(value = "/sync")
    public Map<String, Object> sync(HttpSession session) {
        String companyId = sessionService.getCompanyUUid(session);
        try {
            redisClient.remove(CacheKey.getCacheOrganitionTreeList(companyId));
            redisClient.remove(CacheKey.getCacheGroupTreeList(companyId));
            return dingGroupService.sync(companyId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return NewReturnUtils.failureResponse("处理异常!");
    }
}
