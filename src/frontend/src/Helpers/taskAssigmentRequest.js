export function taskAssigmentRequest(userId, taskId) {
    return {
        userId: `${userId}`,
        taskId: `${taskId}`
    }
}