package cipher.console.oidc.mapper;

import cipher.console.oidc.domain.web.ApplicationSysInfo;

public interface ApplicationSysInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ApplicationSysInfo record);

    int insertSelective(ApplicationSysInfo record);

    ApplicationSysInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ApplicationSysInfo record);

    int updateByPrimaryKey(ApplicationSysInfo record);
}