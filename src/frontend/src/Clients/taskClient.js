import fetch from 'unfetch';
import {authHeaderWithApplicationJson} from "../Helpers/authHeaderWithApplicationJson";
import {authHeader} from "../Helpers/authHeader";
import {checkStatus} from "../Helpers/checkStatus";

export const getTask = (taskId) =>
    fetch(`http://localhost:8080/api/v1/tasks/${taskId}`, {
        headers: authHeaderWithApplicationJson()
    })
        .then(checkStatus);

export const getTasks = (size = 3, page = 0) =>
    fetch(`http://localhost:8080/api/v1/tasks?size=${size}&page=${page}`, {
        headers: authHeaderWithApplicationJson(),
        method: 'GET',
    })
        .then(checkStatus);

export const sendTaskCommand = (taskId, taskCommand) =>
    fetch(`http://localhost:8080/api/v1/tasks/${taskId}/commands`, {
        headers: authHeaderWithApplicationJson(),
        method: 'POST',
        body: JSON.stringify(taskCommand())
    })
        .then(checkStatus);

export const getFilesAssignedToUserTask = (taskId) =>
    fetch(`http://localhost:8080/api/v1/tasks/${taskId}/files`, {
        headers: authHeaderWithApplicationJson()
    })
        .then(checkStatus);

export const getFileAssignedToUserTask = (taskId, fileId) =>
    fetch(`http://localhost:8080/api/v1/tasks/${taskId}/files/${fileId}`, {
        headers: authHeaderWithApplicationJson()
    })
        .then(checkStatus);

export const postFileAssignedToUserTask = (taskId, fileId, fileContent) =>
    fetch(`http://localhost:8080/api/v1/tasks/${taskId}/files/${fileId}`, {
        headers: authHeader(),
        method: 'POST',
        body: fileContent
    })
        .then(checkStatus);

export const getTaskResults = (taskId) =>
    fetch(`http://localhost:8080/api/v1/tasks/${taskId}/results`, {
        headers: authHeaderWithApplicationJson()
    })
        .then(checkStatus);