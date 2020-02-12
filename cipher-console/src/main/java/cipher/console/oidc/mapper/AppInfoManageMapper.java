package cipher.console.oidc.mapper;

import cipher.console.oidc.domain.web.AppManageinfo;
import cipher.console.oidc.domain.web.WifiPortalWebInfo;

import java.util.List;

public interface AppInfoManageMapper {

  List<AppManageinfo> queryAppinfoList(AppManageinfo appManageinfo);


  public AppManageinfo queryInfoById(int id);
  public List<AppManageinfo> queryallinfo(AppManageinfo appManageinfo);
  public int  insertAppinfo(AppManageinfo appManageinfo);
  public int updateAppInfoId(AppManageinfo appManageinfo);
  public void deleteInfoById(int id);
  int selectAppInfoCount(AppManageinfo appManageinfo);


}
