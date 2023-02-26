<template>
  <div>
    <header-nav></header-nav>
    <div class="container">
      <div class="title">인원 모집 등록</div>
      <div class="content">
        <div class="subtitle">제목</div>
        <b-form-input type="text" v-model="inputs.title" trim id="title" />
        <div class="subtitle">작성자</div>
        <b-form-input type="text" v-model="inputs.uid" trim id="uid" />
        <div class="subtitle">내용</div>
        <b-form-input type="text" v-model="inputs.content" trim id="content" />
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
        <b-button class="submit" @click="regist">작성</b-button
        ><b-button class="cancel">취소</b-button>
      </div>
    </div>
  </div>
</template>

<script>
import HeaderNav from "../HeaderNav.vue";
import { mapActions } from "vuex";
const projectStore = "projectStore";
export default {
  components: { HeaderNav },
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
  methods: {
    ...mapActions(projectStore, ["recruitBoardRegist", "getRecruitBoard"]),
    async regist() {
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

      await this.recruitBoardRegist(params);
      this.moveTo();
    },
    moveTo() {
      const data = { name: [] };
      this.getRecruitBoard(data);
      this.$router.push({ name: "recruit" });
    },
    formatNames(files) {
      return files.length === 1
        ? files[0].name
        : `${files.length} files selected`;
    },
  },
};
</script>

<style scoped>
.content {
  text-align: left;
}
.content > button {
  text-align: center;
}

input {
  background: rgba(255, 255, 255, 0.07);
  border: none;
  color: white;
}

.subtitle {
  margin: 20px 0 10px 10px;
}
.cancel {
  width: 80px;
  height: 40px;
  left: 725px;
  top: 837px;

  background: rgba(182, 182, 182, 0.6);
  border-radius: 20px;
  color: black;
  border: none;
  margin-left: 10px;
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
.submit {
  width: 80px;
  height: 40px;
  left: 634px;
  top: 837px;
  margin-top: 50px;
  background: #6465a6;
  border-radius: 20px;
}
.cancel {
  margin-top: 50px;
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
