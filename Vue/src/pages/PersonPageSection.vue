<template>
  <div class="middle">
      <el-row>
        <el-col :span="5">
          <LeftNav title="个人">
            <ul class="row">
              <li>        
                <router-link :to="{
                  path:'/PersonPageSection/PersonPageHome',
                  query:{
                    email:$router.currentRoute.query.email
                  }
                }">
                  <span class="iconfont">&#xe603;<i>我的主页</i></span>
                </router-link>
              </li>
              <li @click="toggleFollow">
                <router-link :to="{
                  path:'/PersonPageSection/follow',
                  query:{
                    email:$router.currentRoute.query.email,
                    flag:0
                  }
                }"
                >
                  <span class="iconfont">&#xe601;<i>我的关注</i></span>
                </router-link>
              </li>
              <li @click="toggleFans">
                <router-link :to="{
                  path:'/PersonPageSection/follow',
                  query:{
                    email:$router.currentRoute.query.email,
                    flag:1
                  }
                }"
                >
                  <span class="iconfont">&#xe60f;<i>我的粉丝</i></span>
                </router-link>
              </li>
              <li>
                <a href="javascript:;" @click="outLog" slot="reference">
                  <span class="iconfont">
                    &#xe607;
                    <i>注销登录</i>
                  </span>
                </a>
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
  data(){
    return{
      datap:[],
    }
  },
  components:{
      LeftNav,
  },
  methods:{
    outLog(){
      localStorage.clear();
      
      sessionStorage.clear();

      window.router.push({path:'/index'});

      window.router.go(0);

    },
    toggleFollow(){
      window.Vue.x.$emit('tog',0)
    },
    toggleFans(){
      window.Vue.x.$emit('tog',1)
    }
  },
  beforeRouteEnter(to,from,next){

    if (window.Vue.$store.state.isLog === 'no') {
        window.Vue.$confirm('您还未登录，您要现在登录吗？', {
            distinguishCancelAndClose: true,
            confirmButtonText: '现在登录',
            cancelButtonText: '放弃登录'
        })
        .then(() => {
            window.router.push({path:'/PersonLog'})
          })
        .catch(() => {
          window.Vue.$notify.info({
            title: "取消",
            message: "取消登录!",
            position: "bottom-left",
          });
          window.router.go(0)
        });
        }else{
            next();
      
            // window.router.push({path:'/PersonPageSection/PersonPageHome'})
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