package cipher.console.oidc.service;

import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.gold_mantis.GoldMantisUserLog;

import java.util.Map;

/**
 * @Author: zt
 * @Date: 2018/9/18 10:14
 */
public interface GoldMantisLogService {

    /**
     * 查询数据列表
     * @param form
     * @return
     * @throws Exception
     */
    public Map<String,Object> queryPageList(GoldMantisUserLog form, DataGridModel pageMdoel) throws Exception;

}
