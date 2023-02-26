import { apiInstance } from "./index.js";

const api = apiInstance();

// ExhibitBoard
async function exhibitBoardregister(data, success, fail) {
  await api
    .post(`http://localhost:9090/exhibit`, data, {
      headers: { "Content-Type": "multipart/form-data" },
    })
    .then(success)
    .catch(fail);
}
async function exhibitBoardList(data, success, fail) {
  console.log(data);
  await api
    .post(`http://localhost:9090/exhibit/get`, data)
    .then(success)
    .catch(fail);
}
async function exhibitBoardModify(data, success, fail) {
  console.log(data);
  await api
    .put(`http://localhost:9090/exhibit`, data, {
      headers: { "Content-Type": "multipart/form-data" },
    })
    .then(success)
    .catch(fail);
}
async function exhibitBoardDelete(data, success, fail) {
  console.log("111111111111", data);
  await api
    .delete(`http://localhost:9090/exhibit`, {
      data: { uid: data.uid, bid: data.bid },
    })
    .then(success)
    .catch(fail);
}
async function exhibitBoardLike(data, success, fail) {
  console.log(data);
  await api
    .post(`http://localhost:9090/exhibit/like`, data)
    .then(success)
    .catch(fail);
}
async function exhibitBoardOne(data, success, fail) {
  console.log(data);
  await api
    .get(`http://localhost:9090/exhibit/${data}`)
    .then(success)
    .catch(fail);
}

//RecruitBoard
async function recruitBoardregister(data, success, fail) {
  await api
    .post(`http://localhost:9090/recruit`, data, {
      headers: { "Content-Type": "multipart/form-data" },
    })
    .then(success)
    .catch(fail);
}
async function recruitBoardList(data, success, fail) {
  console.log(data);
  await api
    .post(`http://localhost:9090/recruit/get`, data)
    .then(success)
    .catch(fail);
}
async function recruitBoardModify(data, success, fail) {
  console.log(data);
  await api
    .put(`http://localhost:9090/recruit`, data, {
      headers: { "Content-Type": "multipart/form-data" },
    })
    .then(success)
    .catch(fail);
}
async function recruitBoardDelete(data, success, fail) {
  console.log(data);
  await api
    .delete(`http://localhost:9090/recruit`, {
      data: { uid: data.uid, bid: data.bid },
    })
    .then(success)
    .catch(fail);
}
async function recruitBoardLike(data, success, fail) {
  console.log(data);
  await api
    .post(`http://localhost:9090/recruit/like`, data)
    .then(success)
    .catch(fail);
}
async function recruitBoardOne(data, success, fail) {
  console.log(data);
  await api
    .get(`http://localhost:9090/recruit/${data}`)
    .then(success)
    .catch(fail);
}

//Comment
async function writeFirstComment(data, success, fail) {
  console.log(data);
  await api
    .post(`http://localhost:9090/comment/first`, data)
    .then(success)
    .catch(fail);
}
async function writeSecondComment(data, success, fail) {
  console.log(data);
  await api
    .post(`http://localhost:9090/comment/second`, data)
    .then(success)
    .catch(fail);
}
async function modifyComment(data, success, fail) {
  console.log("mod", data.cid);
  console.log("mod", data.comment);
  await api
    .put(`http://localhost:9090/comment`, {
      data: { cid: data.cid, comment: data.comment },
    })
    .then(success)
    .catch(fail);
}
async function deleteComment(data, success, fail) {
  console.log(data);
  await api
    .delete(`http://localhost:9090/comment`, {
      data: { uid: data.uid, cid: data.cid },
    })
    .then(success)
    .catch(fail);
}
async function getComment(data, success, fail) {
  console.log(data);
  await api
    .get(`http://localhost:9090/comment/${data}`)
    .then(success)
    .catch(fail);
}

//FileDownload
async function getTermsConditions(data, success, fail) {
  console.log(data.folder);
  await api
    .get(`http://localhost:9090/files/${data.folder}/${data.name}`)
    .then(success)
    .catch(fail);
}

export {
  exhibitBoardregister,
  exhibitBoardList,
  exhibitBoardModify,
  exhibitBoardDelete,
  exhibitBoardLike,
  exhibitBoardOne,
  recruitBoardLike,
  recruitBoardDelete,
  recruitBoardModify,
  recruitBoardList,
  recruitBoardregister,
  recruitBoardOne,
  writeFirstComment,
  writeSecondComment,
  modifyComment,
  deleteComment,
  getComment,
  getTermsConditions,
};
