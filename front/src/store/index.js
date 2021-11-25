import Vue from 'vue';
import Vuex from 'vuex';

import accounts from './modules/accounts';
import accountTypes from './modules/account-types';
import transactionActions from './modules/transaction-actions';
import transactionTypes from './modules/transaction-types';

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
  },
  mutations: {
  },
  actions: {
  },
  modules: {
    accounts,
    accountTypes,
    transactionActions,
    transactionTypes,
  }
});
