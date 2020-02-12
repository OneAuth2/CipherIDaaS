/**
 * 处理错误
 * @param {*错误对象} error
 * @param {*} _this
 */
let handlingErrors = function (error, _this) {
  if (error.response) {
    // The request was made and the server responded with a status code
    // that falls out of the range of 2xx
    console.log(error.response);
    _this = _this || this;
    _this.$Message.error("服务器端网络异常，请稍后重试");
  } else if (error.request) {
    // The request was made but no response was received
    // `error.request` is an instance of XMLHttpRequest in the browser and an instance of
    // http.ClientRequest in node.js
    console.log(error.request);
    _this.$Message.error("客户端网络异常，请稍后重试");
  } else {
    // Something happened in setting up the request that triggered an Error
    console.log("Error", error.message);
    _this.$Message.error(error.message);
  }
  console.log(error);
};

export default {
  handlingErrors
};
