import SingleChoiceClosedQuestion from "./Questions/SingleChoiceClosedQuestion";
import MultiChoiceClosedQuestion from "./Questions/MultiChoiceClosedQuestion";
import OpenQuestion from "./Questions/OpenQuestion";

function ExamQuestions(props) {
    const list = []
    const questions = props.questions;
    const updateAnswersState = props.updateAnswersState;
    const testId = props.testId;

    let i = 0;
    const randomNumber = parseInt(Math.random() * 1000)
    for (const question of questions) {
        i++;
        if (question.type == "SINGLE_CHOICE_CLOSED_QUESTION") {
            list.push(
                <SingleChoiceClosedQuestion key={testId + randomNumber + i} updateAnswersState={updateAnswersState} question={question} testId={testId}></SingleChoiceClosedQuestion>
            )
        }
        if (question.type == "MULTI_CHOICE_CLOSED_QUESTION") {
            list.push(
                <MultiChoiceClosedQuestion key={testId + randomNumber + i} updateAnswersState={updateAnswersState} question={question} testId={testId}></MultiChoiceClosedQuestion>
            )
        }
        if (question.type == "OPEN_QUESTION") {
            list.push(
                <OpenQuestion key={testId + randomNumber + i} updateAnswersState={updateAnswersState} question={question} testId={testId}></OpenQuestion>
            )
        }
    }

    return (
        <div>
            {list}
        </div>

    )
}

export default ExamQuestions;