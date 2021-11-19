import Vue from 'vue';
import axios from 'axios';
import router from '../router';

let VueAuth = require('@websanova/vue-auth/dist/v2/vue-auth.common');
let bearer = require('@websanova/vue-auth/dist/drivers/auth/bearer.common');
let axiosDriver = require('@websanova/vue-auth/dist/drivers/http/axios.1.x.common');
let routerDriver = require('@websanova/vue-auth/dist/drivers/router/vue-router.2.x.common');

Vue.use(VueAuth, {
    plugins: {
        http: axios,
        router: router,
    },
    drivers: {
        http: axiosDriver,
        router: routerDriver,

        auth: {
            request: bearer.request,
            response(response) {
                if (response.data && response.data.token) {
                    return response.data.token;
                }
            },
        },
    },
});

axios.interceptors.response.use(r => r, error => {
    if (error.response.status === 401) {
        Vue.prototype.$auth.logout({
            makeRequest: false,
            redirect: '/login',
        });
    }

    return Promise.reject(error);
});
