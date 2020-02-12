/**
 * api接口列表
 */
export default {
  // 无线认证策略
  wifiauthList: "/cipher/wifiauth/list?json",
  wifiauthUpdate: "/cipher/wifiauth/update",
  // ac设置
  acsetList: "/cipher/acset/list",
  acsetCommon: "/cipher/acset/common?json",
  acsetUpdateNew: "/cipher/acset/updateNew",
  acsetUpdate: "/cipher/acset/update",
  // portal设置
  wifiportalList: "/cipher/wifiportal/list",
  wifiportalUpdatePage: "/cipher/wifiportal/updatePage", // 预览/编辑接口
  wifiportalDelete: "/cipher/wifiportal/delete", // 删除
  // 无线策略
  strategyStaff: "/cipher/wifi/strategy/staff", // 获取员工策略
  staffUpdate: "/cipher/wifi/strategy/staff/update", // 更新员工策略
  strategVisitor: "/cipher/wifi/strategy/visitor", // 获取访客策略
  visitorUpdate: "/cipher/wifi/strategy/visitor/update", // 更新访客策略
  deviceList: "/cipher/staffdevice/list?json", // 获取绑定设备列表
  deviceDelete: "/cipher/staffdevice/delete", // 解除绑定设备列表
  strategyMac: "/cipher/wifi/strategy/mac", // 获取Mac绑定策略
  macUpdate: "/cipher/wifi/strategy/mac/update", // 更新Mac绑定策略
  statusOk: 200,
  successOk: 1,
  returnOk: 0,
  statusText: "OK",
  errorStatus: 2 // 错误状态
};
