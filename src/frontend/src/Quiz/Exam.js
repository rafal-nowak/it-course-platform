import {Divider} from "antd";
import ExamQuestions from "./ExamQuestions";


function Exam(props) {
    const testId = props.test.testId || 0;
    const testName = props.test.testName || "Default test name";
    const questions = props.test.questions || [];
    const updateAnswersState = props.updateAnswersState;

    return (
        <>
            <>
                <Divider orientation="center">Test</Divider>
                <h4>Test name: {testName}</h4>
                <h4>Test id: {testId}</h4>
                <ExamQuestions updateAnswersState={updateAnswersState} questions={questions} testId={testId}></ExamQuestions>
            </>
        </>
    );
}

export default Exam;