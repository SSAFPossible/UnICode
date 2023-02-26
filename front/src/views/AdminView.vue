<template>
  <div>
    <HeaderNav></HeaderNav>
    <router-view></router-view>
  </div>
</template>

<script>
import HeaderNav from "@/components/admin/AdminNav.vue";
export default {
  components: {
    HeaderNav,
  },
};
</script>

<style></style>
