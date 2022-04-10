<template>
      <div class="container">
        <div class="form-box">
            <div class="register-box " v-show="registerBox">
                <h1>注册</h1>
                <form class="personRegister">
                    <input type="text" name="name" placeholder="姓名" v-model="name"/>
                    <input type="text" placeholder="密码" v-model="pwd"/>
                    <input type="text" name="pwd" placeholder="确认密码" v-model="configPwd"/>
                    <input type="email" name="email" placeholder="邮箱" v-model="email"/>
                    <button class="sendCode" @click="sendCode" >{{word}}</button>
                    <input type="text" placeholder="验证码" v-model="code"/>
                    <button class="register" @click="register">注册</button>
                </form>
            </div>

            <div class="login-box" v-show="loginBox">
                <h1>登录</h1>
                <form class="personLogin">
                    <input type="email" name="logEmail" placeholder="邮箱" v-model="logEmail"/>
                    <input type="password" name="logPwd" placeholder="密码" v-model="logPwd"/>
                    <button class="login" @click="login">登录</button>
                </form>
            </div>
        </div>

        <div class="con-box leftyemian">
            <h2>Register</h2>
            <img src="../assets/1.png" />
            <p>已有帐号</p>
            <button id="login" @click="loginSlide">去登陆</button>
        </div>

        <div class="con-box rightyemian">
            <h2>Login</h2>
            <img src="../assets/preview.jpg"/>
            <button id="register" @click = 'registerSlide'>去注册</button>
        </div>

    </div>
</template>

<script>
import axios from 'axios';
// 引入dayjs
import dayjs from 'dayjs';
export default {
    name:'PersonLog',
    data(){
        return{
            name:'',
            pwd:'',
            configPwd:'',
            email:'',
            code:'',
            logEmail:'',
            logPwd:'',
            loginBox:1,
            registerBox:0,
            flag:0,
            word:'发送'
        }
    },
    methods:{
        registerSlide(){
            this.loginBox = 0;

            this.registerBox = 1;

            document.querySelector('.form-box').style.transform = 'translateX(80%)';
        },
        loginSlide(){
            this.loginBox = 1;

            this.registerBox = 0;

            document.querySelector('.form-box').style.transform = 'translateX(0%)';
        },
        receiveCode(){
            axios({
                method:'POST',
                url:'http://82.157.48.184:5482/user/capture',
                params:{
                    email:this.email,
                }
            }).then(response=>{
                if(response.data.msg){
                    alert(response.data.msg)
                }
            })
        },
        sendCode(e){
            e.preventDefault();
            
            const regEmail = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;

            if (regEmail.test(this.email)) {

                var s = null;
                
                var will = null; //将来总毫秒数 + 60000    这个要提前声明，因为每次点击都会获取，要获取当前时间加60秒，
                
                var timer = null;

                var _this = this;

                function mes(){
                    var now = +new Date(); //现在总毫秒数

                    s = Math.ceil((will - now) / 1000); //秒数 ，向上取整

                    if (s > 0) { //每次调用进行判断

                        _this.word = `${s}秒`;

                        document.querySelector('.sendCode').disabled = true
                    }
                    if (s <= 0) {
                        clearInterval(timer);

                        _this.word = '发送';

                        document.querySelector('.sendCode').disabled = false
                    }
                }

            will = +new Date() + 60000;

            mes(); //提前调用一次

            timer = setInterval(mes, 1000); //定时器调用计时函数

            // 调用ajax

            this.receiveCode();
            
            this.flag = 1;

    } else {

        alert("邮箱有误！");

        this.flag = 0;

    }
        },
        register(e){
            e.preventDefault();
            axios({
                method:'POST',
                url:'http://82.157.48.184:5482/user/regist',
                params:{
                    email:this.email,
                    name:this.name,
                    password:this.configPwd,
                    code:this.code
                }
            }).then(response=>{
                if (response.data.msg === '注册成功') {
                    alert('注册成功！ 请返回登录');
                }else{
                    alert('注册失败!')
                }
            })
        },
        login(e){
            e.preventDefault();
            this.$store.commit('changeLoading',true)
            axios({
                method:'POST',
                url:'http://82.157.48.184:5482/user',
                params:{
                    email:this.logEmail,
                    password:this.logPwd
                }
            }).then(response=>{
                this.$store.commit('changeLoading',false)
                if(response.data.msg === '登陆成功'){
                    alert('登陆成功！')
                    this.$store.commit('changeLog','yes');
                    console.log(response.data.user);
                    // 此处存储token
                    this.$store.commit('changeToken',response.data.user.token)
                    // 此处存储name
                    this.$store.commit('changeName',response.data.user.name)
                    // 此处存储email
                    this.$store.commit('changeEmail',response.data.user.email)
                    // 此处存储生日
                    this.$store.commit('changeBirthday',dayjs(response.data.user.birthday).format('YYYY-MM-DD'))
                    // 此处存储头像地址
                    this.$store.commit('changeAdvUrl','http://' + response.data.user.headPicture)
                    // 此处存储id
                    this.$store.commit('changeUid',response.data.user.uid)
                    
                    axios({
                        url:'http://82.157.48.184:5482/notices',
                        method:'GET',
                        headers:{
                            token:this.$store.state.token
                        }
                    }).then(response=>{
                        this.$store.commit('changeAplay',response.data.转发未读数)
                        this.$store.commit('changeLike',response.data.点赞未读数)
                        this.$store.commit('changeComment',response.data.评论未读数)
                    })
                    
                    if(!window.Vue.$store.state.isComplete){
                        this.$store.commit('changeComplete','no');
                    }

                    if (this.$store.state.isComplete == 'no') {
                        
                        alert('请先完善您的个人信息!');
                        
                        this.$router.push({path:'/EditMessage'})
    
                    }

                }else{
                    alert('登陆失败！')
                }
            })
        }

    }
}
</script>

