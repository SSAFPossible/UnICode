import {
  exhibitBoardregister,
  exhibitBoardList,
  exhibitBoardModify,
  exhibitBoardDelete,
  exhibitBoardLike,
  recruitBoardLike,
  recruitBoardDelete,
  recruitBoardModify,
  recruitBoardList,
  recruitBoardregister,
  writeFirstComment,
  writeSecondComment,
  modifyComment,
  deleteComment,
  getComment,
  getTermsConditions,
  recruitBoardOne,
  exhibitBoardOne,
} from "@/api/project.js";

const projectStore = {
  namespaced: true,
  state: {
    projectList: [],
    exhibitBoard: [],
    recruitList: [],
    recruitBoard: [],
    commentList: [],
    boardStatus: false,
  },
  getters: {},
  mutations: {
    REGIST_EXHIBIT_BOARD() {},
    SET_EXHIBIT_LIST(state, data) {
      state.projectList = data;
      console.log("exhibit_list", data);
    },
    MOD_EXHIBIT_BOARD(state, data) {
      state.exhibitBoard = data;
      console.log("exhibit_list_MOD", data);
    },
    DEL_EXHIBIT_BOARD() {},
    SET_EXHIBIT_LIKE() {},
    REGIST_RECRUIT_BOARD() {},
    SET_RECRUIT_LIST(state, data) {
      state.recruitList = data;
      console.log(data);
    },
    SET_RECRUIT_ONE(state, data) {
      state.recruitBoard = data;
      console.log("RECRUIT", state.recruitBoard);
    },
    MOD_RECRUIT_BOARD() {},
    GET_FILE_DOWN() {},
    GET_COMMENT_LIST(state, data) {
      console.log(data);
      state.commentList = data;
      console.log("COMMENT_LIST", state.commentList);
    },
    DEL_COMMENT() {},
    MOD_COMMENT() {},
    REGIST_SECOND_COMMENT() {},
    REGIST_FIRST_COMMENT() {},
    SET_RECRUIT_LIKE() {},
    DEL_RECRUIT_BOARD() {},
    SET_EXHIBIT_ONE(state, data) {
      console.log(data);
      state.exhibitBoard = data;
    },
    SET_BOARD_STATUS(state, data) {
      state.boardStatus = data;
    },
  },
  actions: {
    async exhibitBoardRegist({ commit }, params) {
      await exhibitBoardregister(
        params,
        ({ data }) => {
          commit("REGIST_EXHIBIT_BOARD", data);
          alert("게시글이 등록되었습니다.");
        },
        (error) => {
          console.log(error);
          alert("게시글 등록에 실패하였습니다.");
        }
      );
    },
    async getExhibitBoard({ commit }, params) {
      console.log(params);
      await exhibitBoardList(
        params,
        ({ data }) => {
          commit("SET_EXHIBIT_LIST", data);
        },
        (error) => {
          console.log(error);
          alert("게시글을 가져오는데 실패했습니다.");
        }
      );
    },
    async getExhibitBoardOne({ commit }, params) {
      await exhibitBoardOne(
        params,
        ({ data }) => {
          commit("SET_EXHIBIT_ONE", data);
        },
        (error) => {
          console.log(error);
          alert("게시글을 가져오는데 실패했습니다.");
        }
      );
    },
    async modExhibitBoard({ commit }, data) {
      console.log("MOD", data);
      await exhibitBoardModify(
        data,
        ({ data }) => {
          commit("MOD_EXHIBIT_BOARD", data);
        },
        (error) => {
          console.log(error);
          alert("게시글 등록에 실패하였습니다.");
        }
      );
    },
    async delExhibitBoard({ commit }, data) {
      console.log(data);
      await exhibitBoardDelete(
        data,
        ({ data }) => {
          commit("DEL_EXHIBIT_BOARD", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async setExhibitLike({ commit }, data) {
      await exhibitBoardLike(
        data,
        ({ data }) => {
          commit("SET_EXHIBIT_LIKE", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async recruitBoardRegist({ commit }, params) {
      await recruitBoardregister(
        params,
        ({ data }) => {
          commit("REGIST_RECRUIT_BOARD", data);
          alert("게시글이 등록되었습니다.");
        },
        (error) => {
          console.log(error);
          alert("게시글 등록에 실패하였습니다.");
        }
      );
    },
    async getRecruitBoard({ commit }, params) {
      console.log(params);
      await recruitBoardList(
        params,
        ({ data }) => {
          commit("SET_RECRUIT_LIST", data);
        },
        (error) => {
          console.log(error);
          alert("게시글을 가져오는데 실패했습니다.");
        }
      );
    },
    async getRecruitBoardOne({ commit }, params) {
      console.log(params);
      await recruitBoardOne(
        params,
        ({ data }) => {
          commit("SET_RECRUIT_ONE", data);
        },
        (error) => {
          console.log(error);
          alert("게시글을 가져오는데 실패했습니다.");
        }
      );
    },
    async modRecruitBoard({ commit }, data) {
      console.log("MOD", data);
      await recruitBoardModify(
        data,
        ({ data }) => {
          commit("MOD_RECRUIT_BOARD", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async delRecruitBoard({ commit }, data) {
      console.log(data);
      await recruitBoardDelete(
        data,
        ({ data }) => {
          commit("DEL_RECRUIT_BOARD", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async setRecruitLike({ commit }, data) {
      await recruitBoardLike(
        data,
        ({ data }) => {
          commit("SET_RECRUIT_LIKE", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async registFirstComment({ commit }, data) {
      console.log("registFirstComment", data);
      await writeFirstComment(
        data,
        ({ data }) => {
          commit("REGIST_FIRST_COMMENT", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async registSecondComment({ commit }, data) {
      await writeSecondComment(
        data,
        ({ data }) => {
          commit("REGIST_SECOND_COMMENT", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async modComment({ commit }, data) {
      console.log("store", data);
      await modifyComment(
        data,
        ({ data }) => {
          commit("MOD_COMMENT", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async delComment({ commit }, data) {
      console.log(data);
      await deleteComment(
        data,
        ({ data }) => {
          commit("DEL_COMMENT", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async getCommentList({ commit }, data) {
      await getComment(
        data,
        ({ data }) => {
          commit("GET_COMMENT_LIST", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    getFileDown: ({ commit }, data) => {
      getTermsConditions(
        data,
        ({ data }) => {
          commit("GET_FILE_DOWN", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
};
export default projectStore;
