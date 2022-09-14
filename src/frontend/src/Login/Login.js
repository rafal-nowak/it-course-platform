import {Button, Form, Input} from "antd";
import { Buffer } from 'buffer';
import { login } from '../Services/userService'
import { authHeader } from '../Helpers/authHeader'

function Login(props) {

    const onFinish=(e) => {
        console.log(e);
        const string = `${e.username}:${e.password}`;
        console.log(string);
        const encodedString = Buffer.from(string).toString('base64');
        console.log(encodedString)
        const decodedString = Buffer.from(encodedString, 'base64').toString('ascii');
        console.log(decodedString)
        login(e.username, e.password)
        console.log(authHeader)
    }

    return (

        <>
            <div>Login</div>
            <Form onFinish={onFinish}>
                <Form.Item label='User Name' name='username'>
                    <Input placeholder='User name' required></Input>
                </Form.Item>
                <Form.Item label='Password' name='password' required>
                    <Input.Password placeholder='Password'></Input.Password>
                </Form.Item>
                <Form.Item>
                    <Button block type='primary' htmlType='submit'>Log In</Button>
                </Form.Item>
            </Form>
        </>

    );
}

export default Login;