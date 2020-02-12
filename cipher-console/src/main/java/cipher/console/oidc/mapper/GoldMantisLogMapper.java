package cipher.console.oidc.mapper;

import cipher.console.oidc.domain.gold_mantis.GoldMantisUserLog;

import java.util.List;

/**
 * @Author: zt
 * @Date: 2018/9/17 16:59
 */
public interface GoldMantisLogMapper {

    /**
     * 查询数据列表
     * @param form
     * @return
     * @throws Exception
     */
   public List<GoldMantisUserLog> queryPageList(GoldMantisUserLog form) throws Exception;

    /**
     * 查询数据总的条数
     * @param form
     * @return
     * @throws Exception
     */
   public int queryPageListTotal(GoldMantisUserLog form) throws Exception;

}
