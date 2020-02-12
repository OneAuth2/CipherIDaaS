package cipher.console.oidc.service.impl;

import cipher.console.oidc.service.UUIDService;
import com.alibaba.fastjson.serializer.UUIDCodec;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @Author: zt
 * @Date: 2019-04-29 19:23
 */
@Service
public class UUIDServiceImpl implements UUIDService {
    @Override
    public String getUUid() {
        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }
}
