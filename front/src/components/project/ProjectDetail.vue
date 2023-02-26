<template>
  <div>
    <HeaderNav></HeaderNav>
    <div class="container">
      <div class="title">인원 모집 상세 정보</div>
      <font-awesome-icon
        class="like"
        icon="fa-regular fa-heart"
        @click="like(recruitBoard.bid)"
        v-if="!recruitBoard.likeCnt"
      />
      <font-awesome-icon
        class="like"
        icon="fa-solid fa-heart"
        @click="like(recruitBoard.bid)"
        v-if="recruitBoard.likeCnt"
      />
      <!-- {{ recruitBoard }} -->
      <div class="content">
        <div class="subtitle">제목</div>
        <b-form-input
          id="title"
          v-model="recruitBoard.title"
          trim
        ></b-form-input>
        <div class="subtitle">작성자</div>
        <b-form-input id="uid" v-model="recruitBoard.uid" trim></b-form-input>
        <div class="subtitle">내용</div>
        <b-form-textarea
          id="content"
          v-model="recruitBoard.content"
          rows="8"
          max-rows="8"
        ></b-form-textarea>

        <div class="subtitle">인원 수</div>
        <b-form-input
          type="text"
          v-model="inputs.maxMember"
          trim
          id="content"
        />
        <div class="subtitle">태그</div>
        <div>
          <input
            type="checkbox"
            v-model="inputTag.name"
            value="java"
            id="java"
          /><label for="java"># Java</label>
          <input
            type="checkbox"
            v-model="inputTag.name"
            value="javascript"
            id="javascript"
          /><label for="javascript"># JavaScript</label>
          <input
            type="checkbox"
            v-model="inputTag.name"
            value="vue"
            id="vue"
          /><label for="vue"># Vue</label>
          <input
            type="checkbox"
            v-model="inputTag.name"
            value="react"
            id="react"
          /><label for="react"># React</label>
          <input
            type="checkbox"
            v-model="inputTag.name"
            value="spring"
            id="spring"
          /><label for="spring"># Spring</label>
          <input
            type="checkbox"
            v-model="inputTag.name"
            value="nodejs"
            id="nodejs"
          /><label for="nodejs"># Node.js</label>
        </div>
        <div>
          <input
            type="checkbox"
            v-model="inputTag.name"
            value="mysql"
            id="mysql"
          /><label for="mysql"># MySQL</label>
          <input
            type="checkbox"
            v-model="inputTag.name"
            value="kotlin"
            id="kotlin"
          /><label for="kotlin"># Kotlin</label>
          <input
            type="checkbox"
            v-model="inputTag.name"
            value="python"
            id="python"
          /><label for="python"># Python</label>
          <input
            type="checkbox"
            v-model="inputTag.name"
            value="django"
            id="django"
          /><label for="django"># Django</label>
          <input
            type="checkbox"
            v-model="inputTag.name"
            value="aws"
            id="aws"
          /><label for="aws"># AWS</label>
          <input
            type="checkbox"
            v-model="inputTag.name"
            value="docker"
            id="docker"
          /><label for="docker"># Docker</label>
          <input
            type="checkbox"
            v-model="inputTag.name"
            value="git"
            id="git"
          /><label for="git"># Git</label>
          <input
            type="checkbox"
            v-model="inputTag.name"
            value="c"
            id="c"
          /><label for="c"># C</label>
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
          <li v-for="(file, index) in recruitBoard.fileList" :key="index">
            {{ index + 1 }}. <a :href="download(file)">{{ file.originFile }}</a>
          </li>
        </ul>
      </div>

      <b-button class="modify" @click="modify">수정</b-button>
      <b-button class="delete" @click="del">삭제</b-button>

      <Comment type="recruit" @refreshUp="refresh"></Comment>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";
