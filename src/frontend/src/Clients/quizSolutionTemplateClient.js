import fetch from 'unfetch';
import {authHeaderWithApplicationJson} from "../Helpers/authHeaderWithApplicationJson";
import {checkStatus} from "../Helpers/checkStatus";

export const getQuizSolutionTemplate = (solutionId) =>
    fetch(`http://localhost:8080/api/v1/quiz-solution-templates/${solutionId}`, {
        headers: authHeaderWithApplicationJson()
    })
        .then(checkStatus);

export const deleteQuizSolutionTemplate = (solutionId) =>
    fetch(`http://localhost:8080/api/v1/quiz-solution-templates/${solutionId}`, {
        headers: authHeaderWithApplicationJson(),
        method: 'DELETE',
    })
        .then(checkStatus);

export const sendQuizSolutionTemplate = quizTemplate =>
    fetch("http://localhost:8080/api/v1/quiz-solution-templates", {
        headers: authHeaderWithApplicationJson(),
        method: 'PUT',
        body: JSON.stringify(quizTemplate)
    })
        .then(checkStatus);

export const createQuizSolutionTemplate = quizTemplate =>
    fetch("http://localhost:8080/api/v1/quiz-solution-templates", {
        headers: authHeaderWithApplicationJson(),
        method: 'POST',
        body: JSON.stringify(quizTemplate)
    })
        .then(checkStatus);

export const getQuizSolutionTemplates = (size = 3, page = 0) =>
    fetch(`http://localhost:8080/api/v1/quiz-solution-templates?size=${size}&page=${page}`, {
        headers: authHeaderWithApplicationJson(),
        method: 'GET',
    })
        .then(checkStatus);
