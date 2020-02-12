package cipher.console.oidc.service.impl;

import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.config.LdapMultiSourceConfig;
import cipher.console.oidc.domain.web.AdAutoSyncInfo;
import cipher.console.oidc.domain.web.AdInfoDomain;
import cipher.console.oidc.domain.web.AutoSyncAdDomain;
import cipher.console.oidc.domain.web.AutoSyncAdInfo;
import cipher.console.oidc.mapper.AdInfoMapper;
import cipher.console.oidc.model.AdOuModel;
import cipher.console.oidc.service.AdInfoService;
import cipher.console.oidc.service.AutoSyncService;
import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.unboundid.ldap.sdk.*;
import com.unboundid.ldap.sdk.controls.SimplePagedResultsControl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.security.GeneralSecurityException;
import java.util.*;

/**
 * @Author: zt
 * @Date: 2018/6/7 17:38
 */

@Service
public class AdInfoServiceImpl implements AdInfoService {

    @Autowired
    private AdInfoMapper adInfoMapper;

    @Autowired
    private AutoSyncService autoSyncService;

    @Override
    public Map<String, Object> deleteAd(AdInfoDomain adInfoDomain) {
        Map<String, Object> map = new HashMap<>();
        try {
            //通过id获取上一次自动同步配置信息
            AutoSyncAdDomain autoSyncAdDomain = adInfoMapper.selectAutoSync(adInfoDomain.getId());
            if(autoSyncAdDomain!=null){
                if(autoSyncAdDomain.getAutoConfig()!=null&&StringUtils.isNotEmpty(autoSyncAdDomain.getAutoConfig())){
                    //上一次配置信息
                    AutoSyncAdInfo autoSyncAdInfo = new Gson().fromJson(autoSyncAdDomain.getAutoConfig(), AutoSyncAdInfo.class);
                    if(autoSyncAdInfo.getIsAutoSync()==0){
                        //关闭上一次定时任务
                        autoSyncService.closeAutoSyncAdTask(adInfoDomain.getId());
                    }
                }
            }
            adInfoMapper.deleteAd(adInfoDomain);
            map.put("code", 0);
            map.put("msg", "删除成功");
        } catch (Exception e) {
            map.put("code", 1);
            map.put("msg", "内部服务器错误");
            e.printStackTrace();
            return map;
        }

        return map;
    }

    @Override
    public void insertAdInfo(AdInfoDomain form) throws Exception {
        adInfoMapper.insertAdInfo(form);
        //ad自动定时同步 （先插入数据，再拿到id）
        if(form!=null){
            if(form.getId()!=null&&form.getId().intValue()>0){
                if(form.getAutoConfig()!=null&&StringUtils.isNotEmpty(form.getAutoConfig())){
                    //获取当前定时同步配置信息
                    AutoSyncAdInfo autoSyncAdInfo = new Gson().fromJson(form.getAutoConfig(), AutoSyncAdInfo.class);
                    if(autoSyncAdInfo.getIsAutoSync()==0&&
                            StringUtils.isNotEmpty(autoSyncAdInfo.getAutoSyncDate())&&autoSyncAdInfo.getInterval()>-1){
                        //开启一次新的定时任务
                        autoSyncService.autoSyncAdTask(form.getId(),form.getCompanyId(),autoSyncAdInfo);
                    }
                }
            }
        }
    }

    @Override
    public int queryAdCountByIp(AdInfoDomain adInfoDomain) {
        return adInfoMapper.queryByIpAndCompanyId(adInfoDomain);
    }

    @Override
    public AdInfoDomain queryAdInfo(AdInfoDomain form) {
        return adInfoMapper.queryAdInfo(form);
    }

    @Override
    public void updateAdInfo(AdInfoDomain form) throws Exception {
        adInfoMapper.updateAdInfo(form);
    }

