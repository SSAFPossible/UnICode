<template>
  <div class="reply-container">
    <hr />

    <div v-for="(item, index) in commentList" :key="index">
      <SingleComment
        @refresh="refresh"
        :comment="{ item }"
        v-if="item.parentCid == 0"
      ></SingleComment>
    </div>
    <form>
      <textarea
        :class="{ light: !boardStatus, dark: boardStatus }"
        v-model="inputs.comment"
        placeholder="댓글을 작성해주세요."
      >
      </textarea>
      <br />
      <button @click="submit">등록</button>
    </form>
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";
import SingleComment from "@/components/project/SingleComment.vue";
const projectStore = "projectStore";
const boardStore = "boardStore";
export default {
  data() {
    return {
      inputs: {
        comment: "",
        bid: "",
        uid: "",
      },
    };
  },
  methods: {
    ...mapActions(projectStore, ["registFirstComment"]),
    submit() {
      if (this.type == "recruit") {
        this.inputs.bid = this.recruitBoard.bid;
      } else {
        this.inputs.bid = this.boardOne.bid;
      }

      this.inputs.uid = sessionStorage.getItem("user");
      this.registFirstComment(this.inputs);

      this.inputs.comment = "";
    },
    async refresh() {
      console.log("REfresh");
      this.$emit("refreshUp");
    },
  },
  computed: {
    ...mapState(projectStore, ["recruitBoard", "commentList", "boardStatus"]),
    ...mapState(boardStore, ["boardOne"]),
  },
  components: {
    SingleComment,
  },
  props: {
    type: String,
  },
};
</script>

<style scoped>
form {
  display: flex;
}
form > textarea {
  width: 100%;
  border-radius: 5px;
  margin-bottom: 40px;
}
form > button {
  width: 20%;
  height: 52px;
}
.reply-container {
  text-align: left;
}
.dark {
  background: rgba(255, 255, 255, 0.07);
  border: none;
  color: white;
}
.light {
  background: rgba(255, 255, 255, 0.07);
  border: none;
}
</style>
