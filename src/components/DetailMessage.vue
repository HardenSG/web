<template>
    <div class="middle">
        <el-row>
        <el-col :span="5">
          <LeftNav title="信息">
            <ul class="row">
              <li>        
                <router-link to="/index">
                  <span class="iconfont">&#xe7c2;<i>返回主页</i></span>
                </router-link>
              </li>
            </ul>
          </LeftNav>
        </el-col>
        <el-col :span="19" :offset="5">
            <section>
                <header>
                    <h3>
                        {{message.user.name}} 
                    </h3>
                    <hr>
                </header>
                <article>
                    <div>
                        <router-link :to="{
                            path:'/PersonPageSection/PersonPageHome',
                            query:{
                                email: this.message.user.email
                            }
                        }">
                            <img :src="`http://${this.message.user.headPicture}`" class="headerAdv" />
                            <span class="Name">{{message.user.name}}</span>
                        </router-link>
                        <i class="time">{{message.dynamic.date |timeFormat}}</i>
                    </div>
                    <div>
                        <p>
                            {{message.dynamic.content}}
                        </p>
                        <p>
                            <router-link :to="{
                                path:'/HotNews/HotTopic',
                                query:{
                                    topic:message.topic
                                }    
                            }" v-if="message.topic !== null">
                            #{{message.topic}}#
                            </router-link>
                        </p>
                        <div>
                            <div class="demo-image__preview Pictures">
                            <el-image
                                v-for="(p,index) in message.pictures" :key="index"
                                :src="p" 
                                :preview-src-list="srcList"
                                class="PreviewImg"
                                title="大图预览">
                            </el-image>
                            </div>
                        </div>
                        <!-- <div>
                            <div class="demo-image__preview Pictures">
                            <el-image
                                v-for="(p,index) in message.pictures" :key="index"
                                :src="p" 
                                :preview-src-list="srcList"
                                class="PreviewImg"
                                title="大图预览">
                            </el-image>
                            </div>
                        </div> -->
                    </div>
                
                    <el-row class="tools">
                        <el-col :span="8">
                        <el-badge :value="message.dynamic.forwardCount" class="item" type="warning">
                            <span class="iconfont"> &#xe602; </span>
                        </el-badge>
                        </el-col>
                        <el-col :span="8">
                        <el-badge :value="message.dynamic.commentCount" class="item" type="warning">
                            <span class="iconfont"> &#xe6ad; </span>
                        </el-badge>
                        </el-col>
                        <el-col :span="8">
                        <el-badge :value="message.dynamic.likes" class="item" type="warning">
                            <span class="iconfont" @click="like(message.dynamic.did,message.dynamic.forwardCount)" > &#xe644; </span>
                        </el-badge>
                        </el-col>
                    </el-row>
                    <div class="comment">
                        <img :src="$store.state.HeaderAdvUrl" class="commentHeader">
                        <textarea maxlength="30" placeholder="发布你的评论!" v-model="comment"></textarea>
                        <el-button type="warning" @click="publish(message.dynamic.did,message.dynamic.email)" class="commentPublish">发布评论</el-button>
                        同时转发
                        <el-switch
                            v-model="value"
                            active-color="#13ce66"
                            inactive-color="#ff4949"
                            title="同时转发">
                        </el-switch>
                    </div>
                    <hr>
                    <div class="detailComment">
                        <div class="firstComment" v-for="(p,index) in comments.commentsAll " :key="index">
                            <img :src="`http://${p.content.headPhoto}`">
                           <router-link :to="{
                                path:'/PersonPageSection/PersonPageHome',
                                query:{
                                    email:p.content.email
                                }
                                }" class="name">
                            {{p.content.name}}:
                           </router-link>
                            <span class="content" @click="SecondComment(p.content.commentId)">
                                {{p.content.comment}}
                            </span>
                            <span class="time">
                                {{p.content.commentDate | timeFormat}}
                            </span>
                            <!-- 二级评论 -->
                            <div class="secondComment" v-for="(p1,index) in p.comments" :key="index">
                                <router-link :to="{
                                        path:'/PersonPageSection/PersonPageHome',
                                        query:{
                                            email:p1.content.email
                                        }
                                    }" class="secondName">
                                    {{p1.content.name}}:
                                </router-link>
                                    <span @click="ThirdComment(p.content.commentId,p1.content.commentId)">
                                        {{p1.content.comment}}
                                    </span>
                                    <span class="TwoTime">
                                        {{p1.content.commentDate |timeFormat}}
                                    </span>
                            <!-- 三级评论 -->
                                <div class="thirdComment" v-for="(p2,index) in p1.comments" :key="index">
                                    <router-link :to="{
                                        path:'/PersonPageSection/PersonPageHome',
                                        query:{
                                            email:p2.comment.email
                                        }
                                    }" class="thirdName">
                                        {{p2.comment.name}}
                                    </router-link><span v-if="p2.comment.replyId != 0">
                                        回复
                                    </span>
                                    <router-link :to="{
                                        path:'/PersonPageSection/PersonPageHome',
                                        query:{
                                            email:p2.user.email
                                        }
                                    }" class="thirdName" v-if="p2.comment.replyId != 0">
                                        {{p2.user.name}}:
                                    </router-link>
                                    <span @click="lastComment(p.content.commentId,p2.comment.commentId,p1.content.commentId)">
                                        {{p2.comment.comment}}
                                    </span>
                                    <span class="thirdTime">
                                        {{p2.comment.commentDate | timeFormat}}
                                    </span>
                                </div>
                            </div>
                        </div>
                    </div>
                </article>
                <footer>
                    <el-divider>暂无更多了！</el-divider>
                </footer>
            </section>
        </el-col>
      </el-row>
    </div>
