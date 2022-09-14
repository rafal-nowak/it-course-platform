import {useEffect, useRef, useState} from "react";
import {getQuiz, sendQuiz} from "../Clients/quizClient";
import Exam from "./Exam";
import {Button, message} from "antd";

function ExamLayout(props) {

    const testId = props.testId;
    const submitCallback = props.submitCallback

    const [test, setTest] = useState([]);
    const answers = useRef([]);

    function sendTestResults() {
        const quizResults = {
            testId: test.testId,
            gradingTableId: test.gradingTableId,
            userId: test.userId,
            testTemplateId: test.testTemplateId,
            maximumScore: test.maximumScore,
            score: test.score,
            grade: test.grade,
            testName: test.testName,
            status: 'SUBMITTED',
            questions: [...answers.current]
        }

        sendQuiz(quizResults)
            .then(() => {
                if (submitCallback)
                    submitCallback()
                message.success('quiz sent successfully')
            })
            .catch(() => {
                message.error('there was a problem sending quiz results');
            })
            .finally(() => {

            });
    }

    function updateAnswersState(question) {
        const newAnswers = answers.current.filter((e) => e.questionId != question.questionId);
        newAnswers.push(question);
        answers.current = newAnswers;
    }

    const fetchTest = () =>
        getQuiz(testId)
            .then(res => res.json())
            .then(data => {
                setTest(data)
                answers.current = [];
            })
            .catch(() => {
                message.error('there was a problem fetching quiz');
            })
            .finally(() => {

            });

    useEffect(() => {
        if (testId) {
            fetchTest();
        }
    }, [testId]);

    return (
        <div>
            <Exam updateAnswersState={updateAnswersState} test={test}></Exam>
            <br/>
            <Button block type="primary" onClick={sendTestResults}>Submit</Button>
        </div>

    )
}

export default ExamLayout;