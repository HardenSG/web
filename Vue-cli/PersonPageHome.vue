<template>
  <div>
    <HeaderMessage :message="message"></HeaderMessage>
    <DynamicMainSection :datap='datap'></DynamicMainSection>
  </div>
</template>

<script>
import axios from "axios";
import HeaderMessage from "@/components/HeaderMessage";
import DynamicMainSection from "@/components/DynamicMainSection";

export default {
  name: "PersonPageHome",
  components: {
    HeaderMessage,
    DynamicMainSection,
  },
  data() {
    return {
      message: [],
      datap: [],
      flag : 1,
      pageNumber:1
    };
  },
  methods:{
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
    axiosRequest(){
      axios({
        url:'http://111.229.237.78:8080/dynamic/my',
        params:{
          email:this.$router.currentRoute.query.email,
          pageNumber:this.pageNumber++
        }
      }).then(response=>{
        for (const key in response.data) {
          if (typeof response.data[key].dynamic == 'object') {
            this.datap.push(response.data[key])
          }
        }
        this.flag = 1;
      })
    },
    axiosScroll(){
        if (this.touchScroll() < 300) {
          if (this.flag == 1) {
            this.flag = 0;
            this.axiosRequest();
          } else {
            console.log("等一等");
          }
        } else {
          return;
        }
    }
  },
  beforeMount(){
    axios({
      url: "http://82.157.48.184:5482/user/showDataByemail",
      method: "GET",
      params: {
        email: this.$router.currentRoute.query.email,
      },
    })
      .then((response) => {
        this.message.push(response.data.List[0]);

        this.message.follow = response.data.关注数;

        this.message.followed = response.data.粉丝数;
      })
      .catch(() => {
        console.log("错了！");
      });
      this.axiosRequest();
  },
  mounted() {
    let that = this;
    window.addEventListener(
      "scroll",
      that.axiosScroll,
      true
    );
    
  },
  beforeDestroy(){
    let that = this;
    window.removeEventListener('scroll',that.axiosScroll)
  }
};
</script>

<style>
</style>