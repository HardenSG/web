<template>
  <div class="container">
    <el-row>
        <el-menu
            class="el-menu-demo"
            mode="horizontal">
        <el-col :span="6" :offset="2"><div class="grid-content bg-purple" >
            <el-input placeholder="搜索微博"  class="input-with-select" v-model="input3">
                <el-button slot="append" icon="el-icon-search"></el-button>
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
        <el-col :span="2"><div class="grid-content bg-purple">
          <el-menu-item ><span class="iconfont" title="热搜">&#xe61a;</span></el-menu-item>
        </div></el-col>
        <el-col :span="2"><div class="grid-content bg-purple-light">
          <el-badge :value="100" :max="10" class="item"/>
                <el-menu-item ><span class="iconfont" title="私信">&#xe628;</span></el-menu-item>
        </div></el-col>
        <el-col :span="2">
          <router-link to="/PersonPageSection">
            <div class="grid-content bg-purple-light">
              <el-menu-item ><span class="iconfont" :title="headerAdvName"><img :src="headerAdvUrl" /></span></el-menu-item>
            </div>
          </router-link>
        </el-col>
        <el-col :span="2"><div class="grid-content bg-purple-light">
          <el-menu-item ><span class="iconfont" @click="toggleColor" title="明暗">&#xe620;</span></el-menu-item>
        </div></el-col>
        <el-col :span="2"><div class="grid-content bg-purple-light">
          <el-menu-item ><span class="iconfont" title="编辑">&#xe6b0;</span></el-menu-item>
        </div></el-col>
      </el-menu>
    </el-row>
  </div>
</template>

<script>

export default {
  name: "HeaderNav",
  data(){
    return{
      input3:'',
      color:['black','rgb(245,245,245)'],
      index:0,
      headerAdvUrl:this.$store.state.HeaderAdvUrl,
      headerAdvName:this.$store.state.name
    }
  },
  computed:{
    // CountheaderAdvUrl(){
    //   return this.$store.headerAdvUrl
    // }
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
    }
  }
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