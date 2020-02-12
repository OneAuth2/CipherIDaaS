package cipher.console.oidc.mapper;


import cipher.console.oidc.domain.quartz.QuartzLog;

public interface QuartzLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(QuartzLog record);

    int insertSelective(QuartzLog record);

    QuartzLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(QuartzLog record);

    int updateByPrimaryKey(QuartzLog record);


}