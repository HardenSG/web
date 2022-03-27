<template>
  <!-- <div class="container"> -->
    <div class="middle">
      <el-row>
        <el-col :span="5">
          <LeftNav title="首页">
            <ul class="row">
              <li>        
                <router-link to="">
                  <span class="iconfont">&#xe7c2;<i>全部</i></span>
                </router-link>
              </li>
              <li>
                <router-link to="">
                  <span class="iconfont">&#xe6c8;<i>最新</i></span>
                </router-link>
              </li>
              <li>
                <router-link to="">
                  <span class="iconfont">&#xe601;<i>关注</i></span>
                </router-link>
              </li>
            </ul>
          </LeftNav>
        </el-col>
        <el-col :span="19" :offset="5">
          <!-- 发布微博组件 -->
          <PublishDynamic></PublishDynamic>
          <!-- 轮播图组件 -->
          <ImgSlide></ImgSlide>
          <!-- 主要动态的组件 -->
          <DynamicMainSection></DynamicMainSection>
        </el-col>
      </el-row>
    </div>
  <!-- </div> -->
</template>

<script>
import LeftNav from '@/components/LeftNav'
import PublishDynamic from '@/components/PublishDynamic'
import ImgSlide from '@/components/ImgSlide'
import DynamicMainSection from '@/components/DynamicMainSection'


export default {
  name: "MiddleSection",
  data(){
    return{
      
    }
  },
  components:{
    LeftNav,
    PublishDynamic,
    ImgSlide,
    DynamicMainSection,

  },
  
}
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
    p {
      // width: 100%;
      // height: auto;
      word-wrap: break-word;
      word-break: normal;
    }
    textarea {
      display: block;
      width: 90%;
      height: 90px;
      margin-left: 40px;
      padding: 10px;
      outline: 0;
      border: 1px solid transparent;
      background-color: rgb(240, 241, 245);
      border-radius: 10px;
      resize: none;

      &:focus {
        border: 1px solid rgb(245, 131, 0);
      }

    }
    .row{
      width: 90%;
      span{
          cursor: pointer !important;
          &:hover{
            color: orange;
          }
      }
    
    }
  }
}
// .el-carousel__container {
//   div{
//     border-radius: 10px;
//   }
// }
//   .publish {
    
// }
</style>