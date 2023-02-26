import {
  freeBoardregister,
  noticeBoardregister,
  questionBoardregister,
  freeBoardList,
  noticeBoardList,
  questionBoardList,
  freeBoardOne,
  noticeBoardOne,
  questionBoardOne,
  freeBoardModify,
  studyBoardModify,
  noticeBoardModify,
  freeBoardDelete,
  noticeBoardDelete,
  studyBoardDelete,
  studyBoardLike,
  mainBoardRank,
  mainBoardAlgo,
} from "@/api/board.js";

const boardStore = {
  namespaced: true,
  state: {
    boardList: [],
    freeBoard: [],
    noticeBoard: [],
    questionBoard: [],
    boardOne: [],
    rankList: [],
    algoList: [],
  },
  getters: {},
  mutations: {
    REGIST_FREE_BOARD() {},
    REGIST_NOTICE_BOARD() {},
    REGIST_QUESTION_BOARD() {},
    SET_BOARD_LIST(state, data) {
      state.boardList = data;
      // console.log("BOARD List", state.boardList);
    },
    SET_FREE_LIST(state, data) {
      state.freeBoard = data;
      console.log("FREE List", state.freeBoard);
    },
    SET_NOTICE_LIST(state, data) {
      state.noticeBoard = data;
      console.log("Notice List", state.noticeBoard);
    },
    SET_QUESTION_LIST(state, data) {
      state.questionBoard = data;
      console.log("Question List", state.questionBoard);
    },
    SET_BOARD_ONE(state, data) {
      state.boardOne = data;
      console.log(state.boardOne);
    },
    MOD_FREE_BOARD() {
      console.log("MOD");
    },
    MOD_STUDY_BOARD() {},
    MOD_NOTICE_BOARD() {},
    DEL_FREE_BOARD() {},
    DEL_STUDY_BOARD() {},
    DEL_NOTICE_BOARD() {},
    SET_STUDY_LIKE() {},
    SET_MAIN_RANK(state, data) {
      state.rankList = data;
      console.log(data);
    },
    SET_MAIN_ALGO(state, data) {
      state.algoList = data;
      console.log("ALGO", data);
    },
  },
  actions: {
    async freeBoardRegist({ commit }, params) {
      await freeBoardregister(
        params,
        ({ data }) => {
          commit("REGIST_FREE_BOARD", data);
          alert("게시글이 등록되었습니다.");
        },
        (error) => {
          console.log(error);
          alert("게시글 등록에 실패하였습니다.");
        }
      );
    },
    async noticeBoardRegist({ commit }, params) {
      await noticeBoardregister(
        params,
        ({ data }) => {
          commit("REGIST_NOTICE_BOARD", data);
          alert("게시글이 등록되었습니다.");
        },
        (error) => {
          console.log(error);
          alert("게시글 등록에 실패하였습니다.");
        }
      );
    },
    async questionBoardRegist({ commit }, params) {
      await questionBoardregister(
        params,
        ({ data }) => {
          commit("REGIST_QUESTION_BOARD", data);
          alert("게시글이 등록되었습니다.");
        },
        (error) => {
          console.log(error);
          alert("게시글 등록에 실패하였습니다.");
        }
      );
    },
    getFreeBoardList: ({ commit }) => {
      console.log("FreeBoard");
      freeBoardList(
        ({ data }) => {
          commit("SET_BOARD_LIST", data);
          commit("SET_FREE_LIST", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    getNoticeBoardList: ({ commit }) => {
      console.log("NoticeBoard");
      noticeBoardList(
        ({ data }) => {
          commit("SET_BOARD_LIST", data);
          commit("SET_NOTICE_LIST", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async getQuestionBoardList({ commit }, data) {
      console.log("QuestionBoard");
      const params = {
        tag: data.tag,
        category: data.category,
        uid: data.uid,
        sortLike: data.sortLike,
      };
      console.log(params);
      await questionBoardList(
        params,
        ({ data }) => {
          commit("SET_BOARD_LIST", data);
          commit("SET_QUESTION_LIST", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    getFreeBoardOne: ({ commit }, data) => {
      freeBoardOne(
        data,
        ({ data }) => {
          commit("SET_BOARD_ONE", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    getNoticeBoardOne: ({ commit }, data) => {
      noticeBoardOne(
        data,
        ({ data }) => {
          commit("SET_BOARD_ONE", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    getQuestionBoardOne: ({ commit }, data) => {
      questionBoardOne(
        data,
        ({ data }) => {
          commit("SET_BOARD_ONE", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async modFreeBoard({ commit }, data) {
      console.log(data);
      await freeBoardModify(
        data,
        ({ data }) => {
          commit("MOD_FREE_BOARD", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async modNoticeBoard({ commit }, data) {
      console.log(data);
      await noticeBoardModify(
        data,
        ({ data }) => {
          commit("MOD_NOTICE_BOARD", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async modStudyBoard({ commit }, data) {
      console.log("study", data);
      await studyBoardModify(
        data,
        ({ data }) => {
          commit("MOD_STUDY_BOARD", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    delFreeBoard: ({ commit }, data) => {
      console.log(data);
      freeBoardDelete(
        data,
        ({ data }) => {
          commit("DEL_FREE_BOARD", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    delNoticeBoard: ({ commit }, data) => {
      console.log(data);
      noticeBoardDelete(
        data,
        ({ data }) => {
          commit("DEL_NOTICE_BOARD", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    delStudyBoard: ({ commit }, data) => {
      console.log(data);
      studyBoardDelete(
        data,
        ({ data }) => {
          commit("DEL_STUDY_BOARD", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    setStudyLike: ({ commit }, data) => {
      studyBoardLike(
        data,
        ({ data }) => {
          commit("SET_STUDY_LIKE", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async getMainRank({ commit }) {
      await mainBoardRank(
        ({ data }) => {
          commit("SET_MAIN_RANK", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async getMainAlgo({ commit }) {
      await mainBoardAlgo(
        ({ data }) => {
          commit("SET_MAIN_ALGO", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
};
export default boardStore;
