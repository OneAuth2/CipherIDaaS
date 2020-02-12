package cipher.console.oidc.service.impl;

import cipher.console.oidc.domain.web.AcAclInfo;
import cipher.console.oidc.mapper.AcAclInfoMapper;
import cipher.console.oidc.service.AcAclInfoService;
import cipher.console.oidc.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AcAclInfoServiceImpl implements AcAclInfoService {

    @Autowired
    private AcAclInfoMapper acAclInfoMapper;

    @Override
    public List<AcAclInfo> selectAclInfo(AcAclInfo acAclInfo) {
        return acAclInfoMapper.selectAclInfo(acAclInfo);
    }

    @Override
    public void insertAclInfo(AcAclInfo acAclInfo) {
        acAclInfo.setMac(
                acAclInfo.getMac()
                         .trim()
                         .replace("-","")
                         .replace(",","")
                         .replace("/","")
                         .toLowerCase()
        );
        acAclInfo.setTime(String.valueOf(DateUtil.getSystemDate()), DateUtil.getSystemDate(), DateUtil.getSystemDate(), DateUtil.getMoreYearsDate(1));
        acAclInfoMapper.insertAclInfo(acAclInfo);
    }

    @Override
    public boolean isExist(String mac) {
        mac = mac.trim()
                .replace("-","")
                .replace(",","")
                .replace("/","")
                .toLowerCase();
        if (acAclInfoMapper.selectAclInfoByMac(mac) == null){
            return false;
        }
        return true;
    }

    @Override
    public void updateAclInfoByMac(AcAclInfo acAclInfo) {
        acAclInfo.setMac(
                acAclInfo.getMac()
                        .trim()
                        .replace("-","")
                        .replace(",","")
                        .replace("/","")
                        .toLowerCase()
        );
        acAclInfo.setModifiedTime(DateUtil.getSystemDate());
        acAclInfoMapper.updateAclInfoByMac(acAclInfo);
        return;
    }

    @Override
    public void deleteAclInfoByMac(String mac) {
        acAclInfoMapper.deleteAclInfoByMac(mac);
        return;
    }

    @Override
    public Integer selectCount(AcAclInfo acAclInfo) {
        return acAclInfoMapper.selectCount(acAclInfo);
    }

    @Override
    public void updateAclInfo(AcAclInfo acAclInfo) {
        acAclInfoMapper.updateAclInfo(acAclInfo);
    }
}
