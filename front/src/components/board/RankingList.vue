<template>
  <div class="rank">
    <div class="ranking-content" v-for="(item, index) in rankList" :key="index">
      <span class="rank">{{ index + 1 }}</span>
      <img class="tier" src="@/assets/kyuwon.svg" alt="" />
      <span class="name">{{ item.uid }}</span>
      <span class="point">{{ item.score }}p</span>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";
const boardStore = "boardStore";
export default {
  computed: {
    ...mapState(boardStore, ["rankList"]),
  },
  methods: {
    ...mapActions(boardStore, ["getMainRank"]),
  },
  created() {
    this.getMainRank();
  },
};
</script>

<style scoped>
.rank {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
}
.ranking-content {
  position: relative;
  width: 16%;
  height: 50px;
  border: 0.5px solid #a1a1a1;
  border-radius: 30px;
  line-height: 48px;
  margin-top: 26px;
}
.ranking-content > span {
  margin: 0 2px;
}

.ranking-content .rank {
  position: absolute;
  font-family: "Metal";
  left: -14px;
  top: -2px;
  background-color: white;
  font-style: normal;
  font-weight: 400;
  font-size: 36px;
  color: #6465a6;
  width: 20px;
}
.ranking-content .name {
  font-size: 14px;
}
.ranking-content .point {
  font-size: 10px;
  color: #a1a1a1;
}
</style>
