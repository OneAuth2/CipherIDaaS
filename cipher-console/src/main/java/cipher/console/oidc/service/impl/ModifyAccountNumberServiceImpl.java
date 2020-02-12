package cipher.console.oidc.service.impl;

import cipher.console.oidc.mapper.ModifyAccountNumberMapper;
import cipher.console.oidc.model.ModifyAccountNumberModel;
import cipher.console.oidc.service.ModifyAccountNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: zt
 * @Date: 2018/11/13 16:27
 */
@Service
public class ModifyAccountNumberServiceImpl implements ModifyAccountNumberService {

    @Autowired
    private ModifyAccountNumberMapper modifyAccountNumberMapper;

    @Override
    @Transactional
    public void updateCipherGroupUserMapAccountNumber(List<ModifyAccountNumberModel> modifyAccountNumberModelList) {
        modifyAccountNumberMapper.updateCipherGroupUserMapAccountNumber(modifyAccountNumberModelList);
    }

    @Override
    @Transactional
    public void updateCipherRoleUserMapAccountNumber(List<ModifyAccountNumberModel> modifyAccountNumberModelList) {
        modifyAccountNumberMapper.updateCipherRoleUserMapAccountNumber(modifyAccountNumberModelList);
    }

    @Override
    @Transactional
    public void updateCipherUserAuthorizationMapAccountNumber(List<ModifyAccountNumberModel> modifyAccountNumberModelList) {
        modifyAccountNumberMapper.updateCipherUserAuthorizationMapAccountNumber(modifyAccountNumberModelList);
    }


}
