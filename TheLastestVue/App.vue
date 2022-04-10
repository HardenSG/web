<template>
  <div v-loading.fullscreen.lock="this.$store.state.loading">
    <HeaderNav></HeaderNav>
    <div class="container">
      <el-row>
        <el-col :span="19"><div class="grid-content bg-purple">
          <keep-alive include="MiddleSection">
            <router-view></router-view>
          </keep-alive>
        </div></el-col>
        <el-col :span="5" :offset="19"><div class="grid-content bg-purple-light">
          <RightHots></RightHots>
        </div></el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import HeaderNav from "@/components/HeaderNav";
import RightHots from '@/components/RightHots'
import axios from 'axios'

export default {
  name: "App",
  data(){
    return {
      fullscreenLoading: this.$store.state.loading
    }
  },
  components: {
    HeaderNav,
    RightHots,
  },
  methods:{
    loadingMask(e){
      this.fullscreenLoading = e;
    },
    changeLoading(x,value){
      this.fullscreenLoading = value;
    }
  },
  beforeMount(){
    // 这个是判断登陆状态
    if(!sessionStorage.getItem('isLog')){
      
      this.$store.commit('changeLog','no')
      
    }
  },
  mounted(){
    setInterval(() => {
      axios({
      url:'http://82.157.48.184:5482/notices',
      method:'GET',
      headers:{
          token:this.$store.state.token
      }
      }).then(response=>{
        console.log(response.data);
          this.$store.commit('changeAplay',response.data.forwardCount);
          this.$store.commit('changeLike',response.data.likeCount);
          this.$store.commit('changeComment',response.data.commentCount);
      })
    }, 60000);
  }
  
};
</script>

<style>
* {
  margin: 0;
  padding: 0; 
  box-sizing: border-box;
  font-family: Microsoft YaHei, Heiti SC, tahoma, arial, Hiragino Sans GB,
    "\5B8B\4F53", sans-serif;
}

.container {
  position: relative;
  width: 1200px;
  height: 100vh;
  height: auto;
  margin: 0 auto;
}

body{
  background-color: rgb(245, 245, 245);
  /* height: 2500px; */
  color: rgb(51, 51, 51);
  overflow-y: scroll;
  overflow-x: scroll;
}

hr{
  margin-top: 10px;
  margin-bottom: 10px;
}

@font-face {
  font-family: 'iconfont';  /* Project id 3169833 */
  src: url('http://at.alicdn.com/t/font_3169833_5diqsqdddoq.woff2?t=1649164932263') format('woff2'),
       url('http://at.alicdn.com/t/font_3169833_5diqsqdddoq.woff?t=1649164932263') format('woff'),
       url('http://at.alicdn.com/t/font_3169833_5diqsqdddoq.ttf?t=1649164932263') format('truetype');
}
/* http: */

.iconfont {
  font-family: "iconfont" !important;
  font-size: 25px;
  font-style: normal;
  font-weight: 500;
  user-select: none;
  -webkit-font-smoothing: antialiased;
  -webkit-text-stroke-width: 0.2px;
  -moz-osx-font-smoothing: grayscale;
}

li {
  list-style: none;
}

i,
em {
  font-style: normal;
  font-weight: 300;
  vertical-align: middle;
  user-select: none;
}

a {
  text-decoration: none;
}
</style>
