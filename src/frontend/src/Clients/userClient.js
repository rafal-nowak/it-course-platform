import fetch from 'unfetch';
import {authHeaderWithApplicationJson} from "../Helpers/authHeaderWithApplicationJson";
import {checkStatus} from "../Helpers/checkStatus";

export const getUser = (userId) =>
    fetch(`http://localhost:8080/api/v1/users/${userId}`, {
        headers: authHeaderWithApplicationJson()
    })
        .then(checkStatus);

export const deleteUser = (userId) =>
    fetch(`http://localhost:8080/api/v1/users/${userId}`, {
        headers: authHeaderWithApplicationJson(),
        method: 'DELETE',
    })
        .then(checkStatus);

export const sendUser = user =>
    fetch("http://localhost:8080/api/v1/users", {
        headers: authHeaderWithApplicationJson(),
        method: 'PUT',
        body: JSON.stringify(user)
    })
        .then(checkStatus);

export const createUser = user =>
    fetch("http://localhost:8080/api/v1/users", {
        headers: authHeaderWithApplicationJson(),
        method: 'POST',
        body: JSON.stringify(user)
    })
        .then(checkStatus);

export const getUsers = (size = 3, page = 0) =>
    fetch(`http://localhost:8080/api/v1/users?size=${size}&page=${page}`, {
        headers: authHeaderWithApplicationJson(),
        method: 'GET',
    })
        .then(checkStatus);
