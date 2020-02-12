package cipher.console.oidc.service.impl;

import cipher.console.oidc.common.CacheKey;
import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.web.*;
import cipher.console.oidc.mapper.ResMenuMapper;
import cipher.console.oidc.service.ResMenuService;
import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.jni.Library;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ResMenuServiceImpl implements ResMenuService {

    private static final Logger logger = LoggerFactory.getLogger(ResMenuServiceImpl.class);

    @Autowired
    private ResMenuMapper resMenuMapper;

    @Override
    public void deleteResMenu(int id) {
       resMenuMapper.deleteResMenu(id);
    }

    @Override
    public void saveMenu(MenuForm form) {
        if (form != null && "1".equals(form.getDisplayType())) {
            form.setParent(0);
        }
        this.insertResMenu(form);
    }

    @Override
    public int insertResMenu(MenuForm menuForm) {
         return resMenuMapper.insertResMenu(menuForm);
    }

    @Override
    public int updateResMenu(MenuForm menuForm) {
        return resMenuMapper.updateResMenu(menuForm);
    }

    @Override
    public List<MenuForm> selectAll(MenuForm menuForm) {
        return resMenuMapper.selectMenuList(menuForm);
    }


    @Override
    public Map<String, Object> selectMenuTreeList(MenuForm menuForm,DataGridModel pageModel) {
        Map<String, Object> map = new HashMap<>();
        menuForm = (menuForm == null ? new MenuForm() : menuForm);
        menuForm.setPageData(pageModel);
        List<MenuForm> list = this.selectAll(menuForm);
        int total = resMenuMapper.selectMenuInfoCount(menuForm);
        map.put("rows", list);
        map.put("total", total);
        return map;
    }

    @Override
    public List<MenuForm> selectMenuForms(MenuForm menuForm) {
        return resMenuMapper.selectMenuForms();
    }

    @Override
    public List<MenuTreeNodesDomain> getAllRootList(){
            List<MenuForm> rootList = resMenuMapper.selectAllRoot();
            List<MenuTreeNodesDomain> trees = new ArrayList<MenuTreeNodesDomain>();
            constructMenuTree(trees,0,rootList);
            return trees;

    }

    @Override
    public List<MenuForm> selectByParent(MenuForm menuForm){
       return  resMenuMapper.selectChildByParent(menuForm);
    }

    @Override
     public List<MenuForm> selectRootMenu(int resourceId){
        return resMenuMapper.selectRootMenu(resourceId);
    }

    @Override
    public int getRootStruct(List<MenuTreeNodesDomain> result, List<MenuTreeNodesDomain> list, int resourceId) {
        for (MenuTreeNodesDomain treeNodesDomain : list){
            if (Integer.valueOf(treeNodesDomain.getResourceId()) == resourceId ){
                result.add(treeNodesDomain);
                return 1;
            }else {
                int d = getRootStruct(result,treeNodesDomain.getNodes(),resourceId);
                if (d == 1){
                    result.add(treeNodesDomain);
                    return 1;
                }
            }
        }
        return 0;
    }


    private List<MenuTreeNodesDomain> constructMenuTree(List<MenuTreeNodesDomain> trees,int parentId,List<MenuForm> rootList) {
        for (MenuForm menuForm : rootList) {
            //查找根节点
            if (menuForm.getParent() == parentId) {
                MenuTreeNodesDomain treeNode = new MenuTreeNodesDomain(menuForm);
                treeNode.setHref("/cipher/menu/pmlist?resourceId="+menuForm.getResourceId());
                treeNode.setResourceId(String.valueOf(menuForm.getResourceId()));
                treeNode.setResourceName(menuForm.getResourceName());
                treeNode.setParent(menuForm.getParent());
                treeNode.setNodes(constructMenuTree(treeNode.getNodes(),menuForm.getResourceId(),rootList));
                trees.add(treeNode);
            }
        }

        return trees;
    }

    @Override
    public List<MenuTreeNodesDomain> getAllMenuList() {
        List<MenuForm> rootList = resMenuMapper.selectAllRoot();
        List<MenuTreeNodesDomain> menuList = resMenuMapper.selectAllChild();
        List<MenuTreeNodesDomain> listTree = new ArrayList<MenuTreeNodesDomain>();
        constructTreeV2(listTree,0,rootList, menuList);
       // System.out.println(listTree);
        return listTree;

    }

    @Override
    public List<MenuForm> getNowListByResourceId(MenuForm menuForm){
        return  resMenuMapper.selectChildByParent(menuForm);
    }

    @Override
    public  List<MenuForm> selectMenuListByParent(MenuForm menuForm){
        List<MenuForm> list = resMenuMapper.selectMenuListByParent(menuForm);
        return  list;
    }

    @Override
    public List<MenuForm> getListByResourceId(MenuForm menuForm) {
       // List<MenuForm> menuForms = new ArrayList<>();
        List<MenuForm> list = resMenuMapper.selectChildByParent(menuForm);
        return list;


    }

    @Override
    public Map<String, Object> getMenuListByParentId(DataGridModel pageModel,MenuForm menuForm) {
        Map<String, Object> map = new HashMap<>();
        menuForm = (menuForm == null ? new MenuForm() : menuForm);
        menuForm.setPageData(pageModel);
        List<MenuForm> list = this.getListByResourceId(menuForm);
        int total = resMenuMapper.selectMenuInfoCount(menuForm);
        map.put("rows", list);
        map.put("total", total);
        return map;
    }

    public List<MenuTreeNodesDomain> constructTreeV2(List<MenuTreeNodesDomain> tree, int parent, List<MenuForm> rootList, List<MenuTreeNodesDomain> menuList ){
        MenuTreeNodesDomain treeNode = new MenuTreeNodesDomain();
        if(rootList.size()>0){
            for (MenuForm menuForm : rootList) {
                //查找根节点

                if (!StringUtils.isEmpty(String.valueOf(menuForm.getParent()))&&menuForm.getParent()== parent) {
                    treeNode = new MenuTreeNodesDomain(menuForm);
                    treeNode.setHref("/cipher/menu/pmlist?resourceId="+menuForm.getResourceId());
                    treeNode.setResourceId(String.valueOf(menuForm.getResourceId()));
                    treeNode.setResourceName(menuForm.getResourceName());
                    treeNode.setParent(menuForm.getParent());
                    treeNode.setNodes(constructTreeV2(treeNode.getNodes(),menuForm.getResourceId(),rootList,menuList));
                    if (treeNode.getNodes() == null || treeNode.getNodes().size() == 0){
                        treeNode.setNodes(null);
                    }
                    tree.add(treeNode);
                }
            }
        }else{
            treeNode.setHref("/cipher/menu/list");
        }
        return tree;
    }
    @Override
    public MenuForm queryMenuForm(int id) {
        return resMenuMapper.queryMenuForm(id);
    }

    @Override
    public List<RoleMenu> selectRoleMenuList(int id) {
        return resMenuMapper.selectRoleMenuList(id);
    }



    @Override
    public List<MenuInfoDomain> getAllMenuListNew() {
        List<MenuInfoDomain> rootList = resMenuMapper.selectAllRootNew();
        List<MenuInfoDomain> menuList = resMenuMapper.selectAllChildNew();
        List<MenuInfoDomain> listTree = new ArrayList<MenuInfoDomain>();
        constructTreeNewV2(listTree,0,rootList, menuList);
        return  listTree;
    }


    public List<MenuInfoDomain> constructTreeNewV2(List<MenuInfoDomain> tree, int parent, List<MenuInfoDomain> rootList, List<MenuInfoDomain> menuList ){
        MenuInfoDomain menuInfoDomain = new MenuInfoDomain();
        if(rootList.size()>0){
            for (MenuInfoDomain menuFormnNew : rootList) {
                //查找根节点
                menuInfoDomain = new MenuInfoDomain(menuFormnNew);
                if (!StringUtils.isEmpty(String.valueOf(menuFormnNew.getParent()))&&menuFormnNew.getParent()== parent) {
                    menuInfoDomain.setResourceId(menuInfoDomain.getResourceId());
                    menuInfoDomain.setResourceName(menuFormnNew.getResourceName());
                    menuInfoDomain.setParent(menuFormnNew.getParent());
                    menuInfoDomain.setDisplaySort(menuFormnNew.getDisplaySort());
                    menuInfoDomain.setDisplayType(menuFormnNew.getDisplayType());
                    menuInfoDomain.setResourceUrl(menuFormnNew.getResourceUrl());
                    menuInfoDomain.setMenuList(constructTreeNewV2(menuInfoDomain.getMenuList(),menuFormnNew.getResourceId(),rootList,menuList));
                    if (menuInfoDomain.getMenuList() == null || menuInfoDomain.getMenuList().size() == 0){
                        menuInfoDomain.setMenuList(null);
                    }
                    tree.add(menuInfoDomain);
                }
            }
        }

        for (MenuInfoDomain menuNew:menuList){
            if (Integer.valueOf(menuNew.getParent()) == parent){
                tree.add(menuNew);
            }
        }
        return tree;
    }

}