package com.portal.executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池的配置类
 * create by shizhao at 2019/7/23
 *
 * @author shizhao
 * @since 2019/7/23 2:13 PM
 */
@Configuration
@EnableAsync
public class ExecutorConfig {

    private static final Logger logger = LoggerFactory.getLogger(ExecutorConfig.class);

    /**
     * 核心线程数
     */
    @Value("${async.executor.thread.coreSize}")
    private int corePoolSize;

    /**
     * 最大线程数
     */
    @Value("${async.executor.thread.maxSize}")
    private int maxPoolSize;

    /**
     * 队列的容量
     */
    @Value("${async.executor.thread.queueCapacity}")
    private int queueCapacity;

    /**
     * 线程名称前缀
     * */
    @Value("${async.executor.thread.namePrefix}")
    private String namePrefix;


    @Bean(name = "asyncServiceExecutor")
    public Executor asyncServiceExecutor(){
        logger.info("start asyncServiceExecutor");
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(Integer.MAX_VALUE);
        executor.setThreadNamePrefix(namePrefix);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

}
