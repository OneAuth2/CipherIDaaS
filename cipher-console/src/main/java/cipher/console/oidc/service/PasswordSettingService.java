package cipher.console.oidc.service;

import cipher.console.oidc.domain.web.PasswordSettingDomain;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 密码设定格式管理的service层
 * @author sizhao
 * */
public interface PasswordSettingService {

    /**
     * 密码策略保存密码
     * create by 田扛
     * create time 2019年3月11日16:21:25
     */
     Map<String,Object> savePassword(PasswordSettingDomain passwordSettingDomain);
    /**
     * 查询密码设定
     * */
    PasswordSettingDomain queryPasswordSetting(String companyId);

    /**
     * 修改密码设定的长度和初始密码
     * @param length 密码最大长度
     * @param init 初始密码
     * */
    int updatePasswordSetting(@Param(value = "length")int length,
                              @Param(value = "init")String init);






}
