import Vue from 'vue';
import axios from 'axios';
import VueAxios from 'vue-axios';

axios.defaults.baseURL = 'http://localhost:8001';

Vue.use(VueAxios, axios);
