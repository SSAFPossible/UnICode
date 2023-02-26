<template>
  <div>
    <header-nav></header-nav>
    <div class="container">
      <div class="title">인원 모집</div>
      <div class="options"><button @click="moveTo">글 작성</button></div>
      <div class="line"></div>
      <BoardList></BoardList>
    </div>
  </div>
</template>

<script>
import { mapActions } from "vuex";
import BoardList from "@/components/project/RecruitList.vue";
import HeaderNav from "../HeaderNav.vue";
const projectStore = "projectStore";
export default {
  components: {
    BoardList,
    HeaderNav,
  },
  data() {
    return {
      inputs: {
        name: [],
      },
    };
  },
  methods: {
    ...mapActions(projectStore, ["getRecruitBoard"]),
    async getRecruit(data) {
      await this.getRecruitBoard(data);
    },
    moveTo() {
      this.$router.push("/writerecruit");
    },
  },
  created() {
    console.log("created Reruit Board", this.inputs);
    this.getRecruit(this.inputs);
  },
};
</script>

<style scoped>
.options button {
  /* visibility:hidden; */
  margin-right: 30px;
  margin-bottom: 15px;
  float: right;
  border: none;
  background-color: transparent;
  color: white;
}
.line {
  clear: both;
  height: 1px;
  background: linear-gradient(270deg, #6465a6 -2.63%, #6465a6 99.99%);
}

.container .title {
  font-weight: 400;
  font-size: 30px;
  line-height: 36px;
  margin-bottom: 20px;
}
</style>
