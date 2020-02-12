package cipher.console.oidc.controller.web;


import cipher.console.oidc.common.ConstantsCMP;
import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.web.CompanyInfoDomain;
import cipher.console.oidc.domain.web.InvitCodeInfo;
import cipher.console.oidc.service.CompanyInfoService;
import cipher.console.oidc.service.InvitCodeService;
import cipher.console.oidc.service.UserCompanyMapService;
import cipher.console.oidc.util.aes.AES;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
@RequestMapping(value = "/cipher/invit")
@EnableAutoConfiguration
public class InvitCodeController {

    @Autowired
    private InvitCodeService invitCodeService;

    @Autowired
    private CompanyInfoService companyInfoService;

    @Autowired
    private UserCompanyMapService userCompanyMapService;
    /**
     * create by 田扛
     * create time 2019年3月18日08:59:29
     * 根据管理员账号获取公司的companyId
     *
     */
    @RequestMapping("/getCompanyId")
    @ResponseBody
    public Map<String,Object> getCompanyId(String accountNumber){
        return  userCompanyMapService.getCompanyId(accountNumber);
    }


    /**
     * 获取已邀请用户信息列表
     *
     * @param pageModel
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/list", params = "json", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getList(String queryName,DataGridModel pageModel, HttpServletRequest request, HttpServletResponse response) {
        String userName="ly@cipherchina.com.cn";
        Map<String, Object> map = userCompanyMapService.getUserInfo(queryName,userName, pageModel);
        return map;
    }

    /**
     * 获取公司超时时间
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/outOfDate", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getOutOfDate(HttpServletRequest request, HttpServletResponse response) {
        HashMap<String, Object> map = new HashMap<>();
        String userName = ConstantsCMP.getSessionUser(request);
        CompanyInfoDomain companyInfo = companyInfoService.getCompanyInfoById(userName);
        map.put("companyInfo", companyInfo);
        return map;

    }

    /**
     * 获取过去生成未使用的邀请码，初始化页面
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/oldCode", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getOldCodeList(HttpServletRequest request, HttpServletResponse response) {
        String userName = ConstantsCMP.getSessionUser(request);
        HashMap<String, Object> map = new HashMap<>();
        List<InvitCodeInfo> list = invitCodeService.queryInvitCodeList(userName);
        map.put("codelist", list);
        return map;

    }

    /**
     * 生成新邀请码并回显页面
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getCreateInvitCode(HttpServletRequest request, HttpServletResponse response) {
        String userName = ConstantsCMP.getSessionUser(request);
        //解密
       //  String name=new String(AES.decryptFromBase64(userName,ConstantsCMP.AES_KEY));
        //生成邀请码
        return invitCodeService.createInvitCode(userName);
    }


}
