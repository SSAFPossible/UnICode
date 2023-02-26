<template>
  <div class="problem-content">
    <div class="tier">
      <img
        src="@/assets/rankingTier.svg"
        alt=""
        v-if="problem.site == 'backjoon'"
      />
      <img src="@/assets/programmers.svg" alt="" v-else />
    </div>
    <div>
      <a :href="goTo(problem.url)" target="”_blank”">{{ problem.name }}</a>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
const boardStore = "boardStore";
export default {
  methods: {
    goTo(url) {
      return url;
    },
  },
  computed: {
    ...mapState(boardStore, ["algoList"]),
  },
  props: {
    problem: Object,
  },
};
</script>

<style scoped>
.problem-content {
  width: 16%;
  height: 40px;
  border: none;
  background-color: #f8f8f8;
  border-radius: 30px;
  line-height: 40px;
}
.problem-content .tier {
  margin-right: 5px;
  width: 24px;
}
.problem-content a {
  text-decoration: none;
  color: black;
}
.problem-content div {
  font-size: 14px;
  display: inline-block;
  width: 70%;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
</style>
