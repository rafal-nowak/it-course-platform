import {useEffect, useState} from "react";
import {Button, Input, message, Modal, Table, Tooltip} from 'antd';
import {DeleteOutlined, EditOutlined} from "@ant-design/icons";
import {
    createTaskBlueprint,
    deleteTaskBlueprint,
    getTaskBlueprints,
    sendTaskBlueprint
} from "../Clients/taskBlueprintClient";
import {taskBlueprintTemplate} from "../Helpers/taskBlueprintTemplate";

const {TextArea} = Input;

function TaskBlueprints(props) {

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
            title: 'repositoryUrl',
            dataIndex: 'repositoryUrl',
            key: 'repositoryUrl',
        },
        {
            title: 'actions',
            key: 'actions',
            render: (record) => {
                return <>
                    <Tooltip placement="bottom" title={"Edit"}>
                    <EditOutlined onClick={() => {
                        onEditTaskBlueprint(record)
                    }}/>
                    </Tooltip>
                    <Tooltip placement="bottom" title={"Delete"} color={"red"}>
                    <DeleteOutlined onClick={() => {
                        onDeleteTaskBlueprint(record)
                    }
                    } style={{color: "red", marginLeft: 12}}/>
                    </Tooltip>
                </>
            }
        },
    ]

    const renderTaskBlueprints = () => {
        if (state.taskBlueprints && state.taskBlueprints <= 0) {
            return "no data available";
        }
        return <Table
            dataSource={state.taskBlueprints}
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
    const [isEdited, setIsEdited] = useState(false);
    const [isCreated, setIsCreated] = useState(false);
    const [editedRecord, setEditedRecord] = useState(null);
    const [createdRecord, setCreatedRecord] = useState(null);
    const [pagination, setPagination] = useState([
        {
            total: 0,
            current: 1,
            pageSize: 3,
            pageIndex: 0,
            randomNumber: parseInt(Math.random() * 1000)
        }
    ]);

    const fetchTaskBlueprints = (size, page) =>
        getTaskBlueprints(size, page)
            .then(res => res.json())
            .then(data => {
                if (data.taskBlueprints) {
                    for (let i = 0; i < data.taskBlueprints.length; i++) {
                        data.taskBlueprints[i].key = data.taskBlueprints[i].id
                    }
                }
                setState(data)
                setPagination(
                    {
                        ...pagination,
                        total: data.totalElements,
                        current: data.currentPage,
                    }
                )
            })
            .catch(() => {
                message.error('there was a problem fetching task blueprints');
            })
            .finally(() => {

            });


    useEffect(() => {
        fetchTaskBlueprints(pagination.pageSize, pagination.pageIndex)
    }, [pagination.pageIndex, pagination.pageSize, pagination.randomNumber])

    const onDeleteTaskBlueprint = (record) => {
        Modal.confirm({
            title: 'Are you sure, you want to delete this record?',
            okText: 'Yes',
            okType: 'danger',
            onOk: () => {
                deleteTaskBlueprint(record)
                    .then(res => {
                        if (res.ok) {
                            setPagination(
                                {
                                    ...pagination,
                                    randomNumber: parseInt(Math.random() * 1000)
                                }
                            )
                            message.success(`task blueprint deleted`)
                        }
                    })
                    .catch(() => {
                        message.error('there was a problem deleting task blueprint');
                    })
                    .finally(() => {

                    });
            }
        })

    }

    const onEditTaskBlueprint = (record) => {
        setIsEdited(true)
        setEditedRecord({...record})
    }

    return (

        <>
            <h1>Task Blueprints</h1>
            <p></p>
            <div>
                <Button
                    type="primary"
                    onClick={()=>{
                        setIsCreated(true)
                        setCreatedRecord({...taskBlueprintTemplate})
                    }}
                >Create New Task Blueprint</Button>
            </div>
            <p></p>
            <div>
                {renderTaskBlueprints()}
            </div>
            <div>
                <Modal
                    title="Edited Task Blueprint"
                    open={isEdited}
                    okText="Save"
                    onCancel={() => {
                        setIsEdited(false)
                    }}
                    onOk={() => {
                        setIsEdited(false)
                        sendTaskBlueprint(editedRecord)
                            .then(res => {
                                if (res.ok) {
                                    setPagination(
                                        {
                                            ...pagination,
                                            randomNumber: parseInt(Math.random() * 1000)
                                        }
                                    )
                                    message.success(`task blueprint saved`)
                                }
                            })
                            .catch(() => {
                                message.error('there was a problem editing task blueprint');
                            })
                            .finally(() => {

                            });

                    }}
                >
                    <TextArea
                        value={JSON.stringify(editedRecord, null, 4)}
                        rows={15}
                        onChange = {(e)=>{
                            setEditedRecord(JSON.parse(e.target.value))
                        }
                        }
                    />


                </Modal>
            </div>

            <div>
                <Modal
                    title="Created Task Blueprint"
                    open={isCreated}
                    okText="Create"
                    onCancel={() => {
                        setIsCreated(false)
                    }}
                    onOk={() => {
                        setIsCreated(false)
                        createTaskBlueprint(createdRecord)
                            .then(res => {
                                if (res.ok) {
                                    setPagination(
                                        {
                                            ...pagination,
                                            randomNumber: parseInt(Math.random() * 1000)
                                        }
                                    )
                                    message.success(`task blueprint created`)
                                }
                            })
                            .catch(() => {
                                message.error('there was a problem creating task blueprint');
                            })
                            .finally(() => {

                            });

                    }}
                >
                    <TextArea
                        value={JSON.stringify(createdRecord, null, 4)}
                        rows={15}
                        onChange = {(e)=>{
                            setCreatedRecord(JSON.parse(e.target.value))
                        }
                        }
                    />


                </Modal>
            </div>
        </>

    );
}

export default TaskBlueprints;