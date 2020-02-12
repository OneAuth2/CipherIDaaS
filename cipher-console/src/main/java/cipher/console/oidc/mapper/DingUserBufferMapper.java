package cipher.console.oidc.mapper;

import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.web.DingUserDomain;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: zt
 * @Date: 2019-05-14 15:49
 */
public interface DingUserBufferMapper {

    /**
     * 查询已经存在于缓冲表中的
     *
     * @param list
     * @return
     */
    public List<String> queryAlredyInBufferUser(@Param(value = "list") List<DingUserDomain> list, @Param("companyId") String companyId);

    /**
     * 批量插入钉钉用户
     *
     * @param list
     */
    public void insert(@Param(value = "list") List<DingUserDomain> list);


    /**
     * @param status        筛选条件:0-全部，1-新增，2-待绑定，默认全部
     * @param companyId     公司id
     * @param dataGridModel 分页参数
     * @param queryStr      姓名模糊查询
     * @param matchRule     账号匹配规则,0-手机号,1-邮箱,2-手机号和邮箱,3-手机号或邮箱，满足其中一个条件则认为是一致
     * @return
     */
    public List<DingUserDomain> queryList(
            @Param(value = "status") Integer status,
            @Param(value = "companyId") String companyId,
            @Param(value = "dataGridModel") DataGridModel dataGridModel,
            @Param(value = "queryStr") String queryStr,
            @Param(value = "matchRule") Integer matchRule);

    public int queryListCount(
            @Param(value = "status") Integer status,
            @Param(value = "companyId") String companyId,
            @Param(value = "dataGridModel") DataGridModel dataGridModel,
            @Param(value = "queryStr") String queryStr,
            @Param(value = "matchRule") Integer matchRule);


    /**
     * @param useridList 钉钉的用户id列表 为空时，查询整个公司的
     * @param companyId  公司id
     * @param matchRule  账号匹配规则,0-手机号,1-邮箱,2-手机号和邮箱,3-手机号或邮箱，满足其中一个条件则认为是一致
     * @return
     */
    public List<DingUserDomain> queryListToSync(
            @Param(value = "userIdList") List<String> useridList,
            @Param(value = "companyId") String companyId,
            @Param(value = "matchRule") Integer matchRule);


    /**
     * 批量删除缓冲表中的钉钉用户
     *
     * @param idList
     * @param companyId
     */
    public void deleteByIdList(@Param(value = "list") List<String> idList,
                               @Param(value = "companyId") String companyId);

    /**
     * 删除缓冲表中某个公司下的所有用户
     *
     * @param companyId
     */
    public void deleteByCompanyIdList(
            @Param(value = "companyId") String companyId);

}
