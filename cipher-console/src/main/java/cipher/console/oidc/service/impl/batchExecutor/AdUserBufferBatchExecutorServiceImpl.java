package cipher.console.oidc.service.impl.batchExecutor;

import cipher.console.oidc.domain.web.AdUserBufferDomain;
import cipher.console.oidc.mapper.AdUserBufferMapper;
import cipher.console.oidc.service.batchExecutor.IBatchExecutorService;
import edu.hziee.common.queue.IBatchExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;

public class AdUserBufferBatchExecutorServiceImpl  implements IBatchExecutorService, IBatchExecutor<AdUserBufferDomain> {

    private static final Logger logger  = LoggerFactory.getLogger(AdUserBufferBatchExecutorServiceImpl.class);

    @Autowired
    private AdUserBufferMapper adUserBufferMapper;

    @Override
    public void execute(List<AdUserBufferDomain> list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }

        try {
            adUserBufferMapper.updateBufferList(list);
        } catch (Exception e) {
            logger.error("Enter AdUserBufferDomainBatchExecutorServiceImpl.execute() but failed ..==");
            logger.error(e.getMessage(), e);
        }
    }
}
