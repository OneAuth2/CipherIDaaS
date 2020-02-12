package cipher.console.oidc.service.impl;

import cipher.console.oidc.domain.web.CompanyInfoDomain;
import cipher.console.oidc.mapper.CompanyInfoMapper;
import cipher.console.oidc.mapper.UserCompanyMapMapper;
import cipher.console.oidc.service.CompanyInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyInfoServiceImpl implements CompanyInfoService {

    @Autowired
    private CompanyInfoMapper companyInfoMapper;

    @Autowired
    private UserCompanyMapMapper userCompanyMapMapper;

    /**
     * 获取公司信息
     * @param userName
     * @return
     */
    @Override
    public CompanyInfoDomain getCompanyInfoById(String userName) {

        String companyId=userCompanyMapMapper.queryCompanyId(userName);

        return companyInfoMapper.getCompanyInfoById(companyId);
    }
}
