import {
  algorithmRegister,
  modifyAlgo,
  deleteAlgo,
  getAllAlgo,
  getAlgo,
} from "@/api/admin.js";

const adminStore = {
  namespaced: true,
  state: {
    algoSolved: [],
    algoUnsolved: [],
    algoOne: [],
  },
  mutations: {
    REGIST_ALGO_ONE() {},
    MOD_ALGO_ONE() {},
    DEL_ALGO_ONE() {},
    SET_ALGO_UNSOLVED(state, data) {
      console.log(data);
      state.algoUnsolved = data;
    },
    SET_ALGO_SOLVED(state, data) {
      console.log(data);
      state.algoSolved = data;
    },
    SET_ALGO_ONE(state, data) {
      state.algoOne = data;
    },
  },
  actions: {
    async registAlgorithm({ commit }, data) {
      await algorithmRegister(
        data,
        ({ data }) => {
          commit("REGIST_ALGO_ONE", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async modifyAlgorithm({ commit }, data) {
      await modifyAlgo(
        data,
        ({ data }) => {
          commit("MOD_ALGO_ONE", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async deleteAlgorithm({ commit }, data) {
      await deleteAlgo(
        data,
        ({ data }) => {
          commit("DEL_ALGO_ONE", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async getAlgorithmList({ commit }, data) {
      await getAllAlgo(
        data,
        ({ data }) => {
          if (data[0].solved == 1) {
            commit("SET_ALGO_SOLVED", data);
          } else if (data[0].solved == 0) {
            commit("SET_ALGO_UNSOLVED", data);
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async getAlgorithm({ commit }, data) {
      await getAlgo(
        data,
        ({ data }) => {
          commit("SET_ALGO_ONE", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
};
export default adminStore;
