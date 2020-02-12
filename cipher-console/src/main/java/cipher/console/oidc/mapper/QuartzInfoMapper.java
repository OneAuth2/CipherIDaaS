package cipher.console.oidc.mapper;


import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.quartz.QuartzInfo;

import java.util.List;
import java.util.Map;

public interface QuartzInfoMapper {


    int deleteByPrimaryKey(Long id);

    int insert(QuartzInfo record);

    int insertSelective(QuartzInfo record);

    QuartzInfo selectByPrimaryKey(Long id);

     int updateByPrimaryKeySelective(Map<String, Object> paramMap);

    int updateByPrimaryKey(QuartzInfo record);

    public List<QuartzInfo> findList(Map<String, Object> result);

    public QuartzInfo findByCode(String code);

    public QuartzInfo findSelective(Map<String, Object> paramMap);








}