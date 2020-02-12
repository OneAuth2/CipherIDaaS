package cipher.console.oidc.service;

import cipher.console.oidc.domain.web.AutoSyncAdInfo;
import cipher.console.oidc.domain.web.AutoSyncInfo;


/**
 * 定时同步任务操作
 * @author cozi
 * @date 2019-08-08
 */
public interface AutoSyncService {

    /**
     * 创建钉钉定时同步任务
     * @param autoSyncInfo 定时同步配置
     * @param jobkey 钉钉同步jobkey
     * @return true-创建成功，false-创建失败
     */
    boolean autoSyncDingTask(AutoSyncInfo autoSyncInfo, String jobkey,String companyId);

    /**
     * 删除钉钉定时同步任务
     * @param jobkey 钉钉同步jobkey
     * @return true-删除成功，false-删除失败
     */
    boolean closeAutoSyncDingTask(String jobkey);

    /**
     * 创建ad定时同步任务
     * @param id ad目录id
     * @param autoSyncAdInfo 定时同步配置
     * @return true-创建成功，false-创建失败
     */
    boolean autoSyncAdTask(Integer id,String companyId,AutoSyncAdInfo autoSyncAdInfo);

    /**
     * 删除ad定时同步任务
     * @param id ad目录id
     * @return true-删除成功，false-删除失败
     */
    boolean closeAutoSyncAdTask(Integer id);

    /**
     * 创建应用子账号定时同步任务
     * @param id 应用id
     * @param autoSyncInfo 定时同步配置
     * @return true-创建成功，false-创建失败
     */
    boolean autoSyncApplicationTask(Integer id,String companyId,AutoSyncInfo autoSyncInfo);

    /**
     * 删除应用子账号定时同步任务
     * @param id 应用id
     * @return true-删除成功，false-删除失败
     */
    boolean closeAutoSyncApplicationTask(Integer id);

    /**
     * 删除微信定时同步任务
     *
     * @param id
     * @return
     */
    boolean closeAutoSyncWxTask(String id);

    /**
     * 创建企业微信定时同步任务
     *
     * @param autoSyncInfo 定时同步配置
     * @param jobkey       企业微信同步jobkey
     * @return true-创建成功，false-创建失败
     */
    boolean autoSyncWxTask(AutoSyncInfo autoSyncInfo, String jobkey, String companyId);
}
