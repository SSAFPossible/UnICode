<template>
  <div>
    <HeaderNav></HeaderNav>
    <div class="container">
      <div class="title">게시글</div>
      <font-awesome-icon
        class="like"
        icon="fa-regular fa-heart"
        @click="like(boardOne.bid)"
        v-if="(boardOne.bcid != 'free') & (boardOne.bcid != 'notice') & !status"
      />
      <font-awesome-icon
        class="like"
        icon="fa-solid fa-heart"
        @click="like(boardOne.bid)"
        v-if="(boardOne.bcid != 'free') & (boardOne.bcid != 'notice') & status"
      />
      <div class="content">
        <div class="subtitle">제목</div>
        <b-form-input id="title" v-model="boardOne.title" trim></b-form-input>
        <div class="subtitle">작성자</div>
        <b-form-input id="uid" v-model="boardOne.uid" trim></b-form-input>
        <div class="subtitle">내용</div>
        <b-form-textarea
          id="content"
          v-model="boardOne.content"
          rows="8"
          max-rows="8"
        ></b-form-textarea>
        <div v-show="(boardOne.bcid != 'free') & (boardOne.bcid != 'notice')">
          <div class="subtitle">태그</div>
          <div class="tag">
            <input
              type="radio"
              v-model="boardOne.tag"
              value="ques"
              id="ques"
            /><label for="ques"># 질문있어요</label>
            <input
              type="radio"
              v-model="boardOne.tag"
              value="info"
              id="info"
            /><label for="info"># 알려드려요</label>
            <input
              type="radio"
              v-model="boardOne.category"
              value="cs"
              id="cs"
            /><label for="cs"># CS</label>
            <input
              type="radio"
              v-model="boardOne.category"
              value="algo"
              id="algo"
            /><label for="algo"># 알고리즘</label>
            <input
              type="radio"
              v-model="boardOne.category"
              value="job"
              id="job"
            /><label for="job"># 취업</label>
          </div>
        </div>
        <b-form-file
          class="file-input"
          multiple
          input
          Array
          :file-name-formatter="formatNames"
          v-model="fileList"
        ></b-form-file>
        <ul id="file-list">
          <div>첨부파일</div>
          <li v-for="(file, index) in boardOne.fileList" :key="index">
            {{ index + 1 }}. <a :href="download(file)">{{ file.originFile }}</a>
          </li>
        </ul>
      </div>
      <b-button class="modify" @click="modify(boardOne.bcid)">수정</b-button>
      <b-button class="delete" @click="del(boardOne.bcid)">삭제</b-button>
      <Comment v-if="boardOne.bcid != 'notice'" :type="boardOne.bcid"></Comment>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";
import HeaderNav from "@/components/HeaderNav.vue";
import Comment from "@/components/project/CommentList.vue";
const boardStore = "boardStore";
const projectStore = "projectStore";
export default {
  data() {
    return {
      inputs: {
        uid: "",
        title: "",
        content: "",
        bcid: "",
        tag: [],
        category: [],
      },
      fileList: [],
      imageList: [],
      status: false,
    };
  },
  async created() {
    this.inputs = this.boardOne;
    await this.getCommentList(this.boardOne.bid);
    this.status = this.boardOne.like;
  },
  computed: {
    ...mapState(boardStore, ["boardOne"]),
  },
  methods: {
    ...mapActions(boardStore, [
      "modFreeBoard",
      "modNoticeBoard",
      "modStudyBoard",
      "delFreeBoard",
      "delNoticeBoard",
      "delStudyBoard",
      "setStudyLike",
    ]),
    ...mapActions(projectStore, ["getFileDown", "getCommentList"]),
    async modify(bcid) {
      const params = new FormData();
      console.log(this.boardOne);
      // this.boardOne.fileList=[];
      // this.boardOne.imageList=[];
      const json = JSON.stringify(this.boardOne);
      console.log(json);
      const blob = new Blob([json], { type: "application/json" });
      if (bcid === "free") {
        params.append("freeBoard", blob);
      } else if (bcid === "notice") {
        params.append("noticeBoard", blob);
        console.log("notice1111111");
      } else {
        params.append("studyBoard", blob);
      }

      this.fileList.forEach((element) => {
        params.append("upfile", element);
      });

      this.fileList.forEach((element) => {
        params.append("upimage", element);
      });

      var object = {};
      params.forEach(function (value, key) {
        console.log(key);
        console.log(value);
        object[key] = value;
      });
      var json22 = JSON.stringify(object);
      console.log("JSON", json22);

      if (bcid === "free") {
        await this.modFreeBoard(params);
        this.$router.push({ name: "general" });
      } else if (bcid === "notice") {
        await this.modNoticeBoard(params);
        this.$router.push({ name: "notice" });
      } else {
        await this.modStudyBoard(params);
        this.$router.push({ name: "question" });
      }
    },
    del(bcid) {
      const data = {
        bid: this.boardOne.bid,
        uid: sessionStorage.getItem("user"),
      };
      if (bcid === "free") {
        this.delFreeBoard(data);
        this.$router.push({ name: "general" });
      } else if (bcid === "notice") {
        this.delNoticeBoard(data);
        this.$router.push({ name: "notice" });
      } else {
        this.delStudyBoard(data);
        this.$router.push({ name: "question" });
      }
    },
    like(bid) {
      const uid = sessionStorage.getItem("user");
      const params = { uid: uid, bid: bid };
      this.setStudyLike(params);
      this.status = !this.status;
    },
    formatNames(files) {
      return files.length === 1
        ? files[0].name
        : `${files.length} files selected`;
    },
    download(file) {
      return `${process.env.VUE_APP_API_BASE_URL}/files/${file.saveFolder}/${file.saveFile}`;
    },
  },
  components: {
    HeaderNav,
    Comment,
  },
};
</script>

<style scoped>
.custom-file.b-form-file.file-input #file-upload-button {
  background-color: white;
}

.container {
  height: 100vh;
}
.content {
  text-align: left;
}
.content > button {
  text-align: center;
}

.subtitle {
  margin: 20px 0 10px 10px;
}
.delete {
  width: 80px;
  height: 40px;
  background: rgba(182, 182, 182, 0.6);
  border-radius: 20px;
  color: black;
  border: none;
  margin-left: 10px;
  margin-top: 20px;
}
.like {
  position: absolute;
  top: 120px;
  right: 80px;
  font-size: 24px;
}
.title {
  margin-bottom: 50px;
  font-weight: 400;
  font-size: 24px;
  line-height: 29px;
}
.tag {
  color: #fff;
}
.modify {
  width: 80px;
  height: 40px;
  margin-top: 20px;
  background: #6465a6;
  border-radius: 20px;
}

#file-list {
  margin-top: 15px;
  margin-left: 5px;
  padding: 0;
  list-style-type: none;
  border-radius: 5px;
}
#file-list li {
  margin: 0 10px;
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
  background: rgba(194, 150, 195, 0.5);
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
  background: rgba(134, 170, 197, 0.5);
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
