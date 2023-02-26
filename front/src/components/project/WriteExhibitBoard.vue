<template>
  <div>
    <header-nav></header-nav>
    <div class="container">
      <div class="title">프로젝트 등록</div>
      <div class="content">
        <div class="main-container">
          <div class="room-deal-information-container">
            <div class="room-file-upload-wrapper">
              <div
                v-if="!files.length"
                class="room-file-upload-example-container"
              >
                <div class="room-file-upload-example">
                  <div class="room-file-notice-item">
                    프로젝트 메인 이미지를 등록해주세요.
                  </div>
                  <div class="room-file-notice-item room-file-upload-button">
                    <div class="image-box">
                      <div class="image-profile">
                        <img />
                      </div>
                      <label for="file">이미지 등록</label>
                      <input
                        type="file"
                        id="file"
                        ref="files"
                        @change="imageUpload"
                      />
                    </div>
                  </div>
                </div>
              </div>
              <div v-else class="file-preview-content-container">
                <div class="file-preview-container">
                  <div
                    v-for="(file, index) in filesPreview"
                    :key="index"
                    class="file-preview-wrapper"
                  >
                    <div
                      class="file-close-button"
                      @click="fileDeleteButton"
                      :name="file.number"
                    >
                      x
                    </div>
                    <img :src="file.preview" />
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="subtitle">제목</div>
        <b-form-input type="text" v-model="inputs.title" trim id="title" />
        <div class="subtitle">작성자</div>
        <b-form-input type="text" v-model="inputs.uid" trim id="uid" />
        <div class="subtitle">내용</div>
        <b-form-input type="text" v-model="inputs.content" trim id="content" />
        <div class="subtitle">URL</div>
        <b-form-input type="text" id="url" v-model="inputs.project.url" trim />
        <div class="subtitle">태그</div>
        <div>
          <input
            type="checkbox"
            v-model="inputs.project.language.name"
            value="java"
            id="java"
          /><label for="java"># Java</label>
          <input
            type="checkbox"
            v-model="inputs.project.language.name"
            value="javascript"
            id="javascript"
          /><label for="javascript"># JavaScript</label>
          <input
            type="checkbox"
            v-model="inputs.project.language.name"
            value="vue"
            id="vue"
          /><label for="vue"># Vue</label>
          <input
            type="checkbox"
            v-model="inputs.project.language.name"
            value="react"
            id="react"
          /><label for="react"># React</label>
          <input
            type="checkbox"
            v-model="inputs.project.language.name"
            value="spring"
            id="spring"
          /><label for="spring"># Spring</label>
          <input
            type="checkbox"
            v-model="inputs.project.language.name"
            value="nodejs"
            id="nodejs"
          /><label for="nodejs"># Node.js</label>
        </div>
        <div>
          <input
            type="checkbox"
            v-model="inputs.project.language.name"
            value="mysql"
            id="mysql"
          /><label for="mysql"># MySQL</label>
          <input
            type="checkbox"
            v-model="inputs.project.language.name"
            value="kotlin"
            id="kotlin"
          /><label for="kotlin"># Kotlin</label>
          <input
            type="checkbox"
            v-model="inputs.project.language.name"
            value="python"
            id="python"
          /><label for="python"># Python</label>
          <input
            type="checkbox"
            v-model="inputs.project.language.name"
            value="django"
            id="django"
          /><label for="django"># Django</label>
          <input
            type="checkbox"
            v-model="inputs.project.language.name"
            value="aws"
            id="aws"
          /><label for="aws"># AWS</label>
          <input
            type="checkbox"
            v-model="inputs.project.language.name"
            value="docker"
            id="docker"
          /><label for="docker"># Docker</label>
          <input
            type="checkbox"
            v-model="inputs.project.language.name"
            value="git"
            id="git"
          /><label for="git"># Git</label>
          <input
            type="checkbox"
            v-model="inputs.project.language.name"
            value="c"
            id="c"
          /><label for="c"># C</label>
        </div>

        <div class="subtitle">구성 인원</div>
        <b-form-input
          type="text"
          v-model="inputs.project.member.uid"
          id="member"
          trim
        />

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
        project: {
          url: "",
          language: {
            name: [],
          },
          member: {
            uid: ["ssafy", "naver", "kakao"],
          },
        },
      },
      fileList: [],
      imageList: [],
      files: [], //업로드용 파일
      filesPreview: [],
      uploadImageIndex: 0, // 이미지 업로드를 위한 변수
    };
  },
  methods: {
    ...mapActions(projectStore, ["exhibitBoardRegist"]),
    async regist() {
      console.log(this.inputs);
      this.inputs.bcid = "exhibit";

      const params = new FormData();
      const json = JSON.stringify(this.inputs);
      const blob = new Blob([json], { type: "application/json" });
      params.append("exhibitBoard", blob);

      this.fileList.forEach((element) => {
        params.append("upfile", element);
      });
      this.fileList.forEach((element) => {
        params.append("upimage", element);
      });

      params.append("mainImg", this.files[0]);

      await this.exhibitBoardRegist(params);
      this.moveTo();
    },
    moveTo() {
      this.$router.push("/main/project");
    },
    formatNames(files) {
      return files.length === 1
        ? files[0].name
        : `${files.length} files selected`;
    },
    imageUpload() {
      console.log("123", this.$refs.files.files);

      // this.files = [...this.files, this.$refs.files.files];
      //하나의 배열로 넣기
      let num = -1;
      for (let i = 0; i < this.$refs.files.files.length; i++) {
        this.files = [(this.file = this.$refs.files.files[i])];
        num = i;
        //이미지 업로드용 프리뷰
        this.filesPreview = [
          ...this.filesPreview,
          {
            preview: URL.createObjectURL(this.$refs.files.files[i]),
            number: i,
          },
        ];
      }
      this.uploadImageIndex = num + 1; //이미지 index의 마지막 값 + 1 저장
      console.log(this.files);
      // console.log(this.filesPreview);
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
  margin-bottom: 40px;
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

input {
  background: rgba(255, 255, 255, 0.07);
  border: none;
  color: white;
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
.room-deal-information-container {
  color: #222222;
  border: 1px solid #dddddd;
}

.room-deal-information-title {
  text-align: center;
  font-size: 18px;
  line-height: 60px;
  border-bottom: 1px solid #dddddd;
}

.room-deal-information-content-wrapper {
  min-height: 50px;
  display: flex;
}

.room-deal-informtaion-content-title {
  font-size: 15px;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 150px;
  background-color: #f9f9f9;
}

.room-deal-information-content {
  width: 100%;
  font-size: 14px;
}

.room-deal-option-selector {
  display: flex;
  align-items: center;
  padding: 15px;
}

.room-deal-option-item {
  width: 100px;
  height: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
  border: 1px solid #cccccc;
  cursor: pointer;
}

.room-deal-option-item:last-child {
  margin-left: 10px;
}

.room-deal-option-notice {
  margin-left: auto;
  font-size: 14px;
  color: #888888;
}

.room-deal-option-item-deposit {
  margin-left: 10px;
}

.room-deal-information-wrapper {
  display: flex;
  flex-direction: column;
}

.room-deal-information-option {
  padding: 10px;
  display: flex;
  align-items: center;
}

.room-deal-information-option:last-child {
  border-bottom: 1px solid #dddddd;
}

.room-deal-information-item-type {
  font-size: 13px;
  color: #fff;
  background-color: #61b6e5;
  min-width: 50px;
  height: 26px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.room-deal-information-item-wrapper {
  display: flex;
  align-items: center;
  margin-left: 10px;
  height: 46px;
  width: 100%;
}

.room-deal-information-item-wrapper > input {
  border: 1px solid #dddddd;
  width: 140px;
  height: 100%;
  padding: 0 15px;
  font-size: 15px;
}

.room-deal-inforamtion-won {
  margin: 0 10px;
}

.room-deal-information-example {
  color: #888888;
}

.room-deal-information-option:not(:first-child) {
  margin-top: 10px;
}

.room-deal-inforamtion-divide {
  font-size: 22px;
  margin: 0 8px;
  color: #222222;
  font-weight: 100;
}

.room-deal-close-button-wrapper {
  margin-left: auto;
  cursor: pointer;
}

.room-deal-close-button {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 22px;
  height: 22px;
  background-color: #666666;
  color: rgb(220, 220, 220);
}

.room-deal-cliked {
  background-color: rgb(235, 235, 235);
  color: rgb(170, 170, 170);
}

.room-file-upload-example {
  height: 100%;
}

.room-write-content-container {
  border-top: 1px solid #dddddd;
  min-height: 260px;
}

.room-picture-notice {
  margin: 20px;
  padding: 20px 40px;
  border: 1px solid #dddddd;
}

.file-preview-content-container {
  height: 100%;
}

.room-file-upload-wrapper {
  margin: 20px;
  border: 1px solid #dddddd;
  min-height: 80px;
  font-size: 15px;
  color: #888888;
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100%;
}

.room-file-upload-example-container {
  display: flex;
  align-items: center;
  justify-content: center;
  /* height: 100%;
  width: 100%; */
}

.room-file-image-example-wrapper {
  text-align: center;
}

.room-file-notice-item {
  margin-top: 5px;
  text-align: center;
}

.image-box {
  margin-top: 30px;
  padding-bottom: 20px;
  text-align: center;
}

.image-box input[type="file"] {
  position: absolute;
  width: 0;
  height: 0;
  padding: 0;
  overflow: hidden;
  border: 0;
}

.image-box label {
  display: inline-block;
  padding: 10px 20px;
  background-color: #232d4a;
  color: #fff;
  vertical-align: middle;
  font-size: 15px;
  cursor: pointer;
  border-radius: 30px;
}

.file-preview-wrapper {
  padding: 10px;
  position: relative;
}

.file-preview-wrapper > img {
  position: relative;
  width: 140px;
  height: 160px;
  z-index: 10;
}

.file-close-button {
  position: absolute;
  /* align-items: center; */
  line-height: 18px;
  z-index: 99;
  font-size: 18px;
  right: 5px;
  top: 10px;
  color: #fff;
  font-weight: bold;
  background-color: #666666;
  width: 20px;
  height: 20px;
  text-align: center;
  cursor: pointer;
}

.file-preview-container {
  height: 100%;
  display: flex;
  flex-wrap: wrap;
}

.room-write-button-wrapper {
  margin-top: 20px;
  display: flex;
  justify-content: center;
  align-items: center;
  color: #222222;
}

.room-write-button-wrapper > div {
  width: 160px;
  height: 50px;
  border: 1px solid #dddddd;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 15px;
  cursor: pointer;
}

.room-write-button {
  margin-left: 15px;
  color: #fff;
  background-color: #1564f9;
}

.room-write-button:hover {
  opacity: 0.8;
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
