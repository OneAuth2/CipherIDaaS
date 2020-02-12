// 初始化获取企业微信二维码
/* href 为以下css base64加密内容
  .impowerBox .qrcode {width: 200px;}
  .impowerBox .title {display: none;}
  .status_icon {display: none}
  .result{top:70px}
  .wrp_code_rl_btn{
    border:none;
    background:#81CFE6;
    border-radius:4px;
    color:#fff;
    height:32px;
    line-height:32px;
  }
  #wx_default_tip p:nth-child(2){display:none;}
*/
let initGetWx = function (pathname, store, qywxConfig) {
  let url = qywxConfig.redirect_uri + pathname;
  let urlT =
    url +
    "?scanWay=wx&wxRedirect=true&userId=" +
    store.state.authLogic.userInfo.userId;
  var encodeUrl = encodeURIComponent(urlT);
  window.WwLogin({
    id: "wx_qrcode",
    appid: qywxConfig.appid,
    agentid: qywxConfig.agentid,
    redirect_uri: `${encodeUrl}`,
    href:
      "data:text/css;base64,IC5pbXBvd2VyQm94IC5xcmNvZGUge3dpZHRoOiAyMDBweDt9DQogIC5pbXBvd2VyQm94IC50aXRsZSB7ZGlzcGxheTogbm9uZTt9DQogIC5zdGF0dXNfaWNvbiB7ZGlzcGxheTogbm9uZX0NCiAgLnJlc3VsdHt0b3A6NzBweH0NCiAgLndycF9jb2RlX3JsX2J0bnsNCiAgICBib3JkZXI6bm9uZTsNCiAgICBiYWNrZ3JvdW5kOiM4MUNGRTY7DQogICAgYm9yZGVyLXJhZGl1czo0cHg7DQogICAgY29sb3I6I2ZmZjsNCiAgICBoZWlnaHQ6MzJweDsNCiAgICBsaW5lLWhlaWdodDozMnB4Ow0KICB9DQogICN3eF9kZWZhdWx0X3RpcCBwOm50aC1jaGlsZCgyKXtkaXNwbGF5Om5vbmU7fQ=="
  });
};
export default { initGetWx };
