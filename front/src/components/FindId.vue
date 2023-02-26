<template>
  <div class="find-container">
    <div class="find-logo">
      <h2>아이디 찾기</h2>
      <div>가입시 입력하신 이름과 이메일을 통해 아이디를 찾을 수 있습니다.</div>
    </div>

    <div class="find-contents">
      <b-form-input
        id="name"
        v-model="user.name"
        placeholder="이름"
        trim
      ></b-form-input>
      <b-form-input
        id="email"
        v-model="user.email"
        placeholder="이메일"
        trim
      ></b-form-input>

      <div class="btnContainer">
        <button class="findBtn" @click="find">찾기</button>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions } from "vuex";
const userStore = "userStore";
export default {
  data() {
    return {
      user: {
        name: "",
        email: "",
      },
    };
  },
  methods: {
    ...mapActions(userStore, ["findUser"]),
    find() {
      this.findUser(this.user);
      this.$router.push({ name: "ResultFindId" });
    },
  },
  components: {},
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
