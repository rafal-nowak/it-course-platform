import {getFilesAssignedToUserTask} from "../Clients/taskClient";
import {useEffect, useState} from "react";
import TaskFile from "./TaskFile";
import {message} from "antd";

function TaskFiles(props) {
    const list = []
    const taskId = props.taskId

    const [state, setState] = useState({
        files: ['abc']
    });

    const fetchFilesList = () =>
        getFilesAssignedToUserTask(taskId)
            .then(res => res.json())
            .then(data => {
                setState(data)
            })
            .catch(() => {
                message.error('there was a problem getting files assigned to user task');
            })
            .finally(() => {

            });

    useEffect(() => {
        if (taskId) {
            fetchFilesList();
        }
    }, [taskId]);

    if (state.files && state.files.length > 0) {
        const files = state.files;
        let i = 0;
        const randomNumber = parseInt(Math.random() * 1000)
        for (const file of files) {
            list.push(
                <TaskFile key={taskId + randomNumber + i} taskId={taskId} filePath={files.at(i)} fileId={i}/>
            )
        }

    }

    return (
        <>

            <div>
                {list}
            </div>

        </>

    )
}

export default TaskFiles;