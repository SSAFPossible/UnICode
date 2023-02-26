<template>
  <div class="container">
    <div class="search-black" v-if="boardStatus">
      <input
        class="search-input"
        v-model="search"
        placeholder="프로젝트 검색"
      />
      <button class="search-btn">
        <font-awesome-icon
          class="search-icon"
          icon="fa-solid fa-magnifying-glass"
        />
      </button>
    </div>

    <div class="tag">
      <div>
        <input
          type="checkbox"
          id="my-project"
          v-model="inputs.language"
          value="mine"
        /><label for="my-project"># 내가 참여한 프로젝트</label>
      </div>
      <div>
        <input
          type="checkbox"
          v-model="inputs.language"
          value="java"
          id="java"
        /><label for="java"># Java</label>
        <input
          type="checkbox"
          v-model="inputs.language"
          value="javascript"
          id="javascript"
        /><label for="javascript"># JavaScript</label>
        <input
          type="checkbox"
          v-model="inputs.language"
          value="vue"
          id="vue"
        /><label for="vue"># Vue</label>
        <input
          type="checkbox"
          v-model="inputs.language"
          value="react"
          id="react"
        /><label for="react"># React</label>
        <input
          type="checkbox"
          v-model="inputs.language"
          value="spring"
          id="spring"
        /><label for="spring"># Spring</label>
        <input
          type="checkbox"
          v-model="inputs.language"
          value="nodejs"
          id="nodejs"
        /><label for="nodejs"># Node.js</label>
      </div>
      <div>
        <input
          type="checkbox"
          v-model="inputs.language"
          value="mysql"
          id="mysql"
        /><label for="mysql"># MySQL</label>
        <input
          type="checkbox"
          v-model="inputs.language"
          value="kotlin"
          id="kotlin"
        /><label for="kotlin"># Kotlin</label>
        <input
          type="checkbox"
          v-model="inputs.language"
          value="python"
          id="python"
        /><label for="python"># Python</label>
        <input
          type="checkbox"
          v-model="inputs.language"
          value="django"
          id="django"
        /><label for="django"># Django</label>
        <input
          type="checkbox"
          v-model="inputs.language"
          value="aws"
          id="aws"
        /><label for="aws"># AWS</label>
        <input
          type="checkbox"
          v-model="inputs.language"
          value="docker"
          id="docker"
        /><label for="docker"># Docker</label>
        <input
          type="checkbox"
          v-model="inputs.language"
          value="git"
          id="git"
        /><label for="git"># Git</label>
        <input
          type="checkbox"
          v-model="inputs.language"
          value="c"
          id="c"
        /><label for="c"># C</label>
      </div>
    </div>

    <ExhibitBoard :search="search"></ExhibitBoard>

    <div class="layer-buttons">
      <!-- <div class="layer-button" @click="moveTo('recruit')" v-if="!status"> -->
      <div class="layer-button" @click="moveToRecruit" v-if="!status">
        <font-awesome-icon icon="fa-solid fa-user-plus" />
      </div>
      <div class="layer-button" @click="moveTo('exhibit')" v-if="!status">
        <font-awesome-icon icon="fa-solid fa-file-circle-plus" />
      </div>
      <div class="layer-button" @click="changeStatus">
        <font-awesome-icon icon="fa-solid fa-circle-plus" />
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions, mapMutations, mapState } from "vuex";
import ExhibitBoard from "@/components/project/ExhibitBoard.vue";
const projectStore = "projectStore";
export default {
  name: "MainProject",
  data() {
    return {
      search: "",
      status: "false",
      inputs: {
        language: [],
        sortLike: false,
      },
    };
  },
  methods: {
    ...mapActions(projectStore, ["getExhibitBoard"]),
    ...mapMutations(projectStore, ["SET_BOARD_STATUS"]),
    changeStatus() {
      this.status = !this.status;
    },
    moveTo(path) {
      this.$router.push(`/write${path}`);
    },
    moveToRecruit() {
      this.$router.push({ name: "recruit" });
    },
  },
  components: {
    ExhibitBoard,
  },
  created() {
    this.getExhibitBoard(this.inputs);
    this.SET_BOARD_STATUS(true);
  },
  computed: {
    ...mapState(projectStore, ["projectList", "boardStatus"]),
  },
  watch: {
    inputs: {
      handler: function (val) {
        this.getExhibitBoard(val);
      },
      deep: true,
    },
  },
};
</script>

