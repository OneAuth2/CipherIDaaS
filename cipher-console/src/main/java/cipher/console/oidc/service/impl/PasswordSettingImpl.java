package cipher.console.oidc.service.impl;

import cipher.console.oidc.domain.web.PasswordSettingDomain;
import cipher.console.oidc.mapper.PasswordSettingMapper;
import cipher.console.oidc.service.PasswordSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PasswordSettingImpl implements PasswordSettingService {

    @Autowired
    private PasswordSettingMapper passwordSettingMapper;

    @Override
    public Map<String, Object> savePassword(PasswordSettingDomain passwordSettingDomain) {
        Map<String,Object> map =new HashMap<>();
        try{
            int update= passwordSettingMapper.savePassword(passwordSettingDomain);
            map.put("code",0);
            map.put("msg","更新成功");
        }catch (Exception e){
           map.put("code",1);
           map.put("msg","服务器错误");
           e.printStackTrace();
           return  map;
        }


        return  map;
    }

    @Override
    public PasswordSettingDomain queryPasswordSetting(String companyId) {
        return passwordSettingMapper.queryPasswordSetting(companyId);
    }



    @Override
    public int updatePasswordSetting(int length, String init) {
        return passwordSettingMapper.updatePasswordSetting(length,init);
    }

}
