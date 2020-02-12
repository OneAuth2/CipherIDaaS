package cipher.console.oidc.service.impl;

import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.gold_mantis.GoldMantisUserLog;
import cipher.console.oidc.mapper.GoldMantisLogMapper;
import cipher.console.oidc.service.GoldMantisLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: zt
 * @Date: 2018/9/18 10:14
 */
@Service
public class GoldMantisLogServiceImpl implements GoldMantisLogService {

    @Autowired
    private GoldMantisLogMapper goldMantisLogMapper;


    @Override
    public Map<String, Object> queryPageList(GoldMantisUserLog form, DataGridModel pageModel) throws Exception {
        Map<String, Object> map = new HashMap<>();
        form = (form == null ? new GoldMantisUserLog() : form);
        form.setPageData(pageModel);
        List<GoldMantisUserLog> list=goldMantisLogMapper.queryPageList(form);
        int total=goldMantisLogMapper.queryPageListTotal(form);
        map.put("rows", list);
        map.put("total", total);
        return map;
    }
}
