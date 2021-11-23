import Vue from 'vue'
import Vuex from 'vuex'

import accountTypes from './modules/account-types';
import transactionActions from './modules/transaction-actions';

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
  },
  mutations: {
  },
  actions: {
  },
  modules: {
    accountTypes,
    transactionActions,
  }
})
