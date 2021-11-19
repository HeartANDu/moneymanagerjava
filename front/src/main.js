import Vue from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';
import vuetify from '@/plugins/vuetify';
import Toasted from 'vue-toasted';
import Vuelidate from 'vuelidate';
import '@mdi/font/css/materialdesignicons.css';

require('@/plugins/axios');
require('@/plugins/auth');

Vue.config.productionTip = false;
Vue.use(Toasted);
Vue.use(Vuelidate);

new Vue({
  router,
  store,
  vuetify,
  render: h => h(App),
  methods: {
    success(message, duration = 2000) {
      this.$toasted.show(message, {
        duration: duration,
        className: 'success',
        action: {
          class: 'mdi mdi-close',
          onClick: (e, toastObject) => {
            toastObject.goAway(0);
          }
        },
      });
    },
    error(message, duration = 5000) {
      this.$toasted.show(message, {
        duration: duration,
        className: 'error',
        action: {
          class: 'mdi mdi-close',
          onClick: (e, toastObject) => {
            toastObject.goAway(0);
          }
        },
      });
    },
    responseError(response) {
      if (response && response.response && response.response.data && response.response.data.message) {
        this.error(response.response.data.message);
      } else {
        this.error('Unknown Error');
      }
    },
  }
}).$mount('#app');
