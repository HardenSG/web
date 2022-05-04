<template>
    <HotWord :datap='datap'></HotWord>
</template>

<script>
import HotWord from '@/components/HotWord'
import axios from 'axios'

export default {
    name:'HotNewsMain',
    data(){
        return{
            datap:[]
        }
    },
    components:{
        HotWord
    },
    beforeMount(){
        axios({
            url:"http://111.229.237.78:8080/topic/",
            method:'POST',
            params:{
                topic:this.$router.currentRoute.query.topic,
                pageNumber:0
            }
            }).then(response=>{
                this.datap = response.data
                console.log(response.data);
            }).catch(err=>{
            console.log(err);
        })
    } 
}
</script>

<style>

</style>