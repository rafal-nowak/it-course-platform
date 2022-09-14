import {Divider, message} from "antd";
import {useEffect, useState} from "react";
import {getTaskResults, postFileAssignedToUserTask} from "../Clients/taskClient";
import TextArea from "antd/lib/input/TextArea";


function TaskResults(props) {
    const taskId = props.task.id || 0;
    const taskName = props.task.name || "Default task name";

    const [taskResult, setTaskResult] = useState();

    useEffect(() => {
        if (taskId) {
            takeTaskResult();
        }
    }, [taskId]);
    const takeTaskResult = () => {

        getTaskResults(taskId)
            // .then((res) => res.json())
            .then((response) => response.blob())
            .then((data) => {

                data.text().then((text)=>{
                    setTaskResult(text)
                })

                message.success('results read successfully.');
            })
            .catch(() => {
                message.error('there was a problem reading results');
            })
            .finally(() => {

            });

    };

    return (
        <>
            <>
                <Divider orientation="center">Task Results</Divider>
                <h4>Task name: {taskName}</h4>
                <h4>Task id: {taskId}</h4>
                <TextArea
                    value={taskResult}
                    rows={5}
                />
            </>
        </>
    );
}

export default TaskResults;