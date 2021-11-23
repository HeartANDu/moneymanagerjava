import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'

Vue.use(VueRouter)

const routes = [
  {
    meta: {auth: true},
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    meta: {auth: true},
    path: '/accounts',
    name: 'Accounts',
    component: () => import(/* webpackChunkName: "accounts" */ '../views/Accounts.vue')
  },
  {
    meta:  {auth: false},
    path: '/login',
    name: 'Login',
    component: () => import(/* webpackChunkName: "login" */ '../views/Login.vue')
  },
  {
    meta: {auth: false},
    path: '/register',
    name: 'Register',
    component: () => import(/* webpackChunkName: "register" */ '../views/Register.vue')
  },
  {
    meta: {error: true},
    props: {error: 'Forbidden'},
    path: '/403',
    name: 'Forbidden',
    component: () => import('../views/Error.vue')
  },
  {
    meta: {error: true},
    props: {error: 'Not Found'},
    path: '/404',
    name: 'Not Found',
    component: () => import('../views/Error.vue')
  },
  {
    path: '*',
    redirect: '/404',
  },
]

const router = new VueRouter({
  routes,
  mode: 'history',
})

export default router
