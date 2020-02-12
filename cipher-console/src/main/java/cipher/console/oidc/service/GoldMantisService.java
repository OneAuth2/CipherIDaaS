package cipher.console.oidc.service;

import cipher.console.oidc.domain.gold_mantis.GoldMantisUser;

/**
 * @Author: zt
 * @Date: 2018/9/17 17:35
 */
public interface GoldMantisService {

    /**
     * 插入一个用户
     * @param goldMantisUser
     * @throws Exception
     */
    public void insertGoldMantisUser(GoldMantisUser goldMantisUser) throws Exception;

    /**
     * 更新一个用户,以手机号为维度
     * @param goldMantisUser
     * @throws Exception
     */
    public void updateGoldMantisUser(GoldMantisUser goldMantisUser) throws Exception;

    /**
     * 根据手机号查询用户
     * @param goldMantisUser
     * @throws Exception
     */
    public GoldMantisUser queryGoldMantisUserByMobile(GoldMantisUser goldMantisUser) throws Exception;

    /**
     * 插入导入用户的日志
     * @param goldMantisUser
     * @throws Exception
     */
    public void insertGoldMantisLog(GoldMantisUser goldMantisUser) throws Exception;

}
