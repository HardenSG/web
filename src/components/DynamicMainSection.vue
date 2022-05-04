<template>
  <ul class="infinite-list list">
    <section class="infinite-list-item" v-for="(p1,index) in datap" :key="index">
      <!-- 头像和昵称 -->
      <router-link :to="{
        path:'/PersonPageSection/PersonPageHome',
        query:{
          email:p1.user.email
        }
      }">
        <img :src="'http://' + p1.user.headPicture" class="headerAdv" />
        <span class="Name">{{p1.user.name}}</span>
      </router-link>
      <!-- 时间 -->
      <i class="time">{{p1.dynamic.date | timeFomat}}</i>
      <el-dropdown class="detail">
        <span class="el-dropdown-link">
          <i class="el-icon-arrow-down el-icon--right"></i>
        </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item>
            <!-- 查看详细 -->
            <router-link :to="{
              path:'/DetailMessage',
              query:{
                did:p1.dynamic.did
              }
            }">  
              查看详细
            </router-link>
          </el-dropdown-item>
          <el-dropdown-item>
            <!-- 删除动态 -->
            <span v-if="p1.user.email == $store.state.email" @click="deleteDynamic(p1.dynamic.did,p1.user.email)">删除动态</span>
          </el-dropdown-item>

        </el-dropdown-menu>
      </el-dropdown>
      <el-row>
        <!-- 动态的原创或转发评论动态的文字 -->
        <el-col :span="24" v-if="p1.dynamicType == 0">
          <div class="grid-content bg-purple-dark">
            <!-- 文字性评论 -->
            {{p1.dynamic.content}}
            <!-- 话题 -->
            <router-link :to="{
              path:'/HotNews/HotTopic',
              query:{
                topic:p1.topic
              }
            }" v-if="p1.topic !== null">
              #{{p1.topic}}#
            </router-link> 
          </div>
        </el-col>
        <el-col :span="24" v-if="p1.dynamicType == 1">
          <div class="grid-content bg-purple-dark">
            <!-- 文字性评论 -->
            {{p1.forwardComment}}
            <!-- 话题 -->
            <router-link :to="{
              path:'/HotNews/HotTopic',
              query:{
                topic:p1.topic
              }
            }" v-if="p1.topic !== null">
              #{{p1.topic}}#
            </router-link> 
          </div>
        </el-col>
        <!-- 此处是原创的图片 -->
        <el-col :span="24" v-if="p1.dynamicType == 0">
          <div class="grid-content bg-purple-dark" v-for="(p,index) in p1.pictures" :key="index">
            <img :src="p"/>
          </div>
        </el-col>
        <!-- 此处转发的分割线 -->
        <hr v-if="p1.dynamicType == 1" />
        <!-- 被转发 -->
        <el-col :span="24" v-if="p1.dynamicType == 1">
          <div class="grid-content bg-purple-dark">
            <!-- 被转发人的昵称 -->
            <router-link :to="{
                path:'/PersonPageSection/PersonPageHome',
                query:{
                  email:p1.originalUser.email
                }
              }"> {{p1.originalUser.name}}
              </router-link>
          </div>
        </el-col>
        <el-col :span="24" v-if="p1.dynamicType == 1">
          <div class="grid-content bg-purple-dark">
            <!-- 文字性评论 -->
            {{p1.dynamic.content}}
          </div>
        </el-col>
        <el-col :span="24" v-if="p1.dynamicType == 1">
          <div class="grid-content bg-purple-dark" v-for="p in p1.pictures" :key="p">
            <img :src="p" />
          </div>
        </el-col>
      </el-row>
      <hr />
      <el-row class="tools">
        <el-col :span="8">
          <el-badge :value="p1.dynamic.forwardCount" class="item" type="warning">
            <span class="iconfont" @click="aplay(p1.dynamic.did,p1.dynamic.forwardCount)"> &#xe602; </span>
          </el-badge>
        </el-col>
        <el-col :span="8">
          <el-badge :value="p1.dynamic.commentCount" class="item" type="warning">
            <el-collapse accordion class="commentDropList">
              <el-collapse-item>
                <template slot="title">
                  <span class="iconfont"> &#xe6ad;</span>
                </template>
                <a href="javascript:;" @click="open(p1.dynamic.did,p1.dynamic.commentCount,p1.user.email)" class="selfComment">
                  我也来评论！
                </a>
                <div class="commentDropListCotent">
                  <router-link :to="{
                    path:'/DetailMessage',
                    query:{
                      did:p1.dynamic.did
                    }
                  }">
                    查看详细
                  </router-link>
                </div>
              </el-collapse-item>
            </el-collapse>
          </el-badge>
        </el-col>
        <el-col :span="8">
          <el-badge :value="p1.dynamic.likes" class="item" type="warning">
            <span class="iconfont" @click="like(p1.dynamic.did,p1.dynamic.likes)"  > &#xe644; </span>
          </el-badge>
        </el-col>
      </el-row>
    </section>
    <section>
      <el-skeleton :rows="6" animated v-show="1" />
    </section>
    <section v-if="flag">
      没有更多内容了
    </section>
  </ul>
