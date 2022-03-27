<template>
  <section> 
    <el-row>
        <el-col :span="24" class="relativeLocation">
            <div class="grid-content bg-purple-dark">
                <img :src="this.$store.state.HeaderAdvUrl" class="backGroundImg">
                <img :src="this.$store.state.HeaderAdvUrl" class="headerAdv">
            </div>
        </el-col>
        <el-col :span="24">
            <div class="grid-content bg-purple-dark">
                <div class="personMessage">
                    <h3>{{this.$store.state.name}}
                        <span v-if='this.$store.state.sex === 1' style='color:blue;' class="sex iconfont">&#xe8b3;</span>
                        <span v-if='this.$store.state.sex === 0' style='color:pink;' class="sex iconfont">&#xe8b4;</span>
                    </h3>
                    <span>
                        <a href="#">粉丝：1</a>
                    </span>
                    <span>
                        <a href="#">关注：1</a>
                    </span>
                    <span>
                        <button class="follow" v-if="1"><span class="iconfont" title="关注好友">&#xe60c;</span></button>
                    </span>
                </div>
            </div>
        </el-col>
        <el-col :span="24" class="relativeLocation">
            <div class="grid-content bg-purple-dark">
                <el-collapse v-model="activeNames">
                    <el-collapse-item title="简介">
                        <p class="introduce"><span class="iconfont">&#xeb4e;</span>{{this.$store.state.introduce}}</p>
                        <p class="introduce"><span class="iconfont">&#xebb2;</span>{{this.$store.state.birthday}}</p>
                        <p class="introduce"><span class="iconfont">&#xe680;</span>{{this.$store.state.school}}</p>
                        <p class="GoToEdit"><router-link to="/EditMessage">编辑资料</router-link></p>
                    </el-collapse-item>
                </el-collapse>
            </div>
        </el-col>
    </el-row>

  </section>
</template>

<script>
export default {
    name:'HeaderMessage'
}
</script>

<style lang='less' scoped>
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
    .follow {
        float: right;
        width: 30px;
        height: 30px;
        border-radius: 15px;
        border:2px solid transparent;
        cursor: pointer;
        &:active{
            border-color:orange;
            color: orange;
    }
}
</style>