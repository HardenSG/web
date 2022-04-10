<template>
  <section> 
      <!-- <button class="goBack" @click="goBack" v-if="this.message[0].uid == this.$store.state.uid"><-返回</button> -->
    <el-row>
        <!-- <button class="goBack" @click="goBack" v-if="!this.message[0].uid == this.$store.state.uid"><-返回</button> -->
        <el-col :span="24" class="relativeLocation">
            <div class="grid-content bg-purple-dark">
                <img :src="`http://${message[0].headPicture}`" class="backGroundImg">
                <img :src="`http://${message[0].headPicture}`" class="headerAdv">
            </div>
        </el-col>
        <el-col :span="24">
            <div class="grid-content bg-purple-dark">
                <div class="personMessage">
                    <h3>{{message[0].name}}
                        <span v-if='message[0].sex === "1"' style='color:blue;' class="sex iconfont">&#xe8b3;</span>
                        <span v-if='message[0].sex === "0"' style='color:pink;' class="sex iconfont">&#xe8b4;</span>
                    </h3>
                    <span>
                        <router-link :to="{
                            path:'/PersonPageSection/follow',
                            query:{
                                email:message[0].email,
                                flag:1
                            }
                        }">
                        粉丝：{{message.followed}}
                        </router-link>
                    </span>
                    <span>
                        <router-link :to="{
                            path:'/PersonPageSection/follow',
                            query:{
                                email:message[0].email,
                                flag:0
                            }
                        }">
                        关注：{{message.follow}}
                        </router-link>
                    </span>
                    <span>
                        <el-dropdown class="detail" v-if="message[0].email !== $store.state.email">
                            <span class="el-dropdown-link">
                            <i class="el-icon-arrow-down el-icon--right"></i>
                            </span>
                            <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item>
                                <span class="iconfont" title="关注好友" v-if="1" @click="follow(message[0].email)">&#xe60c;</span>
                                <span class="iconfont" title="取消关注" v-if="0" >&#xe65a;</span>
                            </el-dropdown-item>
                            <el-dropdown-item>
                                <span class="iconfont" title="拉黑" @click="black(message[0].email)">&#xe627;</span>
                            </el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>
                    </span>
                </div>
            </div>
        </el-col>
        <el-col :span="24" class="relativeLocation">
            <div class="grid-content bg-purple-dark">
                <el-collapse >
                    <el-collapse-item title="简介">
                        <p class="introduce"><span class="iconfont">&#xeb4e;</span>{{message[0].introduction}}</p>
                        <p class="introduce"><span class="iconfont">&#xebb2;</span>{{message[0].birthday | birthday}}</p>
                        <p class="introduce"><span class="iconfont">&#xe680;</span>{{message[0].schoolName}}</p>
                        <p class="GoToEdit" v-if="message[0].uid == $store.state.uid"><router-link to="/EditMessage">编辑资料</router-link></p>
                    </el-collapse-item>
                </el-collapse>
            </div>
        </el-col>
    </el-row>

  </section>
</template>

<script>
import dayjs from 'dayjs'
import axios from 'axios';
export default {
    name:'HeaderMessage',
    props:['message'],
    methods:{
        // goBack(){
        //     this.$router.go(-1);
        // },
        follow(Email){
            axios({
                url:'http://111.229.237.78:8080/follow',
                method:'GET',
                params:{
                    email:Email
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
        },
        black(Email){
            axios({
                url:'http://111.229.237.78:8080/blackList',
                method:'POST',
                params:{
                    email:Email
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
            }).catch(err=>{
                this.$notify({
                  title: "失败",
                  message: err,
                  type: "warning",
                  position: "bottom-left",
                })
            })
        }
    },
    filters:{
        birthday(value){
            return dayjs(value).format('YYYY-MM-DD');
        }
    },
    mounted(){
        console.log(this.message);
    }
    
}
</script>

<style lang='less' scoped>
    .goBack{
        position: absolute;
        top: -5px;
        width: 50px;
        border-radius: 10px;
        border: 0px;
        font-size: 14px;
        cursor: pointer;
    }
    .relativeLocation {
        position: relative;
    }
    .backGroundImg {
        width: 100%;
        height: 200px;
        border-radius: 15px;
    }
    .headerAdv{
        position: absolute;
        bottom: -60px;
        left: 30px;
        width: 100px;
        height: 100px;
        border-radius: 50px;
        border: 2px solid white;
    }
    .personMessage {
        width: 80%;
        margin-left: 20%;
        height: 80px;
        span{
            margin-right: 20px;
        }
        .sex{
            margin-left: 20px;
        }
    }
    .introduce {
        color: #999 !important;
        font-size: 13px;
        span{
            vertical-align: middle;
            padding-right: 20px;
        }
    }
    .GoToEdit {
        text-align: center;
    }
    .detail{
        float: right;
        cursor: pointer;
    }

</style>