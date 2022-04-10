<template>
<div>
    <section class="infinite-list-item" v-for="(p,index) in datap" :key="index" @click="goTopic(p.content)">
        <h2>
            <i class="iconfont first" v-if="index == 0">&#xe66e;</i>
            <i class="iconfont second" v-if="index == 1">&#xe66b;</i>
            <i class="iconfont third" v-if="index == 2">&#xe66c;</i>
            <i class="count" v-if="index > 2">{{index + 1}}</i>
            <span>
                {{p.content}}
            </span>
            <p>
                热度：{{p.hot}}
            </p>
        </h2>
        <article>   
            <img :src="/^82+/.test(p.picture) ? `http://${p.picture}` : p.picture">
        </article>
    </section>
    <NoFound v-if="datap.length === 0"></NoFound>
</div>
</template>

<script>
import axios from 'axios'
import NoFound from '@/components/NoFound'
export default {
    name:'HotWord',
    components:{
        NoFound
    },
    data(){
        return{
            datap:[],
            flag: 1,
            followFlag: 0,
            pageNumber:1
        }
    },
    beforeMount(){
        this.$store.commit('changeLoading',true)
        axios({
            url:"http://111.229.237.78:8080/topicAll",
            method:'GET',
            params:{
                pageNumber:1
            }
            }).then(response=>{
                console.log(response.data);
                this.datap = response.data.topics;
                this.$store.commit('changeLoading',false)
            }).catch(err=>{
                console.log(err);
        })
    },
    methods:{
        goTopic(content){
            this.$router.push({
                path:'/HotNews/HotTopic',
                query:{
                    topic:content
                }
            })
        },
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
        
    },
    mounted(){
        window.addEventListener(
        "scroll",
        () => {
            // 判断当前是否滚动到300以下
            if (this.touchScroll() < 100) {
            // 判断当前是否是处于请求状态，0为正在请求
            if (this.flag == 1) {
                // 说明进行关注的请求
                this.flag = 0;
                axios({
                    url:"http://111.229.237.78:8080/topicAll",
                    method:'GET',
                    params:{
                        pageNumber:++this.pageNumber
                    }
                    }).then(response=>{
                        if (response.data.topics.length != 0) {
                            for (const key in response.data.topics) {
                                this.datap.push(response.data.topics[key])
                            }
                        }
                        this.flag = 1;
                    }).catch(err=>{
                        console.log(err);
                    })
            } else {
                console.log("等一等");
            }
            }else{
                return;
            }
        },
        true
        );
    }
}
</script>

<style lang='less' scoped>
    section{
        &:hover{
            background-color: rgb(234, 230, 230);
        }
    }
    h2{
        display: inline-block;
        width: 80%;
        span{
            vertical-align: middle;
            font-weight: 500;
            font-size: 20px;
        }
        .first{
            color: rgb(242, 79, 79) !important;
        }
        .second{
            color: rgb(253, 150, 23) !important;
        }
        .third{
            color: rgb(251, 198, 55) !important;
        }
        .count{
            display: inline-block;
            width: 25px;
            height: 20px;
            background-color: #999;
            font-size: 14px;
            color: white;
            text-align: center;
            line-height: 20px;
            border-radius: 8px 5px 8px 5px;
        }
        p{
            font-size: 14px;
            margin-top: 10px;
            margin-left: 10px;
        }
    }
    article{
        display: inline-block;
        width: 20%;
        img{
            vertical-align: middle;
            width: 80px;
            height: 80px;
        }
    }
</style>