<style scoped>
    * {
	margin: 0;
	padding: 0;
}

.container {
    position: relative;
	background-color: #fff;
	width: 650px;
	height: 455px;
    margin: 200px 30%;
	border-radius: 5px;
	box-shadow: 5px,5px,5px rgba(0,0,0,0.1);
    background: url(../assets/preview.jpg) center center;
}

.form-box {
	position: absolute;
	top: -10%;
	left: 5%;
    background: url(../assets/1.png) center center;
	width: 320px;
	height: 550px;
	border-radius: 5px;
	box-shadow: 2px 0 10px rgba(0,0,0,0.1);
	display: flex;
	justify-content: center;
	align-items: center;
	z-index: 2;
	transition: 0.5s ease-in-out;
}

.login-box .register-box {
    position: relative;
	display: flex;
	flex-direction: column;
	align-items: center;
	width: 100%;
	height: 200px;
}

.hidden {
	display: none;
	transition: 0.5s;
}

h1 {
	text-align: center;
	margin-bottom: 25px;
	text-transform: uppercase;
	color: #fff;
	letter-spacing: 5px;
}

input {
	background-color:transparent ;
	width: 70%;
	color: #fff;
	border: none;
	border-bottom: 1px solid rgba(255,255,255,0.4);
	padding: 10px 0;
	text-indent: 10px;
	margin: 8px 0;
	font-size: 14px;
	letter-spacing: 2px;
	margin-left: 15%;
}

input::placeholder {
	color: rgb(255,400,610);
}

input:focus {
	color:yellow;
	outline: none;
	border-bottom: 1px solid #a262ad80;
	transition: 0.5s;
}

input:focus::-moz-placeholder {
	opacity: 0;
}

.form-box button {
	width: 70%;
	margin-top: 35px;
	background-color: #f6f6f6;
	outline: none;
	border-radius: 8px;
	padding: 13px;
	color: a262ad;
	letter-spacing: 2px;
	border: none;
	cursor: pointer;
	margin-left: 15%;
}

.form-box button:hover {
	background-color: #a262ad;
	color: #f6f6f6;
	transition: background-color 0.5s ease;
}

.con-box {
	width: 50%;
	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: center;
	position: absolute;
	top: 50%;
	transform: translateY(-50%);
}

.leftyemian {
	left: -2%;
}

.rightyemian {
	right: -2%;
}

.con-box h2 {
	color: #8e9aaf;
	font-size: 25px;
	font-weight: bold;
	letter-spacing: 3px;
	text-align: center;
	margin-bottom: 4px;
}

.con-box p {
	font-size: 12px;
	letter-spacing: 2px;
	color: #8e9aaf;
	text-align: center;
}

.con-box img {
	width: 150px;
	height: 150px;
	opacity: 0.9;
	margin: 40px 0;
}

.con-box button {
	margin-top:3% ;
	background-color: #fff;
	color: #a262ad;
	border: 1px solid #d3b7d8;
	padding: 6px 10px;
	border-radius: 5px;
	letter-spacing: 1px;
	outline: none;
	cursor: pointer;
}
 
.sendCode {
    position: absolute;
    right: 20px;
    bottom: 40%;
    width: 60px !important;
    height: 30px !important;
    padding: 0 !important;
}

.con-box button:hover {
	background-color: #d3b7d8;
	color: #fff;
}
</style>
