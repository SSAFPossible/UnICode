<template>
  <div class="find-container">
    <div class="find-logo">
      <h2>아이디 찾기</h2>
      <div>가입하신 아이디는 아래와 같습니다.</div>
    </div>

    <div class="find-contents">
      ID: {{ findId }}
      <div class="btnContainer">
        <button class="findBtn" @click="goHome">홈으로 가기</button>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
const userStore = "userStore";
export default {
  name: "ResultFindId",
  computed: {
    ...mapState(userStore, ["findId"]),
  },
  methods: {
    goHome() {
      this.$router.push({ name: "LoginView" });
    },
  },
};
</script>

<style scoped>
.find-container {
  width: 100%;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-flow: column;
}

h2 {
  margin-bottom: 20px;
}
.find-logo {
  margin-bottom: 30px;
}

.find-contents {
  width: 40%;
  margin: 0 auto;
  color: #a1a1a1;
}

.btnContainer {
  width: 100%;
  margin-top: 30px;
  clear: both;
}

.btnContainer .findBtn {
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
