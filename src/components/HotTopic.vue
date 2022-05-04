<template>
  <div>
      <section>
        <header>
          <img :src="/^82+/.test(topic.picture) ? `http://${topic.picture}` : topic.picture">
        </header>
        <article>
          <img :src="/^82+/.test(topic.picture) ? `http://${topic.picture}` : topic.picture">
          <span>
            #{{topic.content}}#
          </span>
          <p>
            热度:{{topic.hot}}
          </p>
        <form class="pubForm">
          <img :src="this.$store.state.HeaderAdvUrl" class="headerAdv">
          <textarea
            name="publish"
            cols="20"
            rows="8"
            :placeholder="'#'+topic.content+'#'"
            v-model="dynamicContent"
          ></textarea>
          <ul class="row">
              <el-col :span="2" :offset="22"
                ><div class="grid-content bg-purple-light">
                  <el-button type="warning" @click="publish">发布微博</el-button>
              </div></el-col>
          </ul>
        </form>
       </article>
      </section>
      <DynamicMainSection :datap='datap' ></DynamicMainSection>
  </div>
</template>

<script>
import DynamicMainSection from '@/components/DynamicMainSection'
import axios from 'axios'
export default {
  name:'HotTopic',
  data(){
    return{
      datap:[],
      dynamicContent:'',
      topic:{},
      pageNumber:0,
      flag:true
    }
  },
  components:{
    DynamicMainSection
  },
  beforeMount(){
    this.$store.commit('changeLoading',true)
    axios({
      url:"http://111.229.237.78:8080/topic",
      method:'POST',
      params:{
        topic:this.$router.currentRoute.query.topic,
        pageNumber:0
      }
    }).then(response=>{
      for (const key in response.data) {
        if (typeof response.data[key].dynamic == 'object') {
          this.datap.push(response.data[key])
        }
      }
      this.topic = response.data.topic
      this.$store.commit('changeLoading',false)
      this.dynamicContent = "#" + this.topic.content + "#";
    }).catch(err=>{
      console.log(err);
    })
  },
  methods:{
    publish(e){
      e.preventDefault();

      if (this.$store.state.isLog == 'no') {
          alert('你还没登陆呢！');
          return;
      }

      if (this.dynamicContent !== "" || this.photo.length > 0) {
        // 正则表达式取出topic
        const reg = /#.+#/;

        var Topic = this.dynamicContent.match(reg);

        Topic = Topic == null ? null : Topic[0]

        if (Topic !== null ) {
          
          while (Topic.indexOf('#')!== -1) {

            Topic = Topic.replace('#','')

          }

          this.dynamicContent = this.dynamicContent.replace(this.dynamicContent.substr(this.dynamicContent.indexOf('#'),Topic.length + 2),'')

          axios({ 
            url:'http://82.157.48.184:5482/dynamic',
            method:'POST',
            params:{
              message:this.dynamicContent,
              topic:Topic
            },
            headers:{
              'token': this.$store.state.token,
            }
            }).then(response=>{
              console.log(response.data);
              this.$emit('publish',response.data)
              alert('发布成功!');
              
            }).catch(err=>{
              alert('发布失败！',err)
              console.log(err);
          })

        }else{
          axios({ 
            url:'http://82.157.48.184:5482/dynamic',
            method:'POST',
            params:{
              message:this.dynamicContent,
            },
            headers:{
              'token': this.$store.state.token,
            }
            }).then(response=>{
              console.log(response.data);
              this.$emit('publish',response.data)
              alert('发布成功!');
              
            }).catch(err=>{
              alert('发布失败！',err)
              console.log(err);
          })
        }
        
        // 清空输入
        this.dynamicContent = '',
        this.photo = [];
        this.formdata = new FormData();
      }
    }
  },
  // beforeRouteEnter(to,from,next){
  //   // if (window.router.currentRoute.query.topic) {
  //   //   next();
  //   // }else{
  //   //   alert("你还没有选择话题呢！")
  //   // }
  // }
}
</script>

<style lang='less' scoped>
  section{
    position: relative;
    header{
      img{
        width: 100%;
        height: 150px;
        background-color: rgb(221, 216, 216);
        filter: blur(15px);
      }
    }
    article{
      width: 100%;
      height: 220px;
      img{
        position: absolute;
        width: 80px;
        height: 80px;
        top: -30px;
        user-select: none;
      }
      span{
        position: absolute;
        left: 100px;
        top: -30px;
        font-size: 20px;
        font-weight: 700;
        color: white;
        vertical-align: top;
      }
      p{
        padding-left: 100px;
        border-top: 3px solid rgb(83, 81, 81);
      }
      .pubForm{
        margin-top: 50px;
        .headerAdv{
          width: 40px;
          height: 40px;
          margin-top: 130px;
          margin-left: -10px;
        }
        .el-button{
          margin-top: 10px;
        }
      }
    }
  }
</style>