<style scoped>
.container {
  margin-top: 60px;
  font-size: 14px;
}
.search {
  margin-bottom: 60px;
}
.search-input {
  border: none;
  border-bottom: solid 1px black;
  width: 45%;
  padding-bottom: 10px;
}
.search-btn {
  background-color: transparent;
  border: none;
  width: 20px;
}

.search-black {
  margin-bottom: 60px;
}
.search-black .search-input {
  border: none;
  border-bottom: solid 1px white;
  width: 45%;
  padding-bottom: 10px;
  background-color: transparent;
  color: white;
}
.search-black .search-btn {
  background-color: transparent;
  border: none;
  width: 20px;
  color: white;
}

.tag {
  margin-bottom: 160px;
  display: flex;
  justify-content: center;
  flex-flow: column;
}
.tag-btn {
  background: rgba(194, 150, 195, 0.5);
  border-radius: 12px;
  width: 200px;
  margin-bottom: 15px;
}

.layer-buttons {
  position: fixed;
  bottom: 5%;
  right: 2%;
}

.layer-button {
  width: 90px;
  height: 90px;
  color: white;
  background-color: transparent;
  font-size: 56px;
  text-align: center;
  line-height: 90px;
  cursor: pointer;
  opacity: 50%;
}

input[id="my-project"] + label {
  width: fit-content;
  height: 24px;
  line-height: 24px;
  padding: 0 30px;
  background: rgba(194, 150, 195, 0.5);
  border-radius: 12px;
  margin: 0px 8px 12px 8px;
  cursor: pointer;
}
input[id="my-project"]:checked + label {
  box-shadow: inset 0px 4px 4px rgba(0, 0, 0, 0.25);
}
input[id="my-project"] {
  display: none;
}

input[id="java"] + label {
  width: fit-content;
  height: 24px;
  line-height: 24px;
  padding: 0 30px;
  background: rgba(100, 101, 166, 0.5);
  border-radius: 12px;
  margin: 0px 8px 12px 8px;
  cursor: pointer;
}
input[id="java"]:checked + label {
  box-shadow: inset 0px 4px 4px rgba(0, 0, 0, 0.25);
}
input[id="java"] {
  display: none;
}

input[id="javascript"] + label {
  width: fit-content;
  height: 24px;
  line-height: 24px;
  padding: 0 30px;
  background: rgba(150, 195, 157, 0.5);
  border-radius: 12px;
  margin: 0px 8px 12px 8px;
  cursor: pointer;
}
input[id="javascript"]:checked + label {
  box-shadow: inset 0px 4px 4px rgba(0, 0, 0, 0.25);
}
input[id="javascript"] {
  display: none;
}

input[id="vue"] + label {
  width: fit-content;
  height: 24px;
  line-height: 24px;
  padding: 0 30px;
  background: rgba(255, 255, 255, 0.5);
  border-radius: 12px;
  margin: 0px 8px 12px 8px;
  cursor: pointer;
}
input[id="vue"]:checked + label {
  box-shadow: inset 0px 4px 4px rgba(0, 0, 0, 0.25);
}
input[id="vue"] {
  display: none;
}

input[id="react"] + label {
  width: fit-content;
  height: 24px;
  line-height: 24px;
  padding: 0 30px;
  background: rgba(150, 195, 157, 0.5);
  border-radius: 12px;
  margin: 0px 8px 12px 8px;
  cursor: pointer;
}
input[id="react"]:checked + label {
  box-shadow: inset 0px 4px 4px rgba(0, 0, 0, 0.25);
}
input[id="react"] {
  display: none;
}

input[id="spring"] + label {
  width: fit-content;
  height: 24px;
  line-height: 24px;
  padding: 0 30px;
  background: rgba(115, 115, 115, 0.5);
  border-radius: 12px;
  margin: 0px 8px 12px 8px;
  cursor: pointer;
}
input[id="spring"]:checked + label {
  box-shadow: inset 0px 4px 4px rgba(0, 0, 0, 0.25);
}
input[id="spring"] {
  display: none;
}

