package cipher.console.oidc.service;

import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.web.AdActivityDomain;
import cipher.console.oidc.domain.web.AdUserBufferDomain;

import java.util.Map;

/**
 * @Author: tk
 * @Date: 2018/10/25 20:19
 */
public interface AdActivityService {


    /**
     * 查询所有的缓冲表数据
     * @param dataGridModel
     * @return
     */
    public Map<String,Object> getAllUserBufferList(AdActivityDomain adActivityDomain, DataGridModel dataGridModel);

    /**
     * 按主账号查询数据
     */
    public Map<String,Object> getUserInfo(String accountNumber);
}
