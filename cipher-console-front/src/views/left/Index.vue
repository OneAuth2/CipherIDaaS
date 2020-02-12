<template>
  <div>
    <transition name="fade" mode="out-in">
      <component v-bind:is="toggleCollapse" :data="data"></component>
    </transition>
  </div>
</template>

<script>
// import NavBarCollapse from "./NavBarCollapse";
import NavBarExpanded from "./NavBarExpanded";

export default {
  name: "Index",
  components: {
    // NavBarCollapse,
    NavBarExpanded
  },
  props: {
    toggleCollapse: {
      type: String,
      default: "NavBarExpanded"
    },
    data: {
      type: Array,
      default: () => []
    }
  }
};
</script>
<style scoped>

</style>
