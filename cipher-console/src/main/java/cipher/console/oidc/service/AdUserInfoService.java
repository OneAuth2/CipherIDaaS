package cipher.console.oidc.service;

import cipher.console.oidc.domain.web.AdUserInfoDomain;
import cipher.console.oidc.mapper.AdUserInfoMapper;

import java.util.List;

/**
 * @Author: zt
 * @Date: 2018/6/8 17:54
 */
public interface AdUserInfoService {

    /**
     * 插入Ad域中的用户列表
     *
     * @param list
     */
    public void insertAdUserInfoList(List<AdUserInfoDomain> list);

    /**
     * 查询出已经存在的用户mail地址
     * @param list
     * @return
     */
    public List<AdUserInfoDomain> queryRepeatName(List<AdUserInfoDomain> list);

}
