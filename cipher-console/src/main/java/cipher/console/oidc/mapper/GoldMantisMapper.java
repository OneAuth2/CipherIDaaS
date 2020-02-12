package cipher.console.oidc.mapper;

import cipher.console.oidc.domain.gold_mantis.GoldMantisUser;

import java.util.List;

/**
 * @Author: zt
 * @Date: 2018/9/17 16:59
 */
public interface GoldMantisMapper {


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


    public List<GoldMantisUser> selectVistorList(GoldMantisUser goldMantisUser);


    public  int selectVistorCount(GoldMantisUser goldMantisUser);

    public void insertVistorUser(GoldMantisUser goldMantisUser);


}
