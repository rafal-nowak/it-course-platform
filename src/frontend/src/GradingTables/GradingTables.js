import {useEffect, useState} from "react";
import {Button, Input, message, Modal, Table, Tooltip} from 'antd';
import {DeleteOutlined, EditOutlined} from "@ant-design/icons";
import {
    createGradingTable,
    deleteGradingTable,
    getGradingTables,
    sendGradingTable
} from "../Clients/gradingTableClient";
import {gradingTableTemplate} from "../Helpers/gradingTableTemplate";

const {TextArea} = Input;

function GradingTables(props) {

    const columns = [
        {
            title: 'tableId',
            dataIndex: 'tableId',
            key: 'tableId',
        },
        {
            title: 'tableName',
            dataIndex: 'tableName',
            key: 'tableName',
        },
        {
            title: 'actions',
            key: 'actions',
            render: (record) => {
                return <>
                    <Tooltip placement="bottom" title={"Edit"}>
                    <EditOutlined onClick={() => {
                        onEditGradingTable(record)
                    }}/>
                    </Tooltip>
                    <Tooltip placement="bottom" title={"Delete"} color={"red"}>
                    <DeleteOutlined onClick={() => {
                        onDeleteGradingTable(record)
                    }
                    } style={{color: "red", marginLeft: 12}}/>
                    </Tooltip>
                </>
            }
        },
    ]

    const renderGradingTables = () => {
        if (state.gradingTables && state.gradingTables <= 0) {
            return "no data available";
        }
        return <Table
            dataSource={state.gradingTables}
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

    const fetchGradingTables = (size, page) =>
        getGradingTables(size, page)
            .then(res => res.json())
            .then(data => {
                if (data.gradingTables) {
                    for (let i = 0; i < data.gradingTables.length; i++) {
                        data.gradingTables[i].key = data.gradingTables[i].tableId
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
                message.error('there was a problem fetching grading tables');
            })
            .finally(() => {

            });


    useEffect(() => {
        fetchGradingTables(pagination.pageSize, pagination.pageIndex)
    }, [pagination.pageIndex, pagination.pageSize, pagination.randomNumber])

    const onDeleteGradingTable = (record) => {
        Modal.confirm({
            title: 'Are you sure, you want to delete this record?',
            okText: 'Yes',
            okType: 'danger',
            onOk: () => {
                deleteGradingTable(record.tableId)
                    .then(res => {
                        if (res.ok) {
                            setPagination(
                                {
                                    ...pagination,
                                    randomNumber: parseInt(Math.random() * 1000)
                                }
                            )
                            message.success(`grading table deleted`)
                        }
                    })
                    .catch(() => {
                        message.error('there was a problem deleting grading table');
                    })
                    .finally(() => {

                    });
            }
        })

    }

    const onEditGradingTable = (record) => {
        setIsEdited(true)
        setEditedRecord({...record})
    }

    return (

        <>
            <h1>Grading Tables</h1>
            <p></p>
            <div>
                <Button
                    type="primary"
                    onClick={()=>{
                        setIsCreated(true)
                        setCreatedRecord({...gradingTableTemplate})
                    }}
                >Create New Grading Table</Button>
            </div>
            <p></p>
            <div>
                {renderGradingTables()}
            </div>
            <div>
                <Modal
                    title="Edited Grading Table"
                    open={isEdited}
                    okText="Save"
                    onCancel={() => {
                        setIsEdited(false)
                    }}
                    onOk={() => {
                        setIsEdited(false)
                        sendGradingTable(editedRecord)
                            .then(res => {
                                if (res.ok) {
                                    setPagination(
                                        {
                                            ...pagination,
                                            randomNumber: parseInt(Math.random() * 1000)
                                        }
                                    )
                                }
                                message.success(`grading table modified`)
                            })
                            .catch(() => {
                                message.error('there was a problem modifying grading table');
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
                    title="Created Grading Table"
                    open={isCreated}
                    okText="Create"
                    onCancel={() => {
                        setIsCreated(false)
                    }}
                    onOk={() => {
                        setIsCreated(false)
                        createGradingTable(createdRecord)
                            .then(res => {
                                if (res.ok) {
                                    setPagination(
                                        {
                                            ...pagination,
                                            randomNumber: parseInt(Math.random() * 1000)
                                        }
                                    )
                                    message.success(`grading table created`)
                                }
                            })
                            .catch(() => {
                                message.error('there was a problem creating grading table');
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

export default GradingTables;