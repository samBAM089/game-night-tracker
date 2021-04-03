import axios from 'axios';

export const getAllGames = (token) =>
    axios
        .get('user/games', { headers: { Authorization: 'Bearer ' + token } })
        .then((response) => response.data);
