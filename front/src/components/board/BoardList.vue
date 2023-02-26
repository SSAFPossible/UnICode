<template>
  <div>
    <b-table
      :items="items"
      :fields="fields"
      @row-clicked="goDetail"
      thead-class="hidden_header"
    >
      <template #head(hitname)> </template>
      <template v-slot:cell(hitname)> 조회수 </template>
    </b-table>
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";
const boardStore = "boardStore";
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
    };
  },
  computed: {
    ...mapState(boardStore, ["freeBoard", "noticeBoard", "questionBoard"]),
    rows() {
      return this.items.length;
    },
  },
  methods: {
    ...mapActions(boardStore, [
      "getFreeBoardOne",
      "getNoticeBoardOne",
      "getQuestionBoardOne",
    ]),
    goDetail(item) {
      if (item.bcid === "free") {
        this.getFreeBoardOne(item.bid);
      } else if (item.bcid === "notice") {
        this.getNoticeBoardOne(item.bid);
      } else {
        this.getQuestionBoardOne(item.bid);
      }
      this.$router.push("/detail");
    },
  },
  watch: {
    freeBoard: {
      handler: function () {
        if (this.type === "general") {
          this.items = [];
          for (let index = 0; index < 3; index++) {
            const element = this.freeBoard[index];
            this.items.push({
              bid: element["bid"],
              title: element["title"],
              hit: element["hit"],
              bcid: element["bcid"],
            });
          }
        }
      },
      immediate: true,
      deep: true,
    },
    noticeBoard: {
      handler: function () {
        if (this.type === "notice") {
          this.items = [];
          for (let index = 0; index < 3; index++) {
            const element = this.noticeBoard[index];
            this.items.push({
              bid: element["bid"],
              title: element["title"],
              hit: element["hit"],
              bcid: element["bcid"],
            });
          }
        }
      },
      immediate: true,
      deep: true,
    },
    questionBoard: {
      handler: function () {
        if (this.type === "question") {
          this.items = [];
          for (let index = 0; index < 3; index++) {
            const element = this.questionBoard[index];
            this.items.push({
              bid: element["bid"],
              title: element["title"],
              hit: element["hit"],
              bcid: element["bcid"],
            });
          }
        }
      },
      immediate: true,
      deep: true,
    },
  },
  props: {
    type: String,
  },
};
</script>

<style>
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
</style>
