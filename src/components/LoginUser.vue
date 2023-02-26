<template>
  <div class="login-container">
    <div class="login-logo">
      <img id="login-logo-img" src="@/assets/UnICode.svg" />
    </div>

    <div class="login-contents">
      <b-form-input
        id="uid"
        v-model="user.uid"
        placeholder="아이디"
        trim
      ></b-form-input>
      <b-form-input
        id="password"
        v-model="user.password"
        placeholder="비밀번호"
        type="password"
        trim
      ></b-form-input>
      <b-form-checkbox
        id="checkLogin"
        v-model="checkLogin"
        value="true"
        unchecked-value="false"
        size="sm"
        >아이디 저장
      </b-form-checkbox>
      <div class="btnContainer">
        <button class="loginBtn" @click="login">로그인</button>
        <div>
          <button class="otherBtns" @click="signUp">회원가입</button> |
          <button class="otherBtns" @click="searchId">아이디 찾기</button> |
          <button class="otherBtns" @click="searchPass">비밀번호 찾기</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions, mapState } from "vuex";
const userStore = "userStore";
export default {
  data() {
    return {
      user: {
        uid: "",
        password: "",
      },
      checkLogin: "false",
    };
  },
  methods: {
    ...mapActions(userStore, ["userConfirm", "getUserInfo"]),
    async login() {
      console.log("LOGINVUER Vue", this.user);
      await this.userConfirm(this.user);
      let token = sessionStorage.getItem("jwt-auth-token");
      console.log("tokennnnnnnn ", token);
      // console.log("1. confirm() token >> " + token);
      if (this.isLogin) {
        await this.getUserInfo(token);
        console.log(this.userInfo);
        // console.log("4. confirm() userInfo :: ", this.userInfo);
        this.$router.push("/main");
      }
    },
    signUp() {
      this.$router.push("/signup");
    },
    searchId() {
      this.$router.push("/findid");
    },
    searchPass() {},
  },
  computed: {
    ...mapState(userStore, ["isLogin", "isLoginError", "userInfo"]),
  },
};
</script>

<style scoped>
.login-container {
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
  margin: 10px 0;
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
  width: 100%;
  background: #6465a6;
  border-radius: 30px;
  height: 40px;
  font-size: 14px;
  line-height: 24px;
  color: white;
  border: none;
  margin-bottom: 16px;
}

.btnContainer .otherBtns {
  width: fit-content;
  font-size: 12px;
  border: none;
  background-color: white;
  color: #a1a1a1;
}

.btnContainer .otherBtns:hover {
  transition: all 0.5s;
  color: #000;
}
</style>
