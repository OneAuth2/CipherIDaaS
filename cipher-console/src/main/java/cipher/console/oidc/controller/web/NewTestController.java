package cipher.console.oidc.controller.web;

public class NewTestController {

    //package cipher.console.oidc.controller;
//
//import cipher.console.oidc.domain.zhonghe.DepTree;
//import cipher.console.oidc.domain.zhonghe.ZhongHeDeparment;
//import cipher.console.oidc.domain.zhonghe.ZhongHeGangWei;
//import cipher.console.oidc.domain.zhonghe.ZhongHeUser;
//import cipher.console.oidc.service.ZhongheDeparentmentService;
//import cipher.console.oidc.service.ZhongheGangweiService;
//import cipher.console.oidc.service.ZhongheUserService;
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.ListOperations;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.ValueOperations;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.lang.reflect.Type;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @Author: zt
// * @Date: 2018/9/25 17:45
// */
//@Controller
//@RequestMapping(value = "/cipher/zhonghe")
//public class TreeController {
//
//    @Autowired
//    private RedisTemplate<String, String> redisTemplate;
//
//
//
//    @Autowired
//    private ZhongheDeparentmentService zhongheDeparentmentService;
//
//    @Autowired
//    private ZhongheUserService zhongheUserService;
//
//    @Autowired
//    private ZhongheGangweiService zhongheGangweiService;
//
//    @RequestMapping(value = "/insert")
//    @ResponseBody
//    public void insertSql(HttpServletRequest request, HttpServletResponse response) {
//
//        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
//
//        String department = valueOperations.get("department");
//        String gangwei = valueOperations.get("gangwei");
//        String user = valueOperations.get("user");
//        Gson gson = new Gson();
//
//
//        /**********************************解析部门列表*************************************************/
//        List<ZhongHeDeparment> zhongHeDeparmentList = new ArrayList<>();
//        Type type = new TypeToken<ArrayList<ZhongHeDeparment>>() {
//        }.getType();
//        zhongHeDeparmentList = gson.fromJson(department, type);
////        zhongheDeparentmentService.insertDepartList(zhongHeDeparmentList);
////        for (ZhongHeDeparment zhongHeDeparment : zhongHeDeparmentList) {
////            System.err.println(zhongHeDeparment.getName());
////        }
//
//
//        /**********************************解析岗位列表*************************************************/
//        List<ZhongHeGangWei> gangWeiList = new ArrayList<>();
//        Type type1 = new TypeToken<ArrayList<ZhongHeGangWei>>() {
//        }.getType();
//        gangWeiList = gson.fromJson(gangwei, type1);
//        zhongheGangweiService.insertGangweiList(gangWeiList);
////        for(ZhongHeGangWei gangWei:gangWeiList){
////            System.err.println(gangWei.getName());
////        }
//
//        /**********************************解析岗位列表*************************************************/
//        List<ZhongHeUser> userList = new ArrayList<>();
//        Type type2 = new TypeToken<ArrayList<ZhongHeUser>>() {
//        }.getType();
//        userList = gson.fromJson(user, type2);
////        zhongheUserService.insertUserList(userList);
////        for (ZhongHeUser zhongHeUser : userList) {
////            System.err.println(zhongHeUser.getEmail());
////        }
//    }
//
//
//    @RequestMapping(value = "/tree")
//    public String treeView() {
//        return "zhonghe/tree";
//    }
//
//
//    @RequestMapping(value = "/treeList")
//    @ResponseBody
//    public List<DepTree> constructTree(HttpServletRequest request, HttpServletResponse response) {
//        List<DepTree> trreList = null;
//        List<ZhongHeUser> userList = null;
//        trreList = zhongheDeparentmentService.queryDepTree();
//
//        userList = zhongheUserService.queryAllZhongHeUser();
//
//        List<DepTree> trees = constructTreeV2(trreList, userList);
//        return trees;
//    }
//
//
//    @RequestMapping(value = "/treeUserList")
//    @ResponseBody
//    public List<DepTree> treeListBuilder(HttpServletRequest request, HttpServletResponse response) {
//        List<DepTree> trreList = null;
//        List<ZhongHeUser> userList = null;
//
//        trreList = zhongheDeparentmentService.queryDepTree();
//
//        userList = zhongheUserService.queryAllZhongHeUser();
//
//
//        return constructTree(trreList, userList);
//    }
//
//    /**
//     * 循环拼接树结构
//     *
//     * @param trreList
//     * @return
//     */
//    private List<DepTree> constructTree(List<DepTree> trreList, List<ZhongHeUser> userList) {
//        List<DepTree> trees = new ArrayList<>();
//        for (DepTree dep : trreList) {
//
//            if (dep.getParentPk() == 0) {
//
//                List<ZhongHeUser> depUser = new ArrayList<>();
//                for (ZhongHeUser zhongHeUser : userList) {
//                    if (zhongHeUser.getDepartment() == dep.getPk()) {
//                        zhongHeUser.setText(zhongHeUser.getStaffName());
//                        zhongHeUser.setHref("/userinfo?userPk=" + zhongHeUser.getPk());
//                        depUser.add(zhongHeUser);
//                    }
//                }
//
//                dep.setText(dep.getName());
//                dep.setHref("depInfo?depPk=" + dep.getPk());
//                dep.setUserList(depUser);
//                trees.add(dep);
//            }
//
//            for (DepTree childNode : trreList) {
//                if (childNode.getParentPk() == (dep.getPk())) {
//
//                    List<ZhongHeUser> depUser = new ArrayList<>();
//                    for (ZhongHeUser zhongHeUser : userList) {
//                        if (zhongHeUser.getDepartment() == childNode.getPk()) {
//                            zhongHeUser.setText(zhongHeUser.getStaffName());
//                            zhongHeUser.setHref("/userinfo?userPk=" + zhongHeUser.getPk());
//                            depUser.add(zhongHeUser);
//                        }
//                    }
//                    childNode.setUserList(depUser);
//
//                    if (dep.getNodes() == null) {
//                        dep.setNodes(new ArrayList<>());
//                    }
//                    childNode.setHref("depInfo?depPk=" + childNode.getPk());
//                    childNode.setText(childNode.getName());
//
//                    dep.getNodes().add(childNode);
//                }
//            }
//        }
//        return trees;
//    }
//
//
//    private List<DepTree> constructTreeV2(List<DepTree> trreList, List<ZhongHeUser> userList) {
//        List<DepTree> trees = new ArrayList<>();
//
//        for (DepTree dep : trreList) {
//
//            //查找根节点
//            if (dep.getParentPk() == 0) {
//                List<DepTree> depUser = new ArrayList<>();
//                for (ZhongHeUser zhongHeUser : userList) {
//
//                    //一个用户在多个部门,+","是为了防止部门是个位数的情况
//                    if (zhongHeUser.getDepartments().contains(",") && zhongHeUser.getDepartments().contains(dep.getPk() + ",")) {
//                        DepTree depTree = new DepTree();
//                        depTree.setText(zhongHeUser.getStaffName());
//                        depTree.setHref("/userinfo?userPk=" + zhongHeUser.getPk());
//                        depUser.add(depTree);
//                    }
//                    //一个用户只在一个部门
//                    if (zhongHeUser.getDepartment() == dep.getPk()) {
//                        DepTree depTree = new DepTree();
//                        depTree.setText(zhongHeUser.getStaffName());
//                        depTree.setHref("/userinfo?userPk=" + zhongHeUser.getPk());
//                        depUser.add(depTree);
//                    }
//                }
//                dep.setText(dep.getName());
//                dep.setHref("depInfo?depPk=" + dep.getPk());
//                dep.setNodes(depUser);
//                trees.add(dep);
//            }
//
//            //查找根节点下面的子节点
//            for (DepTree childNode : trreList) {
//                if (childNode.getParentPk() == (dep.getPk())) {
//                    List<DepTree> depUser = new ArrayList<>();
//                    //找到某个部门下所有的用户
//                    for (ZhongHeUser zhongHeUser : userList) {
//
//                        if (zhongHeUser.getDepartments().contains(",") && zhongHeUser.getDepartments().contains(dep.getPk() + ",")) {
//                            DepTree depTree = new DepTree();
//                            depTree.setText(zhongHeUser.getStaffName());
//                            depTree.setHref("/userinfo?userPk=" + zhongHeUser.getPk());
//                            depUser.add(depTree);
//                        }
//
//                        if (zhongHeUser.getDepartment() == childNode.getPk()) {
//                            DepTree depTree = new DepTree();
//                            depTree.setText(zhongHeUser.getStaffName());
//                            depTree.setHref("/userinfo?userPk=" + zhongHeUser.getPk());
//                            depUser.add(depTree);
//                        }
//                    }
//
//                    if (childNode.getNodes() == null) {
//                        childNode.setNodes(new ArrayList<>());
//                    }
//                    childNode.getNodes().addAll(depUser);
//                    if (dep.getNodes() == null) {
//                        dep.setNodes(new ArrayList<>());
//                    }
//                    childNode.setHref("depInfo?depPk=" + childNode.getPk());
//                    childNode.setText(childNode.getName());
//                    dep.getNodes().add(childNode);
//                }
//            }
//        }
//        return trees;
//    }
//
//
//}
//

}
