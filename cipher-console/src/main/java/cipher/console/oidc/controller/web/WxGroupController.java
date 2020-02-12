package cipher.console.oidc.controller.web;/**
 * @author lqgzj
 * @date 2019-10-11
 */

import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.common.NewReturnUtils;
import cipher.console.oidc.service.SessionService;
import cipher.console.oidc.service.WxGroupService;
import cipher.console.oidc.token.CheckToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 企业微信组织架构相关
 *
 * @Author qiaoxi
 * @Date 2019-10-1114:54
 **/
@RestController
@RequestMapping(value = "/cipher/wxGroup")
public class WxGroupController {

    @Autowired
    private WxGroupService wxGroupService;

    @Autowired
    private SessionService sessionService;

    /**
     * 获取企业微信的group列表
     *
     * @param dataGridModel
     * @param session
     * @return
     */
    @CheckToken
    @RequestMapping(value = "/getGroupList")
    public Map<String, Object> getGroupList(DataGridModel dataGridModel, HttpSession session) {
        String companyId = sessionService.getCompanyUUid(session);
        return wxGroupService.getWxGroupList(dataGridModel, companyId);
    }

    /**
     * 扫描企业微信部门
     *
     * @param session
     * @param response
     * @return
     */
    @RequestMapping(value = "/scanWxGroup")
    public Map<String, Object> scanWxGroup(HttpSession session, HttpServletResponse response) {
        return wxGroupService.scanWxGroup( sessionService.getCompanyUUid(session));
    }


    @CheckToken
    @RequestMapping(value = "/sync")
    public Map<String, Object> sync(HttpSession session) {
       String companyId = sessionService.getCompanyUUid(session);
        try {
            return wxGroupService.sync(companyId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return NewReturnUtils.failureResponse("处理异常!");
    }

}
