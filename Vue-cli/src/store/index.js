// 该文件用于vuex中最为核心的store

// 引入vue
import Vue from 'vue';
// 引入vuex
import Vuex from 'vuex'

// 应用vuex
Vue.use(Vuex);

// 准备actions--用于响应组件中的动作
const actions = {
    changetitle(context,value){
        context.commit('changeTitle',value)
    }
}

// 准备mutations--用于操作数据（state）
const mutations = {
    changeTitle(state,value){
        state.leftNav = value; 
    }
}

// 准备state--用于存储数据（state）
const state = {
    // title:['首页','视频','信息','主页'],
    
    email:'@qq.com',
    HeaderAdvUrl:'/img/preview.ab31eee0.jpg',
    name:'SG',
    isLog:'yes',
    isComplete:'yes',
    sex:1,
    introduce:'写bug改bug',
    birthday:'2002-05-08',
    school:'天津理工大学',
    schoolState:"college",
    schoolTime:'2021',
    leftNav:'首页',
}

// 创建store 并向外暴露（导出）
export default new Vuex.Store({
    actions,
    mutations,
    state
})



















