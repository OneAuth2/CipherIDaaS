package cipher.console.oidc.service;

import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.web.NewUserInfo;

import java.util.List;
import java.util.Map;

public interface WelcomeUserService {

    //获取无部门用户列表
    public Map<String,Object> getNoGroupUserList(NewUserInfo form, DataGridModel pageModel);


    //获取锁定用户列表
    public Map<String, Object> getLockUserList(NewUserInfo form, DataGridModel pageModel);


    //获取最近30天未登录用户
    public Map<String,Object> getNoLoginUserList(NewUserInfo form,DataGridModel page);




}
