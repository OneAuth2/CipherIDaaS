/**
 * api接口列表
 */
export default {
  //  组织结构
  orgList: "/cipher/organization/list?json", // 返回所有树结构
  // 获取组织结构树对应接口如下两种
  "newUserGetlist": "/cipher/newUser/getlist?json", // 获取所有或单个人（accountNumber）
  "userGetlist": "/cipher/newUser/userlist?json", // 根据部门groupId获取所有人，根据selObj.href
  "createDepartment": "/cipher/group/adding", // 增加部门
  "parentDepart": "/cipher/group/common?json", // 上级组织
  "elseDepart": "/cipher/group/commons", // 上级组织,去除当前节点及 当前节点下面的部门组织
  "groupEditEcho": "/cipher/group/editEcho", // 编辑部门回显
  "groupEdit": "/cipher/group/edited", // 编辑部门
  "groupDelete": "/cipher/newUser/groupDelete", // 删除部门或者人，只有部门下没有节点时才能删除
  "setStatus": "/cipher/user/status", // 启用，停用
  "unlock": "/cipher/newUser/unlock", // 解锁
  "resetPW": "/cipher/user/reset/password", // 重置密码
  "delete": "/cipher/newUser/delete", // 删除账户
  "appList": "/cipher/group/appList", // 部门授权-应用列表
  "addApp": "cipher/group/addApp", // 添加应用
  "groupTreeList": "/cipher/group/treeList", // 获取部门
  "securityGroup": "/cipher/team/teamList", // 获取安全组
  "add": "/cipher/newUser/add", // 添加用户
  "getUserDetails": "/cipher/userMsg/modifyUser", // 用户详情
  "updateUserDetails": "/cipher/user/updateNewUser", // 修改用户详情
  "newList": "/cipher/welcomeuser/newList?json", // 首页：无部门用户，被锁定的账号和最近30天未登录账号
  "getStrategy": "/cipher/resetPwd/getCode", // 获取是AD认证，还是本地认证
  "getADComplexRate": "/cipher/auth/management/password/info", // 获取AD认证密码策略复杂度
  "resetAdPwd": "/cipher/resetPwd/resetAdPwd", // 修改AD密码
  // "/cipher/auth/management/password/save",
  getOuTree: "/cipher/ldap/getOuTree", // 获取AD OU树结构
  ldapAdd: "/cipher/ldap/add", // 新增AD连接信息
  statusOk: 200,
  successOk: 1, // 成功状态：删除，解锁，重置密码，删除账户
  returnOk: 0
};
