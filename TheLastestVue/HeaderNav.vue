<template>
  <div class="container">
    <el-row>
        <el-menu
            class="el-menu-demo"
            mode="horizontal">
        <el-col :span="6" :offset="2"><div class="grid-content bg-purple" >
            <el-input placeholder="搜索微博"  class="input-with-select" v-model="input3">
                <el-button slot="append" icon="el-icon-search" @click="searchWeibo"></el-button>
            </el-input>         
        </div></el-col>
        <el-col :span="2">
          <router-link to='/index'>
            <div class="grid-content bg-purple">
          <el-menu-item ><span class="iconfont" title="主页">&#xe609;</span></el-menu-item>
        </div>
          </router-link>
        </el-col>
        <el-col :span="2"><div class="grid-content bg-purple-light">
          <el-menu-item ><span class="iconfont" title="视频">&#xe600;</span></el-menu-item>
        </div></el-col>
        <el-col :span="2">
          <router-link to='/HotNews/HotNewsMain'>
            <div class="grid-content bg-purple">
              <el-menu-item ><span class="iconfont" title="热搜">&#xe61a;</span></el-menu-item>
            </div>
          </router-link>
        </el-col>
        <el-col :span="2">
          <router-link to='/MessageAlarm'>
            <div class="grid-content bg-purple">
              <el-badge :value="$store.state.comment + $store.state.like + $store.state.aplay" :max="10" class="item"/>
              <el-menu-item ><span class="iconfont" title="私信">&#xe628;</span></el-menu-item>
            </div>
          </router-link>
        </el-col>
        <el-col :span="2">
          <router-link :to="{
            path:'/PersonPageSection/PersonPageHome',
            query:{
              email:$store.state.email
            }
          }">
            <div class="grid-content bg-purple-light">
              <el-menu-item ><span class="iconfont" :title="name"><img :src="headerAdv" /></span></el-menu-item>
            </div>
          </router-link>
        </el-col>
        <el-col :span="2"><div class="grid-content bg-purple-light">
          <el-menu-item ><span class="iconfont" @click="toggleColor" title="明暗">&#xe620;</span></el-menu-item>
        </div></el-col>
        <el-col :span="2"><div class="grid-content bg-purple-light">
          <el-menu-item ><span class="iconfont" title="编辑" @click="publish">&#xe6b0;</span></el-menu-item>
        </div></el-col>
      </el-menu>
    </el-row>
  </div>
</template>

<script>
import axios from 'axios';
import {mapState} from 'vuex'
export default {
  name: "HeaderNav",
  data(){
    return{
      input3:'',
      color:['black','rgb(245,245,245)'],
      index:0,
    }
  },
  computed:{
    ...mapState({name:'name',headerAdv:'HeaderAdvUrl'})
  },
  methods:{
    toggleColor(){
      if (this.index === 0) {
        document.querySelector('body').style.backgroundColor = this.color[this.index];

        this.index = this.index + 1;
      }else{
        document.querySelector('body').style.backgroundColor = this.color[this.index];

        this.index = 0;
      }
    },
    searchWeibo(){
      this.$router.push({
        path:'/HotNews/HotTopic',
        query:{
          topic:this.input3
        }
      })
    },
    publish(){
      // 判断是否登录
      // if (this.$store.state.isLog == 'no') {
      //   alert('你还没登陆呢！');
      //   return;
      // }
      const reg = /#.+#/;
      var param={};

      this.$prompt('请输入你要发布的动态', '发布', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
        }).then(({ value }) => {

          var Topic = value.match(reg);

          Topic = Topic == null ? null : Topic[0]

          if (Topic !== null ) {
            
            while (Topic.indexOf('#')!== -1) {

              Topic = Topic.replace('#','')

            }

          value = value.replace(value.substr(value.indexOf('#'),Topic.length + 2),'')

          param = {
            message:value,
            topic:Topic
          }

          }else{
            param = {
              message:value
            }
          }


          axios({ 
            url:'http://82.157.48.184:5482/dynamic',
            method:'POST',
            params:param,
            headers:{
              'token': this.$store.state.token,
            }
            })
          .then(response=>{
            console.log(response.data);
            this.$notify({
              title: "发布成功！",

              message: response.data.message,

              type: "success",

              position: "bottom-left",

            });
            this.x.$emit('receive',response.data)
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '取消输入',
            position: "bottom-left",
          });       
        });

    }
  },
};
</script>

<style lang="less" scoped>
.container {
  position: relative;
  width: 1200px;
  height: auto;
  margin: 0 auto;
  .el-row{
    position: fixed;
    width: 1200px;
    text-align: center;
    z-index: 9999;
    .el-col{
    line-height: 56px;
  }
  .input-with-select{
    width: 200px;
    input{
      border-top-left-radius: 10px;
      border-bottom-left-radius: 10px;
    }
  }
  }
}

.toggele{
  background-color: black;
}

.origin{
  background-color: rgb(245, 245,245);
}

.el-menu {
  border-radius:10px ;
  background-color: white !important;
  border-bottom: 0 !important;
  .el-menu-item {
    position: relative;
  background-color: white !important;
  color: blue !important;
  &:focus{
    color: orange !important;
    border-bottom: 3px solid orange !important;
  }
  img{
    width: 30px;
    height: 30px;
    border-radius: 15px;
  }
}
}

.item{
  position: absolute;
  z-index: 9999;
  user-select: none;
}

</style>