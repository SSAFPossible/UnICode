import Vue from "vue";
import Vuex from "vuex";
import projectStore from "@/store/modules/projectStore.js";
import boardStore from "@/store/modules/boardStore.js";
import userStore from "@/store/modules/userStore.js";
import adminStore from "@/store/modules/adminStore.js";
import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    boardStore,
    projectStore,
    userStore,
    adminStore,
  },
  plugins: [
    createPersistedState({
      // 브라우저 종료시 제거하기 위해 localStorage가 아닌 sessionStorage로 변경. (default: localStorage)
      storage: sessionStorage,
    }),
  ],
});
