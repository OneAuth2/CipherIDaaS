package cipher.console.oidc.mapper;

import cipher.console.oidc.domain.web.DingGroupDomain;
import cipher.console.oidc.domain.web.GroupInfoDomain;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: zt
 * @Date: 2019-05-13 10:14
 */
public interface DingGroupMapper {

    /**
     * 查询钉钉的部门已经存在于db的部分数据
     *
     * @param list 已经存在的钉钉的id
     * @return
     */
    public List<Long> queryAlredyExistsGroup(@Param(value = "list") List<DingGroupDomain> list);




}
