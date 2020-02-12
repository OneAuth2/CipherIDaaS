package cipher.console.oidc.service.impl;

import cipher.console.oidc.common.ConstantsCMP;
import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.exceldomain.AdminBehaviorExcle;
import cipher.console.oidc.domain.exceldomain.NewAdminBehaviorExcle;
import cipher.console.oidc.domain.web.AdminBehaviorInfo;
import cipher.console.oidc.domain.web.UserInfoDomain;
import cipher.console.oidc.enums.AdminBehaviorEnum;
import cipher.console.oidc.mapper.AdminBehaviorInfoMapper;
import cipher.console.oidc.service.AdminBehaviorInfoService;
import cipher.console.oidc.util.IpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 95744 on 2018/6/4.
 */
@Service
public class AdminBehaviorInfoServiceImpl implements AdminBehaviorInfoService {

    private static final Logger LOGGER= LoggerFactory.getLogger(AdminBehaviorInfoServiceImpl.class);

    @Autowired
    private AdminBehaviorInfoMapper adminBehaviorInfoMapper;
    @Override
    public Map<String, Object> getAdminBehaviorPageList(AdminBehaviorInfo form, DataGridModel pageModel) {
        Map<String, Object> map = new HashMap<>();
        form = (form == null ? new AdminBehaviorInfo() : form);
        form.setPageData(pageModel);
        List<AdminBehaviorInfo> list = adminBehaviorInfoMapper.selectAdminBehaviorList(form);

        for(AdminBehaviorInfo adminBehaviorInfo:list){
            adminBehaviorInfo.setTypeStr(AdminBehaviorEnum.getAdminBehaviorEnum(Integer.valueOf(adminBehaviorInfo.getType())));
        }

        int total = adminBehaviorInfoMapper.selectAdminBehaviorCount(form);
        map.put("rows", list);
        map.put("total", total);
        return map;
    }

    @Override
    public List<NewAdminBehaviorExcle> exportExcle(AdminBehaviorInfo form) {
        return adminBehaviorInfoMapper.exportExcle(form);
    }

    @Override
    public void insertSelective(AdminBehaviorInfo record, HttpSession session) {
        try {
            String companyId = (String) session.getAttribute(ConstantsCMP.CIPHER_CONSOLE_COMPANY_SESSION_INFO);
            UserInfoDomain userInfoDomain = (UserInfoDomain) session.getAttribute(ConstantsCMP.CIPHER_CONSOLE_USER_SESSION_INFO);
            record.setCompanyId(companyId);
            record.setCreateTime(new Date());
            record.setIp(IpUtil.getIp());
            record.setUserId(userInfoDomain.getUserName() + "(" + userInfoDomain.getAccountNumber() + ")");
            adminBehaviorInfoMapper.insertSelective(record);
        }catch (Exception e){
           LOGGER.error("插入日志失败:{},{}",e.getMessage(),e.getCause());
        }

    }


}
