package cipher.console.oidc.service.impl;

import cipher.console.oidc.domain.web.CustomPropertiesInfo;
import cipher.console.oidc.mapper.CustomPropertiesInfoMapper;
import cipher.console.oidc.service.CustomPropertiesInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class CustomPropertiesInfoServiceImpl implements CustomPropertiesInfoService {

    @Autowired
    private CustomPropertiesInfoMapper customPropertiesInfoMapper;

    @Override
    public void saveProperties(CustomPropertiesInfo record) {
        record.setCreateTime(new Date());
        record.setModifyTime(new Date());
        int insert = customPropertiesInfoMapper.insert(record);
    }

    @Override
    public List<CustomPropertiesInfo> getInitData(CustomPropertiesInfo record) {
    return customPropertiesInfoMapper.getInitData(record);
    }

    @Override
    public void closeCustomProperties(CustomPropertiesInfo record) {

        customPropertiesInfoMapper.closeCustomProperties(record);
    }
}
