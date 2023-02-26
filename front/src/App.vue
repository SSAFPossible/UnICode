<template>
  <div id="app">
    <!-- <nav><router-link to="/">Home</router-link> |</nav> -->
    <router-view class="white" v-if="!boardStatus" />
    <router-view class="black" v-else />
  </div>
</template>

<script>
import { mapState } from "vuex";
const projectStore = "projectStore";
export default {
  computed: {
    ...mapState(projectStore, ["boardStatus"]),
  },
};
</script>

<style lang="scss" scoped>
body,
html,
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  display: flex;
  flex-direction: column;
}
.black {
  height: 200vh;
  background-color: #202020;
  color: white;
}
</style>