import HeaderNav from "@/components/HeaderNav.vue";
import Comment from "@/components/project/CommentList.vue";
const projectStore = "projectStore";
export default {
  data() {
    return {
      inputs: {
        uid: sessionStorage.getItem("user"),
        title: "",
        content: "",
        open: true,
        maxMember: 0,
      },
      inputTag: { name: [] },
      fileList: [],
      imageList: [],
    };
  },
  created() {
    this.inputs = this.recruitBoard;
    this.getCommentList(this.recruitBoard.bid);
    console.log("check bid", this.recruitBoard.bid);
  },
  computed: {
    ...mapState(projectStore, ["recruitBoard"]),
  },
  methods: {
    ...mapActions(projectStore, [
      "setRecruitLike",
      "modRecruitBoard",
      "delRecruitBoard",
      "getCommentList",
    ]),
    async modify() {
      console.log(this.inputs);
      this.inputs.bcid = "recruit";

      const params = new FormData();
      const json = JSON.stringify(this.inputs);
      const blob = new Blob([json], { type: "application/json" });
      params.append("recruitBoard", blob);

      const jsonNames = JSON.stringify(this.inputTag);
      const blob2 = new Blob([jsonNames], { type: "application/json" });
      params.append("language", blob2);

      this.fileList.forEach((element) => {
        params.append("upfile", element);
      });
      this.fileList.forEach((element) => {
        params.append("upimage", element);
      });

      await this.modRecruitBoard(params);
      this.moveTo();
    },
    moveTo() {
      this.$router.push({ name: "recruit" });
    },
    del() {
      const data = {
        bid: this.recruitBoard.bid,
        uid: sessionStorage.getItem("user"),
      };
      this.delRecruitBoard(data);
      this.$router.push({ name: "recruit" });
    },
    like(bid) {
      const uid = sessionStorage.getItem("user");
      const params = { uid: uid, bid: bid };
      this.setRecruitLike(params);
    },
    formatNames(files) {
      return files.length === 1
        ? files[0].name
        : `${files.length} files selected`;
    },
    async refresh() {
      console.log("REfresh2222");
      await this.getCommentList(this.recruitBoard.bid);
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
.like {
  position: absolute;
  top: 120px;
  right: 80px;
  font-size: 24px;
  cursor: pointer;
  color: white;
}

input {
  background: rgba(255, 255, 255, 0.07);
  border: none;
  color: white;
}
textarea {
  background: rgba(255, 255, 255, 0.07);
  border: none;
  color: white;
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
#file-list li a {
  color: white;
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

input[id="java"] + label {
  width: fit-content;
  height: 24px;
  line-height: 24px;
  padding: 0 30px;
  background: rgba(100, 101, 166, 0.5);
  border-radius: 12px;
  margin: 0px 8px 12px 8px;
  cursor: pointer;
}
input[id="java"]:checked + label {
  box-shadow: inset 0px 4px 4px rgba(0, 0, 0, 0.25);
}
input[id="java"] {
  display: none;
}

input[id="javascript"] + label {
  width: fit-content;
  height: 24px;
  line-height: 24px;
  padding: 0 30px;
  background: rgba(150, 195, 157, 0.5);
  border-radius: 12px;
  margin: 0px 8px 12px 8px;
  cursor: pointer;
}
input[id="javascript"]:checked + label {
  box-shadow: inset 0px 4px 4px rgba(0, 0, 0, 0.25);
}
input[id="javascript"] {
  display: none;
}

input[id="vue"] + label {
  width: fit-content;
  height: 24px;
  line-height: 24px;
  padding: 0 30px;
  background: rgba(255, 255, 255, 0.5);
  border-radius: 12px;
  margin: 0px 8px 12px 8px;
  cursor: pointer;
}
input[id="vue"]:checked + label {
  box-shadow: inset 0px 4px 4px rgba(0, 0, 0, 0.25);
}
input[id="vue"] {
  display: none;
}

input[id="react"] + label {
  width: fit-content;
  height: 24px;
  line-height: 24px;
  padding: 0 30px;
  background: rgba(150, 195, 157, 0.5);
  border-radius: 12px;
  margin: 0px 8px 12px 8px;
  cursor: pointer;
}
input[id="react"]:checked + label {
  box-shadow: inset 0px 4px 4px rgba(0, 0, 0, 0.25);
}
input[id="react"] {
  display: none;
}

input[id="spring"] + label {
  width: fit-content;
  height: 24px;
  line-height: 24px;
  padding: 0 30px;
  background: rgba(115, 115, 115, 0.5);
  border-radius: 12px;
  margin: 0px 8px 12px 8px;
  cursor: pointer;
}
input[id="spring"]:checked + label {
  box-shadow: inset 0px 4px 4px rgba(0, 0, 0, 0.25);
}
input[id="spring"] {
  display: none;
}

input[id="nodejs"] + label {
  width: fit-content;
  height: 24px;
  line-height: 24px;
  padding: 0 30px;
  background: rgba(134, 170, 197, 0.5);
  border-radius: 12px;
  margin: 0px 8px 12px 8px;
  cursor: pointer;
}
input[id="nodejs"]:checked + label {
  box-shadow: inset 0px 4px 4px rgba(0, 0, 0, 0.25);
}
input[id="nodejs"] {
  display: none;
}

input[id="mysql"] + label {
  width: fit-content;
  height: 24px;
  line-height: 24px;
  padding: 0 30px;
  background: rgba(100, 101, 166, 0.5);
  border-radius: 12px;
  margin: 0px 8px 12px 8px;
  cursor: pointer;
}
input[id="mysql"]:checked + label {
  box-shadow: inset 0px 4px 4px rgba(0, 0, 0, 0.25);
}
input[id="mysql"] {
  display: none;
}

input[id="kotlin"] + label {
  width: fit-content;
  height: 24px;
  line-height: 24px;
  padding: 0 30px;
  background: rgba(255, 255, 255, 0.5);
  border-radius: 12px;
  margin: 0px 8px 12px 8px;
  cursor: pointer;
}
input[id="kotlin"]:checked + label {
  box-shadow: inset 0px 4px 4px rgba(0, 0, 0, 0.25);
}
input[id="kotlin"] {
  display: none;
}

input[id="python"] + label {
  width: fit-content;
  height: 24px;
  line-height: 24px;
  padding: 0 30px;
  background: rgba(134, 170, 197, 0.5);
  border-radius: 12px;
  margin: 0px 8px 12px 8px;
  cursor: pointer;
}
input[id="python"]:checked + label {
  box-shadow: inset 0px 4px 4px rgba(0, 0, 0, 0.25);
}
input[id="python"] {
  display: none;
}

input[id="django"] + label {
  width: fit-content;
  height: 24px;
  line-height: 24px;
  padding: 0 30px;
  background: rgba(115, 115, 115, 0.5);
  border-radius: 12px;
  margin: 0px 8px 12px 8px;
  cursor: pointer;
}
input[id="django"]:checked + label {
  box-shadow: inset 0px 4px 4px rgba(0, 0, 0, 0.25);
}
input[id="django"] {
  display: none;
}

input[id="aws"] + label {
  width: fit-content;
  height: 24px;
  line-height: 24px;
  padding: 0 30px;
  background: rgba(194, 150, 195, 0.5);
  border-radius: 12px;
  margin: 0px 8px 12px 8px;
  cursor: pointer;
}
input[id="aws"]:checked + label {
  box-shadow: inset 0px 4px 4px rgba(0, 0, 0, 0.25);
}
input[id="aws"] {
  display: none;
}

input[id="docker"] + label {
  width: fit-content;
  height: 24px;
  line-height: 24px;
  padding: 0 30px;
  background: rgba(150, 195, 157, 0.5);
  border-radius: 12px;
  margin: 0px 8px 12px 8px;
  cursor: pointer;
}
input[id="docker"]:checked + label {
  box-shadow: inset 0px 4px 4px rgba(0, 0, 0, 0.25);
}
input[id="docker"] {
  display: none;
}

input[id="git"] + label {
  width: fit-content;
  height: 24px;
  line-height: 24px;
  padding: 0 30px;
  background: rgba(150, 195, 157, 0.5);
  border-radius: 12px;
  margin: 0px 8px 12px 8px;
  cursor: pointer;
}
input[id="git"]:checked + label {
  box-shadow: inset 0px 4px 4px rgba(0, 0, 0, 0.25);
}
input[id="git"] {
  display: none;
}

input[id="c"] + label {
  width: fit-content;
  height: 24px;
  line-height: 24px;
  padding: 0 30px;
  background: rgba(100, 101, 166, 0.5);
  border-radius: 12px;
  margin: 0px 8px 12px 8px;
  cursor: pointer;
}
input[id="c"]:checked + label {
  box-shadow: inset 0px 4px 4px rgba(0, 0, 0, 0.25);
}
input[id="c"] {
  display: none;
}
</style>
