import fetch from 'unfetch';
import {authHeaderWithApplicationJson} from "../Helpers/authHeaderWithApplicationJson";
import {taskAssigmentRequest} from "../Helpers/taskAssigmentRequest";
import {checkStatus} from "../Helpers/checkStatus";

export const taskAssign = (userId, taskId) =>
    fetch("http://localhost:8080/api/v1/assign", {
        headers: authHeaderWithApplicationJson(),
        method: 'POST',
        body: JSON.stringify(taskAssigmentRequest(userId, taskId))
    })
        .then(checkStatus);

export const deleteTask = (taskId) =>
    fetch(`http://localhost:8080/api/v1/assign/${taskId}`, {
        headers: authHeaderWithApplicationJson(),
        method: 'DELETE',
    })
        .then(checkStatus);