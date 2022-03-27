import Vue from 'vue'
import App from './App.vue'

Vue.config.productionTip = false
// 引入elementUI
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';

Vue.use(ElementUI);

// 引入axios
import axios from 'axios';

Vue.use(axios);

// 引入Vuex
// import Vuex from 'vuex';

// 引入路由
import VueRouter from 'vue-router';

Vue.use(VueRouter);

import router from './router';

// 引入store
import store from './store/index.js'


const vm = new Vue({
  render: h => h(App),
  store,
  router
}).$mount('#app')

// 此处是为了router能够访问store数据向window挂载Vue
window.Vue = vm

