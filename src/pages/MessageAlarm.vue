<template>
  <div class="middle">
      <el-row>
        <el-col :span="5">
          <LeftNav title="信息">
            <ul class="row">
              <li @click="like">        
                <router-link :to="{
                  path:'/MessageAlarm/CommentMain',
                  query:{
                    flag:'like'
                  }
                }">
                  <span class="iconfont">&#xe7c2;<i>点赞</i></span>
                </router-link>
              </li>
              <li @click="comment">
                <router-link :to="{
                  path:'/MessageAlarm/CommentMain',
                  query:{
                    flag:'comment'
                  }
                }">
                  <span class="iconfont">&#xe6c8;<i>评论</i></span>
                </router-link>
              </li>
              <li @click="aplay">
                <router-link :to="{
                  path:'/MessageAlarm/CommentMain',
                  query:{
                    flag:'aplay'
                  }
                }">
                  <span class="iconfont">&#xe6c8;<i>转发</i></span>
                </router-link>
              </li>
              <li>
                <router-link to="/MessageAlarm/ChatMain">
                  <span class="iconfont">&#xe6c8;<i>聊天室</i></span>
                </router-link>
              </li>
            </ul>
          </LeftNav>
        </el-col>
        <el-col :span="19" :offset="5">
            <!-- <AlarmMain></AlarmMain> -->
            <!-- <keep-alive include="ChatMain"> -->
              <router-view></router-view>
            <!-- </keep-alive> -->
        </el-col>
      </el-row>
    </div>
</template>

<script>
import LeftNav from '@/components/LeftNav'

export default {
    name:'MessageAlarm',
    components:{
      LeftNav,
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
              window.router.push({path:'/MessageAlarm/NoFound'})
          }
    },
    methods:{
      like(){
        this.x.$emit('togAlarm','like')
      },
      comment(){
        this.x.$emit('togAlarm','comment')
      },
      aplay(){
        this.x.$emit('togAlarm','aplay')
      }
    }

}
</script>

<style>

</style>