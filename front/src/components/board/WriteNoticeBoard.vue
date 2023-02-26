<template>
  <div>
    <HeaderNav></HeaderNav>
    <div class="container">
      <div class="title">게시글 작성</div>
      <div class="content">
        <div class="subtitle">제목</div>
        <b-form-input id="title" v-model="inputs.title" trim></b-form-input>
        <div class="subtitle">작성자</div>
        <b-form-input id="uid" v-model="inputs.uid" trim></b-form-input>
        <div class="subtitle">내용</div>
        <b-form-textarea
          id="content"
          v-model="inputs.content"
          rows="8"
          max-rows="8"
        ></b-form-textarea>
        <b-form-file
          class="file-input"
          multiple
          input
          Array
          :file-name-formatter="formatNames"
          v-model="fileList"
        ></b-form-file>
      </div>
      <b-button class="submit" @click="regist">작성</b-button
      ><b-button class="cancel">취소</b-button>
    </div>
  </div>
</template>

<script>
import { mapActions } from "vuex";
import HeaderNav from "@/components/HeaderNav.vue";
const boardStore = "boardStore";
export default {
  data() {
    return {
      inputs: {
        uid: sessionStorage.getItem("user"),
        title: "",
        content: "",
        bcid: "",
      },
      fileList: [],
      imageList: [],
    };
  },
  methods: {
    ...mapActions(boardStore, ["noticeBoardRegist"]),
    async regist() {
      // this.inputs.uid = this.userInfo.userId;
      this.inputs.bcid = "notice";

      const params = new FormData();
      const json = JSON.stringify(this.inputs);
      const blob = new Blob([json], { type: "application/json" });
      params.append("noticeBoard", blob);

      this.fileList.forEach((element) => {
        params.append("upfile", element);
      });
      this.fileList.forEach((element) => {
        params.append("upimage", element);
      });

      await this.noticeBoardRegist(params);
      this.$router.push({ name: "notice" });
    },
    formatNames(files) {
      return files.length === 1
        ? files[0].name
        : `${files.length} files selected`;
    },
  },
  components: {
    HeaderNav,
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
