package cipher.console.oidc.mapper;

import cipher.console.oidc.domain.web.ImageInfoDomain;

import java.util.List;

/**
 * Created by 95744 on 2018/7/30.
 */
public interface ImageInfoMapper {

    public List<ImageInfoDomain> getImageList();

}
