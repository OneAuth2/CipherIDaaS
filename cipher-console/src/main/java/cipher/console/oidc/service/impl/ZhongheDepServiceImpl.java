package cipher.console.oidc.service.impl;

import cipher.console.oidc.domain.zhonghe.ZhongheDepartment;
import cipher.console.oidc.mapper.ZhongheDepMapper;
import cipher.console.oidc.service.ZhongheDepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZhongheDepServiceImpl implements ZhongheDepService {

    @Autowired
    private ZhongheDepMapper zhongheDepMapper;

    @Override
    public void insertZhDepList(List<ZhongheDepartment> departmentList) {
        zhongheDepMapper.insertZhDepList(departmentList);
    }

    @Override
    public List<ZhongheDepartment> queryAllZhDep() {
        return zhongheDepMapper.queryAllZhDep();
    }

    @Override
    public void addGroup2Cipher() throws Exception {
        zhongheDepMapper.addGroup2Cipher();
    }
}
