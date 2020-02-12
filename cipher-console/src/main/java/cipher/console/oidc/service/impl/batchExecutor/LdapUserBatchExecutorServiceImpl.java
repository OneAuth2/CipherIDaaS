package cipher.console.oidc.service.impl.batchExecutor;

import cipher.console.oidc.entity.LdapUser;
import cipher.console.oidc.mapper.LdapUserMapper;
import cipher.console.oidc.service.batchExecutor.IBatchExecutorService;
import edu.hziee.common.queue.IBatchExecutor;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class LdapUserBatchExecutorServiceImpl implements IBatchExecutorService, IBatchExecutor<LdapUser> {

    private static final Logger logger = LoggerFactory.getLogger(LdapUserBatchExecutorServiceImpl.class);

    @Autowired
    private LdapUserMapper ldapUserMapper;

    @Override
    public void execute(List<LdapUser> ldapUserList) {
        try {
            if(CollectionUtils.isNotEmpty(ldapUserList)){
                ldapUserMapper.insertLdapUserList(ldapUserList);
            }

        } catch (Exception e) {
            logger.error("Enter BatchExecutorServiceImpl.execute() but failed ..==");
            logger.error(e.getMessage(), e);
        }

    }
}