input[id="nodejs"] + label {
  width: fit-content;
  height: 24px;
  line-height: 24px;
  padding: 0 30px;
  background: rgba(134, 170, 197, 0.5);
  border-radius: 12px;
  margin: 0px 8px 12px 8px;
  cursor: pointer;
}
input[id="nodejs"]:checked + label {
  box-shadow: inset 0px 4px 4px rgba(0, 0, 0, 0.25);
}
input[id="nodejs"] {
  display: none;
}

input[id="mysql"] + label {
  width: fit-content;
  height: 24px;
  line-height: 24px;
  padding: 0 30px;
  background: rgba(100, 101, 166, 0.5);
  border-radius: 12px;
  margin: 0px 8px 12px 8px;
  cursor: pointer;
}
input[id="mysql"]:checked + label {
  box-shadow: inset 0px 4px 4px rgba(0, 0, 0, 0.25);
}
input[id="mysql"] {
  display: none;
}

input[id="kotlin"] + label {
  width: fit-content;
  height: 24px;
  line-height: 24px;
  padding: 0 30px;
  background: rgba(255, 255, 255, 0.5);
  border-radius: 12px;
  margin: 0px 8px 12px 8px;
  cursor: pointer;
}
input[id="kotlin"]:checked + label {
  box-shadow: inset 0px 4px 4px rgba(0, 0, 0, 0.25);
}
input[id="kotlin"] {
  display: none;
}

input[id="python"] + label {
  width: fit-content;
  height: 24px;
  line-height: 24px;
  padding: 0 30px;
  background: rgba(134, 170, 197, 0.5);
  border-radius: 12px;
  margin: 0px 8px 12px 8px;
  cursor: pointer;
}
input[id="python"]:checked + label {
  box-shadow: inset 0px 4px 4px rgba(0, 0, 0, 0.25);
}
input[id="python"] {
  display: none;
}

input[id="django"] + label {
  width: fit-content;
  height: 24px;
  line-height: 24px;
  padding: 0 30px;
  background: rgba(115, 115, 115, 0.5);
  border-radius: 12px;
  margin: 0px 8px 12px 8px;
  cursor: pointer;
}
input[id="django"]:checked + label {
  box-shadow: inset 0px 4px 4px rgba(0, 0, 0, 0.25);
}
input[id="django"] {
  display: none;
}

input[id="aws"] + label {
  width: fit-content;
  height: 24px;
  line-height: 24px;
  padding: 0 30px;
  background: rgba(194, 150, 195, 0.5);
  border-radius: 12px;
  margin: 0px 8px 12px 8px;
  cursor: pointer;
}
input[id="aws"]:checked + label {
  box-shadow: inset 0px 4px 4px rgba(0, 0, 0, 0.25);
}
input[id="aws"] {
  display: none;
}

input[id="docker"] + label {
  width: fit-content;
  height: 24px;
  line-height: 24px;
  padding: 0 30px;
  background: rgba(150, 195, 157, 0.5);
  border-radius: 12px;
  margin: 0px 8px 12px 8px;
  cursor: pointer;
}
input[id="docker"]:checked + label {
  box-shadow: inset 0px 4px 4px rgba(0, 0, 0, 0.25);
}
input[id="docker"] {
  display: none;
}

input[id="git"] + label {
  width: fit-content;
  height: 24px;
  line-height: 24px;
  padding: 0 30px;
  background: rgba(150, 195, 157, 0.5);
  border-radius: 12px;
  margin: 0px 8px 12px 8px;
  cursor: pointer;
}
input[id="git"]:checked + label {
  box-shadow: inset 0px 4px 4px rgba(0, 0, 0, 0.25);
}
input[id="git"] {
  display: none;
}

input[id="c"] + label {
  width: fit-content;
  height: 24px;
  line-height: 24px;
  padding: 0 30px;
  background: rgba(100, 101, 166, 0.5);
  border-radius: 12px;
  margin: 0px 8px 12px 8px;
  cursor: pointer;
}
input[id="c"]:checked + label {
  box-shadow: inset 0px 4px 4px rgba(0, 0, 0, 0.25);
}
input[id="c"] {
  display: none;
}
</style>
