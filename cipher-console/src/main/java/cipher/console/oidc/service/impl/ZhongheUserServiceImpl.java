package cipher.console.oidc.service.impl;

import cipher.console.oidc.domain.zhonghe.ZhongheUser;
import cipher.console.oidc.domain.zhonghe.ZhongheUserDB;
import cipher.console.oidc.mapper.ZhongheUserDAO;
import cipher.console.oidc.service.ZhongheUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZhongheUserServiceImpl implements ZhongheUserService {

    @Autowired
    private ZhongheUserDAO zhongheUserDAO;

    @Override
    public void insertZhUser(List<ZhongheUser> userList) {
        zhongheUserDAO.insertZhUser(userList);
    }

    @Override
    public void zhonghe2cipheruser() throws Exception {
        zhongheUserDAO.zhonghe2cipheruser();
    }

    @Override
    public List<ZhongheUserDB> queryZhAllUser() {
        return zhongheUserDAO.queryAllZhUser();
    }
}
