package cipher.console.oidc.service.impl;

import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.mapper.TotpAuthorizationMapper;
import cipher.console.oidc.model.TotpAuthorizationModel;
import cipher.console.oidc.service.TotpAuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TotpAuthorizationServiceImpl implements TotpAuthorizationService {

    @Autowired
    TotpAuthorizationMapper authorizationMapper;

    @Override
    public Map<String,Object> queryAllTotpTable(TotpAuthorizationModel model, DataGridModel dataGridModel) {
        model = (model == null ? new TotpAuthorizationModel() : model);
        model.setPageData(dataGridModel);
        Map<String,Object> map = new HashMap<>();
        map.put("rows",authorizationMapper.queryAllTotpTable(model));
        map.put("total",authorizationMapper.queryPageCount(model));
        return map;
    }

    @Override
    public List<Integer> queryScratchCodeByAccountNumber(String accountNumber) {
        return authorizationMapper.queryScratchCodeByAccountNumber(accountNumber);
    }
}
