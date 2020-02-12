package cipher.console.oidc.service.impl;

;
import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.quartz.QuartzInfo;
import cipher.console.oidc.mapper.QuartzInfoMapper;
import cipher.console.oidc.service.QuartzInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 定时任务详情ServiceImpl
 *
 */
@Service("quartzInfoService")
public class QuartzInfoServiceImpl  implements QuartzInfoService {
	
	private static final Logger logger = LoggerFactory.getLogger(QuartzInfoServiceImpl.class);
   
    @Resource
    private QuartzInfoMapper quartzInfoMapper;



	@Override
	public boolean save(QuartzInfo qi) {
		int result = quartzInfoMapper.insert(qi);
		if (result > 0) {
			return true;
		}
		return false;
	}


    @Override
	public boolean update(QuartzInfo qi) {
		int result = quartzInfoMapper.updateByPrimaryKey(qi);
		if (result > 0) {
			return true;
		}
		return false;
	}


	@Override
	public List<QuartzInfo> findList(Map<String, Object> result) {

		return quartzInfoMapper.findList(result);
	}





    @Override
	public QuartzInfo findByCode(String code) throws Exception{
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("code", code);
			QuartzInfo quartzInfo = quartzInfoMapper.findByCode(code);
			if (null != quartzInfo) {
				return quartzInfo;
			}
		} catch (Exception e) {
			logger.error("查询定时任务异常", e);
		}
		return null;
	}
	
	@Override
	public QuartzInfo findSelective(Map<String, Object> paramMap) {
		try {
			QuartzInfo quartzInfo = quartzInfoMapper.findSelective(paramMap);
			if (null != quartzInfo) {
				return quartzInfo;
			}
		} catch (Exception e) {
			logger.error("查询定时任务异常", e);
		}
		return null;
	}

	@Override
	public int updateByMap(Map<String, Object> paramMap) {
		return quartzInfoMapper.updateByPrimaryKeySelective(paramMap);
	}

	@Override
	public QuartzInfo getById(Long id) {
		return quartzInfoMapper.selectByPrimaryKey(id);
	}

}