</template>

<script>
import axios from 'axios';
import dayjs from 'dayjs'

export default {
  name: "DynamicMainSection",
  props: ['datap','flag'],
  data() {
    return {
      count: 0,
      send:this.flag,
      likeColor:'',
      
    };
  },
  methods: {
    // 滚动条位置
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
    // dialog框展示图片
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    // 评论内容
    open(did,count,email) {

      if (this.$store.state.isLog == 'no') {
        alert('你还没登陆呢！');
        return;
      }

      this.$prompt("请输入评论内容", "评论", {

        confirmButtonText: "确定",

        cancelButtonText: "取消",
      })
        .then(({ value }) => {
            axios({
              url:'http://82.157.48.184:5482/dynamic/comment',
              method:'POST',
              params:{
                dId:did,
                commentCount:count,
                comment:value,
                dynamicUserEmail:email,
                commentIdP:0,
                commentIdR:0,
                parentTwoId:0
              },
              headers:{
                token:this.$store.state.token
              }
            })
          .then(response=>{
                
                for (let i = 0; i < this.datap.length; i++) {

                  if (this.datap[i].dynamic.did === did) {
                    
                    this.datap[i].dynamic.commentCount = response.data.commentCount;
                    
                    break;
                  }
                }

                
                response.data.comment = value;
                console.log(',,,,,,',response.data);
                // 事件总线，绑定点在middleSection传递did和评论！但是有bug此处记住
                // this.x.$emit('comment',response.data,did)

                this.$notify({
                  title: "成功",
                  message: response.data.msg,
                  type: "success",
                  position: "bottom-left",
                })
            })
          })
        .catch(() => {
          this.$notify.info({
            title: "取消",
            message: "取消评论!",
            position: "bottom-left",
          });
        });
    },
    // 点赞
    like(did,like) {
      // new Promise((resolve, reject) => {
        // 此处书写ajax代码
        if (this.$store.state.isLog == 'no') {
          alert('你还没登陆呢！');
          return;
        }
        axios({
          url:'http://82.157.48.184:5482/dynamic/like',
          method:'POST',
          params:{
            id:did,
            likes:like
          },
          headers:{
            token:this.$store.state.token
          }
        })
      // })
        .then(response => {
          
          console.log(response.data);

          if (response.data.status == 1) {

            this.$notify({

              title: "点赞失败",

              message: response.data.message,

              type: "success",

              position: "bottom-left",

            });
          }else if(response.data.status == 200){

            for (let i = 0; i < this.datap.length; i++) {

              if (this.datap[i].dynamic.did === did) {
                
                this.datap[i].dynamic.likes = response.data.like;

                console.log('点赞成功！');
                
                break;
              }
            }
            this.$notify({
              title: "点赞成功！",

              message: response.data.message,

              type: "success",

              position: "bottom-left",

            });
            
          }else{
            this.$notify({
            title: "点赞失败",
            message: response.data.message,
            type: "warning",
            position: "bottom-left",
          });
          }
        })
    },
    // 转发
    aplay(did,count) {

      if (this.$store.state.isLog == 'no') {
        alert('你还没登陆呢！');
        return;
      }

      this.$prompt("请输入要评论内容", "转发", {

        confirmButtonText: "确定",

        cancelButtonText: "取消",
      })  
        .then(({ value }) => {

            axios({
              url:'http://111.229.237.78:8080/dynamic/forward',
              method:'GET',
              params:{
                dId:did,
                content:value,
                forwardCount:count
              },
              headers:{
                token:this.$store.state.token
              }
             }).then(response=>{

               for (let i = 0; i < this.datap.length; i++) {

                  if (this.datap[i].dynamic.did === did) {
                    
                    this.datap[i].dynamic.forwardCount = response.data.forwardCount;
                    
                    break;
                  }
                }
                console.log(response.data);
                this.$notify({
                  title: "成功",
                  message: response.data.msg,
                  type: "success",
                  position: "bottom-left",
                })
            }).catch(()=>{
              this.$notify({
                title: "失败！",
                message: '转发失败',
                type: "warning",
                position: "bottom-left"
              })
            })
          })
        .catch(() => {
          this.$notify.info({
            title: "取消",
            message: "取消转发!",
            position: "bottom-left",
          });
        });
    },
    // 删除
    deleteDynamic(Did,Email){
      axios({
        url:'http://82.157.48.184:5482/dynamic/delete',
        method:'DELETE',
        params:{
          did:Did,
          email:Email
        },
        headers:{
          token:this.$store.state.token
        }
      }).then(response=>{
        if(response.data.status == 200) {
          this.$notify({
              title: "删除成功！",

              message: response.data.msg,

              type: "success",

              position: "bottom-left",

          });
        }else{
          this.$notify({
              title: "删除失败！",

              message: response.data.msg,

              type: "warning",

              position: "bottom-left",

            });
        }
      }).catch(err=>{
        console.log(err);
        this.$notify({
          title: "删除失败！",

          message: '删除失败' + err,

          type: "warning",

          position: "bottom-left",

        });
      })
    }
  },
  filters:{
    timeFomat(value){
      return dayjs(value).format('YYYY-MM-DD HH:mm:ss')
    }
  }
  
};
</script>