    @Override
    public void updateAdAutSync(Integer id,String companyId,String adAutoSyncInfo) throws Exception {
        //通过id获取上一次自动同步配置信息
        AutoSyncAdDomain autoSyncAdDomain = adInfoMapper.selectAutoSync(id);
        if(autoSyncAdDomain!=null){
            if(autoSyncAdDomain.getAutoConfig()!=null&&StringUtils.isNotEmpty(autoSyncAdDomain.getAutoConfig())){
                //上一次配置信息
                AutoSyncAdInfo autoSyncAdInfo = new Gson().fromJson(autoSyncAdDomain.getAutoConfig(), AutoSyncAdInfo.class);
                if(autoSyncAdInfo.getIsAutoSync()==0){
                    //关闭上一次定时任务
                    autoSyncService.closeAutoSyncAdTask(id);
                }
            }
        }
        if(StringUtils.isNotEmpty(adAutoSyncInfo)){
            //当前配置信息
            AutoSyncAdInfo autoSyncAdInfo1 = new Gson().fromJson(adAutoSyncInfo, AutoSyncAdInfo.class);
            if(autoSyncAdInfo1!=null){
                if(autoSyncAdInfo1.getIsAutoSync()==0&&
                        StringUtils.isNotEmpty(autoSyncAdInfo1.getAutoSyncDate())&&autoSyncAdInfo1.getInterval()>-1){
                    //重新开启新一个定时任务
                    autoSyncService.autoSyncAdTask(id,companyId,autoSyncAdInfo1);
                }
            }
        }
        adInfoMapper.updateAdAutSync(id,adAutoSyncInfo);
    }

    @Override
    public Map<String, Object> list(AdInfoDomain form, DataGridModel pageModel) throws Exception {
        form = (form == null ? new AdInfoDomain() : form);
        form.setPageData(pageModel);
        List<AdInfoDomain> list = adInfoMapper.queryList(form);
        if (!CollectionUtils.isEmpty(list)) {
            for (AdInfoDomain adInfoDomain : list) {
                Date time = adInfoMapper.lastSyncTimeByIp(adInfoDomain.getIp());
                adInfoDomain.setCreateTime(time);
            }
        }
        int count = adInfoMapper.count(form);
        Map<String, Object> map = new HashMap<>();
        map.put("total", count);
        map.put("rows", list);
        return map;
    }

    @Override
    public AdInfoDomain queryAdInfoById(Integer id) {
        return adInfoMapper.queryAdInfoById(id);
    }

    @Override
    public String queryAdSyncInfoById(Integer id) {
        return adInfoMapper.queryAdSyncInfoById(id);
    }

    @Override
    public AdInfoDomain queryAdInfoNew(AdInfoDomain form) {
        return adInfoMapper.queryAdInfoNew(form);
    }


    @Override
    public List<AdOuModel> queryAdOuTree(AdInfoDomain adInfoDomain, String queryStr) throws GeneralSecurityException, LDAPException {
        LDAPConnection ldapConnection = LdapMultiSourceConfig.getLdapSdkConnection(adInfoDomain);
        Filter ouFilter = Filter.create("objectClass=organizationalUnit");

        String baseDn = LdapMultiSourceConfig.getRootDnByAdmin(adInfoDomain.getUserName());
        SearchRequest searchRequest = null;
        //搜索条件不为空
        if (StringUtils.isNotEmpty(queryStr)) {
            //模糊匹配
            Filter likeFilter = Filter.createSubAnyFilter("ou", queryStr);
            Filter resFilter = Filter.createANDFilter(ouFilter, likeFilter);
            searchRequest = new SearchRequest(baseDn, SearchScope.SUB, resFilter);
        } else {
            searchRequest = new SearchRequest(baseDn, SearchScope.SUB, ouFilter);
        }

        searchRequest.setControls(new SimplePagedResultsControl(50000, null));
        //0代表不限制
        searchRequest.setSizeLimit(100000);

        SearchResult searchResult = ldapConnection.search(searchRequest);

        List<SearchResultEntry> searchEntries = searchResult.getSearchEntries();

        List<AdOuModel> resList = new ArrayList<>();

        for (SearchResultEntry se : searchEntries) {
            constructOuTree(se, resList);
        }

        //取出已经保存的，并标记选中和未选中的状态
        AdInfoDomain dbAd = adInfoMapper.queryAdByIpAndCompanyId(adInfoDomain);
        if (dbAd != null && StringUtils.isNotEmpty(dbAd.getOus())) {
            String ouJson = dbAd.getOus();
            List<String> ouList = JSON.parseArray(ouJson, String.class);
            setSelectState(ouList, resList);
        }


        return resList;
    }


    public void setSelectState(List<String> ouList, List<AdOuModel> list) {
        for (AdOuModel adOuModel : list) {
            if (ouList.contains(adOuModel.getDn())) {
                adOuModel.setSelect(true);
            }

            if (!CollectionUtils.isEmpty(adOuModel.getNodes())) {
                setSelectState(ouList, adOuModel.getNodes());
            }
        }

    }


