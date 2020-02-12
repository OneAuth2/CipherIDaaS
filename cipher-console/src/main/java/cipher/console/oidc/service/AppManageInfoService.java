package cipher.console.oidc.service;

import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.web.AppManageinfo;

import java.util.List;
import java.util.Map;

public interface AppManageInfoService {
   /**
    * App资源管理：
    * */


 /*public Map<String,Object> queryAppinfoList(AppManageinfo appManageinfo, DataGridModel pageModel);*/

    public List<AppManageinfo> queryallinfo(AppManageinfo appManageinfo);
    public int  insertAppinfo(AppManageinfo appManageinfo);

    public int updateAppInfoId(AppManageinfo appManageinfo);

    public void deleteInfoById(int id);

    public Map<String,Object> getAppVersionInfoList(AppManageinfo appManageinfo,DataGridModel pageModel);


 public AppManageinfo queryInfoById(int id);
}