<style lang='less' scoped>
.list {
  overflow: auto;
}
.headerAdv {
  width: 30px !important;
  height: 30px !important;
  border-radius: 15px !important;
}
.Name {
  font-size: 20px !important;
  vertical-align: middle;
}
.detail{
  float: right;
  // width: 70px;
  // height: 30px;
  // line-height: 30px;
  // border: 0px;
  // border-radius: 5px;
  cursor: pointer;
}
.time {
  margin-left: 15px;
  font-size: 12px;
  vertical-align: bottom;
}
.follow {
  float: right;
  width: 50px;
  height: 30px;
  border-radius: 15px;
  border:2px solid transparent;
  cursor: pointer;
  &:active{
    border-color:orange;
    color: orange;
  }
}
.grid-content {
  word-wrap: break-word;
  word-break: normal;
  margin-top: 10px;
  img {
    float: left;
    margin-right: 10px;
    width: 100px;
    height: 100px;
    border-radius: 5px;
  }
}
hr {
  margin-top: 30px;
}

.tools {
  text-align: center;
  span {
    cursor: pointer;
    &:hover {
      color: orange;
    }
  }
  .item {
    user-select: none;
  }
  .commentDropList {
    border: 0 !important;
  }
  .commentDropListCotent {
    a {
      width: 100%;
      color: orange;
    }
  }
}
.selfComment {
  display: block;
  text-align: center;
  font-size: 16px;
  color: rgb(39, 38, 36) !important;
  font-weight: 700;
}
</style>