package cipher.console.oidc.service.impl;

import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.exceldomain.SubAccountExcel;
import cipher.console.oidc.domain.web.SubAccountDomain;
import cipher.console.oidc.domain.web.SubAccountInfoDomain;
import cipher.console.oidc.domain.web.SubAccountMapDomain;
import cipher.console.oidc.mapper.SubAccountMapper;
import cipher.console.oidc.model.ApplicationSubAccountSubTableModel;
import cipher.console.oidc.model.MainSubAppAccountModel;
import cipher.console.oidc.model.SubAccountApplicationModel;
import cipher.console.oidc.model.SubAccountAuthModel;
import cipher.console.oidc.service.SubAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SubAccountServiceImpl implements SubAccountService {

    @Autowired
    SubAccountMapper subAccountMapper;


    @Override
    public SubAccountDomain getTheSubAccount(String sub_account, String app_client_id) {
        return subAccountMapper.getTheSubAccount(sub_account,app_client_id);
    }

    @Override
    public List<ApplicationSubAccountSubTableModel> getSubAccountApplication(String account_number,String sidx,String sord) {
        return subAccountMapper.getSubAccountApplication(account_number,sidx,sord);
    }

    @Override
    public List<ApplicationSubAccountSubTableModel> getUnAuthorizedUserApplication(String account_number,String sidx,String sord) {
        return subAccountMapper.getUnAuthorizedUserApplication(account_number,sidx,sord);
    }

    @Override
    public void disconnectWithSubAccount(String account_number, String sub_account) {
        subAccountMapper.disconnectWithSubAccount(account_number,sub_account);
    }

    @Override
    public Map<String, Object> subAccountPageList(DataGridModel pageModel, MainSubAppAccountModel form) {
        form=form==null?new MainSubAppAccountModel():form;
        form.setPageData(pageModel);
        Map<String,Object> map=new HashMap<>();
        List<MainSubAppAccountModel> list=subAccountMapper.subAccountList(form);
        int total=subAccountMapper.subAccountListTotal(form);
        map.put("rows",list);
        map.put("total",total);
        return map;
    }

    @Override
    public Map<String, Object> subAccountAuthPageList(DataGridModel pageModel, SubAccountAuthModel form) {
        form=form==null?new SubAccountAuthModel():form;
        form.setPageData(pageModel);
        Map<String,Object> map=new HashMap<>();
        List<SubAccountAuthModel> list=subAccountMapper.querySubAuthPageList(form);
        int total=subAccountMapper.querySubAuthPageListTotal(form);
        map.put("rows",list);
        map.put("total",total);
        return map;
    }

    @Override
    public void insertSubAccountMap(SubAccountMapDomain form) throws Exception {
        subAccountMapper.insertSubAccountMap(form);
    }

    @Override
    public void deleteSubAccountMap(SubAccountAuthModel form) throws Exception {
        subAccountMapper.deleteSubAccountMap(form);
    }

    @Override
    public SubAccountDomain querySubAccount(SubAccountExcel domain) {
        return subAccountMapper.querySubAccount(domain);
    }

    @Override
    public void insertSubAccount(SubAccountExcel domain) {
        subAccountMapper.insertSubAccount(domain);
    }

    @Override
    public Map<String, Object> querySubAccountByApplicationId(SubAccountApplicationModel subAccountApplicationModel, DataGridModel dataGridModel) {
        subAccountApplicationModel = (subAccountApplicationModel==null?new SubAccountApplicationModel():subAccountApplicationModel);
        subAccountApplicationModel.setPageData(dataGridModel);
        Map<String,Object> map = new HashMap<>();
        map.put("rows",subAccountMapper.querySubAccountByApplicationId(subAccountApplicationModel));
        map.put("total",subAccountMapper.countAppSubAccount());
        return map;
    }

    @Override
    public List<SubAccountDomain> querySubAccountInfoByAccountNumber(String accountNumber) {
        return subAccountMapper.querySubAccountInfoByAccountNumber(accountNumber);
    }

    @Override
    public int insertSubAccountInfo(SubAccountInfoDomain form) throws Exception {
       return subAccountMapper.insertSubAccountInfo(form);
    }

    @Override
    public String querySubPwdByClientAndName(String appClientId, String subAccount) {
        return subAccountMapper.querySubPwdByClientAndName(appClientId,subAccount);
    }

    @Override
    public void updateSubAccountInfo(SubAccountDomain form) {
        subAccountMapper.updateSubAccountInfo(form);
    }



}
