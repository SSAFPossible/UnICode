<template>
  <div class="container">
    <div class="options"><button @click="moveTo">문제 추가</button></div>
    <b-tabs content-class="mt-3">
      <b-tab title="안 푼 문제" active>
        <BoardListPage type="unsolved"></BoardListPage>
      </b-tab>
      <b-tab title="푼 문제">
        <BoardListPage type="solved"></BoardListPage>
      </b-tab>
    </b-tabs>
  </div>
</template>

<script>
import BoardListPage from "@/components/admin/BoardListPage.vue";
import { mapActions, mapState } from "vuex";
const adminStore = "adminStore";
export default {
  name: "AdminAlgo",
  components: { BoardListPage },
  data() {
    return {
      items: [],
      fields: [
        { key: "aid", tdClass: "table-bid" },
        { key: "name", tdClass: "table-title" },
        { key: "site", tdClass: "table-hit" },
      ],
      perPage: 10,
      currentPage: 1,
    };
  },
  computed: {
    ...mapState(adminStore, ["algoSolved", "algoUnsolved"]),
    rows() {
      return this.items.length;
    },
  },
  methods: {
    ...mapActions(adminStore, ["getAlgorithmList"]),
    moveTo() {
      this.$router.push("/writeAlgo");
    },
    goDetail() {},
  },
  created() {
    this.getAlgorithmList(0);
    this.getAlgorithmList(1);
  },
  watch: {
    algoUnsolved: {
      handler: function (val) {
        this.items = [];
        val.forEach((element) => {
          this.items.push({
            aid: element["aid"],
            name: element["name"],
            site: element["site"],
          });
        });
      },
      immediate: true,
    },
  },
};
</script>

<style scoped>
.options button {
  /* visibility:hidden; */
  margin-right: 30px;
  float: right;
  border: none;
  background-color: white;
}
.line {
  clear: both;
  height: 1px;
  background: linear-gradient(270deg, #6465a6 -2.63%, #6465a6 99.99%);
}

.hidden_header {
  display: none;
}
.table-bid {
  width: 5%;
  line-height: 35px;
}
.table-title {
  width: 70%;
  text-align: left;
  line-height: 35px;
}
.table-hit {
  width: fit-content;
  color: #a1a1a1;
  line-height: 35px;
}

.page-link::v-deep > li > a {
  background-color: white;
  color: #5a4181;
}

.page-link::v-deep > li > a:focus,
.page-link::v-deep > li > a:hover,
.page-link::v-deep > li > span:focus,
.page-link::v-deep > li > span:hover {
  color: #5a5a5a;
  background-color: #eee;
  border-color: #ddd;
}

.page-link::v-deep > .active > a {
  color: white;
  background-color: #5a4181 !important;
  border: solid 1px #5a4181 !important;
}

.page-link::v-deep > .active > a:hover {
  background-color: #5a4181 !important;
  border: solid 1px #5a4181;
}
</style>
