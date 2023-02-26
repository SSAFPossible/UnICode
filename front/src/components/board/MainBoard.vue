<template>
  <div class="container">
    <div class="boj-list">
      <BojProblem
        :problem="problem"
        v-for="(problem, index) in algoList"
        :key="index"
      ></BojProblem>
    </div>
    <div class="ranking">
      <div class="title">
        오늘의 랭킹<img src="@/assets/rankingCrown.svg" alt="" />
      </div>
      <div class="line"></div>
      <div class="ranking-list">
        <RankingList></RankingList>
      </div>
    </div>

    <div class="notice">
      <div class="title">
        <router-link :to="{ name: 'notice' }">공지사항</router-link>
      </div>
      <div class="line"></div>
      <div class="notice-list">
        <BoardList type="notice"></BoardList>
      </div>
    </div>

    <div class="board">
      <div class="question">
        <div class="title">
          <router-link :to="{ name: 'question' }">질문 게시판</router-link>
        </div>
        <div class="line"></div>
        <div class="question-list">
          <BoardList type="question"></BoardList>
        </div>
      </div>
      <div class="general">
        <div class="title">
          <router-link :to="{ name: 'general' }">자유 게시판</router-link>
        </div>
        <div class="line"></div>
        <div class="general-list">
          <BoardList type="general"></BoardList>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import BojProblem from "@/components/board/BojProblem.vue";
import RankingList from "@/components/board/RankingList.vue";
import BoardList from "@/components/board/BoardList.vue";
import { mapActions, mapMutations, mapState } from "vuex";
const boardStore = "boardStore";
const projectStore = "projectStore";
export default {
  name: "MainBoard",
  data() {
    return {
      inputs: {
        tag: [],
        category: [],
        sortLike: false,
      },
    };
  },
  components: {
    BojProblem,
    RankingList,
    BoardList,
  },
  methods: {
    ...mapActions(boardStore, [
      "getFreeBoardList",
      "getNoticeBoardList",
      "getQuestionBoardList",
      "getMainAlgo",
    ]),
    ...mapMutations(projectStore, ["SET_BOARD_STATUS"]),
  },
  created() {
    this.SET_BOARD_STATUS(false);
    this.getMainAlgo();
    this.getFreeBoardList();
    this.getNoticeBoardList();
    this.getQuestionBoardList(this.inputs);
  },
  computed: {
    ...mapState(boardStore, ["algoList"]),
  },
};
</script>

<style scoped>
.container {
  margin-top: 60px;
  font-size: 14px;
}

.container a {
  text-decoration: none;
  color: black;
}

.container .boj-list {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 80px;
}

.container .title {
  float: left;
  display: flex;
  align-items: center;
  margin-bottom: 10px;
  font-size: 16px;
}
.container .title {
  font-weight: bold;
}
.container .title > img {
  margin-left: 10px;
}

.container .ranking {
  margin-bottom: 80px;
}
.container .ranking .ranking-list {
  display: flex;
  align-items: center;
  justify-content: space-between;
}
.container .line {
  height: 1px;
  background: linear-gradient(270deg, #6465a6 -2.63%, #6465a6 99.99%);
  clear: both;
}

.container .notice {
  margin-bottom: 80px;
}

.container .board {
  display: flex;
  justify-content: space-between;
}

.container .board .question {
  width: 45%;
}
.container .board .general {
  width: 45%;
}
</style>
