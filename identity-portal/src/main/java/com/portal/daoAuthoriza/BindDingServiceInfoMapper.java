package com.portal.daoAuthoriza;

import com.portal.domain.*;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: TK
 * @Date: 2019/5/8 15:41
 */
public interface BindDingServiceInfoMapper {

    /**
     * 根据公司id回去钉钉的后台配置
     *
     * @param companyUUid
     * @return
     */
    DingdingConfigInfo getDingInfoByCompanyUUid(@Param("companyUUid")String companyUUid);

    BindingDingInfoDomain getDingDingInfoDomain(@Param("userId") String uuid);

    void insertBindDing(BindingDingInfoDomain dingInfoDomain);

    DaBaiBindingInfoDomain getDabaiBindingInfo(String uuid);

    void insertDaBaiBinding(DaBaiBindingInfoDomain daBaiBindingInfoDomain1);

    SaiFuBindingInfoDomain getSaiFuBindingInfo(String uuid);

    void insertBindDingSaiFu(SaiFuBindingInfoDomain saiFuBindingInfoDomain1);

    DaBaiBindingInfoDomain getDabaiBindingInfoByIdNum(@Param("idNum")String idNum,@Param("companyId")String companyId);

    SaiFuBindingInfoDomain getSaiFuBindingInfoBySaiFuId(@Param("platId")String platId,@Param("companyId")String companyId);

    BindingDingInfoDomain getDingTalkInfoByUnionId(@Param("unionId")String unionId,@Param("companyId")String companyId);

    BindWxInfoDomain getWxInfoByUnionId(@Param("wxUserId") String wxUserId,@Param("companyId") String companyId);

    boolean insertWeiXinInfo(BindWxInfoDomain bindWxInfoDomain);


}
