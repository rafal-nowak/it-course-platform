import fetch from 'unfetch';
import {authHeaderWithApplicationJson} from "../Helpers/authHeaderWithApplicationJson";
import {checkStatus} from "../Helpers/checkStatus";

export const getTaskBlueprint = (id) =>
    fetch(`http://localhost:8080/api/v1/task-blueprints/${id}`, {
        headers: authHeaderWithApplicationJson()
    })
        .then(checkStatus);

export const deleteTaskBlueprint = taskBlueprint =>
    fetch(`http://localhost:8080/api/v1/task-blueprints/`, {
        headers: authHeaderWithApplicationJson(),
        method: 'DELETE',
        body: JSON.stringify(taskBlueprint)
    })
        .then(checkStatus);

export const sendTaskBlueprint = taskBlueprint =>
    fetch("http://localhost:8080/api/v1/task-blueprints", {
        headers: authHeaderWithApplicationJson(),
        method: 'PUT',
        body: JSON.stringify(taskBlueprint)
    })
        .then(checkStatus);

export const createTaskBlueprint = quizTemplate =>
    fetch("http://localhost:8080/api/v1/task-blueprints", {
        headers: authHeaderWithApplicationJson(),
        method: 'POST',
        body: JSON.stringify(quizTemplate)
    })
        .then(checkStatus);

export const getTaskBlueprints = (size = 3, page = 0) =>
    fetch(`http://localhost:8080/api/v1/task-blueprints?size=${size}&page=${page}`, {
        headers: authHeaderWithApplicationJson(),
        method: 'GET',
    })
        .then(checkStatus);
