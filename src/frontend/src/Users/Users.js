import {useEffect, useState} from "react";
import {Button, Input, message, Modal, Table, Tooltip} from 'antd';
import {DeleteOutlined, EditOutlined} from "@ant-design/icons";
import {createUser, deleteUser, getUsers, sendUser} from "../Clients/userClient";
import {userTemplate} from "../Helpers/userTemplate";

const {TextArea} = Input;

function Users(props) {

    const columns = [
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
                    <Tooltip placement="bottom" title={"Edit"}>
                    <EditOutlined onClick={() => {
                        onEditUser(record)
                    }}/>
                    </Tooltip>
                    <Tooltip placement="bottom" title={"Delete"} color={"red"}>
                    <DeleteOutlined onClick={() => {
                        onDeleteUser(record)
                    }
                    } style={{color: "red", marginLeft: 12}}/>
                    </Tooltip>
                </>
            }
        },
    ]

    const renderUsers = () => {
        if (state.users && state.users <= 0) {
            return "no data available";
        }
        return <Table
            dataSource={state.users}
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

    const fetchUsers = (size, page) =>
        getUsers(size, page)
            .then(res => res.json())
            .then(data => {
                if (data.users) {
                    for (let i = 0; i < data.users.length; i++) {
                        data.users[i].key = data.users[i].id
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
                message.error('there was a problem fetching users');
            })
            .finally(() => {

            });


    useEffect(() => {
        fetchUsers(pagination.pageSize, pagination.pageIndex)
    }, [pagination.pageIndex, pagination.pageSize, pagination.randomNumber])

    const onDeleteUser = (record) => {
        Modal.confirm({
            title: 'Are you sure, you want to delete this record?',
            okText: 'Yes',
            okType: 'danger',
            onOk: () => {
                deleteUser(record.id)
                    .then(res => {
                        console.log(res)
                        if (res.ok) {
                            setPagination(
                                {
                                    ...pagination,
                                    randomNumber: parseInt(Math.random() * 1000)
                                }
                            )
                            message.success(`user deleted`)
                        }
                    })
                    .catch(() => {
                        message.error('there was a problem deleting user');
                    })
                    .finally(() => {

                    });
            }
        })

    }

    const onEditUser = (record) => {
        setIsEdited(true)
        setEditedRecord({...record})
    }

    return (

        <>
            <h1>Users</h1>
            <p></p>
            <div>
                <Button
                    type="primary"
                    onClick={()=>{
                        setIsCreated(true)
                        setCreatedRecord({...userTemplate})
                    }}
                >Create New User</Button>
            </div>
            <p></p>
            <div>
                {renderUsers()}
            </div>
            <div>
                <Modal
                    title="Edited User"
                    open={isEdited}
                    okText="Save"
                    onCancel={() => {
                        setIsEdited(false)
                    }}
                    onOk={() => {
                        setIsEdited(false)
                        sendUser(editedRecord)
                            .then(res => {
                                if (res.ok) {
                                    setPagination(
                                        {
                                            ...pagination,
                                            randomNumber: parseInt(Math.random() * 1000)
                                        }
                                    )
                                    message.success(`user saved`)
                                }
                            })
                            .catch(() => {
                                message.error('there was a problem editing user');
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
                    title="Created User"
                    open={isCreated}
                    okText="Create"
                    onCancel={() => {
                        setIsCreated(false)
                    }}
                    onOk={() => {
                        setIsCreated(false)
                        createUser(createdRecord)
                            .then(res => {
                                if (res.ok) {
                                    setPagination(
                                        {
                                            ...pagination,
                                            randomNumber: parseInt(Math.random() * 1000)
                                        }
                                    )
                                    message.success(`user created`)
                                }
                            })
                            .catch(() => {
                                message.error('there was a problem creating user');
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

export default Users;