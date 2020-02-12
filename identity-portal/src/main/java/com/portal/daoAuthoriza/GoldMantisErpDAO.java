package com.portal.daoAuthoriza;


import com.portal.domain.ErpInfoDomain;

/**
 * 金螳螂ERP的DAO层
 * create by shizhao at 2019/5/7
 *
 * @author shizhao
 * @since 2019/5/7
 * */
public interface GoldMantisErpDAO {


    /**
     * 查询ERP配置信息
     * */
    ErpInfoDomain selectGoldMantisErpUrl(String companyId);


}
