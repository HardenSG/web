<template>
  <section class="clearfix">
    <div class="left">
      <el-card class="box-card">
        <div slot="header" class="clearfix header">
          <span>在线用户<i>点击小气泡进行聊天</i></span>
        </div>
        <div v-for="o in 4" :key="o" class="text item">
          <div class="col">
            <span>昵称</span>
            <i class="iconfont sort" @click="show = 1">&#xe618;</i>
            <em v-if="show == 1">Chating...</em>
          </div>
        </div>
      </el-card>
    </div>
    <div class="right">
      <div class="chatPanel">
        <header>
          Web聊天室  (
            <span v-for="(p,index) in user" :key="index" class="title">
              {{p.username}},
            </span>
          )
        </header>
        <article>
          <ul class="content">
            <div v-for="(m,index) in msgList" :key="index" >
              <li class="else" v-if="m.email !== $store.state.email">
              <span class="time">{{m.date}}</span>
              <img :src="m.headpicture" class="header">
              <router-link :to="{
                path:'/PersonPageSection/PersonPageHome',
                query:{
                  email:m.email
                }
              }"
              class="name">
                {{m.from}}
              </router-link>
              <div class="word">
                {{m.text}}
              </div>
            </li>
            <li class="me" v-if="m.email == $store.state.email">
              <span class="time">{{m.date}}</span>
              <img :src="m.headpicture" class="header">
              <div class="word">
                {{m.text}}
              </div>
            </li>
            </div>
          </ul>
        </article>
        <textarea v-model="sendMessage" @keydown.enter="sendMsg"></textarea>
        <footer>
          <el-button class="btn" @click="send" >发送</el-button>
        </footer>
      </div>
    </div>
  </section>
</template>

<script>

var ws;

export default {
  name: "ChatMain",
  data() {
    return {
      show: 0,
      sendMessage:'',
      user:[],
      msgList:[],
      flag:0
    };
  },
  methods:{
    send(){
      const msg = this.sendMessage;

      if (!msg.trim().length) {
        return;
      }

      ws.send(JSON.stringify({
        headPicture:this.$store.state.HeaderAdvUrl,
        type:0,
        text:msg
      }))

      this.sendMessage = ''
    },
    sendMsg(){
      const msg = this.sendMessage;

      if (!msg.trim().length) {
        this.sendMessage = ''
        return;
      }

      ws.send(JSON.stringify({
        headPicture:this.$store.state.HeaderAdvUrl,
        type:0,
        text:msg
      }))

      this.sendMessage = ''
    },
    WsEventOpen(e){
      console.log(e);
    },
    WsEventMessage(e){
      new Promise((reslove,reject)=>{
        if (JSON.parse(e.data).users) {
          console.log(JSON.parse(e.data).users);
          this.user = JSON.parse(e.data).users;
          console.log(this.user);
          reslove()
        }
        else{
          this.msgList.push(JSON.parse(e.data));
          console.log(JSON.parse(e.data));
          reslove()
        }
      }).then(()=>{
        document.querySelector(".content").scrollIntoView(false)
        document.querySelector(".content").scrollTop = document.querySelector(".content").scrollHeight + 20 + 'px'
      })
    },
    WsEventError(e){
      console.log(e);
    },
    WsEventClose(e){
      console.log(e);
    },
  },
  beforeMount(){
    ws = new WebSocket(`ws://111.229.237.78:8080/imserver/${this.$store.state.email}/${this.$store.state.name}`)
    let that = this;
    ws.addEventListener('open',that.WsEventOpen);
    ws.addEventListener('message',that.WsEventMessage);
    ws.addEventListener('error',that.WsEventError);
    ws.addEventListener('close',that.WsEventClose);
    if (localStorage.getItem("msgList") !== null) {
      this.msgList = JSON.parse(localStorage.getItem("msgList"))
    }
  },
  beforeRouteLeave(to,from,next){
    localStorage.setItem("msgList",JSON.stringify(this.msgList));
    next();
  }
};
</script>

<style lang='less'>
.left{
  float: left;
}
.right{
  float: left;
  .chatPanel{
    width: 482px;
    height: 600px;
    border: 1px solid #999;
    header{
      width: 100%;
      height: 7%;
      padding-top: 8px;
      text-align: center;
      border-bottom: 1px solid #666;
    }
    .title{
      display: inline-block;
      max-width: 190px;
      vertical-align: bottom;
      overflow: hidden;
      white-space: nowrap;
      text-overflow: ellipsis;
    }
    article{
      width: 100%;
      height: 60%;
      padding: 20px 10px 0 10px;
      border-bottom: 1px solid #666;
      overflow: auto;
      ul{
        li{
          margin-bottom: 30px;
        }
        .else{
          position: relative;
          .header{
            width: 40px;
            height: 40px;
            border-radius: 5px;
            user-select: none;
          }
          .name{
            vertical-align: top;
            user-select: none;
          }
          .word{
            width: 70%;
            padding: 8px 8px 8px 8px;
            border-radius: 4px;
            margin-left: 50px;
            margin-top: -20px;
            background-color: rgb(247, 246, 246);
            word-wrap: break-word;
            word-break: normal;
            &:hover{
              background-color: rgb(235, 235, 235);
            }
          }
          .time{
            position: absolute;
            left: 40%;
          }
        }
        .me{
          position: relative;
          .header{
            float: right;
            width: 40px;
            height: 40px;
            border-radius: 5px;
            user-select: none;
          }
          .name{
            vertical-align: top;
            user-select: none;
          }
          .word{
            width: 70%;
            margin-left: 90px;
            padding: 8px 8px 8px 8px;
            border-radius: 4px;
            background-color: rgb(149, 236, 105);
            word-wrap: break-word;
            word-break: normal;
            &:hover{
              background-color: rgb(137, 217, 97);
            }
          }
          .time{
            position: absolute;
            left: 40%;
            top: -20px;
            // margin-bottom: 10px;
          }
        }
      }
    }
    textarea{
      width: 100%;
      height: 28%;
      margin: 0 !important;
      border: 0 !important;
      background-color: white;
    }
    footer{
      width: 100%;
      height: 5%;
      // padding-top: 5px;
      border-top: 1px solid #666;
      .btn{
        float: right;
        height: 90% !important;
        padding-top: 5px;
      }
    }
  }
}
.el-card__header {
  padding-top: 8px !important;
  padding-bottom: 8px !important;
  span {
    i {
      font-size: 12px;
    }
  }
}
.text {
  font-size: 14px;

  .col {
    display: block;
    span {
      font-size: 16px;
      user-select: none;
    }
    .sort {
      font-size: 18px;
      cursor: pointer;
      &:hover {
        color: rgb(120, 198, 42) !important;
      }
    }
    em {
      font-size: 12px;
    }
  }
}

.item {
  margin-bottom: 18px;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both;
}

.box-card {
  width: 230px;
}
//隐藏滚动条
article::-webkit-scrollbar{
  display: none;
}
</style>