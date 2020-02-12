package com.portal.service.impl;




import com.portal.daoAuthoriza.SubAccountDAO;
import com.portal.domain.SubAccountDomain;
import com.portal.domain.SubAccountMapDomain;
import com.portal.model.SubAccountAuthModel;
import com.portal.service.SubAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SubAccountServiceImpl implements SubAccountService {


   @Autowired
   private SubAccountDAO subAccountDAO;


    @Override
    public SubAccountDomain getTheSubAccount(String sub_account, String app_client_id) {
        return subAccountDAO.getTheSubAccount(sub_account,app_client_id);
    }

    @Override
    public int insertSubAccountInfo(SubAccountDomain form) throws Exception {
        return subAccountDAO.insertSubAccountInfo(form);
    }

    @Override
    public SubAccountDomain querySubAccountInfo(String userId, String appClientId) {
        return subAccountDAO.querySubAccountInfo(userId,appClientId);
    }

    @Override
    public void deleteSubAccountMap(SubAccountAuthModel form) throws Exception {
        subAccountDAO.deleteSubAccountMap(form);
    }

    @Override
    public SubAccountMapDomain querySubMap(SubAccountMapDomain subAccountMapDomain) {
        return subAccountDAO.querySubMap(subAccountMapDomain);
    }

    @Override
    public void insertSubAccountMap(SubAccountMapDomain domain) throws Exception {
        subAccountDAO.insertSubAccountMap(domain);
    }

    @Override
    public Integer queryPrimaryId(String subAccount, String appClientId) {
        return subAccountDAO.queryPrimaryId(subAccount,appClientId);
    }

    @Override
    public int updateSubAccountInfo(SubAccountDomain form) throws Exception {
        return subAccountDAO.updateSubAccountInfo(form);
    }




}
