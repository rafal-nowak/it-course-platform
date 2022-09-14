import {useEffect, useState} from "react";
import {Button, Input, message, Modal, Table, Tooltip} from 'antd';
import {EyeOutlined} from "@ant-design/icons";
import {getUsers} from "../Clients/userClient";
import {getTaskBlueprints} from "../Clients/taskBlueprintClient";
import {taskAssign} from "../Clients/taskAssignClient";

const {TextArea} = Input;

function AssignTask(props) {

    const usersColumns = [
        {
            title: 'id',
            dataIndex: 'id',
            key: 'id',
        },
        {
            title: 'email',
            dataIndex: 'email',
            key: 'email',
        },
        {
            title: 'name',
            dataIndex: 'name',
            key: 'name',
        },
        {
            title: 'actions',
            key: 'actions',
            render: (record) => {
                return <>
                    <Tooltip placement="bottom" title={"Show"}>
                    <EyeOutlined onClick={() => {
                        onShowUser(record)
                    }}/>
                    </Tooltip>
                </>
            }
        },
    ]

    const taskBlueprintsColumns = [
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
                    <Tooltip placement="bottom" title={"Show"}>
                    <EyeOutlined onClick={() => {
                        onShowTaskBlueprint(record)
                    }}/>
                    </Tooltip>
                </>
            }
        },
    ]

    const [usersSelect, setUsersSelect] = useState({
        selectedRowKeys: [],
        loading: false,
    });

    const [taskBlueprintsSelect, setTaskBlueprintsSelect] = useState({
        selectedRowKeys: [],
        loading: false,
    });


    const renderUsers = () => {
        if (usersState.users && usersState.users <= 0) {
            return "no data available";
        }
        return <Table
            dataSource={usersState.users}
            columns={usersColumns}
            rowSelection={
                {
                    selectedRowKeys: usersSelect.selectedRowKeys,
                    type: "checkbox",
                    onChange: (selectedRowKeys) => {
                        setUsersSelect({
                            ...usersSelect,
                            selectedRowKeys: selectedRowKeys
                        });
                    }
                }
            }
            pagination={
                {
                    showSizeChanger: true,
                    pageSizeOptions: [3, 5, 10, 15],
                    defaultPageSize: 3,
                    current: usersPagination.current,
                    pageSize: usersPagination.pageSize,
                    total: usersPagination.total,
                    onChange: (page, pageSize) => {
                        setUsersPagination({
                            ...usersPagination,
                            pageIndex: page - 1,
                            current: page,
                            pageSize: pageSize
                        })
                    }
                }
            }
        />;
    }

    const rendertaskBlueprints = () => {
        if (taskBlueprintsState.taskBlueprints && taskBlueprintsState.taskBlueprints <= 0) {
            return "no data available";
        }
        return <Table
            dataSource={taskBlueprintsState.taskBlueprints}
            columns={taskBlueprintsColumns}
            rowSelection={
                {
                    selectedRowKeys: taskBlueprintsSelect.selectedRowKeys,
                    type: "radio",
                    onChange: (selectedRowKeys) => {
                        setTaskBlueprintsSelect({
                            ...taskBlueprintsSelect,
                            selectedRowKeys: selectedRowKeys
                        });
                    }
                }
            }
            pagination={
                {
                    showSizeChanger: true,
                    pageSizeOptions: [3, 5, 10, 15],
                    defaultPageSize: 3,
                    current: taskBlueprintsPagination.current,
                    pageSize: taskBlueprintsPagination.pageSize,
                    total: taskBlueprintsPagination.total,
                    onChange: (page, pageSize) => {
                        setTaskBlueprintsPagination({
                            ...taskBlueprintsPagination,
                            pageIndex: page - 1,
                            current: page,
                            pageSize: pageSize
                        })
                    }
                }
            }
        />;
    }

    const [usersState, setUsersState] = useState([]);
    const [userIsShown, setUserIsShown] = useState(false);
    const [userShownRecord, setUserShownRecord] = useState(null);
    const [usersPagination, setUsersPagination] = useState([
        {
            total: 0,
            current: 1,
            pageSize: 3,
            pageIndex: 0,
            randomNumber: parseInt(Math.random() * 1000)
        }
    ]);

    const [taskBlueprintsState, setTaskBlueprintsState] = useState([]);
    const [taskBlueprintIsShown, setTaskBlueprintIsShown] = useState(false);
    const [taskBlueprintShownRecord, setTaskBlueprintShownRecord] = useState(null);
    const [taskBlueprintsPagination, setTaskBlueprintsPagination] = useState([
        {
            total: 0,
            current: 1,
            pageSize: 3,
            pageIndex: 0,
            randomNumber: parseInt(Math.random() * 1000)
        }
    ]);

    const fetchUsers = (size, page) =>
        getUsers(size, page)
            .then(res => res.json())
            .then(data => {
                if (data.users) {
                    for (let i = 0; i < data.users.length; i++) {
                        data.users[i].key = data.users[i].id
                    }
                }
                setUsersState(data)
                setUsersPagination(
                    {
                        ...usersPagination,
                        total: data.totalElements,
                        current: data.currentPage,
                    }
                )
            })
            .catch(() => {
                message.error('there was a problem fetching users');
            })
            .finally(() => {

            });

    const fetchQuizTemplatess = (size, page) =>
        getTaskBlueprints(size, page)
            .then(res => res.json())
            .then(data => {
                if (data.taskBlueprints) {
                    for (let i = 0; i < data.taskBlueprints.length; i++) {
                        data.taskBlueprints[i].key = data.taskBlueprints[i].id
                    }
                }
                setTaskBlueprintsState(data)
                setTaskBlueprintsPagination(
                    {
                        ...taskBlueprintsPagination,
                        total: data.totalElements,
                        current: data.currentPage,
                    }
                )
            })
            .catch(() => {
                message.error('there was a problem fetching quiz templates');
            })
            .finally(() => {

            });


    useEffect(() => {
        fetchUsers(usersPagination.pageSize, usersPagination.pageIndex)
    }, [usersPagination.pageIndex, usersPagination.pageSize, usersPagination.randomNumber])

    useEffect(() => {
        fetchQuizTemplatess(taskBlueprintsPagination.pageSize, taskBlueprintsPagination.pageIndex)
    }, [taskBlueprintsPagination.pageIndex, taskBlueprintsPagination.pageSize, taskBlueprintsPagination.randomNumber])

    const onShowUser = (record) => {
        setUserIsShown(true)
        setUserShownRecord({...record})
    }

    const onShowTaskBlueprint = (record) => {
        setTaskBlueprintIsShown(true)
        setTaskBlueprintShownRecord({...record})
    }

    function assignTaskToUser() {
        const testTemplateId = taskBlueprintsSelect.selectedRowKeys.at(0)
        for (let i = 0; i < usersSelect.selectedRowKeys.length; i++) {
            const userId = usersSelect.selectedRowKeys.at(i)
            taskAssign(userId, testTemplateId)
                .then(res => res.json())
                .then(data => {
                    message.success(`task assigned to user`)
                })
                .catch(() => {
                    message.error('there was a problem assigning task to user');
                })
                .finally(() => {

                });
        }
    }

    return (

        <>
            <div>
                <Button
                    type="primary"
                    disabled={!(usersSelect.selectedRowKeys.length > 0 && taskBlueprintsSelect.selectedRowKeys.length > 0)}
                    onClick={() => {
                        assignTaskToUser()
                    }}
                >Assign Task</Button>
            </div>
            <p></p>
            <h1>Users</h1>
            <p></p>
            <p></p>
            <div>
                {renderUsers()}
            </div>
            <div>
                <Modal
                    title="User"
                    open={userIsShown}
                    onCancel={() => {
                        setUserIsShown(false)
                    }}
                    footer={[
                        <Button key="ok" type={"primary"} onClick={() => {
                            setUserIsShown(false)
                        }}>
                            OK
                        </Button>,
                    ]}
                >
                    <TextArea
                        value={JSON.stringify(userShownRecord, null, 4)}
                        rows={15}
                        onChange={(e) => {
                            setUserShownRecord(JSON.parse(e.target.value))
                        }
                        }
                    />


                </Modal>
            </div>

            <h1>Task Blueprints</h1>
            <p></p>
            <div>
                {rendertaskBlueprints()}
            </div>
            <div>
                <Modal
                    title="Task Blueprint"
                    open={taskBlueprintIsShown}
                    onCancel={() => {
                        setTaskBlueprintIsShown(false)
                    }}
                    footer={[
                        <Button key="ok" type={"primary"} onClick={() => {
                            setTaskBlueprintIsShown(false)
                        }}>
                            OK
                        </Button>,
                    ]}
                >
                    <TextArea
                        value={JSON.stringify(taskBlueprintShownRecord, null, 4)}
                        rows={15}
                        onChange={(e) => {
                            setTaskBlueprintShownRecord(JSON.parse(e.target.value))
                        }
                        }
                    />


                </Modal>
            </div>

        </>


    );
}

export default AssignTask;