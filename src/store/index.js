// 该文件用于vuex中最为核心的store

// 引入vue
// import { userSetter } from 'core-js/fn/symbol';
import Vue from 'vue';
// 引入vuex
import Vuex from 'vuex'

// 应用vuex
Vue.use(Vuex);

// 准备actions--用于响应组件中的动作
const actions = {
    
}

// 准备mutations--用于操作数据（state）
const mutations = {
    changeName(state,value){
        sessionStorage.setItem('name',value)

        state.name = value;
    },
    changeBirthday(state,value){
        sessionStorage.setItem('birthday',value)

        state.birthday = value;
    },
    changeAdvUrl(state,value){
        sessionStorage.setItem('HeaderAdvUrl',value)

        state.HeaderAdvUrl = value;
    },
    changeLog(state,value){
        // 修改会话中的状态值
        sessionStorage.setItem('isLog',value)
        // 修改vuex状态值
        state.isLog = value
    },
    changeComplete(state,value){
        // 修改会话中的状态值
        localStorage.setItem('isComplete',value)
        // 修改vuex状态值
        state.isComplete = value
    },
    changeLoading(state,value){
        // 修改vuex状态值
        state.loading = value
    },
    changeHttpFlag(state,value){
        state.httpFlag = value;
    },
    changeEmail(state,value){
        sessionStorage.setItem('email',value)

        state.email = value;
    },
    changeUid(state,value){
        sessionStorage.setItem('uid',value)

        state.uid = value;
    },
    changeToken(state,value){
        sessionStorage.setItem('token',value)

        state.token = value;
    },
    changeComment(state,value){
        state.comment = value;
    },
    changeLike(state,value){
        state.like = value;
    },
    changeAplay(state,value){
        state.aplay = value;
    },
}

// 准备state--用于存储数据（state）
const state = {
    // title:['首页','视频','信息','主页'],
    token:  sessionStorage.getItem('token'),
    email:  sessionStorage.getItem('email'),
    HeaderAdvUrl:sessionStorage.getItem('HeaderAdvUrl'),
    name:   sessionStorage.getItem('name'),
    isLog:  sessionStorage.getItem('isLog'),
    isComplete: localStorage.getItem('isComplete'),
    uid:sessionStorage.getItem('uid'),
    birthday:   sessionStorage.getItem('birthday'),
    loading:false,
    comment:0,
    like:0,
    aplay:0,

    httpFlag:false
}

// 创建store 并向外暴露（导出）
export default new Vuex.Store({
    actions,
    mutations,
    state,
})



















