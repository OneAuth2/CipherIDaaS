package cipher.console.oidc.mapper;

import cipher.console.oidc.domain.web.AdInfoDomain;
import cipher.console.oidc.domain.web.AdUserInfoDomain;

import java.util.List;

/**
 * @Author: zt
 * AD域目前只维护一条记录，且id为1
 * @Date: 2018/6/7 17:31
 */
public interface AdUserInfoMapper {

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
