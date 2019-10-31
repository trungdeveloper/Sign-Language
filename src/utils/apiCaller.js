import * as Config from '../configs/api';
import axios from 'axios';

export default function callApi(endpoint, method = 'GET', body) {
    return axios({
        method: method,
        url: `${Config.API_URL}/${endpoint}`,
        data: body
    }).then(res => {
        return res.data
    })
};