import axios from 'axios';

export const getAllGames = (token) =>
    axios
        .get('user/games', { headers: { Authorization: 'Bearer ' + token } })
        .then((response) => response.data);

export const getAllSessions = (token) =>
    axios
        .get('user/sessions', { headers: { Authorization: 'Bearer ' + token } })
        .then((response) => response.data);

export const getAllPlayers = (token) =>
    axios
        .get('user/players', { headers: { Authorization: 'Bearer ' + token } })
        .then((response) => response.data);
