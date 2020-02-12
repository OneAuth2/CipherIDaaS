package cipher.console.oidc.mapper;

import cipher.console.oidc.domain.exceldomain.WifiVistorExcle;
import cipher.console.oidc.domain.web.OnlineVisitor;
import cipher.console.oidc.domain.web.VistorLoginLogInfo;

import java.util.List;

public interface OnlineVisitorMapper {

    List<VistorLoginLogInfo> selectAllOnlineMember(VistorLoginLogInfo vistorLoginLogInfo);//获取所有在线用户

    int selectOnlineMemberAccount();

    public List<OnlineVisitor> selectOffLineVistor();


    public void deleteVistor(OnlineVisitor onlineVisitor);



    List<OnlineVisitor> selectOnlineVistorList(OnlineVisitor  onlineVisitor);//获取所有在线用户

    int selectOnlineVistorListCount(OnlineVisitor  onlineVisitor);//获取所有在线用户数量


    List<WifiVistorExcle> export(OnlineVisitor form);

}
