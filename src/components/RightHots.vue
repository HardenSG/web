<template>
    <div class="Nav">
      <h2>热搜</h2>
      <span class="iconfont loading" title="换一批">&#xe66d;</span>
        <ul class="row">
            <li v-for="(news,index) in News" :key="index" @click="goTopic(news.content)">
              <span :title="news.content"><em>{{index + 1}}</em><i>{{news.content}}</i></span>
            </li>
        </ul>
    </div>

</template>

<script>
import axios from 'axios'
export default {
    name:'RightHots',
    data(){
      return{
        News:[]
      }
    },
    beforeMount(){
      axios({
        url:'http://111.229.237.78:8080/topic',
        method:'GET',
      }).then(response=>{
        if(response.data.status == 200){
          this.News = response.data.topics;
        }
      }).catch(err=>{
        console.log('右侧热搜加载失败！');
      })
    },
    methods:{
      async goTopic(news){
        this.$router.push({
          path:'/HotNews/HotTopic',
            query:{
              topic:news
            }
        })
        this.$router.go(0)
      }
    }
}
</script>

<style scoped lang = 'less'>
  .Nav{
    margin-left: 20px;
    width: 230px;
    height: 80vh;
    h2{
      display: inline-block;
    }
    .loading {
      float: right;
      padding-right: 30px;
      font-size: 18px !important;
      cursor: pointer;
    }
    .row {
      li{
        span{
          display: inline-block;
          width: 100%;
          overflow: hidden;
          white-space: nowrap;
          
          /* color: rgb(199, 219, 81); */
          text-overflow: ellipsis;
          em{
            font-weight: 700;
            float: left;
          }
          i{

            font-size: 13px;
          }
        }
        
      }
    }
  }
</style>