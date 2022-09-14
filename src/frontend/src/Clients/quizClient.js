import fetch from 'unfetch';
import {authHeaderWithApplicationJson} from "../Helpers/authHeaderWithApplicationJson";
import {checkStatus} from "../Helpers/checkStatus";

export const getQuiz = (quizId) =>
    fetch(`http://localhost:8080/api/v1/quizzes/${quizId}`, {
        headers: authHeaderWithApplicationJson()
    })
        .then(checkStatus);

export const sendQuiz = quizResult =>
    fetch("http://localhost:8080/api/v1/quizzes", {
        headers: authHeaderWithApplicationJson(),
        method: 'PUT',
        body: JSON.stringify(quizResult)
    })
        .then(checkStatus);

export const getQuizzes = (size = 3, page = 0) =>
    fetch(`http://localhost:8080/api/v1/quizzes?size=${size}&page=${page}`, {
        headers: authHeaderWithApplicationJson(),
        method: 'GET',
    })
        .then(checkStatus);

export const sendQuizCommand = (quizId, quizCommand) =>
    fetch(`http://localhost:8080/api/v1/quizzes/${quizId}/commands`, {
        headers: authHeaderWithApplicationJson(),
        method: 'POST',
        body: JSON.stringify(quizCommand())
    })
        .then(checkStatus);

export const deleteQuiz = (quizId) =>
    fetch(`http://localhost:8080/api/v1/quizzes/${quizId}`, {
        headers: authHeaderWithApplicationJson(),
        method: 'DELETE'
    })
        .then(checkStatus);
