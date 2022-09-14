import {useEffect, useState} from "react";
import {Button, Input, message, Modal, Table, Tooltip} from 'antd';
import {EyeOutlined} from "@ant-design/icons";
import {getUsers} from "../Clients/userClient";
import {getQuizTemplates} from "../Clients/quizTemplateClient";
import {quizAssign} from "../Clients/quizAssignClient";

const {TextArea} = Input;

function AssignQuiz(props) {

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

    const testTemplatesColumns = [
        {
            title: 'testTemplateId',
            dataIndex: 'testTemplateId',
            key: 'testTemplateId',
        },
        {
            title: 'testName',
            dataIndex: 'testName',
            key: 'testName',
        },
        {
            title: 'actions',
            key: 'actions',
            render: (record) => {
                return <>
                    <Tooltip placement="bottom" title={"Show"}>
                    <EyeOutlined onClick={() => {
                        onShowTestTemplate(record)
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

    const [testTemplatesSelect, setTestTemplatesSelect] = useState({
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

    const renderTestTemplates = () => {
        if (testTemplatesState.quizTemplates && testTemplatesState.quizTemplates <= 0) {
            return "no data available";
        }
        return <Table
            dataSource={testTemplatesState.quizTemplates}
            columns={testTemplatesColumns}
            rowSelection={
                {
                    selectedRowKeys: testTemplatesSelect.selectedRowKeys,
                    type: "radio",
                    onChange: (selectedRowKeys) => {
                        setTestTemplatesSelect({
                            ...testTemplatesSelect,
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
                    current: testTemplatesPagination.current,
                    pageSize: testTemplatesPagination.pageSize,
                    total: testTemplatesPagination.total,
                    onChange: (page, pageSize) => {
                        setTestTemplatesPagination({
                            ...testTemplatesPagination,
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

    const [testTemplatesState, setTestTemplatesState] = useState([]);
    const [testTemplateIsShown, setTestTemplateIsShown] = useState(false);
    const [testTemplateShownRecord, setTestTemplateShownRecord] = useState(null);
    const [testTemplatesPagination, setTestTemplatesPagination] = useState([
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
        getQuizTemplates(size, page)
            .then(res => res.json())
            .then(data => {
                if (data.quizTemplates) {
                    for (let i = 0; i < data.quizTemplates.length; i++) {
                        data.quizTemplates[i].key = data.quizTemplates[i].testTemplateId
                    }
                }
                setTestTemplatesState(data)
                setTestTemplatesPagination(
                    {
                        ...testTemplatesPagination,
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
        fetchQuizTemplatess(testTemplatesPagination.pageSize, testTemplatesPagination.pageIndex)
    }, [testTemplatesPagination.pageIndex, testTemplatesPagination.pageSize, testTemplatesPagination.randomNumber])

    const onShowUser = (record) => {
        setUserIsShown(true)
        setUserShownRecord({...record})
    }

    const onShowTestTemplate = (record) => {
        setTestTemplateIsShown(true)
        setTestTemplateShownRecord({...record})
    }

    function assignQuizToUser() {
        const testTemplateId = testTemplatesSelect.selectedRowKeys.at(0)
        for (let i = 0; i < usersSelect.selectedRowKeys.length; i++) {
            const userId = usersSelect.selectedRowKeys.at(i)
            quizAssign(userId, testTemplateId)
                .then(res => res.json())
                .then(data => {
                    // console.log(data)
                    message.success(`quiz assigned to user`)
                })
                .catch(() => {
                    message.error('there was a problem assigning quiz to user');
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
                    disabled={!(usersSelect.selectedRowKeys.length > 0 && testTemplatesSelect.selectedRowKeys.length > 0)}
                    onClick={() => {
                        assignQuizToUser()
                    }}
                >Assign Quiz</Button>
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

            <h1>Quiz Templates</h1>
            <p></p>
            <div>
                {renderTestTemplates()}
            </div>
            <div>
                <Modal
                    title="Test Template"
                    open={testTemplateIsShown}
                    onCancel={() => {
                        setTestTemplateIsShown(false)
                    }}
                    footer={[
                        <Button key="ok" type={"primary"} onClick={() => {
                            setTestTemplateIsShown(false)
                        }}>
                            OK
                        </Button>,
                    ]}
                >
                    <TextArea
                        value={JSON.stringify(testTemplateShownRecord, null, 4)}
                        rows={15}
                        onChange={(e) => {
                            setTestTemplateShownRecord(JSON.parse(e.target.value))
                        }
                        }
                    />


                </Modal>
            </div>

        </>


    );
}

export default AssignQuiz;