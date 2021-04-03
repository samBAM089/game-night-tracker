import axios from 'axios';

const baseUrl = 'auth/login';

export const loginUser = (username, password) =>
    axios
        .post(baseUrl, { username, password })
        .then((response) => response.data);
