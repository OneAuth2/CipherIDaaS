package cipher.console.oidc.service.impl;

import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.web.AppManageinfo;
import cipher.console.oidc.mapper.AppInfoManageMapper;
import cipher.console.oidc.service.AppManageInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service

public class AppManageInfoServiceImpl implements AppManageInfoService{

    @Autowired
    private  AppInfoManageMapper appInfoManageMapper;


    private AppManageinfo appManageinfo;


    @Override
    public AppManageinfo queryInfoById(int id) {
        AppManageinfo appManageinfo = appInfoManageMapper.queryInfoById(id);
        return appManageinfo;
    }

    @Override
    public int insertAppinfo(AppManageinfo appManageinfo) {
          return appInfoManageMapper.insertAppinfo(appManageinfo);
    }

    @Override
    public int updateAppInfoId(AppManageinfo appManageinfo) {
        return appInfoManageMapper.updateAppInfoId(appManageinfo);
    }

    @Override
    public void deleteInfoById(int id) {
        appInfoManageMapper.deleteInfoById(id);
    }

    @Override
    public List<AppManageinfo> queryallinfo(AppManageinfo appManageinfo) {
        System.out.println(appInfoManageMapper.queryallinfo(appManageinfo));
        return appInfoManageMapper.queryallinfo(appManageinfo);
    }

    @Override
    public Map<String, Object> getAppVersionInfoList(AppManageinfo appManageinfo,DataGridModel pageModel) {
        Map<String, Object> map = new HashMap<>();
        appManageinfo = (appManageinfo == null ? new AppManageinfo() : appManageinfo);
        appManageinfo.setPageData(pageModel);
        List<AppManageinfo> list = this.queryallinfo(appManageinfo);
        int total = appInfoManageMapper.selectAppInfoCount(appManageinfo);
        map.put("rows", list);
        map.put("total", total);
        return map;
    }

}
