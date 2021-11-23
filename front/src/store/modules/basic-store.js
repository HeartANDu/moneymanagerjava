import axios from 'axios';

export default function (path) {
    return {
        namespaced: true,
        state: {
            items: [],
            initialized: false,
            path: path,
            loading: false,
        },
        getters: {
            items(state) {
                return state.items;
            },
            url(state) {
                return state.path;
            },
        },
        mutations: {
            setItems(state, items) {
                state.items = items;
            },
            setInitialized(state, initialized) {
                state.initialized = initialized;
            },
            setLoading(state, loading) {
                state.loading = loading;
            },
        },
        actions: {
            getItems({getters}, {params = {}}) {
                return axios.get(getters.url, {params});
            },
            init({commit, dispatch, state}, payload = {}) {
                return new Promise((resolve, reject) => {
                    if (!state.initialized || payload.force) {
                        commit('setLoading', true);
                        dispatch('getItems', {params: payload.params ?? {}})
                            .then(response => {
                                commit('setItems', response.data);
                                commit('setInitialized', true);
                                resolve();
                            })
                            .catch(() => reject())
                            .finally(() => commit('setLoading', false));
                    } else {
                        commit('setLoading', false);
                        resolve();
                    }
                });
            },
        },
    }
}
