package cipher.console.oidc.service.impl;
import cipher.console.oidc.domain.web.CompanyInfoDomain;
import cipher.console.oidc.domain.web.InvitCodeInfo;
import cipher.console.oidc.mapper.CompanyInfoMapper;
import cipher.console.oidc.mapper.InvitCodeMapper;
import cipher.console.oidc.mapper.UserCompanyMapMapper;
import cipher.console.oidc.service.InvitCodeService;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InvitCodeServiceImpl implements InvitCodeService {

    @Autowired
    private InvitCodeMapper invitCodeMapper;

    @Autowired
    private UserCompanyMapMapper userCompanyMapMapper;

    @Autowired
    private CompanyInfoMapper companyInfoMapper;

    /**
     * 查询未使用邀请码
     * @param userName
     * @return
     */
    @Override
    public List<InvitCodeInfo> queryInvitCodeList(String userName) {

        String companyId=userCompanyMapMapper.queryCompanyId(userName);

        List<InvitCodeInfo> invitCodeInfos = invitCodeMapper.queryInvitCodeList(companyId);

        return invitCodeInfos;
    }

    /**
     * 生成新的邀请码并回显页面
     * @param userName
     * @return
     */
    @Override
    public Map<String, Object> createInvitCode(String userName) {
        String companyId=userCompanyMapMapper.queryCompanyId(userName);
        Map<String, Object> map = new HashMap<>();
        //获取数据库公司信息
        CompanyInfoDomain companyInfo = companyInfoMapper.getCompanyInfoById(companyId);
        if(null!=companyInfo){
        Integer count = companyInfo.getSingleCount();
        Integer maxsize = companyInfo.getMaxsize();
        //生成邀请码
        for (int i = 0; i < count; i++) {
            CompanyInfoDomain companyInfoB = companyInfoMapper.getCompanyInfoById(String.valueOf(companyId));
            if (companyInfoB.getLeftCount() == 0) {
                break;
            }
            String invitCode = RandomStringUtils.random(8, "1234567890");
            try {
                InvitCodeInfo invitCodeInfo = new InvitCodeInfo();
                invitCodeInfo.setCompanyId(companyId);
                invitCodeInfo.setCreateTime(new Date());
                invitCodeInfo.setInvitCode(invitCode);
                invitCodeMapper.addInvitCodeInfo(invitCodeInfo);
            } catch (Exception e) {
                e.printStackTrace();
            }
            int unusecode = invitCodeMapper.findInvitCodeCount(companyId);/*未使用邀请码*/


            int usedcode = userCompanyMapMapper.getUsedInvitCount(companyId);/*已使用邀请码*/

            CompanyInfoDomain c2 = new CompanyInfoDomain();
            c2.setLeftCount(maxsize - unusecode - usedcode);
            c2.setCompanyAppId(companyId);
            try {
                companyInfoMapper.updateLeftCount(c2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        }
        List<InvitCodeInfo> list = invitCodeMapper.queryInvitCodeList(companyId);
        map.put("codelist", list);
        return map;
    }
}
