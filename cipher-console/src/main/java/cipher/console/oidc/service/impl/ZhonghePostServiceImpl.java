package cipher.console.oidc.service.impl;

import cipher.console.oidc.domain.zhonghe.ZhonghePost;
import cipher.console.oidc.mapper.ZhonghePostMapper;
import cipher.console.oidc.service.ZhonghePostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZhonghePostServiceImpl implements ZhonghePostService {

    @Autowired
    private ZhonghePostMapper zhonghePostMapper;

    @Override
    public void insertPostList(List<ZhonghePost> postList) {
        zhonghePostMapper.insertPostList(postList);
    }

    @Override
    public List<ZhonghePost> queryAllZhPost() {
        return zhonghePostMapper.queryAllZhPost();
    }

    @Override
    public void addmap2Cipher() throws Exception {
        zhonghePostMapper.addmap2Cipher();
    }
}
