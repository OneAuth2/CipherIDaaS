package cipher.console.oidc.mapper;

import cipher.console.oidc.domain.exceldomain.ApplicationAuditExcle;
import cipher.console.oidc.domain.web.AppAuditInfo;
import cipher.console.oidc.domain.web.ApplicationAuditInfo;
import cipher.console.oidc.domain.web.ApplicationInfo;
import cipher.console.oidc.domain.web.ApplicationChartInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ApplicationAuditInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ApplicationAuditInfo record);

    int insertSelective(ApplicationAuditInfo record);

    ApplicationAuditInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ApplicationAuditInfo record);

    int updateByPrimaryKey(ApplicationAuditInfo record);

    //自定义时间
    List<ApplicationAuditInfo> selectApplicationAuditList(ApplicationAuditInfo record);
    //自定义时间
    int selectApplicationAuditCount(ApplicationAuditInfo record);

    //7天内
    List<ApplicationAuditInfo> selectAppAuditListBySev(ApplicationAuditInfo record);

    int selectAppAuditCountBySev(ApplicationAuditInfo record);

    //30天
    List<ApplicationAuditInfo> selectAppAuditListByMon(ApplicationAuditInfo record);

    int selectAppAuditCountByMon(ApplicationAuditInfo record);

    //全部
    List<ApplicationAuditInfo> selectAppAuditListByAll(ApplicationAuditInfo record);

    int selectAppAuditCountByAll(ApplicationAuditInfo record);

    List<ApplicationInfo> selectApplicationList(ApplicationInfo form);

    String selectIdsBycompanyid(String companyId);

    Integer getTotalByTime(@Param(value = "companyId") String companyId,
                           @Param(value = "startTime")String startTime,
                           @Param(value = "endTime")String endTime,
                           @Param(value = "ids")String ids);

    Integer getCountByTime(@Param(value = "applicationId") int applicationId,
                           @Param(value = "companyId")String companyId,
                           @Param(value = "startTime")String startTime,
                           @Param(value = "endTime")String endTime);

    int selectApplicationCount(ApplicationInfo form);

    List<ApplicationAuditExcle> exportExcle(ApplicationAuditInfo form);

    Integer getTotalBySev(@Param(value = "companyId")String companyId,@Param(value = "ids")String ids);

    Integer getCountBySev(@Param(value = "applicationId") int applicationId,@Param(value = "companyId")String companyId);

    Integer getTotalByAll(@Param(value = "companyId")String companyId,@Param(value = "ids")String ids);

    Integer getCountByAll(@Param(value = "applicationId") int applicationId,@Param(value = "companyId")String companyId);

    Integer getTotalByMon(@Param(value = "companyId")String companyId,@Param(value = "ids")String ids);

    Integer getCountByMon(@Param(value = "applicationId") int applicationId,@Param(value = "companyId")String companyId);

    List<ApplicationInfo> getApplyList(@Param(value = "applicationId") int applicationId,
                                       @Param(value = "companyId") String companyId);

    ApplicationInfo getApplyInfo(ApplicationInfo form);

    //添加应用日志
    void insertNew(AppAuditInfo appAuditInfo);

    List<ApplicationChartInfo> getApplicationInfoList(String companyId);

    Integer getApplicationAuditCount(@Param(value = "param") String param,
                                     @Param(value = "companyId") String companyId,
                                     @Param(value = "id") Integer id);

}