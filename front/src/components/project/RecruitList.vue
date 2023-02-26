<template>
  <div>
    <b-table
      id="board-list"
      :items="items"
      :fields="fields"
      :per-page="perPage"
      :current-page="currentPage"
      thead-class="hidden_header"
      @row-clicked="goDetail"
    >
      <template #head(hitname)> </template>
      <template v-slot:cell(hitname)> 조회수 </template>
    </b-table>
    <b-pagination
      v-model="currentPage"
      :total-rows="rows"
      :per-page="perPage"
      aria-controls="board-list"
      align="center"
    ></b-pagination>
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";
const projectStore = "projectStore";
export default {
  data() {
    return {
      items: [],
      fields: [
        { key: "bid", tdClass: "table-bid" },
        { key: "title", tdClass: "table-title" },
        { key: "hitname", tdClass: "table-hit" },
        { key: "hit", tdClass: "table-hit" },
      ],
      perPage: 10,
      currentPage: 1,
    };
  },
  computed: {
    ...mapState(projectStore, ["recruitList"]),
    rows() {
      return this.items.length;
    },
  },
  methods: {
    ...mapActions(projectStore, ["getRecruitBoardOne"]),
    async goDetail(item) {
      await this.getRecruitBoardOne(item.bid);
      this.$router.push("/pdetail");
    },
  },
  watch: {
    recruitList: {
      handler: function (val) {
        this.items = [];
        val.forEach((element) => {
          this.items.push({
            bid: element["bid"],
            title: element["title"],
            hit: element["hit"],
            bcid: element["bcid"],
          });
        });
      },
      immediate: true,
    },
  },
};
</script>

<style scoped>
.hidden_header {
  display: none;
}
.table {
  color: white;
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
