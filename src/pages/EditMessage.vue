<template>

      <el-row>
        <el-col :span="5">
          <LeftNav title="资料编辑">
            <ul class="row">
              <li>        
                <router-link :to="{
                  path:'/PersonPageSection/PersonPageHome',
                  query:{
                    email:$store.state.email
                  }
                }">
                  <span class="iconfont">&#xe603;<i>我的主页</i></span>
                </router-link>
              </li>
            </ul>
          </LeftNav>
        </el-col>
        <el-col :span="19" :offset="5">
          <EditMessageMain></EditMessageMain>
        </el-col>
      </el-row>

</template>

<script>
import LeftNav from '@/components/LeftNav'
import EditMessageMain from '@/components/EditMessageMain'

export default {
  name:'PersonPageSection',
  components:{
      LeftNav,
      EditMessageMain
  },
  
};
</script>