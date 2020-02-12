package cipher.console.oidc.common;

import cipher.console.oidc.domain.web.*;
import cipher.console.oidc.mapper.AutoSyncMapper;
import cipher.console.oidc.service.AutoSyncService;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 服务启动后自动启动定时同步任务
 * 启动后执行所有已经定时同步开启的任务
 * @author cozi
 * @date 2019-08-08
 */
@Component
public class TimingSynchronization implements ApplicationRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(TimingSynchronization.class);

    @Autowired
    private AutoSyncService autoSyncService;

    @Autowired
    private AutoSyncMapper autoSyncMapper;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Gson gson = new Gson();
        /**
         * 任务一：
         * 服务启动后，启动所有钉钉开启自动同步的任务
         */
        LOGGER.info("^-^After the service starts, start all the ding ding tasks that enable automatic synchronization^-^");
        List<AutoSyncDingDomain> dingList = autoSyncMapper.getAllDingId();
        //获取钉钉所有的配置信息列表
        if(dingList!=null&&dingList.size()>0){
            for(AutoSyncDingDomain autoSyncDingDomain:dingList){
                if(autoSyncDingDomain.getSyncConfig()!=null&& StringUtils.isNotEmpty(autoSyncDingDomain.getSyncConfig())){
                    AutoSyncInfo autoSyncInfo = gson.fromJson(autoSyncDingDomain.getSyncConfig(), AutoSyncInfo.class);
                    //当自动同步配置信息同时满足三个条件时，1.自动同步开启，2.自动同步开始时间，3.自动同步时间间隔
                    if(autoSyncInfo.getIsAutoSync()==0&&StringUtils.isNotEmpty(autoSyncInfo.getAutoSyncDate())&&autoSyncInfo.getInterval()>-1){
                        autoSyncService.autoSyncDingTask(autoSyncInfo,autoSyncDingDomain.getId(),autoSyncDingDomain.getCompanyUUid());
                    }
                }
            }
        }

        /**
         * 任务二：
         * 服务启动后，启动所有ad开启自动同步的任务
         */
        LOGGER.info("^-^After the service starts, start all the AD tasks that enable automatic synchronization^-^");
        List<AutoSyncAdDomain> adList = autoSyncMapper.getAllAdId();
        //获取ad所有的配置信息列表
        if(adList!=null&&adList.size()>0){
            for(AutoSyncAdDomain autoSyncAdDomain:adList){
                if(autoSyncAdDomain.getAutoConfig()!=null&& StringUtils.isNotEmpty(autoSyncAdDomain.getAutoConfig())){
                    AutoSyncAdInfo autoSyncAdInfo = gson.fromJson(autoSyncAdDomain.getAutoConfig(), AutoSyncAdInfo.class);
                    //当自动同步配置信息同时满足三个条件时，1.自动同步开启，2.自动同步开始时间，3.自动同步时间间隔
                    if(autoSyncAdInfo.getIsAutoSync()==0&&StringUtils.isNotEmpty(autoSyncAdInfo.getAutoSyncDate())&&autoSyncAdInfo.getInterval()>-1){
                        autoSyncService.autoSyncAdTask(autoSyncAdDomain.getId(),autoSyncAdDomain.getCompanyId(),autoSyncAdInfo);
                    }
                }
            }
        }

        /**
         * 任务三：
         * 服务启动后，启动所有应用子账号开启自动同步的任务
         */
        /*LOGGER.info("^-^After the service starts, start all application sub-accounts to start the task of automatic synchronization^-^");
        List<AutoSyncAppDomain> appList = autoSyncMapper.getAllAppId();
        if(appList!=null&&appList.size()>0){
            for(AutoSyncAppDomain autoSyncAppDomain:appList){
                if(autoSyncAppDomain.getSyncConfig()!=null&&StringUtils.isNotEmpty(autoSyncAppDomain.getSyncConfig())){
                    AutoSyncInfo autoSyncInfo = gson.fromJson(autoSyncAppDomain.getSyncConfig(), AutoSyncInfo.class);
                    //当自动同步配置信息同时满足三个条件时，1.自动同步开启，2.自动同步开始时间，3.自动同步时间间隔
                    if(autoSyncInfo.getIsAutoSync()==0&&StringUtils.isNotEmpty(autoSyncInfo.getAutoSyncDate())&&autoSyncInfo.getInterval()>-1){
                        autoSyncService.autoSyncApplicationTask(autoSyncAppDomain.getId(),autoSyncAppDomain.getCompanyId(),autoSyncInfo);
                    }
                }
            }
        }*/
    }
}
