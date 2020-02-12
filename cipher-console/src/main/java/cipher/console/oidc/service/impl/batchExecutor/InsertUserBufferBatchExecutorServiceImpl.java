package cipher.console.oidc.service.impl.batchExecutor;

import cipher.console.oidc.domain.web.AdUserBufferDomain;
import cipher.console.oidc.mapper.AdUserBufferMapper;
import cipher.console.oidc.service.batchExecutor.IBatchExecutorService;
import edu.hziee.common.queue.IBatchExecutor;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class InsertUserBufferBatchExecutorServiceImpl  implements IBatchExecutorService, IBatchExecutor<AdUserBufferDomain> {

    private static final Logger logger = LoggerFactory.getLogger(InsertUserBufferBatchExecutorServiceImpl.class);

    @Autowired
    private AdUserBufferMapper adUserBufferMapper;

    @Override
    public void execute(List<AdUserBufferDomain> list) {
        try {
            if(CollectionUtils.isNotEmpty(list)){
                adUserBufferMapper.insertBufferList(list);
            }

        } catch (Exception e) {
            logger.error("Enter InsertUserBufferBatchExecutorServiceImpl.execute() but failed ..==");
            logger.error(e.getMessage(), e);
        }

    }
}
