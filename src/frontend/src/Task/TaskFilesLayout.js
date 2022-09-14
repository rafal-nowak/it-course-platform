import {Divider} from "antd";
import TaskFiles from "./TaskFiles";


function TaskFilesLayout(props) {
    const taskId = props.task.id || 0;
    const taskName = props.task.name || "Default task name";

    return (
        <>
            <>
                <Divider orientation="center">Task Files</Divider>
                <h4>Task name: {taskName}</h4>
                <h4>Task id: {taskId}</h4>
                <TaskFiles taskId={taskId}></TaskFiles>
            </>
        </>
    );
}

export default TaskFilesLayout;