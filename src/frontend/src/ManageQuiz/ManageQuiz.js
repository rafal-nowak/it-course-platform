import {useEffect, useState} from "react";
import {Input, message, Modal, Table, Tooltip} from 'antd';
import {CheckCircleOutlined, DeleteOutlined, EditOutlined, SketchOutlined} from "@ant-design/icons";
import {deleteQuiz, getQuizzes, sendQuiz, sendQuizCommand} from "../Clients/quizClient";
import {checkCommand, rateCommand} from "../Helpers/quizCommands";

const {TextArea} = Input;

function ManageQuiz(props) {



    const columns = [
        {
            title: 'testId',
            dataIndex: 'testId',
            key: 'testId',
        },
        {
            title: 'userId',
            dataIndex: 'userId',
            key: 'userId',
        },
        {
            title: 'testName',
            dataIndex: 'testName',
            key: 'testName',
        },
        {
            title: 'maximumScore',
            dataIndex: 'maximumScore',
            key: 'maximumScore',
        },
        {
            title: 'score',
            dataIndex: 'score',
            key: 'score',
        },
        {
            title: 'grade',
            dataIndex: 'grade',
            key: 'grade',
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
                    <Tooltip placement="bottom" title={"Edit"}>
                        <EditOutlined onClick={() => {
                            onEditQuiz(record)
                        }}/>
                    </Tooltip>
                    <Tooltip placement="bottom" title={"Delete"} color={"red"}>
                        <DeleteOutlined onClick={() => {
                            onDeleteQuiz(record)
                        }
                        } style={{color: "red", marginLeft: 12}}/>
                    </Tooltip>
                    <Tooltip placement="bottom" title={"Check"} color={"green"}>
                        <CheckCircleOutlined onClick={() => {
                            onCheckQuiz(record)
                        }
                        } style={{color: "green", marginLeft: 12}}/>
                    </Tooltip>
                    <Tooltip placement="bottom" title={"Rate"} color={"blue"}>
                        <SketchOutlined onClick={() => {
                            onRateQuiz(record)
                        }
                        } style={{color: "blue", marginLeft: 12}}/>
                    </Tooltip>

                </>
            }
        },
    ]

    const renderTests = () => {
        if (state.quizzes && state.quizzes <= 0) {
            return "no data available";
        }
        return <Table
            dataSource={state.quizzes}
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
    const [editedRecord, setEditedRecord] = useState(null);
    const [pagination, setPagination] = useState([
        {
            total: 0,
            current: 1,
            pageSize: 3,
            pageIndex: 0,
            randomNumber: parseInt(Math.random() * 1000)
        }
    ]);

    const fetchQuizzes = (size, page) =>
        getQuizzes(size, page)
            .then(res => res.json())
            .then(data => {
                if (data.quizzes) {
                    for (let i = 0; i < data.quizzes.length; i++) {
                        data.quizzes[i].key = data.quizzes[i].testId
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
                message.error('there was a problem fetching quizzes');
            })
            .finally(() => {

            });

    useEffect(() => {
        fetchQuizzes(pagination.pageSize, pagination.pageIndex)
    }, [pagination.pageIndex, pagination.pageSize, pagination.randomNumber])

    const onDeleteQuiz = (record) => {
        Modal.confirm({
            title: 'Are you sure, you want to delete this record?',
            okText: 'Yes',
            okType: 'danger',
            onOk: () => {
                deleteQuiz(record.testId)
                    .then(res => {
                        if (res.ok) {
                            setPagination(
                                {
                                    ...pagination,
                                    randomNumber: parseInt(Math.random() * 1000)
                                }
                            )
                            message.success(`quiz deleted`)
                        }
                    })
                    .catch(() => {
                        message.error('there was a problem deleting quiz');
                    })
                    .finally(() => {

                    });
            }
        })

    }

    const onEditQuiz = (record) => {
        setIsEdited(true)
        setEditedRecord({...record})
    }

    function onCheckQuiz(record) {
        sendQuizCommand(record.testId, checkCommand)
            .then(res => {
                if (res.ok) {
                    setPagination(
                        {
                            ...pagination,
                            randomNumber: parseInt(Math.random() * 1000)
                        }
                    )
                    message.success(`quiz checked`)
                }
            })
            .catch(() => {
                message.error('there was a problem checking quiz');
            })
            .finally(() => {

            });
    }

    function onRateQuiz(record) {
        sendQuizCommand(record.testId, rateCommand)
            .then(res => {
                if (res.ok) {
                    setPagination(
                        {
                            ...pagination,
                            randomNumber: parseInt(Math.random() * 1000)
                        }
                    )
                    message.success(`quiz rated`)

                }
            })
            .catch(() => {
                message.error('there was a problem rating quiz');
            })
            .finally(() => {

            });
    }

    return (

        <>
            <h1>Manage Quizzes</h1>
            <p></p>
            <div>
                {renderTests()}
            </div>
            <div>
                <Modal
                    title="Edited Quiz"
                    open={isEdited}
                    okText="Save"
                    onCancel={() => {
                        setIsEdited(false)
                    }}
                    onOk={() => {
                        setIsEdited(false)
                        sendQuiz(editedRecord)
                            .then(res => {
                                if (res.ok) {
                                    setPagination(
                                        {
                                            ...pagination,
                                            randomNumber: parseInt(Math.random() * 1000)
                                        }
                                    )
                                    message.success(`quiz saved`)
                                }
                            })
                            .catch(() => {
                                message.error(`there was a problem saving quiz`);
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

        </>

    );
}

export default ManageQuiz;