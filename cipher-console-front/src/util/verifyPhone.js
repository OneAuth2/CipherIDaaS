export default {
  // 判断是否为手机号
  isPoneAvailable: function (phone) {
    var myreg = /^[1][3,4,5,7,8][0-9]{9}$/;
    if (!myreg.test(phone)) {
      return false;
    } else {
      return true;
    }
  },
  // 判断是否为电话号码
  isTelAvailable: function (tel) {
    // eslint-disable-next-line
    var myreg = /^(([0\+]\d{2,3}-)?(0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/
    if (!myreg.test(tel)) {
      return false;
    } else {
      return true;
    }
  }
};
