import Vue from "vue";
import VueRouter from "vue-router";
import LoginView from "@/views/LoginView.vue";
import MainView from "@/views/MainView.vue";
import MainBoard from "@/components/board/MainBoard.vue";
import MainProject from "@/components/project/MainProject.vue";
import BoardView from "@/views/BoardView.vue";
import NoticeBoard from "@/components/board/NoticeBoard.vue";
import QuestionBoard from "@/components/board/QuestionBoard.vue";
import GeneralBoard from "@/components/board/GeneralBoard.vue";
import WriteFree from "@/components/board/WriteFreeBoard.vue";
import WriteQuestion from "@/components/board/WriteQuestionBoard.vue";
import WriteNotice from "@/components/board/WriteNoticeBoard.vue";
import SignupView from "@/views/SignupView.vue";
import BoardDetail from "@/components/board/BoardDetail.vue";
import ProjectDetail from "@/components/project/ProjectDetail.vue";
import ExhibitBoard from "@/components/project/ExhibitBoard.vue";
import RecruitBoard from "@/components/project/RecruitBoard.vue";
import WriteExhibitBoard from "@/components/project/WriteExhibitBoard.vue";
import WriteRecruitBoard from "@/components/project/WriteRecruitBoard.vue";
import ExhibitBoardDetail from "@/components/project/ExhibitBoardDetail.vue";
import AdminView from "@/views/AdminView.vue";
import AdminUser from "@/components/admin/AdminUser.vue";
import AdminAlgo from "@/components/admin/AdminAlgo.vue";
import WriteAlgorithm from "@/components/admin/WriteAlgorithm.vue";
import FindIdView from "@/views/FindIdView.vue";
import FindId from "@/components/FindId.vue";
import ResultFindId from "@/components/ResultFindId.vue";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "LoginView",
    component: LoginView,
  },
  {
    path: "/main",
    name: "MainView",
    component: MainView,
    redirect: "/main/board",
    children: [
      {
        path: "board",
        name: "board",
        component: MainBoard,
      },
      {
        path: "project",
        name: "project",
        component: MainProject,
      },
    ],
  },
  {
    path: "/boards",
    name: "BoardView",
    component: BoardView,
    redirect: "/boards/notice",
    children: [
      {
        path: "notice",
        name: "notice",
        component: NoticeBoard,
      },
      {
        path: "question",
        name: "question",
        component: QuestionBoard,
      },
      {
        path: "general",
        name: "general",
        component: GeneralBoard,
      },
    ],
  },
  {
    path: "/writeFree",
    name: "WriteFree",
    component: WriteFree,
  },
  {
    path: "/writeQuestion",
    name: "WriteQuestion",
    component: WriteQuestion,
  },
  {
    path: "/writeNotice",
    name: "WriteNotice",
    component: WriteNotice,
  },
  {
    path: "/signup",
    name: "SignupView",
    component: SignupView,
  },
  {
    path: "/detail",
    name: "BoardDetail",
    component: BoardDetail,
  },
  {
    path: "/pdetail",
    name: "ProjectDetail",
    component: ProjectDetail,
  },
  {
    path: "/recruit",
    name: "recruit",
    component: RecruitBoard,
  },
  {
    path: "/exhibit",
    name: "exhibit",
    component: ExhibitBoard,
  },
  {
    path: "/writeRecruit",
    name: "WriteRecruit",
    component: WriteRecruitBoard,
  },
  {
    path: "/WriteExhibit",
    name: "WriteExhibit",
    component: WriteExhibitBoard,
  },
  {
    path: "/exhibitDetail:bid",
    name: "ExhibitDetail",
    component: ExhibitBoardDetail,
  },
  {
    path: "/admin",
    name: "AdminView",
    component: AdminView,
    redirect: "/admin/user",
    children: [
      {
        path: "user",
        name: "AdminUser",
        component: AdminUser,
      },
      {
        path: "algorithm",
        name: "AdminAlgo",
        component: AdminAlgo,
      },
    ],
  },
  {
    path: "/writeAlgo",
    name: "writeAlgo",
    component: WriteAlgorithm,
  },
  {
    path: "/",
    name: "FindIdView",
    component: FindIdView,
    children: [
      {
        path: "findid",
        name: "FindId",
        component: FindId,
      },
      {
        path: "resultfind",
        name: "ResultFindId",
        component: ResultFindId,
      },
    ],
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
