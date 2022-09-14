import React, {useEffect, useState} from "react";
import {Button, Input, message, Modal, Table, Tooltip} from 'antd';
import {CheckCircleOutlined, EditOutlined, EyeOutlined, SketchOutlined, FileOutlined} from "@ant-design/icons";
import {getQuizzes, sendQuizCommand} from "../Clients/quizClient";
import {checkCommand, rateCommand} from "../Helpers/quizCommands";
import ExamLayout from "../Quiz/ExamLayout";
import {getTasks, sendTaskCommand} from "../Clients/taskClient";
import TaskFilesLayout from "../Task/TaskFilesLayout";
import TaskResult from "../Task/TaskResults";
import TaskResults from "../Task/TaskResults";
import {executeCommand} from "../Helpers/taskCommands";

const {TextArea} = Input;

function TaskView(props) {


    const columns = [
        {
            title: 'id',
            dataIndex: 'id',
            key: 'id',
        },
        {
            title: 'name',
            dataIndex: 'name',
            key: 'name',
        },
        {
            title: 'description',
            dataIndex: 'description',
            key: 'description',
        },
        {
            title: 'status',
            dataIndex: 'status',
            key: 'status',
        },
        {
            title: 'actions',
            key: 'actions',
            render: (record) => {
                return <>
                    <Tooltip placement="bottom" title={"Files"}>
                        <FileOutlined onClick={() => {
                            onShowFiles(record)
                        }}/>
                    </Tooltip>

                    <Tooltip placement="bottom" title={"Show"} color={"blue"}>
                        <EyeOutlined onClick={() => {
                            onShowTask(record)
                        }} style={{color: "blue", marginLeft: 12}}/>
                    </Tooltip>
                    <Tooltip placement="bottom" title={"Check"} color={"green"}>
                        <CheckCircleOutlined onClick={() => {
                            onCheckTask(record)
                        }
                        } style={{color: "green", marginLeft: 12}}/>
                    </Tooltip>
                    <Tooltip placement="bottom" title={"Results"} color={"red"}>
                        <SketchOutlined onClick={() => {
                            onShowTaskResult(record)
                        }
                        } style={{color: "red", marginLeft: 12}}/>
                    </Tooltip>

                </>
            }
        },
    ]

    const renderTasks = () => {
        if (state.tasks && state.tasks <= 0) {
            return "no data available";
        }
        return <Table
            dataSource={state.tasks}
            columns={columns}
            pagination={
                {
                    showSizeChanger: true,
                    pageSizeOptions: [3, 5, 10, 15],
                    defaultPageSize: 3,
                    current: pagination.current,
                    pageSize: pagination.pageSize,
                    total: pagination.total,
                    onChange: (page, pageSize) => {
                        setPagination({
                            ...pagination,
                            pageIndex: page - 1,
                            current: page,
                            pageSize: pageSize
                        })
                    }
                }
            }
        />;
    }

    const [state, setState] = useState([]);
    const [isShown, setIsShown] = useState(false);
    const [isResultShown, setIsResultShown] = useState(false);
    const [shownRecord, setShownRecord] = useState(null);
    const [isTaken, setIsTaken] = useState(false);
    const [takenRecord, setTakenRecord] = useState(null);
    const [pagination, setPagination] = useState([
        {
            total: 0,
            current: 1,
            pageSize: 3,
            pageIndex: 0,
            randomNumber: parseInt(Math.random() * 1000)
        }
    ]);

    const fetchTasks = (size, page) =>
        getTasks(size, page)
            .then(res => res.json())
            .then(data => {
                if (data.tasks) {
                    for (let i = 0; i < data.tasks.length; i++) {
                        data.tasks[i].key = data.tasks[i].id
                    }
                }
                setState(data)
                setPagination(
                    {
                        ...pagination,
                        total: data.totalElements,
                        current: data.currentPage + 1,
                    }
                )
            })
            .catch(() => {
                message.error('there was a problem fetching tasks');
            })
            .finally(() => {

            });

    useEffect(() => {
        fetchTasks(pagination.pageSize, pagination.pageIndex)
    }, [pagination.pageIndex, pagination.pageSize, pagination.randomNumber])

    const onShowTask = (record) => {
        setIsShown(true)
        setShownRecord({...record})
    }

    const onShowFiles = (record) => {
        setIsTaken(true)
        setTakenRecord({...record})
    }

    function onCheckTask(record) {
        sendTaskCommand(record.id, executeCommand)
            .then(res => {
                console.log(res)
                if (res.ok) {
                    setPagination(
                        {
                            ...pagination,
                            randomNumber: parseInt(Math.random() * 1000)
                        }
                    )
                    message.success(`task executed`)
                }
            })
            .catch(() => {
                message.error('there was a problem executing task');
            })
            .finally(() => {

            });
    }

    function onShowTaskResult(record) {
        setIsResultShown(true)
        setTakenRecord({...record})
    }

    return (

        <>
            <h1>Tasks View</h1>
            <p></p>
            <div>
                {renderTasks()}
            </div>
            <div>
                <Modal
                    title="Task"
                    open={isShown}
                    onCancel={() => {
                        setIsShown(false)
                    }}
                    footer={[
                        <Button key="ok" type={"primary"} onClick={() => {
                            setIsShown(false)
                        }}>
                            OK
                        </Button>,
                    ]}
                >
                    <TextArea
                        value={JSON.stringify(shownRecord, null, 4)}
                        rows={15}
                        onChange={(e) => {
                            setShownRecord(JSON.parse(e.target.value))
                        }
                        }
                    />


                </Modal>

                <Modal
                    title="Task Results"
                    open={isResultShown}
                    onCancel={() => {
                        setIsResultShown(false)
                    }}
                    footer={[
                        <Button key="ok" type={"primary"} onClick={() => {
                            setIsResultShown(false)
                        }}>
                            OK
                        </Button>,
                    ]}
                >
                    <TaskResults task={takenRecord}/>

                </Modal>

                <Modal
                    title="Task Files"
                    open={isTaken}
                    okText="Submit"
                    onCancel={() => {
                        setIsTaken(false)
                    }}
                    footer={[
                        <Button key="ok" type={"primary"} onClick={() => {
                            setIsTaken(false)
                        }}>
                            CLOSE
                        </Button>,
                    ]}
                >

                    {
                        takenRecord &&
                        <TaskFilesLayout task={takenRecord} submitCallback={ () => setIsTaken(false)}></TaskFilesLayout>
                    }

                </Modal>

            </div>

        </>

    );
}

export default TaskView;