// 初始化获取钉钉二维码
let initGetDingding = function (pathname, store, dingTalkData) {
  let url = dingTalkData.dingTalkCallBackUrl + pathname;
  let urlT =
    url +
    "?scanWay=dingding&dingdingRedirect=true&userId=" +
    store.state.authLogic.userInfo.userId;
  var hanndleMessage = event => {
    var origin = event.origin;
    if (origin === "https://login.dingtalk.com") {
      // 判断是否来自ddLogin扫码事件。
      var loginTmpCode = event.data; // 拿到loginTmpCode后就可以在这里构造跳转链接进行跳转了
      localStorage.setItem("authLogic", JSON.stringify(store.state)); // 此次保存localStorage
      var href =
        "https://oapi.dingtalk.com/connect/oauth2/sns_authorize?appid=" +
        dingTalkData.dingTalkScanId +
        "&response_type=code&scope=snsapi_login&state=STATE&redirect_uri=" +
        url +
        "&loginTmpCode=" +
        loginTmpCode;
      window.location.href = href;
    }
  };
  if (typeof window.addEventListener !== "undefined") {
    window.addEventListener("message", hanndleMessage, false);
  } else if (typeof window.attachEvent !== "undefined") {
    window.attachEvent("onmessage", hanndleMessage);
  }
  var encodeUrl = encodeURIComponent(urlT);
  var goto = encodeURIComponent(
    "https://oapi.dingtalk.com/connect/oauth2/sns_authorize?appid=" +
      dingTalkData.dingTalkScanId +
      "&response_type=code&scope=snsapi_login&state=STATE&redirect_uri=" +
      encodeUrl
  );
  // eslint-disable-next-line no-undef
  window.DDLogin({
    id: "dingding",
    goto: goto,
    style: "border:none;background-color:#FFFFFF;",
    width: "300",
    height: "300"
  });
};
export default { initGetDingding };
