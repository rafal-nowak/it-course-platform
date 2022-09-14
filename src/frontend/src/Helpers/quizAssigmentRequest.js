export function quizAssigmentRequest(userId, quizTemplateId) {
    return {
        userId: `${userId}`,
        quizTemplateId: `${quizTemplateId}`
    }
}