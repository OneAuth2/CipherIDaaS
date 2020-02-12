package cipher.console.oidc.service;

import cipher.console.oidc.domain.zhonghe.ZhongheDepartment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ZhongheDepService {

    /**
     * 批量插入众合部门信息
     *
     * @param departmentList
     */
    public void insertZhDepList(@Param("departmentList") List<ZhongheDepartment> departmentList);

    /**
     * 查询已经存储的所有众合部门
     * @return
     */
    public List<ZhongheDepartment> queryAllZhDep();

    /**
     * 将众合部门信息合进自己的部门
     * @throws Exception
     */
    public void addGroup2Cipher() throws Exception;

}
