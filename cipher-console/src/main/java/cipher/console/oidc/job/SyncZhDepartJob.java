package cipher.console.oidc.job;

import cipher.console.oidc.domain.zhonghe.ZhongheDepartment;
import cipher.console.oidc.service.ZhongheDepService;
import cipher.console.oidc.util.HttpClientUtils;
import cipher.console.oidc.util.SpringContextUtil;
import com.google.gson.Gson;

import com.google.gson.reflect.TypeToken;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Type;
import java.util.*;

/**
 * 同步众合部门的定时任务
 */
public class SyncZhDepartJob implements Job {

    /**
     * 众合部门接口
     */
    private static final String depart_interface = "http://183.129.174.36/hr/api/department/?api_code=adfavn@iaerv23sd&q__is_active=__True&q__is_hide=__False";


    private ZhongheDepService zhongheDepService= SpringContextUtil.getBean(ZhongheDepService.class);

    private static final Logger LOGGER= LoggerFactory.getLogger(SyncZhDepartJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        LOGGER.info("进入同步众合部门的定时任务"+new Date());
        String depJson = HttpClientUtils.getJsonFromServer(depart_interface);
        Type depType = new TypeToken<List<ZhongheDepartment>>() {
        }.getType();
        List<ZhongheDepartment> departmentList = new Gson().fromJson(depJson, depType);
        List<ZhongheDepartment> departmentListDB = zhongheDepService.queryAllZhDep();
        List<ZhongheDepartment> uniqueDepList = depChaji(departmentList, departmentListDB);
        try {
            if (!CollectionUtils.isEmpty(uniqueDepList)) {
                zhongheDepService.insertZhDepList(uniqueDepList);
                zhongheDepService.addGroup2Cipher();
               LOGGER.info("同步众合的部门成功"+new Date());
            }

        } catch (Exception e) {
           LOGGER.error("同步众合的部门失败"+new Date()+e.getMessage()+":"+e.getCause());
        }
    }

    /**
     * 去除左边重复的数据，并返回左边
     *
     * @param left
     * @param right
     * @return
     */
    private List<ZhongheDepartment> depChaji(List<ZhongheDepartment> left, List<ZhongheDepartment> right) {
        List<ZhongheDepartment> res = new LinkedList<>(left);

        Set<Integer> set = new HashSet<>();
        for (ZhongheDepartment department : right) {
            set.add(department.getPk());
        }
        res.removeIf(zhongheDepartment -> set.contains(zhongheDepartment.getPk()));
        return res;
    }



}
