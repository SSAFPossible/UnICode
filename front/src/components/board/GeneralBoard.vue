<template>
  <div class="container">
    <div class="options"><button @click="moveTo">글 작성</button></div>
    <div class="line"></div>
    <BoardList></BoardList>
  </div>
</template>

<script>
import { mapActions } from "vuex";
import BoardList from "@/components/board/BoardListPage.vue";
const boardStore = "boardStore";
export default {
  components: {
    BoardList,
  },
  methods: {
    ...mapActions(boardStore, ["getFreeBoardList"]),
    getFreeBoard() {
      this.getFreeBoardList();
    },
    moveTo() {
      this.$router.push("/writeFree");
    },
  },
  created() {
    this.getFreeBoard();
  },
};
</script>

<style scoped>
.options button {
  margin-right: 30px;
  margin-bottom: 15px;
  float: right;
  border: none;
  background-color: white;
}
.line {
  height: 1px;
  background: linear-gradient(270deg, #6465a6 -2.63%, #6465a6 99.99%);
  clear: both;
}
</style>
