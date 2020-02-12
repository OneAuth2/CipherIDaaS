package cipher.console.oidc.job;

import cipher.console.oidc.domain.zhonghe.ZhonghePost;
import cipher.console.oidc.service.ZhonghePostService;
//import cipher.console.oidc.util.BeanUtil;
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
 * 同步众合岗位的定时任务
 */
public class SyncZhPostJob implements Job {

    private ZhonghePostService zhonghePostService= SpringContextUtil.getBean(ZhonghePostService.class);

    /**
     * 众合职位接口
     */
    private static final String post_interface = "http://183.129.174.36/hr/api/post/?api_code=adfavn@iaerv23sd&q__is_active=__True&q__is_hide=__False";

    private static final Logger LOGGER= LoggerFactory.getLogger(SyncZhPostJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        LOGGER.info("进入同步众合岗位的定时任务"+new Date());
        String postJson = HttpClientUtils.getJsonFromServer(post_interface);
        Type postType = new TypeToken<List<ZhonghePost>>() {
        }.getType();
        List<ZhonghePost> postList = new Gson().fromJson(postJson, postType);
        List<ZhonghePost> postListDB = zhonghePostService.queryAllZhPost();

        List<ZhonghePost> postUniqueList = postChaji(postList, postListDB);

        if (!CollectionUtils.isEmpty(postUniqueList)) {
            try {
                zhonghePostService.insertPostList(postUniqueList);
                zhonghePostService.addmap2Cipher();
                LOGGER.info("同步众合岗位成功");
            } catch (Exception e) {
               LOGGER.error("同步众合岗位失败"+e.getMessage()+":"+e.getCause());
            }
        }
    }

    /**
     * 去除左边重复的元素，并返回左边
     *
     * @param left
     * @param right
     * @return
     */
    private List<ZhonghePost> postChaji(List<ZhonghePost> left, List<ZhonghePost> right) {
        List<ZhonghePost> res = new LinkedList<>(left);

        Set<Integer> set = new HashSet<>();
        for (ZhonghePost post : right) {
            set.add(post.getPk());
        }
        res.removeIf(post -> set.contains(post.getPk()));
        return res;
    }



}