    private static List<AdOuModel> constructOuTree(SearchResultEntry searchResultEntry, List<AdOuModel> list) {

        String dn = searchResultEntry.getDN();

        String[] ous = dn.split(",");

        for (int i = ous.length-1; i >= 0; i--) {

            String[] kvs = ous[i].split("=");
            boolean exists = checkIfExists(list, kvs[1], dn);

            //已经 存在
            if (exists) {
                continue;
            }

            if (i == ous.length - 1) {
                //设置全路径
//                if ("dc".equalsIgnoreCase(kvs[0])) {
//                    String rootDn = getRootDn(dn);
//                    list.add(new AdOuModel(kvs[1], rootDn));
//                    continue;
//                }

                //TODO
                String fullDn = getFullDn(dn, kvs[1]);

                List<AdOuModel> nodes = queryNodesByName(list, ous[ous.length-1], fullDn);

                if(checkIfExists(nodes,ous[ous.length-1],fullDn)){
                    continue;
                }
                nodes.add(new AdOuModel(kvs[1], fullDn));
                continue;
               }
                String parentName = ous[i + 1].split("=")[1];

                List<AdOuModel> nodes = queryNodesByName(list, parentName, dn);
                //设置全路径
                String fullDn = getFullDn(dn, kvs[1]);
                int count =0;
                for(AdOuModel adOuModel:nodes){
                    if(kvs[1].equals(adOuModel.getrDN())){
                        count++;
                    }
                }
                if(count==0){
                    nodes.add(new AdOuModel(kvs[1], fullDn));
                }

        }

        return list;
    }

    /**
     * 判断list中是否存在name节点
     *
     * @param list
     * @param name
     * @return
     */
    private static boolean checkIfExists(List<AdOuModel> list, String name, String dn) {

        if (CollectionUtils.isEmpty(list)) {
            return false;
        }

        for (AdOuModel adOuModel : list) {

            if (name.equals(adOuModel.getrDN())) {
                return true;
            }
            List<AdOuModel> nodes = adOuModel.getNodes();

            if (!CollectionUtils.isEmpty(nodes) && nodes.size() > 0) {
                return checkIfExists(nodes, name, dn);
            }

        }
        return false;
    }

    /**
     * 查找父节点所对应的list
     *
     * @param list
     * @param parentName
     * @return
     */
    private static List<AdOuModel> queryNodesByName(List<AdOuModel> list, String parentName, String dn) {

        if (CollectionUtils.isEmpty(list)) {
            return list;
        }

        for (AdOuModel adOuModel : list) {
            //寻找父节点时，必须全路径匹配
            if (parentName.equals(adOuModel.getrDN()) && dn.endsWith(adOuModel.getDn())) {
                return adOuModel.getNodes();
            }
            List<AdOuModel> nodes = adOuModel.getNodes();
            if (!CollectionUtils.isEmpty(nodes)) {
                return queryNodesByName(nodes, parentName, dn);
            }
        }

        return list;
    }


    /**
     * 获取一个节点的根节点
     *
     * @param dn
     * @return
     */
    private static String getRootDn(String dn) {
        String[] split = dn.split(",");

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < split.length; i++) {

            String[] kvs = split[i].split("=");

            if ("DC".equalsIgnoreCase(kvs[0])) {
                stringBuilder.append("DC=").append(kvs[1]).append(",");
            }

        }
        String rootDn = stringBuilder.toString();
        rootDn = rootDn.substring(0, rootDn.length() - 1);
        return rootDn;
    }


    /**
     * 获取某个节点的全路径
     *
     * @param dn   本次的全路径
     * @param name 某个节点
     * @return name节点所对应的全路径
     */
    private static String getFullDn(String dn, String name) {
        String[] ous = dn.split(",");
        boolean flag = false;
        StringBuilder stringBuilder = new StringBuilder();
        for (String kvs : ous) {
            String[] kv = kvs.split("=");

            if (name.equals(kv[1]) || flag) {
                flag = true;
                stringBuilder.append(kv[0]).append("=").append(kv[1]).append(",");
            }
        }
        String dnStr = stringBuilder.toString();
        return dnStr.substring(0, dnStr.length() - 1);
    }


}
