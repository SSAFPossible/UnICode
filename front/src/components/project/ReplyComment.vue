<template>
  <div>
    <p @click="changeReply" v-if="!openReply">댓글 보기</p>
    <p @click="changeReply" v-else>댓글 닫기</p>
    <div v-if="openReply">
      <div v-for="(item, index) in commentList" :key="index">
        <div v-if="commentIdx == item.cid">
          <div v-for="(element, index) in item.secondComment" :key="index">
            <div class="options">
              <font-awesome-icon
                class="options-item"
                icon="fa-regular fa-pen-to-square"
              />
              <font-awesome-icon
                class="options-item"
                @click="del(element.cid)"
                icon="fa-solid fa-trash"
              />
            </div>
            <div class="comment">
              <img id="info-img" src="@/assets/hyunyong.svg" alt="" />
              <span class="comment-id">{{ element.uid }}</span>
              <div class="comment-content" v-if="element.comment != null">
                {{ element.comment }}
              </div>
              <div class="comment-content" v-else>삭제된 댓글 입니다.</div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions, mapState } from "vuex";
const projectStore = "projectStore";
export default {
  computed: {
    ...mapState(projectStore, ["commentList"]),
  },
  data() {
    return {
      openReply: false,
    };
  },
  methods: {
    ...mapActions(projectStore, ["delComment"]),
    changeReply() {
      this.openReply = !this.openReply;
    },
    del(cid) {
      const data = {
        cid: cid,
        uid: sessionStorage.getItem("user"),
      };
      //   console.log(data)
      this.delComment(data);
    },
  },
  props: {
    commentIdx: String,
  },
};
</script>

<style scoped>
p {
  font-size: 14px;
  margin: 0;
  color: grey;
  width: fit-content;
}
#info-img {
  width: 30px;
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
  color: white;
}
.comment-detail {
  font-size: 14px;
  margin: 5px;
  color: grey;
}
.comment {
  margin: 12px 5px;
  cursor: default;
  width: 100%;
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
textarea {
  background: rgba(255, 255, 255, 0.07);
  border: none;
  color: white;
}
</style>
