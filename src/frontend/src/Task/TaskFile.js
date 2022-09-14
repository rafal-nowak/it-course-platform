import {DownloadOutlined, UploadOutlined} from '@ant-design/icons';
import {Button, Divider, message, Upload} from "antd";
import {getFileAssignedToUserTask, postFileAssignedToUserTask} from "../Clients/taskClient";
import {useState} from "react";

function TaskFile(props) {
    const filePath = props.filePath;
    const fileName = filePath.substring(filePath.lastIndexOf('/') + 1)
    const taskId = props.taskId;
    const fileId = props.fileId;

    const [fileList, setFileList] = useState([]);
    const [uploading, setUploading] = useState(false);
    const handleUpload = () => {
        console.log(fileList)
        const formData = new FormData();
        formData.append('file', fileList.at(0))


        setUploading(true);

        postFileAssignedToUserTask(taskId, fileId, formData)
            .then((res) => res.json())
            .then(() => {
                setFileList([]);
                message.success('upload successfully.');
            })
            .catch(() => {
                message.error('upload failed.');
            })
            .finally(() => {
                setUploading(false);
            });

    };
    const uploadProps = {
        onRemove: (file) => {
            const index = fileList.indexOf(file);
            const newFileList = fileList.slice();
            newFileList.splice(index, 1);
            setFileList(newFileList);
        },
        beforeUpload: (file) => {
            setFileList([...fileList, file]);
            return false;
        },
        maxCount: 1,
        fileList,
    };

    return (

        <>
            <Divider orientation="center"></Divider>
            <p>File path: {filePath}</p>
            <p>File id: {fileId}</p>


                <Button
                    block
                    type="primary"
                    icon={<DownloadOutlined/>}
                    onClick={
                        () => {
                            console.log('button clicked')
                            console.log(fileName)
                            getFileAssignedToUserTask(taskId, fileId)
                                .then((response) => response.blob())
                                .then(data => {

                                    // Create blob link to download
                                    const url = window.URL.createObjectURL(
                                        new Blob([data]),
                                    );
                                    const link = document.createElement('a');
                                    link.href = url;
                                    link.setAttribute(
                                        'download',
                                        fileName,
                                    );

                                    // Append to html link element page
                                    document.body.appendChild(link);

                                    // Start download
                                    link.click();

                                    // Clean up and remove the link
                                    link.parentNode.removeChild(link);

                                    message.success(`file downloaded`)
                                })
                                .catch(() => {
                                    message.error('there was a problem downloading file');
                                })
                                .finally(() => {

                                });
                        }
                    }
                >
                    Download
                </Button>


            <p></p>


                <Upload {...uploadProps}>
                    <Button block icon={<UploadOutlined/>}>Select File</Button>
                </Upload>

                <Button
                    block
                    type="primary"
                    onClick={handleUpload}
                    disabled={fileList.length === 0}
                    loading={uploading}
                    style={{
                        marginTop: 16,
                    }}
                >
                    {uploading ? 'Uploading' : 'Start Upload'}
                </Button>

        </>

    );
}

export default TaskFile;