package cipher.console.oidc.mapper;

import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.web.DingUserDomain;
import cipher.console.oidc.domain.web.WxUserDomain;
import javafx.scene.chart.ValueAxis;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lqgzj
 * @date 2019-10-12
 */
public interface WxUserBufferMapper {

    List<WxUserDomain> queryList(
            @Param(value = "status") Integer status,
            @Param(value = "companyId") String companyUUid,
            @Param(value = "dataGridModel")  DataGridModel dataGridModel,
            @Param(value = "queryStr") String queryStr,
            @Param(value = "matchRule") Integer matchRule);

    int queryListCount(
            @Param(value = "status") Integer status,
            @Param(value = "companyId") String companyUUid,
            @Param(value = "dataGridModel")  DataGridModel dataGridModel,
            @Param(value = "queryStr") String queryStr,
            @Param(value = "matchRule") Integer matchRule);

    List<String> queryAlredyInBufferUser(@Param(value = "list") List<WxUserDomain> allWxUser, @Param(value = "companyId") String companyUUid);

    void insert(@Param(value = "list") List<WxUserDomain> allWxUser);

    /**
     * @param useridList 用户id列表 为空时，查询整个公司的
     * @param companyId  公司id
     * @param matchRule  账号匹配规则,0-手机号,1-邮箱,2-手机号和邮箱,3-手机号或邮箱，满足其中一个条件则认为是一致
     * @return
     */
    List<WxUserDomain> queryListToSync(
            @Param(value = "userIdList") List<String> useridList,
            @Param(value = "companyId") String companyId,
            @Param(value = "matchRule") Integer matchRule);

    void deleteByIdList(
            @Param(value = "list") List<String> idList,
            @Param(value = "companyId") String companyId);

    void deleteByCompanyIdList(@Param(value = "companyId") String companyId);
}
