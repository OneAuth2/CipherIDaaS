package com.controller;/**
 * @author lqgzj
 * @date 2019/8/7
 */
import com.config.CacheKey;
import com.redis.RedisClient;
import com.service.CipherAccountDingBindService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author qiaoxi
 * @Date 2019/8/715:13
 **/
@Controller
@RequestMapping(value = "/push")
public class PushController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PushController.class);

    @Resource
    private CipherAccountDingBindService cipherAccountDingBindService;

    @Autowired
    private RedisClient redisClient;

    /**
     *
     * @param corpId
     * @param dingUserId
     * @param result
     * @param timeStamp
     * @return
     */
    @RequestMapping(value = "/result", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> result(@RequestParam(value = "corpId") String corpId,
                                      @RequestParam(value = "userId") String dingUserId,
                                      @RequestParam(value = "result") boolean result,
                                      @RequestParam(value = "timeStamp") Long timeStamp) {
        HashMap<String, Object> map = new HashMap<>();
        if (StringUtils.isEmpty(corpId) || StringUtils.isEmpty(dingUserId) || StringUtils.isEmpty(result)
                || StringUtils.isEmpty(timeStamp)) {

            map.put("return_code", 1);
            map.put("return_msg", "参数存在空值");
            return map;
        }
        //获取用户id
        String uuid = cipherAccountDingBindService.getUuidByUserId(dingUserId);

        redisClient.put(CacheKey.getDingPushAuthResultKey(uuid, timeStamp), result);
        map.put("return_code", 0);
        map.put("return_msg", "已选择");
        return map;
    }

    /**
     * @param timeStamp
     * @param dingUserId
     * @return 计算是否超时
     */
    @RequestMapping(value = "/changeBtn", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> result(@RequestParam(value = "timeStamp") Long timeStamp,
                                      @RequestParam(value = "userId") String dingUserId) {
        HashMap<String, Object> map = new HashMap<>();

        if (StringUtils.isEmpty(timeStamp) || StringUtils.isEmpty(dingUserId)) {

            map.put("code", 0);
            map.put("msg", "参数为空");
            return map;
        }

        String uuid = cipherAccountDingBindService.getUuidByUserId(dingUserId);

        Object obj = redisClient.get(CacheKey.getDingPushAuthResultKey(uuid, timeStamp));

        if (obj != null) {
            map.put("code", 2);
            map.put("flag", true);
            return map;
        }

        long parse = System.currentTimeMillis();
        if ((parse - timeStamp) / 1000 > 180) {
            map.put("code", 1);
            map.put("flag", true);
            map.put("msg", "请求已超时！");
            return map;
        }

        map.put("code", 3);
        map.put("flag", false);
        return map;
    }
}
