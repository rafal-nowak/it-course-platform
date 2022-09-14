import React, {useEffect, useState} from "react";
import {Button, Input, message, Modal, Table, Tooltip} from 'antd';
import {CheckCircleOutlined, EditOutlined, EyeOutlined, SketchOutlined} from "@ant-design/icons";
import {getQuizzes, sendQuizCommand} from "../Clients/quizClient";
import {checkCommand, rateCommand} from "../Helpers/quizCommands";
import ExamLayout from "../Quiz/ExamLayout";

const {TextArea} = Input;

function QuizView(props) {



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
                    {
                        record.status === "ASSIGNED" ?
                        <Tooltip placement="bottom" title={"Take"}>
                            <EditOutlined onClick={() => {
                                onTakeQuiz(record)
                            }}/>
                        </Tooltip>
                            :
                            <div></div>
                    }
                    <Tooltip placement="bottom" title={"Show"} color={"blue"}>
                        <EyeOutlined onClick={() => {
                            onShowQuiz(record)
                        }} style={{color: "blue", marginLeft: 12}}/>
                    </Tooltip>
                    <Tooltip placement="bottom" title={"Check"} color={"green"}>
                        <CheckCircleOutlined onClick={() => {
                            onCheckQuiz(record)
                        }
                        } style={{color: "green", marginLeft: 12}}/>
                    </Tooltip>
                    <Tooltip placement="bottom" title={"Rate"} color={"red"}>
                        <SketchOutlined onClick={() => {
                            onRateQuiz(record)
                        }
                        } style={{color: "red", marginLeft: 12}}/>
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
    const [isShown, setIsShown] = useState(false);
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

    const onShowQuiz = (record) => {
        setIsShown(true)
        setShownRecord({...record})
    }

    const onTakeQuiz = (record) => {
        setIsTaken(true)
        setTakenRecord({...record})
    }

    function onCheckQuiz(record) {
        sendQuizCommand(record.testId, checkCommand)
            .then(res => {
                console.log(res)
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
                console.log(res)
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
            <h1>Quizzes View</h1>
            <p></p>
            <div>
                {renderTests()}
            </div>
            <div>
                <Modal
                    title="Quiz"
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
                        onChange = {(e)=>{
                            setShownRecord(JSON.parse(e.target.value))
                        }
                        }
                    />


                </Modal>

                <Modal
                    title="Quiz"
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
                        <ExamLayout testId={takenRecord.testId} submitCallback={ () => setIsTaken(false)}></ExamLayout>
                    }

                </Modal>

            </div>

        </>

    );
}

export default QuizView;