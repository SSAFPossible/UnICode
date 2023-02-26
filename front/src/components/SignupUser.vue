<template>
  <div class="container">
    <div class="login-logo">
      <img id="login-logo-img" src="@/assets/UnICode.svg" />
    </div>
    <div class="login-contents">
      <div class="main-container">
        <div class="room-deal-information-container">
          <div class="room-file-upload-wrapper">
            <div
              v-if="!files.length"
              class="room-file-upload-example-container"
            >
              <div class="room-file-upload-example">
                <div class="room-file-notice-item">
                  프로필 이미지를 등록해주세요.
                </div>
                <div class="room-file-notice-item room-file-upload-button">
                  <div class="image-box">
                    <div class="image-profile">
                      <img />
                    </div>
                    <label for="file">사진 등록</label>
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

      <b-form-input
        id="userId"
        v-model="user.uid"
        :state="idState"
        placeholder="아이디(필수)"
        trim
        lazy
      ></b-form-input>
      <b-form-invalid-feedback class="feedback" id="userId">
        아이디는 6자 이상 입력하셔야 합니다.
      </b-form-invalid-feedback>
      <b-form-input
        id="userPassword"
        v-model="user.password"
        :state="passwordState"
        placeholder="비밀번호(필수)"
        trim
        lazy
      ></b-form-input>
      <b-form-invalid-feedback class="feedback" id="userPassword">
        비밀번호는 8~16자의 영문, 숫자, 기호를 입력하셔야 합니다.
      </b-form-invalid-feedback>
      <!-- <b-form-input
        id="userPasswordCheck"
        v-model="userPasswordCheck"
        :state="passwordStateCheck"
        placeholder="비밀번호 확인"
        trim
      ></b-form-input>
            <b-form-invalid-feedback id="userPasswordCheck">
      입력하신 비밀번호와 다릅니다.
    </b-form-invalid-feedback> -->
      <b-form-input
        id="userName"
        v-model="user.name"
        placeholder="이름(필수)"
        trim
      ></b-form-input>
      <b-form-input
        id="userEmail"
        v-model="user.email"
        placeholder="이메일 주소(선택)"
        trim
      ></b-form-input>
      <b-form-input
        id="userGithub"
        v-model="user.github"
        placeholder="Github 주소(선택)"
        trim
      ></b-form-input>
      <b-form-file
        class="file-input"
        multiple
        input
        Array
        :file-name-formatter="formatNames"
        v-model="fileList"
      ></b-form-file>

      <div class="btnContainer">
        <button class="loginBtn" @click="regist">가입</button>
        <button class="cancelBtn">취소</button>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions } from "vuex";
const userStore = "userStore";

export default {
  components: {},
  data() {
    return {
      user: {
        uid: "",
        password: "",
        name: "",
        email: "",
        github: "",
      },
      fileList: [],
      files: [], //업로드용 파일
      filesPreview: [],
      uploadImageIndex: 0, // 이미지 업로드를 위한 변수
    };
  },
  computed: {
    idState() {
      if (this.user.uid.length == 0) return null;
      else {
        return this.user.uid.length > 5 ? true : false;
      }
    },
    passwordState() {
      const validatePass =
        /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{8,16}$/;
      if (this.user.password.length == 0) return null;
      else {
        return validatePass.test(this.user.password);
      }
    },
  },
  methods: {
    ...mapActions(userStore, ["userRegister"]),
    async regist() {
      const params = new FormData();
      const json = JSON.stringify(this.user);
      const blob = new Blob([json], { type: "application/json" });
      params.append("user", blob);
      console.log(this.image);

      this.fileList.forEach((element) => {
        params.append("profile", element);
      });

      params.append("accessImg", this.files[0]);

      console.log(params);
      await this.userRegister(params);
      this.$router.push("/");
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

    fileDeleteButton(e) {
      const name = e.target.getAttribute("name");
      this.files = this.files.filter((data) => data.number !== Number(name));
      // console.log(this.files);
    },
  },
};
</script>

<style scoped>
.container {
  width: 100%;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-flow: column;
}

.login-logo {
  margin-bottom: 66px;
}
.login-logo #login-logo-img {
  width: 65%;
}

.login-contents {
  width: 40%;
  margin: 0 auto;
  color: #a1a1a1;
}

.form-control {
  border: 0.5px solid #a1a1a1;
  border-radius: 30px;
  padding-left: 30px;
  height: 40px;
  margin: 15px 0;
  font-size: 14px;
}

.custom-control.custom-checkbox.b-custom-control-sm {
  float: right;
  font-size: 12px;
  margin: 3px 0 37px 0;
  display: flex;
}

.btnContainer {
  width: 100%;
  clear: both;
}

.btnContainer .loginBtn {
  width: 48%;
  background: #6465a6;
  border-radius: 30px;
  height: 40px;
  font-size: 14px;
  line-height: 24px;
  color: white;
  border: none;
  margin-top: 30px;
  margin-bottom: 6px;
  margin-right: 2%;
}

.btnContainer .cancelBtn {
  width: 48%;
  background: #fff;
  border-radius: 30px;
  height: 40px;
  font-size: 14px;
  line-height: 24px;
  color: black;
  border: 0.5px solid #a1a1a1;
  margin-bottom: 16px;
  margin-left: 2%;
}

.feedback {
  text-align: left;
  margin-left: 10px;
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
</style>
