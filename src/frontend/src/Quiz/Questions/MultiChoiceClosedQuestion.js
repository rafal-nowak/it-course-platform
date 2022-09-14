import {Checkbox, Divider, List} from "antd";
import {useEffect, useState} from "react";

const defaultAnswers = [
    'Example answer 1',
    'Example answer 2',
    'Example answer 3',
    'Example answer 4',
];

function MultiChoiceClosedQuestion(props) {
    const question = props.question.question || "Default question?";
    const availableAnswers = props.question.answers || defaultAnswers;
    const updateAnswersState = props.updateAnswersState;
    const testId = props.testId;

    useEffect(() => {
        setState({
            ...props.question,
            answers: []
        });
    }, [testId]);

    const [state, setState] = useState({
        ...props.question,
        answers: []
    });

    useEffect(() => {
        updateAnswersState(state)
    });

    return (
        <>
            <>
                <Divider orientation="center"></Divider>
                <List
                    size="large"
                    header={<div>{question}</div>}
                    bordered
                    dataSource={availableAnswers}

                    renderItem={(item) => <List.Item>
                        <Checkbox
                        onClick={(e) => {
                            // const newAnswers = state.answers.filter(answer => answer != e.target.value);
                            const newAnswers = state.answers.filter(answer => answer != e.target.labels[0].innerText);
                            if (e.target.checked) {
                                // newAnswers.push(e.target.value);
                                newAnswers.push(e.target.labels[0].innerText);
                            }

                            setState(currState => ({
                                ...currState,
                                answers: [...newAnswers]
                            }))

                        }}
                        defaultChecked={false}
                        // value={item}
                        >{item}</Checkbox></List.Item>}

                />
            </>
        </>
    );
}

export default MultiChoiceClosedQuestion;