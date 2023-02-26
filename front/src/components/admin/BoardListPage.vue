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
import { mapActions, mapState } from "vuex";
const adminStore = "adminStore";
export default {
  name: "AdminAlgo",
  components: {},
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
      this.$router.push("/writeNotice");
    },
    goDetail() {},
  },
  watch: {
    algoUnsolved: {
      handler: function (val) {
        if (this.type === "unsolved") {
          this.items = [];
          val.forEach((element) => {
            this.items.push({
              aid: element["aid"],
              name: element["name"],
              site: element["site"],
            });
          });
        }
      },
      immediate: true,
    },
    algoSolved: {
      handler: function (val) {
        if (this.type === "solved") {
          this.items = [];
          val.forEach((element) => {
            this.items.push({
              aid: element["aid"],
              name: element["name"],
              site: element["site"],
            });
          });
        }
      },
      immediate: true,
    },
  },
  props: {
    type: String,
  },
};
</script>

<style scoped>
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
