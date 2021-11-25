import Vue from 'vue';
import axios from 'axios';
import VueAxios from 'vue-axios';
import queryString from 'query-string';

axios.defaults.baseURL = 'http://localhost:8001';
axios.interceptors.request.use(function (config) {
    config.paramsSerializer = params => queryString.stringify(params, {arrayFormat: 'brackets'});
    return config;
});

Vue.use(VueAxios, axios);
