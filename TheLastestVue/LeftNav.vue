<template>
  <el-row class="container">
    <div :span="4">
      <div class="Nav">
      <h2>{{title}}</h2>   
          <slot>我是默认结构</slot>
      </div>
    </div>
  </el-row>

  
</template>

<script>
export default {
  name: "LeftNav",
  props:['title'],
  data(){
    return{
     
    }
   
  }

};
</script>

<style lang="less">
.container {
  position: relative;
  width: 1200px;
  height: auto;
  margin: 0 auto;
}

.orange {
  color: orange;
}

.Nav {
  position: fixed;
  top: 70px;
  width: 190px;
  // height: auto;
  border-radius: 10px;
  height: 100vh;
  padding-top: 20px;
  background-color: white;
  // box-shadow: 0 0 10px rgb(207, 204, 204);
  h2 {
    padding-left: 30px;
  }

  .row {
    padding-left: 0 !important;

    li {
      width: 80%;
      height: 40px;
      margin-left: 20px;
      line-height: 40px;
      text-align: center;
      cursor: pointer;
      span {
        font-size: 16px;

        i {
          font-style: normal;
          padding-left: 10px;
        }

      }

      &:hover {
        background-color: rgb(238, 238, 238);
        // background-color: orange;
        border-radius: 5px;
      }
    }
  }
}
</style>