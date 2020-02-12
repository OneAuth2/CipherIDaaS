package cipher.console.oidc.service;



import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.quartz.QuartzInfo;

import java.util.List;
import java.util.Map;

/**
 * 定时任务详情Service
 *
 * 
 * 未经授权不得进行修改、复制、出售及商业使用
 */


public interface QuartzInfoService {


	/**
	 * 保存定时任务数据
	 * @param qi
	 */
	boolean save(QuartzInfo qi);

	/**
	 * 修改定时任务
	 * @param
	 * @return
	 */
	boolean update(QuartzInfo qi);

	/**
	 * 查询所有任务
	 * @param result
	 * @return
	 */
	List<QuartzInfo> findList(Map<String, Object> result);


	
	/**
	 * 据任务标识查询任务
	 *
	 * @return
	 */
	QuartzInfo findByCode(String code) throws Exception;
	
	/**
	 * 据条件查询定时任务详情
	 * @param paramMap
	 * @return
	 */
	QuartzInfo findSelective(Map<String, Object> paramMap);


	int updateByMap(Map<String, Object> paramMap);



	QuartzInfo getById(Long id);


	
}
