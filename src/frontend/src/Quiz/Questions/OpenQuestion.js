import {Divider, Input} from "antd";
import {useEffect, useState} from "react";

const {TextArea} = Input;

function OpenQuestion(props) {
    const question = props.question.question || "Default question?";
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
                <div>{question}</div>
                <TextArea
                    onChange={(e) => {
                        setState(currState => ({
                            ...currState,
                            answers: [e.target.value]
                        }))
                    }}
                    rows={4}
                    placeholder="maxLength is 250"
                    maxLength={250}
                    showCount={true}/>
            </>
        </>
    );
}

export default OpenQuestion;