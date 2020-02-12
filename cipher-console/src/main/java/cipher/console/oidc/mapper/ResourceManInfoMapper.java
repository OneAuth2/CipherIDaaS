package cipher.console.oidc.mapper;

import cipher.console.oidc.domain.web.ResourceManInfo;

import java.util.List;

public interface ResourceManInfoMapper {
    public List<ResourceManInfo> queryallinfo();

    public int insertinfo(ResourceManInfo resourceManInfo);

    public int updateinfo(ResourceManInfo resourceManInfo);
}
