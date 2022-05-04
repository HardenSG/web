<template>
    <div>
        <section v-for="(p,index) in people" :key="index">
            <div class="HeaderAdv">
                <img :src="/^82+/.test(p.headPicture) ? `http://${p.headPicture}` : p.headPicture">
            </div>
            <div class="messageBox">
                <h3 class="name">
                    <router-link 
                        :to="{
                            path:'/PersonPageSection/PersonPageHome',
                            query:{
                                email:p.email
                            }   
                        }"
                        >
                        {{p.name}}
                    </router-link>
                </h3>
                <span class="introduce">
                    {{p.introduction}}
                </span>
            </div>
            
        </section>
        <NoFound v-if="people.length == 0"></NoFound>
    </div>

</template>

<script>
import NoFound from '@/components/NoFound'
import axios from 'axios'
export default {
    name:'FollowPerson',
    components:{
      NoFound  
    },
    data(){
        return{
            people:[],
            flag:this.$router.currentRoute.query.flag
        }
    },
    methods:{
        justFlag(){
            if (this.flag == 0) {
                axios({
                    url:'http://111.229.237.78:8080/user/showMyFollow',
                    method:'GET',
                    params:{
                        email:this.$router.currentRoute.query.email
                    }
                }).then(response=>{
                    this.people = response.data;
                }).catch(err=>{
                    console.log(err);
                })
            }else{
                axios({
                    url:'http://111.229.237.78:8080/user/showMyFans',
                    method:'GET',
                    params:{
                        email:this.$router.currentRoute.query.email
                    }
                }).then(response=>{
                    this.people = response.data;
                }).catch(err=>{
                    console.log(err);
                })
            }
        },
        toggleFollow(data){
            this.flag = data;
            this.justFlag()
        },
    },
    beforeMount(){
        this.justFlag();
    },
    mounted(){
        this.x.$on("tog", this.toggleFollow);
    },
}
</script>

<style scoped lang='less'>
    section{
        &:hover{
            background-color: rgb(219, 213, 213);
        }
    }
    .HeaderAdv{
        display: inline-block;    
        img{
            width: 60px;
            height: 60px;
            border-radius: 10px;
        }
    }
    .messageBox{
        display: inline-block;
        width: 90%;
        height: 60px;
        padding-left: 10px;
        vertical-align: top;
        .name{
            display: block;
            font-size: 16px;
        }
        .introduce{
            display: block;
            margin-top: 10px;
            font-size: 12px;
            color: #999;
        }
    }
</style>