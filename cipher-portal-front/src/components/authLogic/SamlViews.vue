<template>
  <div v-html="samlhtml">sdadsadsad</div>
</template>

<script>
export default {
  props: {
    samlhtml: {
      required: false
    }
  },
  data () {
    return {
      demohtml: ""
    };
  },
  watch: {
    samlhtml (value) {
      console.log(value + "dsadsads");
      this.demohtml = value;
    }
  }

};
</script>
<style scoped>
</style>
