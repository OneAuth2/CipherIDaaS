package cipher.console.oidc.service;

import cipher.console.oidc.domain.zhonghe.ZhonghePost;


import java.util.List;

public interface ZhonghePostService {

    /**
     * 插入众合岗位信息
     * @param postList
     */
    public void insertPostList(List<ZhonghePost> postList);

    /**
     * 查询众合所有的岗位信息
     * @return
     */
    public List<ZhonghePost> queryAllZhPost();

    /**
     * 同步部门和用户之间的关联关系
     * @throws Exception
     */
    public void addmap2Cipher() throws Exception;


}
