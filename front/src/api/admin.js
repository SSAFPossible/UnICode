import { apiInstance } from "./index.js";

const api = apiInstance();

async function algorithmRegister(data, success, fail) {
  await api.post(`http://localhost:9090/algo`, data).then(success).catch(fail);
}

async function modifyAlgo(data, success, fail) {
  await api.put(`http://localhost:9090/algo`, data).then(success).catch(fail);
}

async function deleteAlgo(data, success, fail) {
  await api
    .delete(`http://localhost:9090/algo/${data.aid}`)
    .then(success)
    .catch(fail);
}

async function getAllAlgo(data, success, fail) {
  await api
    .post(`http://localhost:9090/algo/get`, data)
    .then(success)
    .catch(fail);
}

async function getAlgo(data, success, fail) {
  await api
    .get(`http://localhost:9090/algo/${data.aid}`)
    .then(success)
    .catch(fail);
}

export { algorithmRegister, modifyAlgo, deleteAlgo, getAllAlgo, getAlgo };