</template>

<script>
import axios from 'axios'
import LeftNav from '@/components/LeftNav'
import dayjs from 'dayjs'
export default {
    name:'DetailMessage',
    data(){
        return{
            srcList: [],
            value: false,
            message:{},
            comment:"",
            comments:{}
        }
    },
    components:{
        LeftNav
    },
    mounted(){
        
    },
    beforeMount(){
        axios({
            url:'http://111.229.237.78:8080/dynamic/details',
            method:'GET',
            params:{
                dId:this.$router.currentRoute.query.did
            }
        })
        .then(response=>{
            this.message = response.data;
            // console.log(this.message);
            console.log(response.data);
            this.srcList = response.data.pictures 
        })
        axios({
            url:'http://111.229.237.78:8080/dynamic/comment',
            method:'GET',
            params:{
                dId:this.$router.currentRoute.query.did
            }
        }).then(response=>{
            this.comments = response.data
        })
    },
    methods:{
        commentFlush(){
            axios({
                url:'http://111.229.237.78:8080/dynamic/comment',
                method:'GET',
                params:{
                    dId:this.$router.currentRoute.query.did
                }
            }).then(response=>{
                console.log(response.data);
                this.comments = response.data
            })
        },
        // 点赞
        like(did,like) {
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
        publish(did,email){
            if (this.value === false) {
                axios({
                    url:'http://82.157.48.184:5482/dynamic/comment',
                    method:'POST',
                    params:{
                        dId:did,
                        commentCount:this.message.dynamic.commentCount,
                        comment:this.comment,
                        dynamicUserEmail:email,
                        commentIdP:0,
                        commentIdR:0,
                        parentTwoId:0
                    },
                    headers:{
                        token:this.$store.state.token
                    }
                }).then(response=>{
                    this.$notify({
                        title: "成功",
                        message: response.data.msg,
                        type: "success",
                        position: "bottom-left",
                    })

                    this.comment = '';

                    this.commentFlush();
                })
            }else{
                axios({
                    url:'http://82.157.48.184:5482/dynamic/comment',
                    method:'POST',
                    params:{
                        dId:did,
                        commentCount:this.message.dynamic.commentCount,
                        comment:this.comment,
                        dynamicUserEmail:email
                    },
                    headers:{
                        token:this.$store.state.token
                    }
                }).then(response=>{
                    this.$notify({
                        title: "成功",
                        message: response.data.msg,
                        type: "success",
                        position: "bottom-left",
                    })
                });
                axios({
                    url:'http://82.157.48.184:5482/dynamic/forward',
                    method:'GET',
                    params:{
                        dId:did,
                        content:this.comment,
                        forwardCount:this.message.dynamic.forwardCount
                    },
                    headers:{
                        token:this.$store.state.token
                    }
                }).then(response=>{
                    this.$notify({
                        title: "成功",
                        message: response.data.msg,
                        type: "success",
                        position: "bottom-left",
                    })
                })
                
            }
        },
        SecondComment(id){
            this.$prompt("请输入评论内容", "评论", {

            confirmButtonText: "确定",

            cancelButtonText: "取消",
      })
        .then(({ value }) => {
            axios({
              url:'http://82.157.48.184:5482/dynamic/comment',
              method:'POST',
              params:{
                dId:this.message.dynamic.did,
                commentCount:this.message.dynamic.commentCount,
                comment:value,
                dynamicUserEmail:this.message.user.email,
                commentIdP:id,
                commentIdR:0,
                parentTwoId:0
              },
              headers:{
                token:this.$store.state.token
              }
            }).then(response=>{
                this.$notify({
                    title: "成功",
                    message: response.data.msg,
                    type: "success",
                    position: "bottom-left",
                })
                this.commentFlush();
            })
            
        })
        },
        ThirdComment(idP,idR){
            this.$prompt("请输入评论内容", "评论", {

            confirmButtonText: "确定",

            cancelButtonText: "取消",
        })
            .then(({ value }) => {
                axios({
                url:'http://82.157.48.184:5482/dynamic/comment',
                method:'POST',
                params:{
                    dId:this.message.dynamic.did,
                    commentCount:this.message.dynamic.commentCount,
                    comment:value,
                    dynamicUserEmail:this.message.user.email,
                    commentIdP:idP,
                    commentIdR:0,
                    parentTwoId:idR
                },
                headers:{
                    token:this.$store.state.token
                }
                }).then(response=>{
                   this.$notify({
                        title: "成功",
                        message: response.data.msg,
                        type: "success",
                        position: "bottom-left",
                    })
                    this.commentFlush();
                })
            })
        },
        lastComment(idP,idR,parId){
            console.log(idP,idR,parId);
            this.$prompt("请输入评论内容", "评论", {

            confirmButtonText: "确定",

            cancelButtonText: "取消",
        })
            .then(({ value }) => {
                axios({
                url:'http://82.157.48.184:5482/dynamic/comment',
                method:'POST',
                params:{
                    dId:this.message.dynamic.did,
                    commentCount:this.message.dynamic.commentCount,
                    comment:value,
                    dynamicUserEmail:this.message.user.email,
                    commentIdP:idP,
                    parentTwoId:parId,
                    commentIdR:idR
                },
                headers:{
                    token:this.$store.state.token
                }
                }).then(response=>{
                    this.$notify({
                        title: "成功",
                        message: response.data.msg,
                        type: "success",
                        position: "bottom-left",
                    })
                    this.commentFlush();
                })
            })
        }
    },
    filters:{
        timeFormat(value){
            return dayjs(value).format('YYYY-MM-DD HH:mm:ss')
        }
    }

}
</script>

<style lang='less'>
    header{
        h3{
            text-align: center;
        }
        hr{
            margin-top: 5px !important;
        }
    }
    article{
        position: relative;
        .headerAdv{
            width: 60px;
            height: 60px;
            border-radius: 30px;
        }
        .Name{
            position: absolute;
            top: 15px;
            left: 70px;
        }
        .time{
            font-size: 12px;
        }
        .Pictures{
            margin-left: 100px;
            .PreviewImg{
                width: 150px;
                height: 150px;
                margin-left: 10px;
                margin-top: 5px;
                border-radius: 20px;
            }
        }
        .tools {
            margin-top: 20px;
            .el-col-8{
                text-align: center;
            }
            span {
                cursor: pointer;
                font-size: 20px;
                &:hover {
                    color: orange;
                }
            }
            .item {
                user-select: none;
            }
            .el-badge__content.is-fixed{
                // width: 20px !important;
                // height: 20px !important;
            }
        }
        .comment{
            .commentHeader{
                display: inline-block;
                width: 40px;
                height: 40px;
                border-radius: 20px;
            }
            textarea{
                display: inline-block;
                width: 550px !important;
                height: 40px !important;
                margin-left: 10px !important;
            }
            .commentPublish{
                width: 80px !important;
                height: 40px;
                padding: 0 !important;
                margin-left: 20px;
                vertical-align: top;
            }
        }
        .detailComment{
            width: 100%;
            height: auto;
            .firstComment{
                
                width: 100%;
                height: auto;
                margin-top: 30px;
                // &:hover{
                //     background-color: rgb(237, 226, 226);
                // }
                img{
                    width: 40px;
                    height: 40px;
                    border-radius: 20px;
                }
                .name{
                    position: relative;
                    font-size: 18px;
                    font-weight: 700;
                    vertical-align: top;
                }
                .content{
                    position: absolute;
                    // left: 110px;
                    margin-left: 20px;
                    margin-top: 3px;
                    font-size: 14px;
                    word-spacing: normal;
                }
                .secondComment{
                    width: 85%;
                    height: auto;
                    margin-left: 100px;
                    border-left: 3px solid rgb(241, 237, 237);
                    .secondName{
                        font-weight: 700;
                        font-size: 16px;
                    }
                    span{
                        display: inline-block;
                        padding: 5px 0px 5px 5px;
                    }
                    .TwoTime{
                        display: block;
                        font-size: 12px;
                    }
                    .thirdComment{
                        width: 80%;
                        height: auto;
                        margin-left:30px ;
                        margin-bottom: 15px;
                        .thirdName{
                            font-size: 14px;
                        }
                        .thirdTime{
                            display: block;
                            font-size: 12px;
                        }
                    }
                }
            }
            // detail结束
        }
    }
</style>