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
                <span class="iconfont" @click="lastest"
                  >&#xe6c8;<i>最新</i></span
                >
              </router-link>
            </li>
            <li>
              <router-link to="">
                <span class="iconfont" @click="follow"
                  >&#xe601;<i>关注</i></span
                >
              </router-link>
            </li>
          </ul>
        </LeftNav>
      </el-col>
      <el-col :span="19" :offset="5">
        <!-- 发布微博组件 -->
        <PublishDynamic @publish="receiveData"></PublishDynamic>
        <!-- 轮播图组件 -->
        <ImgSlide></ImgSlide>
        <!-- 主要动态的组件 -->
        <DynamicMainSection
          :datap="datap"
        ></DynamicMainSection>
      </el-col>
    </el-row>
  </div>
  <!-- </div> -->
</template>

<script>
import LeftNav from "@/components/LeftNav";
import PublishDynamic from "@/components/PublishDynamic";
import ImgSlide from "@/components/ImgSlide";
import DynamicMainSection from "@/components/DynamicMainSection";
import axios from "axios";

export default {
  name: "MiddleSection",
  data() {
    return {
      datap: [],
      flag: 1,
      count: 1,
      followFlag: 0,
    };
  },
  components: {
    LeftNav,
    PublishDynamic,
    ImgSlide,
    DynamicMainSection,
  },
  methods: {
    // 此方法是由window的滚动事件进行调用，涉及url的不同
    axiosScroll(url, param) {
      // 判断当前是否可发请求
      if (this.$store.state.httpFlag === true) {
        // 调用请求函数
        this.axiosRequest(url, param);
      } 
    },
    // 这个就是一个发请求的方法，不涉及是谁请求
    axiosRequest(url, param) {
      axios({
        url: url,
        method: "GET",
        params: param,
        headers:{
          token:this.$store.state.token
        }
      })
        .then((response) => {
          if (response.data.message == '成功') {
            this.datap.push(response.data.info1);
            this.datap.push(response.data.info2);
            this.datap.push(response.data.info3);
            this.datap.push(response.data.info4);
            this.datap.push(response.data.info5);
            this.flag = 1;
            console.log(response.data);
          }else{
            alert("已经到底了！")
            console.log(response.data);
          }
        })
        .catch(() => {
          console.log("catch");
          alert("没有更多信息了");
        });
    },
    // 这是滚动条距离底部距离  
    touchScroll() {
      let offsetHeight = Math.max(
        document.body.scrollHeight,
        document.body.offsetHeight
      ); // 内容高度
      let clientHeight =
        window.innerHeight ||
        document.documentElement.clientHeight ||
        document.body.clientHeight ||
        0; //视窗高度
      let scrollTop =
        window.pageYOffset ||
        document.documentElement.scrollTop ||
        document.body.scrollTop ||
        0; //滚动条滚动高度
      return offsetHeight - clientHeight - scrollTop;
    },
    // 这是新发动态的添加
    receiveData(x, value) {
      this.datap.unshift(value);
      this.$router.go(0);
    },
    // 这是新增评论插入不过没大用了
    changeComment(value, did) {
      console.log(this.datap[1].comments);

      for (let i = 0; i < this.datap.length; i++) {
        if (this.datap[i].dynamic.did === did) {
          // this.datap[i].comments.push(value)

          console.log(this.datap);

          break;
        }
      }
    },
    // 关注人的切换
    follow() {
      this.count = 1;

      this.followFlag = 1;

      this.datap = this.datap.splice(0, -1);

      this.flag = 1;

    },
    // 最新切换，默认为最新刷新
    lastest() {
      this.count = 1;

      this.followFlag = 0;

      this.datap = this.datap.splice(0, -1);

      this.flag = 1;
    },
    // 就是为了mounted去调用
    axiosHeader(){
      if (this.flag == 1) {
        // 说明进行关注的请求
        if (this.followFlag == 1) {
          this.flag = 0;
          this.axiosScroll(
            "http://111.229.237.78:8080/dynamic/follow",
            {pageNumber:this.count++}
          );
        } else {
          // 说明进行最新的请求
          this.flag = 0;
          this.axiosScroll(
            "http://82.157.48.184:5482/dynamic",
            {page:this.count++}
          );
        }
      } else {
        console.log("等一等");
      }
    },
    fn(){
        // 判断当前是否滚动到300以下
        if (this.touchScroll() < 300) {
          // 判断当前是否是处于请求状态，0为正在请求
          this.axiosHeader();
        }else{
          return;
        }
    },
  },
  mounted() {
    // 当界面挂载时开启http请求的权限
    this.$store.commit("changeHttpFlag", true);
    // 初始化调用一下
    this.axiosHeader();

    let that = this;
    // 挂载后为window绑定scroll
    window.addEventListener("scroll",that.fn,true);
    // 这是加入评论信息的事情总线
    this.x.$on("comment", this.changeComment);
    // 用于接受
    this.x.$on("receive", this.receiveData);
  },
  activated() {
    this.flag = 1;
    
    this.$store.commit("changeHttpFlag", true);
  },
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
    .row {
      width: 90%;
      span {
        cursor: pointer !important;
        &:hover {
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