import {Divider, List, Radio} from "antd";
import {useEffect, useState} from "react";

const defaultAnswers = [
    'Example answer 1',
    'Example answer 2',
    'Example answer 3',
    'Example answer 4',
];

function SingleChoiceClosedQuestion(props) {
    const question = props.question.question || "Default question?";
    const answers = props.question.answers || defaultAnswers;
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
                <Radio.Group style={{width:'100%'}}>
                    <List
                        size="large"
                        header={<div>{question}</div>}
                        bordered
                        dataSource={answers}

                        renderItem={(item) => <List.Item><Radio
                            onClick={(e) => {
                                setState(currState => ({
                                    ...currState,
                                    answers: [e.target.value]
                                }))
                            }}
                            value={item}>{item}</Radio></List.Item>}

                    />
                </Radio.Group>
            </>

        </>
    );
}

export default SingleChoiceClosedQuestion;