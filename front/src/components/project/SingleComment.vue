<template>
  <div class="comment-container">
    <div class="options">
      <font-awesome-icon
        class="options-item"
        @click="update(comment.item.cid)"
        icon="fa-regular fa-pen-to-square"
      />
      <font-awesome-icon
        class="options-item"
        @click="del(comment.item.cid)"
        icon="fa-solid fa-trash"
      />
    </div>
    <div>
      <img id="info-img" src="@/assets/hyunyong.svg" alt="" />
      <span class="comment-id">{{ comment.item.uid }}</span>
      <div class="comment-content" v-if="comment.item.comment != null">
        {{ comment.item.comment }}
      </div>
      <div class="comment-content" v-else>삭제된 댓글 입니다.</div>

      <div class="comment">
        <p class="comment-detail" @click="changeReply">댓글 작성</p>
        <ReplyComment
          class="comment-reply"
          :commentIdx="comment.item.cid"
        ></ReplyComment>
      </div>
    </div>

    <form v-if="openReply">
      <textarea v-model="inputs.comment" placeholder="댓글을 작성해주세요.">
      </textarea>
      <br />
      <button @click="submit">등록</button>
    </form>
  </div>
</template>

<script>
import { mapActions } from "vuex";
import ReplyComment from "@/components/project/ReplyComment.vue";
const projectStore = "projectStore";
export default {
  components: {
    ReplyComment,
  },
  data() {
    return {
      openReply: false,
      inputs: {
        comment: "",
        bid: "",
        uid: "",
        parentCid: "",
      },
    };
  },
  methods: {
    ...mapActions(projectStore, [
      "registSecondComment",
      "delComment",
      "modComment",
    ]),
    submit() {
      this.inputs.bid = this.comment.item.bid;
      this.inputs.uid = sessionStorage.getItem("user");
      this.inputs.parentCid = this.comment.item.cid;
      console.log(this.inputs);
      this.registSecondComment(this.inputs);
    },
    changeReply() {
      this.openReply = !this.openReply;
    },
    update(cid) {
      const data = {
        cid: cid,
        comment: "수정",
      };
      this.modComment(data);
    },
    del(cid) {
      const data = {
        cid: cid,
        uid: sessionStorage.getItem("user"),
      };
      this.delComment(data);
      this.$emit("refresh");
    },
  },
  props: {
    comment: Object,
  },
};
</script>

<style scoped>
form {
  display: flex;
  width: 97%;
  margin-left: 3%;
}
form > textarea {
  width: 100%;
  border-radius: 5px;
  background: rgba(255, 255, 255, 0.07);
  border: none;
  color: white;
}
form > button {
  width: 20%;
  height: 52px;
}
.comment-container {
  margin: 10px 5px;
}
.comment-id {
  margin-left: 5px;
  font-size: 14px;
  color: grey;
}
.comment-content {
  margin-left: 40px;
  margin-bottom: 5px;
  font-size: 14px;
}
.comment-detail {
  font-size: 14px;
  margin: 5px;
  color: grey;
  width: fit-content;
  cursor: pointer;
}
.comment-reply {
  font-size: 14px;
  margin: 5px;
  color: grey;
  width: fit-content;
  cursor: pointer;
  flex: auto;
}
.comment {
  display: flex;
}
#info-img {
  width: 30px;
}
.options {
  float: right;
}
.options-item {
  margin: 4px;
  color: grey;
}
.options-item :hover {
  color: white;
}
</style>
