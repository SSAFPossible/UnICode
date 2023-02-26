<template>
  <div class="header">
    <div class="logo">
      <router-link :to="{ name: 'MainView' }"
        ><img id="logo-img" src="@/assets/UnICode.svg" alt="UnICode"
      /></router-link>
    </div>
    <div class="navbar">
      <router-link :to="{ name: 'AdminUser' }" class="board"
        >사용자 관리</router-link
      >
      |
      <router-link :to="{ name: 'AdminAlgo' }" class="project"
        >알고리즘 문제 관리</router-link
      >
    </div>

    <div class="info">
      <img id="info-img" src="@/assets/hyunyong.svg" alt="" />
      <span>{{ userInfo.name }}</span>
      <span @click="logout">로그아웃</span>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";
const userStore = "userStore";
export default {
  computed: {
    ...mapState(userStore, ["userInfo"]),
  },
  methods: {
    ...mapActions(userStore, ["userLogout"]),
    logout() {
      console.log(this.userInfo);
      this.userLogout(this.userInfo.uid);
      sessionStorage.removeItem("jwt-auth-token"); //저장된 토큰 없애기
      sessionStorage.removeItem("refresh-token"); //저장된 토큰 없애기
      sessionStorage.removeItem("user");
      this.$router.push("/");
    },
  },
};
</script>

<style scoped>
.header {
  display: flex;
  align-items: center;
  margin: 40px 80px;
  font-size: 14px;
}

.header #logo-img {
  width: 120px;
  cursor: pointer;
}

.header .navbar {
  margin-left: 40px;
}
.header .navbar .board {
  width: fit-content;
  color: #222;
  text-decoration: none;
  padding-right: 10px;
}
.header .navbar .project {
  width: fit-content;
  color: #222;
  text-decoration: none;
  padding-left: 10px;
}
.header .navbar .board-black {
  width: fit-content;
  color: white;
  text-decoration: none;
  padding-right: 10px;
}
.header .navbar .project-black {
  width: fit-content;
  color: white;
  text-decoration: none;
  padding-left: 10px;
}
/* .header .navbar a.router-link-exact-active {
  color: white;
}
.header .navbar a.router-link-exact-inactive {
  color: #b6b6b6;
} */

.header .info {
  position: absolute;
  right: 80px;
}

.header .info #info-img {
  margin-right: 10px;
}

.header .info > span {
  margin-right: 10px;
}
</style>
