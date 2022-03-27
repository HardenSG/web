<template>
  <div v-loading.fullscreen.lock="fullscreenLoading">
    <HeaderNav></HeaderNav>
    <div class="container">
      <el-row>
        <el-col :span="20"><div class="grid-content bg-purple">
          <router-view></router-view>
        </div></el-col>
        <el-col :span="4"><div class="grid-content bg-purple-light">
          <RightHots></RightHots>
        </div></el-col>
    </el-row>
    </div>
  </div>
</template>

<script>
import HeaderNav from "@/components/HeaderNav";
import RightHots from '@/components/RightHots'



export default {
  name: "App",
  data(){
    return {
      fullscreenLoading: false
    }
  },
  components: {
    HeaderNav,
    RightHots,
  },
  methods:{
    loadingMask(e){
      this.fullscreenLoading = e;
    }
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
}

hr{
  margin-top: 10px;
  margin-bottom: 10px;
}

@font-face {
  font-family: 'iconfont';  /* Project id 3169833 */
  src: url('http://at.alicdn.com/t/font_3169833_oheccvoiu38.woff2?t=1648392986516') format('woff2'),
       url('http://at.alicdn.com/t/font_3169833_oheccvoiu38.woff?t=1648392986516') format('woff'),
       url('http://at.alicdn.com/t/font_3169833_oheccvoiu38.ttf?t=1648392986516') format('truetype');
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
