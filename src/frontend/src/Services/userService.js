import {Buffer} from "buffer";

export {
    user,
    login,
    logout
};

function user() {
    return localStorage.getItem('user');
}

function loginHeaders(username, password) {
    const authdata = `${username}:${password}`;
    const encodedAuthdata = Buffer.from(authdata).toString('base64');

    return {
        'Content-Type': 'application/json',
        'Authorization': 'Basic ' + encodedAuthdata
    };
}

function login(username, password) {
    const requestOptions = {
        method: 'GET',
        headers: loginHeaders(username, password),
    };

    return fetch(`http://localhost:8080/api/v1/users/me`, requestOptions)
        .then(handleResponse)
        .then(user => {
            // login successful if there's a user in the response
            if (user) {
                // store user details and basic auth credentials in local storage
                // to keep user logged in between page refreshes
                user.authdata = window.btoa(username + ':' + password);
                localStorage.setItem('user', JSON.stringify(user));
                window.location.reload()
                // window.location.assign("http://localhost:3000/")
                window.location.assign("/")

            }

            return user;
        })
        .catch(err => {
            logout()
        })
        .finally( () => {

        });
}

function logout() {
    // remove user from local storage to log user out
    localStorage.removeItem('user');
}

function handleResponse(response) {
    return response.text().then(text => {
        const data = text && JSON.parse(text);
        if (!response.ok) {
            if (response.status === 401) {
                // auto logout if 401 response returned from api
                logout();
                // location.reload(true);
                window.location.reload();
            }

            const error = (data && data.message) || response.statusText;
            return Promise.reject(error);
        }

        return data;
    });
}