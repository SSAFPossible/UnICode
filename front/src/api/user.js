import { apiInstance } from "./index.js";

const api = apiInstance();

async function login(user, success, fail) {
  console.log(user);
  await api
    .post(`/api/user/login`, JSON.stringify(user))
    .then(success)
    .catch(fail);
}

async function findById(userid, success, fail) {
  api.defaults.headers["jwt-auth-token"] =
    sessionStorage.getItem("jwt-auth-token");
  await api.get(`/api/user/${userid}`).then(success).catch(fail);
}

async function tokenRegeneration(user, success, fail) {
  api.defaults.headers["refresh-token"] =
    sessionStorage.getItem("refresh-token"); //axios header에 refresh-token 셋팅
  await api.post(`/api/user/refresh`, user).then(success).catch(fail);
}

async function logout(userid, success, fail) {
  api.defaults.headers["jwt-auth-token"] =
    sessionStorage.getItem("jwt-auth-token");
  console.log(userid);
  await api
    .post(`/api/user/logout`, null, { params: { uid: userid } })
    .then(success)
    .catch(fail);
}

//회원가입
async function register(user, success, fail) {
  console.log("USERRRR", user);
  api.defaults.headers["jwt-auth-token"] =
    sessionStorage.getItem("jwt-auth-token");
  await api
    .post(`/api/user/regist`, user, {
      headers: { "Content-Type": "multipart/form-data" },
    })
    .then(success)
    .catch(fail);
}

//회원삭제
async function deleteUser(userid, success, fail) {
  await api.delete(`/api/user/${userid}`).then(success).catch(fail);
}

async function updateUser(user, success, fail) {
  await api.put(`/api/user`, JSON.stringify(user)).then(success).catch(fail);
}

async function userIdFind(user, success, fail) {
  console.log(user.email);
  await api
    .post(`/api/user/find/id`, { email: user.email })
    .then(success)
    .catch(fail);
}

export {
  login,
  findById,
  tokenRegeneration,
  logout,
  register,
  deleteUser,
  updateUser,
  userIdFind,
};
