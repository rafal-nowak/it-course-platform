import {createQuizTemplate, deleteQuizTemplate, getQuizTemplates, sendQuizTemplate} from "../Clients/quizTemplateClient";
import {useEffect, useState} from "react";
import {Button, Input, message, Modal, Table, Tooltip} from 'antd';
import {DeleteOutlined, EditOutlined} from "@ant-design/icons";
import {quizTemplate} from "../Helpers/quizTemplate";

const {TextArea} = Input;

function QuizTemplates(props) {

    const columns = [
        {
            title: 'testTemplateId',
            dataIndex: 'testTemplateId',
            key: 'testTemplateId',
        },
        {
            title: 'gradingTableId',
            dataIndex: 'gradingTableId',
            key: 'gradingTableId',
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
                        onEditQuizTemplate(record)
                    }}/>
                    </Tooltip>
                    <Tooltip placement="bottom" title={"Delete"} color={"red"}>
                    <DeleteOutlined onClick={() => {
                        onDeleteQuizTemplate(record)
                    }
                    } style={{color: "red", marginLeft: 12}}/>
                    </Tooltip>
                </>
            }
        },
    ]

    const renderQuizTemplates = () => {
        if (state.quizTemplates && state.quizTemplates <= 0) {
            return "no data available";
        }
        return <Table
            dataSource={state.quizTemplates}
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

    const fetchQuizTemplatess = (size, page) =>
        getQuizTemplates(size, page)
            .then(res => res.json())
            .then(data => {
                if (data.quizTemplates) {
                    for (let i = 0; i < data.quizTemplates.length; i++) {
                        data.quizTemplates[i].key = data.quizTemplates[i].testTemplateId
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
                message.error('there was a problem fetching quiz templates');
            })
            .finally(() => {

            });


    useEffect(() => {
        fetchQuizTemplatess(pagination.pageSize, pagination.pageIndex)
    }, [pagination.pageIndex, pagination.pageSize, pagination.randomNumber])

    const onDeleteQuizTemplate = (record) => {
        Modal.confirm({
            title: 'Are you sure, you want to delete this record?',
            okText: 'Yes',
            okType: 'danger',
            onOk: () => {
                deleteQuizTemplate(record.testTemplateId)
                    .then(res => {
                        if (res.ok) {
                            setPagination(
                                {
                                    ...pagination,
                                    randomNumber: parseInt(Math.random() * 1000)
                                }
                            )
                            message.success(`quiz template deleted`)
                        }
                    })
                    .catch(() => {
                        message.error('there was a problem deleting quiz template');
                    })
                    .finally(() => {

                    });
            }
        })

    }

    const onEditQuizTemplate = (record) => {
        setIsEdited(true)
        setEditedRecord({...record})
    }

    return (

        <>
            <h1>Quiz Templates</h1>
            <p></p>
            <div>
                <Button
                    type="primary"
                    onClick={()=>{
                        setIsCreated(true)
                        setCreatedRecord({...quizTemplate})
                    }}
                >Create New Quiz Template</Button>
            </div>
            <p></p>
            <div>
                {renderQuizTemplates()}
            </div>
            <div>
                <Modal
                    title="Edited Quiz Template"
                    open={isEdited}
                    okText="Save"
                    onCancel={() => {
                        setIsEdited(false)
                    }}
                    onOk={() => {
                        setIsEdited(false)
                        sendQuizTemplate(editedRecord)
                            .then(res => {
                                if (res.ok) {
                                    setPagination(
                                        {
                                            ...pagination,
                                            randomNumber: parseInt(Math.random() * 1000)
                                        }
                                    )
                                    message.success(`quiz template sent`)
                                }
                            })
                            .catch(() => {
                                message.error('there was a problem editing quiz template');
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
                    title="Created Quiz Template"
                    open={isCreated}
                    okText="Create"
                    onCancel={() => {
                        setIsCreated(false)
                    }}
                    onOk={() => {
                        setIsCreated(false)
                        createQuizTemplate(createdRecord)
                            .then(res => {
                                if (res.ok) {
                                    setPagination(
                                        {
                                            ...pagination,
                                            randomNumber: parseInt(Math.random() * 1000)
                                        }
                                    )
                                    message.success(`quiz template created`)
                                }
                            })
                            .catch(() => {
                                message.error('there was a problem creating quiz template');
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

export default QuizTemplates;