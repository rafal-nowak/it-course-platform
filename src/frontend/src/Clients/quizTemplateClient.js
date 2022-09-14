import fetch from 'unfetch';
import {authHeaderWithApplicationJson} from "../Helpers/authHeaderWithApplicationJson";
import {checkStatus} from "../Helpers/checkStatus";

export const getQuizTemplate = (quizId) =>
    fetch(`http://localhost:8080/api/v1/quiz-templates/${quizId}`, {
        headers: authHeaderWithApplicationJson()
    })
        .then(checkStatus);

export const deleteQuizTemplate = (quizId) =>
    fetch(`http://localhost:8080/api/v1/quiz-templates/${quizId}`, {
        headers: authHeaderWithApplicationJson(),
        method: 'DELETE',
    })
        .then(checkStatus);

export const sendQuizTemplate = quizTemplate =>
    fetch("http://localhost:8080/api/v1/quiz-templates", {
        headers: authHeaderWithApplicationJson(),
        method: 'PUT',
        body: JSON.stringify(quizTemplate)
    })
        .then(checkStatus);

export const createQuizTemplate = quizTemplate =>
    fetch("http://localhost:8080/api/v1/quiz-templates", {
        headers: authHeaderWithApplicationJson(),
        method: 'POST',
        body: JSON.stringify(quizTemplate)
    })
        .then(checkStatus);

export const getQuizTemplates = (size = 3, page = 0) =>
    fetch(`http://localhost:8080/api/v1/quiz-templates?size=${size}&page=${page}`, {
        headers: authHeaderWithApplicationJson(),
        method: 'GET',
    })
        .then(checkStatus);
