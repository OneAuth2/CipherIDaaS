<template>
  <transition name="fade"
              mode="out-in">
    <router-view />
  </transition>
</template>
<script>
import api from "@/api/authLogic/index.js";
import { mapState, mapMutations } from "vuex";
export default {
  created () {
    this.init();
  },
  computed: {
    ...mapState({ authLogic: "authLogic" })
  },
  methods: {
    init () {
      this.axios({
        method: "post",
        data: {},
        url: api.registFlow
      })
        .then(response => {
          if (
            response.data.return_code === 0
          ) {
            let authLogic = Object.assign({}, this.authLogic, { registFlow: response.data.registFlow });
            this.changeAuthLogic(authLogic);
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

<style lang="less" scoped>
</style>
