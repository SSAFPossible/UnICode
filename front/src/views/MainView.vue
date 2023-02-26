<template>
  <div>
    <HeaderNav></HeaderNav>
    <router-view class="view" :key="$route.fullPath"></router-view>
  </div>
</template>

<script>
import HeaderNav from "@/components/HeaderNav.vue";
export default {
  components: {
    HeaderNav,
  },
};
</script>

<style></style>
