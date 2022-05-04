<template>
  <div>
  <section v-for="(p,index) in datap" :key="index">
      <header>
        <img :src="`http://${p.headPicture}`" class="headerAdv">
        <router-link :to="{
          path:'/PersonPageSection/PersonPageHome',
          query:{
            email:p.email
          }
        }">{{p.name}}</router-link>
        <i>{{p.date | dateFomat}}</i>
        <div class="content">{{word}}
          <router-link :to="{
            path:'/DetailMessage',
            query:{
              did:p.did
            }
          }"
          class="detail">
          查看详情</router-link> 
        </div>
      </header>

      <article>
        <img :src="`${p.picture}`" v-if="p.picture.length !== 0">
        <img :src="`http://${p.oldheadPicture}`" v-if="p.picture.length == 0">
        <div class="message">
          <router-link :to="{
          path:'/DetailMessage',
          query:{
            did:p.did
          }
        }">
          <i>{{p.content}}</i>
        </router-link>
          
        </div>
      </article>
      <footer>
         <!-- <i class="iconfont">&#xe6ad;</i> -->
      </footer>
  </section>
  </div>
</template>

<script>
import axios from 'axios'
import dayjs from 'dayjs'
export default {
    name:'CommentMain',
    data(){
      return{
        datap:[],
        word:'',
        flag:this.$router.currentRoute.query.flag
      }
    },
    methods:{
      axiosRequest(URL){
        this.datap=[];
        axios({
          url:URL,
          method:'GET',
          headers:{
            token:this.$store.state.token
          }
        }).then(response=>{
        console.log(response.data);
        for (var i = 0; i < response.data.length; i++) {
          // 定义对象
          var obj = {};
          // 内层循环
          for (var j = 0; j < response.data[i].length; j++) {
            if (j >= 3) {
              if (j % 2 == 0) {
                obj.email = response.data[i][j].email;
                obj.name = response.data[i][j].name;
                obj.headPicture = response.data[i][j].headPicture;
              }else{
                obj.date = response.data[i][j].commentDate
              }
            }else{
              obj.did = response.data[i][0].did;
              obj.content = response.data[i][0].content;
              obj.picture = response.data[i][2];
              obj.oldheadPicture = response.data[i][1].headPicture;
            }
            if ( j > 3 && j % 2 == 0){
              this.datap.push(obj)
            }
          }
        }
        console.log("11111",this.datap);
      })
      },
      justFlag(){
        if (this.flag == 'like') {
          this.axiosRequest('http://82.157.48.184:5482/dynamic/likeNotice');
          this.word = '有人点赞了你的动态'
        }
        if (this.flag == 'comment') {
          this.axiosRequest('http://82.157.48.184:5482/dynamic/commentNotice');
          this.word = '有人评论了你的动态'
        }
        if (this.flag == 'aplay') {
          this.axiosRequest('http://82.157.48.184:5482/dynamic/forwardNotice');
          this.word = '有人转发了你的动态'
        }
      },
      receiveEmit(data){
        this.flag = data;
        this.justFlag();
      }
    },
    beforeMount(){
      this.justFlag();
    },
    filters:{
      dateFomat(value){
        return dayjs(value).format("YY-MM-DD HH:mm:ss");
      }
    },
    mounted(){
      this.x.$on('togAlarm',this.receiveEmit)
    }
}
</script>

<style lang='less' scoped>
  header{
  position: relative;
  .headerAdv{
    width: 50px;
    height: 50px;
    border-radius: 25px;
  }
  a{
    font-size: 20px;
    vertical-align: top;
  }
  .detail{
    font-size: 16px !important;
  }
  i{
    position: absolute;
    top: 30px;
    left: 60px;
    font-size: 12px;
  }
  .content{
    margin-left: 10%;
  }
}
  article{
    width: 70%;
    height: 100px;
    margin-left: 10%;
    border-radius: 6px;
    background-color: rgb(243, 239, 239);
    img{
      float: left;
      width: 100px; 
      height: 100px !important; 
      border-top-left-radius: 6px;
      border-bottom-left-radius: 6px;
      background-color: rgb(237, 236, 236);
    }
    .message{
      float: left;
      width: 398px;
      height: 100%;
      font-weight: 700;
      line-height: 100px;
      a{
        display: block;
      }
      i{
        display: inline-block;
        width: 100% !important;
        font-size: 14px;
        padding-left: 20px;
        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
      }
    }
  }
  footer{
    padding-top: 10px;
    text-align: center;
  }
</style>