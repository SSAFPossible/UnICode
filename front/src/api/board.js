import { apiInstance } from "./index.js";

const api = apiInstance();

// 등록
async function freeBoardregister(data, success, fail) {
  await api
    .post(`http://localhost:9090/free`, data, {
      headers: { "Content-Type": "multipart/form-data" },
    })
    .then(success)
    .catch(fail);
}
async function noticeBoardregister(data, success, fail) {
  await api
    .post(`http://localhost:9090/notice`, data, {
      headers: { "Content-Type": "multipart/form-data" },
    })
    .then(success)
    .catch(fail);
}
async function questionBoardregister(data, success, fail) {
  await api
    .post(`http://localhost:9090/study`, data, {
      headers: { "Content-Type": "multipart/form-data" },
    })
    .then(success)
    .catch(fail);
}

// 조회
async function freeBoardList(success, fail) {
  await api.get(`http://localhost:9090/free`).then(success).catch(fail);
}
async function questionBoardList(data, success, fail) {
  await api
    .post(`http://localhost:9090/study/get`, data)
    .then(success)
    .catch(fail);
}
async function noticeBoardList(success, fail) {
  await api.get(`http://localhost:9090/notice`).then(success).catch(fail);
}

async function freeBoardOne(data, success, fail) {
  console.log(data);
  await api.get(`http://localhost:9090/free/${data}`).then(success).catch(fail);
}
async function noticeBoardOne(data, success, fail) {
  console.log(data);
  await api
    .get(`http://localhost:9090/notice/${data}`)
    .then(success)
    .catch(fail);
}
async function questionBoardOne(data, success, fail) {
  console.log(data);
  await api
    .get(`http://localhost:9090/study/${data}`)
    .then(success)
    .catch(fail);
}

// 수정
async function freeBoardModify(data, success, fail) {
  await api
    .put(`http://localhost:9090/free`, data, {
      headers: { "Content-Type": "multipart/form-data" },
    })
    .then(success)
    .catch(fail);
}
async function noticeBoardModify(data, success, fail) {
  await api
    .put(`http://localhost:9090/notice`, data, {
      headers: { "Content-Type": "multipart/form-data" },
    })
    .then(success)
    .catch(fail);
}
async function studyBoardModify(data, success, fail) {
  await api
    .put(`http://localhost:9090/study`, data, {
      headers: { "Content-Type": "multipart/form-data" },
    })
    .then(success)
    .catch(fail);
}

// 삭제
async function freeBoardDelete(data, success, fail) {
  await api
    .delete(`http://localhost:9090/free`, {
      data: { uid: data.uid, bid: data.bid },
    })
    .then(success)
    .catch(fail);
}
async function studyBoardDelete(data, success, fail) {
  await api
    .delete(`http://localhost:9090/study`, {
      data: { uid: data.uid, bid: data.bid },
    })
    .then(success)
    .catch(fail);
}
async function noticeBoardDelete(data, success, fail) {
  await api
    .delete(`http://localhost:9090/notice`, {
      data: { uid: data.uid, bid: data.bid },
    })
    .then(success)
    .catch(fail);
}

// 좋아요
async function studyBoardLike(data, success, fail) {
  console.log(data);
  await api
    .post(`http://localhost:9090/study/like`, data)
    .then(success)
    .catch(fail);
}

async function mainBoardRank(success, fail) {
  api.defaults.headers["jwt-auth-token"] =
    sessionStorage.getItem("jwt-auth-token");
  await api
    .get(`http://localhost:9090/api/user/rank`)
    .then(success)
    .catch(fail);
}

async function mainBoardAlgo(success, fail) {
  await api.get(`http://localhost:9090/algo/pick`).then(success).catch(fail);
}

export {
  freeBoardregister,
  noticeBoardregister,
  questionBoardregister,
  freeBoardList,
  questionBoardList,
  noticeBoardList,
  freeBoardOne,
  noticeBoardOne,
  questionBoardOne,
  freeBoardModify,
  noticeBoardModify,
  studyBoardModify,
  freeBoardDelete,
  studyBoardDelete,
  noticeBoardDelete,
  studyBoardLike,
  mainBoardRank,
  mainBoardAlgo,
};
