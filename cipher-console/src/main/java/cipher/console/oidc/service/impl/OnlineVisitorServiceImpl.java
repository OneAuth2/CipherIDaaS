package cipher.console.oidc.service.impl;

import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.web.OnlineVisitor;
import cipher.console.oidc.mapper.OnlineVisitorMapper;
import cipher.console.oidc.service.OnlineVisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OnlineVisitorServiceImpl implements OnlineVisitorService {

    @Autowired
    private OnlineVisitorMapper onlineVisitorMapper;

    @Override
    public List<OnlineVisitor> selectAllOnlineMember(DataGridModel dataGridModel) {
        return null;
    }

    @Override
    public int selectOnlineMemberAccount() {
        return onlineVisitorMapper.selectOnlineMemberAccount();
    }

    @Override
    public List<OnlineVisitor> selectOffLineVistor() {
        return onlineVisitorMapper.selectOffLineVistor();
    }
}
