<template>
  <div>
    <HeaderNav></HeaderNav>
    <BoardNav></BoardNav>
    <router-view></router-view>
  </div>
</template>

<script>
import HeaderNav from "@/components/HeaderNav.vue";
import BoardNav from "@/components/board/BoardNav.vue";

export default {
  components: {
    HeaderNav,
    BoardNav,
  },
};
</script>

<style scoped></style>
