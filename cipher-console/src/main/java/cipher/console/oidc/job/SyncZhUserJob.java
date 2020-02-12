package cipher.console.oidc.job;

import cipher.console.oidc.domain.zhonghe.ZhongheUser;
import cipher.console.oidc.domain.zhonghe.ZhongheUserDB;
import cipher.console.oidc.service.ZhonghePostService;
import cipher.console.oidc.service.ZhongheUserService;
import cipher.console.oidc.service.impl.ZhongheUserServiceImpl;
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
 * 同步众合用户的定时任务
 */
public class SyncZhUserJob implements Job {


    private ZhongheUserService zhongheUserService = SpringContextUtil.getBean(ZhongheUserServiceImpl.class);

    /**
     * 众合在职员工接口
     */
    private static final String user_interface = "http://183.129.174.36/hr/api/staff/?api_code=adfavn@iaerv23sd&q__is_active=__True&q__status=trial,intern,official";


    private ZhonghePostService zhonghePostService = SpringContextUtil.getBean(ZhonghePostService.class);


    private static final Logger LOGGER= LoggerFactory.getLogger(SyncZhUserJob.class);


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        LOGGER.info("进入同步众合用户的定时任务:"+new Date());
        String json = HttpClientUtils.getJsonFromServer(user_interface);
        Type type = new TypeToken<List<ZhongheUser>>() {
        }.getType();
        List<ZhongheUser> userList = new Gson().fromJson(json, type);
        List<ZhongheUserDB> zhongheUserDBList = zhongheUserService.queryZhAllUser();

        List<ZhongheUser> uniqueZhUserList = chaji(userList, zhongheUserDBList);

        try {
            if (!CollectionUtils.isEmpty(uniqueZhUserList)) {
                LOGGER.info("同步众合用户定时任务,此次发现新用户:"+uniqueZhUserList.size()+"个");
                zhongheUserService.insertZhUser(uniqueZhUserList);
                zhongheUserService.zhonghe2cipheruser();
                LOGGER.info("映射用户到自己的用户体系成功");
            }
        } catch (Exception e) {
            LOGGER.error("映射用户到自己的用户体系失败"+e.getMessage()+":"+e.getCause());
        }

        /***************************当同步完用户后同步用户和组之间的关联关系**********************************/
        LOGGER.info("进入同步众合用户和组之间的关联关系:" + new Date());
        try {
            zhonghePostService.addmap2Cipher();
            LOGGER.info("同步众合用户和组之间的关联关系成功:"+new Date());
        } catch (Exception e) {
            LOGGER.error("同步众合用户和组之间的关联关系失败:"+new Date(),e);
        }


    }


    /**
     * 求两个集合的差集，去除左边重复的数据并返回左边
     *
     * @param left
     * @param right
     * @return
     */
    private List<ZhongheUser> chaji(List<ZhongheUser> left, List<ZhongheUserDB> right) {
        List<ZhongheUser> res = new LinkedList<>(left);
        Set<String> set = new HashSet<>();
        for (ZhongheUserDB zhongheUserDB : right) {
            set.add(zhongheUserDB.getUserName());
        }
        res.removeIf(user -> set.contains(user.getUser_name()));
        return res;
    }



}
