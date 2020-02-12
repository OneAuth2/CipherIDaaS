const setNewPassWord = "/authLogic/setNewPassWord";
const waitReview = "/authLogic/newUser/waitReview";
/**
 * cipher，dabai，dingding扫码方式获取跳转路径
 * @param {*3种扫码方式绑定字段} isBound
 * @param {*登录信息} loginInfo
 * @param {*二次认证信息} secondLoginInfo
 */
let getScanSkipPath = function (isBound, loginInfo, secondLoginInfo) {
  let path = "";
  if (isBound) {
    // 没有绑定
    path = "/authLogic/newUser/authGetInfo";
  } else {
    // 绑定
    path = this.getSkipPath(loginInfo, secondLoginInfo);
  }
  return path;
};
/**
 * 用户名密码，手机随机码，top码以及3种扫码绑定过的用户，可通过此接口获取跳转路径
 * @param {*登录信息} loginInfo
 * @param {*二次认证信息} secondLoginInfo
 */
let getSkipPath = function (loginInfo, secondLoginInfo) {
  let path = "";
  if (loginInfo.infoCollection && loginInfo.infoCollectionMail) {
    // 不需要进行信息采集
    if (loginInfo.secondLogin || secondLoginInfo === null) {
      // 不需要二次认证
      if (loginInfo.updatePwd === 0 && loginInfo.firstLogin === 0) {
        // 需要修改密码
        path = setNewPassWord;
      } else {
        // 不需要修改密码
        path = "/loginedShow";
      }
    } else {
      if (secondLoginInfo.switches === 0) {
        // 并行二次登录并是账号密码登录
        path = skipSecondAuthPath(loginInfo, secondLoginInfo, false);
      } else {
        // 串行二次认证
        path = skipNextPage(loginInfo, secondLoginInfo, 1);
      }
    }
  } else {
    // 绑定的用户才需要信息采集
    if (loginInfo.infoCollection === 0) {
      // 需要进行手机信息采集
      path = "/authLogic/oldUser/bindPhone";
    } else {
      // 需要进行邮箱信息采集
      path = "/authLogic/oldUser/bindMail";
    }
  }
  return path;
};
/**
 * 并行二次认证跳转到下一步
 * @param {*登录信息} loginInfo
 * @param {*二次认证信息} secondLoginInfo
 * @param {*是否已过二次认证} isPassSecondAuth
 */
let skipSecondAuthPath = function (
  loginInfo,
  secondLoginInfo,
  isPassSecondAuth
) {
  let skipPath = "";
  // 至少开启了一样二次认证
  let twoAuth =
    secondLoginInfo.twoAuthDd === 0 ||
    secondLoginInfo.twoAuthDingPush === 0 ||
    secondLoginInfo.twoAuthDt === 0 ||
    secondLoginInfo.twoAuthWx === 0 ||
    secondLoginInfo.twoAuthNum === 0;
  if (twoAuth && !isPassSecondAuth) {
    skipPath = "/authLogic/oldUser/twoAuth";
  } else if (loginInfo.updatePwd === 0 && loginInfo.firstLogin === 0) {
    // 需要修改密码
    skipPath = setNewPassWord;
  } else {
    // 不需要修改密码
    skipPath = "/loginedShow";
  }
  return skipPath;
};
/**
 * 串行二次认证跳转到下一步
 * @param {*登录信息} loginInfo
 * @param {*二次认证信息} secondLoginInfo
 * @param {*二次认证进行到第几步} stepNum
 * return {*跳转路径} skipPath
 */
let skipNextPage = function (loginInfo, secondLoginInfo, stepNum) {
  let skipPath = "";
  let basePath = "/authLogic/oldUser/";
  if (secondLoginInfo.twoAuthDd === 0 && stepNum <= 1) {
    // 1.钉钉扫码
    skipPath = basePath + "twoAuthDd";
  } else if (secondLoginInfo.twoAuthDingPush === 0 && stepNum <= 2) {
    // 2.钉钉push认证
    skipPath = basePath + "twoAuthDingPush";
  } else if (secondLoginInfo.twoAuthDb === 0 && stepNum <= 3) {
    // 2.大白扫码-微信扫码
    skipPath = basePath + "twoAuthDb";
  } else if (secondLoginInfo.twoAuthWx === 0 && stepNum <= 4) {
    // 3.企业微信扫码
    skipPath = basePath + "twoAuthWx";
  } else if (secondLoginInfo.twoAuthDt === 0 && stepNum <= 5) {
    // 4.动态验证码
    skipPath = basePath + "twoAuthDt";
  } else if (secondLoginInfo.twoAuthNum === 0 && stepNum <= 6) {
    // 5.手机验证码
    skipPath = basePath + "twoAuthNum";
  } else if (secondLoginInfo.twoAuthMail === 0 && stepNum <= 7) {
    // 6.邮箱验证码
    skipPath = basePath + "twoAuthMail";
  } else if (loginInfo.updatePwd === 0 && loginInfo.firstLogin === 0) {
    // 需要修改密码
    skipPath = setNewPassWord;
  } else {
    // 不需要修改密码
    skipPath = "/loginedShow";
  }
  return skipPath;
};
/**
 * 忘记密码跳转下一步
 * @param {*忘记密码流程信息} forgetPwdFlow
 * @param {*忘记密码进行到第几步} stepNum
 */
let fogetPWSkipNextPage = function (forgetPwdFlow, stepNum) {
  let skipPath = "";
  let basePath = "/authLogic/forgetPW/";
  if (forgetPwdFlow.isOpenNumForget === 0 && stepNum <= 1) {
    // 1.手机验证码验证
    skipPath = basePath + "twoAuthNum";
  } else if (forgetPwdFlow.isOpenMailForget === 0 && stepNum <= 2) {
    // 2.邮箱验证码验证
    skipPath = basePath + "twoAuthMail";
  } else if (forgetPwdFlow.twoAuthForgetDd === 0 && stepNum <= 3) {
    // 3.钉钉扫码
    skipPath = basePath + "twoAuthDd";
  } else if (forgetPwdFlow.twoAuthForgetDb === 0 && stepNum <= 4) {
    // 4.大白扫码验证
    skipPath = basePath + "twoAuthDb";
  } else if (forgetPwdFlow.twoAuthForgetWx === 0 && stepNum <= 5) {
    // 5.微信扫码验证
    skipPath = basePath + "twoAuthWx";
  } else {
    // 需要修改密码
    skipPath = "/authLogic/forgetPW/setNewPassWord";
  }
  return skipPath;
};
/**
 * 绑定跳转下一步
 * @param {*绑定流程信息} bindFlow
 * @param {*绑定进行到第几步} stepNum
 */
let bindSkipNextPage = function (bindFlow, stepNum) {
  let skipPath = "";
  let basePath = "/authLogic/newUser/";
  if (bindFlow.isOpenNumBind === 0 && stepNum <= 1) {
    // 1.手机验证码验证
    skipPath = basePath + "phoneVerify";
  } else if (bindFlow.isOpenMailBind === 0 && stepNum <= 2) {
    // 2.邮箱验证码验证
    skipPath = basePath + "mailVerify";
  } else {
    // 等待审核状态
    skipPath = waitReview;
  }
  return skipPath;
};
export default {
  getSkipPath,
  skipNextPage,
  skipSecondAuthPath,
  getScanSkipPath,
  fogetPWSkipNextPage,
  bindSkipNextPage
};
