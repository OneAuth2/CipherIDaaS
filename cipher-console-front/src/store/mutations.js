export default {
  changeToken (state, token) {
    state.token = token;
    try {
      if (token === null) {
        localStorage.removeItem("token");
      } else {
        localStorage.token = token;
      }
    } catch (e) {
      console.log(e);
    }
  },
  changePhoneNumber (state, phoneNumber) {
    state.phoneNumber = phoneNumber;
    try {
      localStorage.phoneNumber = phoneNumber;
    } catch (e) {}
  },
  changeTop (state, toggle) {
    state.top.toggle = toggle;
  },
  changeLeft (state, left) {
    state.left = left;
  },
  changeSelectOSTree (state, selectOSTree) {
    state.selectOSTree = selectOSTree;
  },
  changeTab (state, tab) {
    state.tab = tab;
  },
  changeAppId (state, appId) {
    state.appId = appId;
    try {
      localStorage.appId = appId;
    } catch (e) {}
  },
  changeApplicationInfo (state, applicationInfo) {
    state.applicationInfo = applicationInfo;
    try {
      if (JSON.stringify(applicationInfo) === "{}") {
        localStorage.removeItem("applicationInfo");
      } else {
        localStorage.applicationInfo = JSON.stringify(applicationInfo);
      }
    } catch (e) {
      console.log(e);
    }
  },
  changeBreadCrumb (state, payload) {
    state.breadCrumb = [...payload];
  },
  changeUserInfo (state, userInfo) {
    state.userInfo = userInfo;
  },
  changeHomeOrg (state, homeOrg) {
    state.homeOrg = homeOrg;
  },
  changeGroup (state, group) {
    state.group = group;
  },
  changeActive (state, activeState) {
    state.activeState = activeState;
  },
  changePath (state, path) {
    state.path = path;
  }
};
