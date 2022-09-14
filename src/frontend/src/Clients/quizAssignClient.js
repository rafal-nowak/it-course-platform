import fetch from 'unfetch';
import {authHeaderWithApplicationJson} from "../Helpers/authHeaderWithApplicationJson";
import {quizAssigmentRequest} from "../Helpers/quizAssigmentRequest";
import {checkStatus} from "../Helpers/checkStatus";

export const quizAssign = (userId, testTemplateId) =>
    fetch("http://localhost:8080/api/v1/quiz-assign", {
        headers: authHeaderWithApplicationJson(),
        method: 'POST',
        body: JSON.stringify(quizAssigmentRequest(userId, testTemplateId))
    })
        .then(checkStatus);
