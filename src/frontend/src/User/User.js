import {user} from "../Services/userService";
import {useState} from "react";
import {Input} from "antd";

const {TextArea} = Input;

function User(props) {
    const [loggedInUser, setLoggedInUser] = useState(user())


    return (

        <>
            <div>User</div>
            <div>
                <TextArea
                    value={JSON.stringify(JSON.parse(loggedInUser), null, 4)}
                    rows={15}
                    disabled={true}
                />
            </div>
        </>

    );
}

export default User;