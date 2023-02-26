<template>
  <div>
    <div class="grid-container">
      <div
        class="img-container"
        v-for="(item, index) in filtered"
        :key="index"
        @click="goDetail(item)"
      >
        <div class="img-wrapper">
          <img
            class="main-img"
            :src="`http://localhost:9090/upload/${item.project.mainImg.saveFolder}/${item.project.mainImg.saveFile}`"
            @error="replaceByDefault"
            alt=""
          />
        </div>
        <div class="icon">
          <font-awesome-icon class="like" icon="fa-regular fa-heart" />
          <div class="like-count">{{ item.likeCnt }}</div>
          <font-awesome-icon class="hit" icon="fa-regular fa-circle-check" />
          <div class="hit-count">{{ item.hit }}</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";
import img from "@/assets/temp.jpg";
const projectStore = "projectStore";
export default {
  data() {
    return {};
  },
  props: {
    search: String,
  },
  computed: {
    ...mapState(projectStore, ["projectList"]),
    filtered() {
      if (this.search) {
        return this.projectList.filter((item) => {
          return item.title.startsWith(this.search.toLowerCase());
        });
      } else {
        return this.projectList;
      }
    },
  },
  methods: {
    ...mapActions(projectStore, ["getExhibitBoardOne"]),
    goDetail(element) {
      this.getExhibitBoardOne(element.bid);

      this.$router.push(`/exhibitDetail:${element.bid}`);
    },
    replaceByDefault(e) {
      e.target.src = img;
    },
  },
};
</script>

<style scoped>
.grid-container {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(240px, 1fr));
  grid-template-rows: repeat(auto-fill, minmax(190px, 1fr));
  justify-content: center;
  align-items: center;
}
.grid-container .main-img {
  object-fit: cover;
}
.grid-container .img-wrapper:hover {
  transition: 0.3s;
  filter: brightness(0.4);
}

.grid-container > div:hover .icon {
  display: block;
  transition: 0.3s;
}
.img-container {
  position: relative;
}
.img-container .icon {
  color: white;
  display: none;
}
.img-wrapper {
  width: 100%;
  min-height: 100%;
}
.like {
  position: absolute;
  top: 5px;
  right: 50px;
  font-size: 24px;
}
.like-count {
  position: absolute;
  top: 30px;
  right: 59px;
  font-size: 12px;
}
.hit {
  position: absolute;
  top: 5px;
  right: 10px;
  font-size: 24px;
}
.hit-count {
  position: absolute;
  top: 30px;
  right: 19px;
  font-size: 12px;
}
</style>
