package cipher.console.oidc.mapper;

import cipher.console.oidc.domain.exceldomain.WifeLogExcle;
import cipher.console.oidc.domain.web.PortalLogInfo;
import cipher.console.oidc.domain.web.VistorLoginLogInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface VistorLoginLogInfoMapper {
    /**
     * create by 田扛
     * create time 2019年3月15日10:35:26
     * 获取每天的数据量
     */
     int  selectCount(@Param(value = "date5") String date5,
                      @Param(value = "date6")String date6,
                      @Param(value = "type")String type,
                      @Param(value = "companyId")String companyId);
    VistorLoginLogInfo getUserNameState(String userName);
    int deleteByPrimaryKey(Integer id);
    int getEchartsYear(@Param(value = "i")int i,
                       @Param(value = "type")String type,
                       @Param(value = "date")Date date,
                       @Param(value = "companyId")String companyId);
    int insert(VistorLoginLogInfo record);
    int insertDemo(String date);
    int insertSelective(VistorLoginLogInfo record);

    VistorLoginLogInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(VistorLoginLogInfo record);

    int updateByPrimaryKey(VistorLoginLogInfo record);

    List<VistorLoginLogInfo>   selectAllVistorLoginLogList();

    List<VistorLoginLogInfo> selectVistorLoginLogList(VistorLoginLogInfo record);

    int selectVistorLogCount(VistorLoginLogInfo record);

    List<WifeLogExcle> downloadExcelLog(VistorLoginLogInfo vistorLoginLogInfo);

    /**
     * 当统计维度为ssid
     * @author cozi
     * @date 2019-07-19
     */
    List<PortalLogInfo> selectPortalList(String companyId);

    int selectSsidCount(@Param(value = "startTime") String startTime,
                        @Param(value = "endTime")String endTime,
                        @Param(value = "companyId")String companyId,
                        @Param(value = "portalId")Integer portalId);
    int selectSsidMonthCount(@Param(value = "i")int i,
                             @Param(value = "date")Date date,
                             @Param(value = "companyId")String companyId,
                             @Param(value = "portalId")Integer portalId);


}
