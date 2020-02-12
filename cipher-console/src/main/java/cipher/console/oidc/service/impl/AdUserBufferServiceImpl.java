package cipher.console.oidc.service.impl;

import cipher.console.oidc.domain.web.AdUserBufferDomain;
import cipher.console.oidc.domain.web.UserInfoDomain;
import cipher.console.oidc.entity.LdapUser;
import cipher.console.oidc.mapper.AdUserBufferMapper;
import cipher.console.oidc.service.AdUserBufferService;

import edu.hziee.common.queue.DelayExecuteBuffer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @Author: zt
 * @Date: 2018/10/25 9:44
 */
@Service
public class AdUserBufferServiceImpl implements AdUserBufferService {

    @Autowired
    private AdUserBufferMapper adUserBufferMapper;


//    @Autowired
//    private DelayExecuteBuffer<AdUserBufferDomain> updateAdUserBufferBuffer;


//    @Autowired
//    private DelayExecuteBuffer<AdUserBufferDomain> insertAdUserBufferBuffer;

    @Override
    @Transactional
    public List<UserInfoDomain> queryUserByBufferUser(List<AdUserBufferDomain> bufferDomainList,String companyId) {
        if (CollectionUtils.isEmpty(bufferDomainList)) {
            return null;
        }
        return adUserBufferMapper.queryUserByBufferUser(bufferDomainList,companyId);
    }

    @Override
    public void insertBufferList(List<AdUserBufferDomain> bufferDomainList) throws Exception {
        if (CollectionUtils.isEmpty(bufferDomainList)) {
            return;
        }

//        for (AdUserBufferDomain adUserBufferDomain : bufferDomainList) {
//            insertAdUserBufferBuffer.add(adUserBufferDomain);
//        }

        adUserBufferMapper.insertBufferList(bufferDomainList);
    }

    @Override
    public List<AdUserBufferDomain> queryInBufferList(List<UserInfoDomain> userInfoDomainList,String companyId) {
        return adUserBufferMapper.queryInBufferList(userInfoDomainList,companyId);
    }

    @Override
    public void updateBufferList(List<AdUserBufferDomain> bufferList) {
        if (CollectionUtils.isEmpty(bufferList)) {
            return;
        }

//        for (AdUserBufferDomain adUserBufferDomain : bufferList) {
//            updateAdUserBufferBuffer.add(adUserBufferDomain);
//        }

        adUserBufferMapper.updateBufferList(bufferList);
    }

    @Override
    public List<AdUserBufferDomain> queryInBufferListByBuffer(List<AdUserBufferDomain> existsList,String companyId) {
        return adUserBufferMapper.queryInBufferListByByffer(existsList,companyId);
    }

    @Override
    public List<AdUserBufferDomain> queryUserByBufferList(List<AdUserBufferDomain> bufferDomainList,String companyId) {
        return adUserBufferMapper.queryUserByBufferList(bufferDomainList,companyId);
    }

    @Override
    public List<AdUserBufferDomain> queryListByIdList(List<Integer> idList) {
        return adUserBufferMapper.queryListByIdList(idList);
    }

    @Override
    public void insertIntoCipherUser(List<AdUserBufferDomain> bufferDomainList) throws Exception {
        adUserBufferMapper.insertIntoCipherUser(bufferDomainList);
    }

    @Override
    @Transactional
    public void updateCipherUserByBuffer(List<AdUserBufferDomain> bufferDomainList) throws Exception {
        adUserBufferMapper.updateCipherUserByBuffer(bufferDomainList);
    }

    @Override
    public void deleteByIdList(List<Integer> idList) throws Exception {
        adUserBufferMapper.deleteByIdList(idList);
    }

    @Override
    public List<UserInfoDomain> queryUserInfoByBufferList(List<AdUserBufferDomain> bufferDomainList,String companyId) {
        return adUserBufferMapper.queryUserInfoByBufferList(bufferDomainList,companyId);
    }

    @Override
    public List<AdUserBufferDomain> queryAllBufferUser(String companyId) {
        return adUserBufferMapper.queryAllBufferUser(companyId);
    }
}
