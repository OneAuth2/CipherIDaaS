package cipher.console.oidc.service;


import cipher.console.oidc.domain.quartz.QuartzLog;

/**
 * 定时任务记录Service
 *
 */


public interface QuartzLogService {

	/**
	 * 保存日志
	 */
	int save(QuartzLog ql) throws Exception;


}
