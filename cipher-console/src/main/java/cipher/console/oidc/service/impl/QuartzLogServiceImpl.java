package cipher.console.oidc.service.impl;



import cipher.console.oidc.domain.quartz.QuartzLog;
import cipher.console.oidc.mapper.QuartzLogMapper;
import cipher.console.oidc.service.QuartzLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * 定时任务记录ServiceImpl

 * 
 * 未经授权不得进行修改、复制、出售及商业使用
 */

@Service("quartzLogService")
public class QuartzLogServiceImpl implements QuartzLogService {
	
    
	private static final Logger logger = LoggerFactory.getLogger(QuartzLogServiceImpl.class);
   
    @Resource
    private QuartzLogMapper quartzLogMapper;


	@Override
	public int save(QuartzLog ql) throws Exception {
		return quartzLogMapper.insert(ql);
	}





}