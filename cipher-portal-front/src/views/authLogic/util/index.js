import TYPE from "@/views/authLogic/constantType/index.js";
let logoItems = function (modes) {
  let logoItems = [
    { logo: TYPE.CIPHER, index: 1 },
    { logo: TYPE.DABAI, index: 2 },
    { logo: TYPE.DINGDING, index: 3 },
    { logo: TYPE.WX, index: 4 }
  ];
  logoItems.forEach(function (elem, index, arr) {
    switch (elem.logo) {
      case TYPE.CIPHER:
        elem.show = !modes.otherAuthDt;
        // elem.show = !modes.otherAuthSf || !modes.otherAuthDt;
        break;
      case TYPE.DABAI:
        elem.show = !modes.otherAuthDb;
        break;
      case TYPE.DINGDING:
        elem.show = !modes.otherAuthDd;
        break;
      case TYPE.WX:
        elem.show = !modes.otherAuthWx;
        break;
      default:
        break;
    }
    return elem.show;
  });
  return logoItems;
};

export default {
  logoItems
};
