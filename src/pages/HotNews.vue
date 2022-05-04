<template>
  <div class="middle">
      <el-row>
        <el-col :span="5">
          <LeftNav title="热搜">
            <ul class="row">
              <li>        
                <router-link to="/HotNews/HotTopic">
                  <span class="iconfont">&#xe7c2;<i>话题</i></span>
                </router-link>
              </li>
              <li>
                <router-link to="/HotNews/HotNewsMain">
                  <span class="iconfont">&#xe6c8;<i>最新热搜</i></span>
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
    name:'HotNews',
    components:{
        LeftNav,
    }
}
</script>

<style>

</style>