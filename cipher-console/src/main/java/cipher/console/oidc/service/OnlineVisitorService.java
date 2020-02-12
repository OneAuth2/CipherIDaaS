package cipher.console.oidc.service;

import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.web.OnlineVisitor;

import java.util.List;

public interface OnlineVisitorService {

    List<OnlineVisitor> selectAllOnlineMember(DataGridModel dataGridModel);

    int selectOnlineMemberAccount();


    List<OnlineVisitor> selectOffLineVistor();

}
