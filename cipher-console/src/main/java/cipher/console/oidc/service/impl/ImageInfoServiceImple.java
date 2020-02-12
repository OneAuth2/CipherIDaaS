package cipher.console.oidc.service.impl;

import cipher.console.oidc.domain.web.ImageInfoDomain;
import cipher.console.oidc.mapper.ImageInfoMapper;
import cipher.console.oidc.service.ImageInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 95744 on 2018/7/30.
 */

@Service
public class ImageInfoServiceImple implements ImageInfoService {

    @Autowired
    private ImageInfoMapper imageInfoMapper;


    @Override
    public List<ImageInfoDomain> getImageList() {
        return imageInfoMapper.getImageList();
    }
}
