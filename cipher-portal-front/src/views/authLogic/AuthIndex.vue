<template>
  <div>
    <transition name="fade"
                mode="out-in">
      <router-view />
    </transition>
  </div>
</template>

<script>
import apiLogin from "@/api/authLogic/login.js";
import { mapState, mapMutations } from "vuex";
export default {
  data () {
    return {
      data: {}
    };
  },
  computed: {
    ...mapState({ authLogic: "authLogic" })
  },
  mounted () {
    this.init();
  },
  methods: {
    /**
     * 配置标签的信息，ico和title
     * @param {*Object 包含ico图标和title} icon
     * @author yezi 2019-09-17
     */
    configHead (icon) {
      var link = document.querySelector("link[rel*='icon']") || document.createElement("link");
      link.type = "image/x-icon";
      link.rel = "shortcut icon";
      link.href = icon.iconfont;
      document.getElementsByTagName("head")[0].appendChild(link);
      let headTitle = document.getElementsByTagName("head")[0].querySelector("title") || document.createElement("title");
      headTitle.innerText = icon.title;
      document.getElementsByTagName("head")[0].appendChild(headTitle);
    },
    init () {
      this.axios({
        method: "post",
        data: {},
        url: apiLogin.getConfigService
      })
        .then(response => {
          if (
            response.data.return_code === 0
          ) {
            let registMode = {
              forget: response.data.forget,
              regist: response.data.regist
            };
            let portalConfig = response.data.portalConfig;
            portalConfig.copyright = response.data.copyright;
            let temp = {
              portalConfig: portalConfig,
              modes: response.data.modes,
              registMode: registMode
            };
            let authLogic = Object.assign({}, this.authLogic, temp);
            this.changeAuthLogic(authLogic);
            this.configHead(response.data.icon);
          } else {
            let errorMsg = response.data.return_msg;
            console.error(errorMsg);
          }
        })
        .catch(function (error) {
          this.axios.error.handlingErrors(error);
        });
    },
    ...mapMutations(["changeAuthLogic"])
  }
};
</script>

<style lang="scss" scoped>
</style>
