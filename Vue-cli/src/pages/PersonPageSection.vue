<template>
  <div class="middle">
      <el-row>
        <el-col :span="5">
          <LeftNav title="个人">
            <ul class="row">
              <li>        
                <router-link to="/PersonPageSection/PersonPageHome">
                  <span class="iconfont">&#xe603;<i>我的主页</i></span>
                </router-link>
              </li>
              <li>
                <router-link to="/PersonPageSection/follow">
                  <span class="iconfont">&#xe601;<i>我的关注</i></span>
                </router-link>
              </li>
              <li>
                <router-link to="">
                  <span class="iconfont">&#xe60f;<i>我的粉丝</i></span>
                </router-link>
              </li>
            </ul>
          </LeftNav>
        </el-col>
        <el-col :span="19" :offset="5">
          <router-view></router-view>
        </el-col>
      </el-row>
    </div>
</template>

<script>
import LeftNav from '@/components/LeftNav'


export default {
  name:'PersonPageSection',
  components:{
      LeftNav,
  },
  beforeRouteEnter(to,from,next){
    if (to.path == '/PersonPageSection') {
      next();
      window.router.push({path:'/PersonPageSection/PersonPageHome'})
      
      console.log('滚开！');
    }
  }
};
</script>

<style lang='less'>
.middle {
  // height: 2500px;
  margin-top: 70px;
  section {
    position: relative;
    width: 100%;
    height: auto;
    padding: 20px;
    background-color: white;
    margin-bottom: 10px;
    border-radius: 10px;
    box-shadow: 0 0 10px rgb(207, 204, 204);
  }
}

</style>