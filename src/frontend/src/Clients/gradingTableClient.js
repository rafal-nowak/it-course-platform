import fetch from 'unfetch';
import {authHeaderWithApplicationJson} from "../Helpers/authHeaderWithApplicationJson";
import {checkStatus} from "../Helpers/checkStatus";

export const getGradingTable = (tableId) =>
    fetch(`http://localhost:8080/api/v1/grading-tables/${tableId}`, {
        headers: authHeaderWithApplicationJson()
    })
        .then(checkStatus);

export const deleteGradingTable = (tableId) =>
    fetch(`http://localhost:8080/api/v1/grading-tables/${tableId}`, {
        headers: authHeaderWithApplicationJson(),
        method: 'DELETE',
    })
        .then(checkStatus);

export const sendGradingTable = gradingTable =>
    fetch("http://localhost:8080/api/v1/grading-tables", {
        headers: authHeaderWithApplicationJson(),
        method: 'PUT',
        body: JSON.stringify(gradingTable)
    })
        .then(checkStatus);

export const createGradingTable = gradingTable =>
    fetch("http://localhost:8080/api/v1/grading-tables", {
        headers: authHeaderWithApplicationJson(),
        method: 'POST',
        body: JSON.stringify(gradingTable)
    })
        .then(checkStatus);

export const getGradingTables = (size = 3, page = 0) =>
    fetch(`http://localhost:8080/api/v1/grading-tables?size=${size}&page=${page}`, {
        headers: authHeaderWithApplicationJson(),
        method: 'GET',
    })
        .then(checkStatus);
