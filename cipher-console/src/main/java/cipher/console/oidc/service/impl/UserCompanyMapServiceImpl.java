package cipher.console.oidc.service.impl;


import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.web.UserCompanyMapDomain;
import cipher.console.oidc.mapper.UserCompanyMapMapper;
import cipher.console.oidc.service.UserCompanyMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserCompanyMapServiceImpl implements UserCompanyMapService {

    @Autowired
    private UserCompanyMapMapper userCompanyMapMapper;

    @Override
    public Map<String, Object> getCompanyId(String accountNumber) {
        Map<String,Object> map=new HashMap<>();
        try{
            String companyId=userCompanyMapMapper.queryCompanyId(accountNumber);
            map.put("code",0);
            map.put("companyId",companyId);
        }catch (Exception e){
            map.put("code",1);
            map.put("msg","内部服务器错误");
            e.printStackTrace();
            return map;

        }

        return map;
    }

    /**
     * 获取已邀请用户信息列表
     * @param userName
     * @param pageModel
     * @return
     */
    @Override
    public Map<String, Object> getUserInfo(String queryName,String userName, DataGridModel pageModel) {
        HashMap<String, Object> map = new HashMap<>();
        String companyId=userCompanyMapMapper.queryCompanyId(userName);
        System.out.println("公司"+companyId);
        UserCompanyMapDomain userCompany = new UserCompanyMapDomain();
        userCompany.setCompanyId(companyId);
        userCompany.setPageData(pageModel);
        userCompany.setQueryName(queryName);
        List<UserCompanyMapDomain> list=
                userCompanyMapMapper.getInvitAndUserInfo(userCompany);
        Integer total=userCompanyMapMapper.getUsedInvitCount(companyId);

        map.put("rows",list);
        map.put("total",total);
        return map;
    }

}
