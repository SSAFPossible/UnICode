<template>
  <div class="container">
    <div class="options">
      <div class="tag">
        <input
          type="checkbox"
          v-model="inputs.tag"
          value="ques"
          id="ques"
        /><label for="ques"># 질문있어요</label>
        <input
          type="checkbox"
          v-model="inputs.tag"
          value="info"
          id="info"
        /><label for="info"># 알려드려요</label>
        <input
          type="checkbox"
          v-model="inputs.category"
          value="cs"
          id="cs"
        /><label for="cs"># CS</label>
        <input
          type="checkbox"
          v-model="inputs.category"
          value="algo"
          id="algo"
        /><label for="algo"># 알고리즘</label>
        <input
          type="checkbox"
          v-model="inputs.category"
          value="job"
          id="job"
        /><label for="job"># 취업</label>
        <input
          type="checkbox"
          id="my-project"
          v-model="inputs.uid"
          value="mine"
        /><label for="my-project"># 내가 참여한 프로젝트</label>
      </div>
      <div><button class="write-btn" @click="moveTo">글 작성</button></div>
    </div>
    <div class="line"></div>
    <BoardList></BoardList>
  </div>
</template>

<script>
import { mapActions } from "vuex";
import BoardList from "@/components/board/BoardListPage.vue";
const boardStore = "boardStore";
export default {
  data() {
    return {
      inputs: {
        tag: [],
        category: [],
      },
    };
  },
  components: {
    BoardList,
  },
  methods: {
    ...mapActions(boardStore, ["getQuestionBoardList"]),
    getQuestionBoard(inputs) {
      this.getQuestionBoardList(inputs);
    },
    moveTo() {
      this.$router.push("/writeQuestion");
    },
  },
  created() {
    this.getQuestionBoard(this.inputs);
  },
  watch: {
    inputs: {
      handler: function (val) {
        this.getQuestionBoard(val);
      },
      deep: true,
    },
  },
};
</script>
<style scoped>
.options .tag {
  float: left;
}
.options .write-btn {
  margin-right: 30px;
  margin-bottom: 15px;
  float: right;
  border: none;
  background-color: white;
}
.line {
  clear: both;
  height: 1px;
  background: linear-gradient(270deg, #6465a6 -2.63%, #6465a6 99.99%);
}

input[id="my-project"] + label {
  width: fit-content;
  height: 24px;
  line-height: 24px;
  padding: 0 30px;
  background: rgba(194, 150, 195, 0.5);
  border-radius: 12px;
  margin: 0px 8px 12px 8px;
  cursor: pointer;
}
input[id="my-project"]:checked + label {
  box-shadow: inset 0px 4px 4px rgba(0, 0, 0, 0.25);
}
input[id="my-project"] {
  display: none;
}

input[id="ques"] + label {
  width: fit-content;
  height: 24px;
  line-height: 24px;
  padding: 0 30px;
  background: rgba(100, 101, 166, 0.5);
  border-radius: 12px;
  margin: 0px 8px 12px 8px;
  cursor: pointer;
}
input[id="ques"]:checked + label {
  box-shadow: inset 0px 4px 4px rgba(0, 0, 0, 0.25);
}
input[id="ques"] {
  display: none;
}

input[id="info"] + label {
  width: fit-content;
  height: 24px;
  line-height: 24px;
  padding: 0 30px;
  background: rgba(150, 195, 157, 0.5);
  border-radius: 12px;
  margin: 0px 8px 12px 8px;
  cursor: pointer;
}
input[id="info"]:checked + label {
  box-shadow: inset 0px 4px 4px rgba(0, 0, 0, 0.25);
}
input[id="info"] {
  display: none;
}

input[id="cs"] + label {
  width: fit-content;
  height: 24px;
  line-height: 24px;
  padding: 0 30px;
  background: rgba(255, 255, 255, 0.5);
  border-radius: 12px;
  margin: 0px 8px 12px 8px;
  cursor: pointer;
}
input[id="cs"]:checked + label {
  box-shadow: inset 0px 4px 4px rgba(0, 0, 0, 0.25);
}
input[id="cs"] {
  display: none;
}

input[id="algo"] + label {
  width: fit-content;
  height: 24px;
  line-height: 24px;
  padding: 0 30px;
  background: rgba(150, 195, 157, 0.5);
  border-radius: 12px;
  margin: 0px 8px 12px 8px;
  cursor: pointer;
}
input[id="algo"]:checked + label {
  box-shadow: inset 0px 4px 4px rgba(0, 0, 0, 0.25);
}
input[id="algo"] {
  display: none;
}

input[id="job"] + label {
  width: fit-content;
  height: 24px;
  line-height: 24px;
  padding: 0 30px;
  background: rgba(115, 115, 115, 0.5);
  border-radius: 12px;
  margin: 0px 8px 12px 8px;
  cursor: pointer;
}
input[id="job"]:checked + label {
  box-shadow: inset 0px 4px 4px rgba(0, 0, 0, 0.25);
}
input[id="job"] {
  display: none;
}
</style>
