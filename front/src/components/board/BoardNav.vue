<template>
  <div class="container">
    <div class="title">게시판</div>
    <div>
      <router-link :to="{ name: 'notice' }" class="notice"
        >공지사항</router-link
      >
      <router-link :to="{ name: 'question' }" class="question"
        >질문 게시판</router-link
      >
      <router-link :to="{ name: 'general' }" class="general"
        >자유 게시판</router-link
      >
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style scoped>
.container {
  margin-bottom: 50px;
}

.container a {
  text-decoration: none;
  color: black;
  margin: 15px;
  font-size: 14px;
}

.container .title {
  font-weight: 400;
  font-size: 30px;
  line-height: 36px;
  color: #000000;
  margin-bottom: 20px;
}
</style>
