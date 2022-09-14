import {useEffect, useState} from "react";
import {Button, Input, message, Modal, Table, Tooltip} from 'antd';
import {DeleteOutlined, EditOutlined} from "@ant-design/icons";
import {quizSolutionTemplate} from "../Helpers/quizSolutionTemplate";
import {
    createQuizSolutionTemplate,
    deleteQuizSolutionTemplate,
    getQuizSolutionTemplates,
    sendQuizSolutionTemplate
} from "../Clients/quizSolutionTemplateClient";

const {TextArea} = Input;

function QuizSolutionTemplates(props) {

    const columns = [
        {
            title: 'testSolutionId',
            dataIndex: 'testSolutionId',
            key: 'testSolutionId',
        },
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
            title: 'score',
            dataIndex: 'score',
            key: 'score',
        },
        {
            title: 'maximumScore',
            dataIndex: 'maximumScore',
            key: 'maximumScore',
        },
        {
            title: 'grade',
            dataIndex: 'grade',
            key: 'grade',
        },
        {
            title: 'actions',
            key: 'actions',
            render: (record) => {
                return <>
                    <Tooltip placement="bottom" title={"Edit"}>
                    <EditOutlined onClick={() => {
                        onEditQuizSolutionTemplate(record)
                    }}/>
                    </Tooltip>
                    <Tooltip placement="bottom" title={"Delete"} color={"red"}>
                    <DeleteOutlined onClick={() => {
                        onDeleteQuizSolutionTemplate(record)
                    }
                    } style={{color: "red", marginLeft: 12}}/>
                    </Tooltip>
                </>
            }
        },
    ]

    const renderQuizSolutionTemplates = () => {
        if (state.quizSolutionTemplates && state.quizSolutionTemplates <= 0) {
            return "no data available";
        }
        return <Table
            dataSource={state.quizSolutionTemplates}
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

    const fetchQuizSolutionTemplates = (size, page) =>
        getQuizSolutionTemplates(size, page)
            .then(res => res.json())
            .then(data => {
                if (data.quizSolutionTemplates) {
                    for (let i = 0; i < data.quizSolutionTemplates.length; i++) {
                        data.quizSolutionTemplates[i].key = data.quizSolutionTemplates[i].testSolutionId
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
                message.error('there was a problem fetching quiz solution templates');
            })
            .finally(() => {

            });


    useEffect(() => {
        fetchQuizSolutionTemplates(pagination.pageSize, pagination.pageIndex)
    }, [pagination.pageIndex, pagination.pageSize, pagination.randomNumber])

    const onDeleteQuizSolutionTemplate = (record) => {
        Modal.confirm({
            title: 'Are you sure, you want to delete this record?',
            okText: 'Yes',
            okType: 'danger',
            onOk: () => {
                deleteQuizSolutionTemplate(record.testSolutionId)
                    .then(res => {
                        if (res.ok) {
                            setPagination(
                                {
                                    ...pagination,
                                    randomNumber: parseInt(Math.random() * 1000)
                                }
                            )
                            message.success(`quiz solution template deleted`)
                        }
                    })
                    .catch(() => {
                        message.error('there was a problem deleting quiz solution template');
                    })
                    .finally(() => {

                    });
            }
        })

    }

    const onEditQuizSolutionTemplate = (record) => {
        setIsEdited(true)
        setEditedRecord({...record})
    }

    return (

        <>
            <h1>Quiz Solution Templates</h1>
            <p></p>
            <div>
                <Button
                    type="primary"
                    onClick={()=>{
                        setIsCreated(true)
                        setCreatedRecord({...quizSolutionTemplate})
                    }}
                >Create New Quiz Solution Template</Button>
            </div>
            <p></p>
            <div>
                {renderQuizSolutionTemplates()}
            </div>
            <div>
                <Modal
                    title="Edited Quiz Solution Template"
                    open={isEdited}
                    okText="Save"
                    onCancel={() => {
                        setIsEdited(false)
                    }}
                    onOk={() => {
                        setIsEdited(false)
                        sendQuizSolutionTemplate(editedRecord)
                            .then(res => {
                                if (res.ok) {
                                    setPagination(
                                        {
                                            ...pagination,
                                            randomNumber: parseInt(Math.random() * 1000)
                                        }
                                    )
                                    message.success(`quiz solution template sent`)
                                }
                            })
                            .catch(() => {
                                message.error('there was a problem sending quiz solution template');
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
                    title="Created Quiz Solution Template"
                    open={isCreated}
                    okText="Create"
                    onCancel={() => {
                        setIsCreated(false)
                    }}
                    onOk={() => {
                        setIsCreated(false)
                        createQuizSolutionTemplate(createdRecord)
                            .then(res => {
                                if (res.ok) {
                                    setPagination(
                                        {
                                            ...pagination,
                                            randomNumber: parseInt(Math.random() * 1000)
                                        }
                                    )
                                    message.success(`quiz solution template created`)
                                }
                            })
                            .catch(() => {
                                message.error('there was a problem creating quiz solution template');
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

export default QuizSolutionTemplates;