// 引入路由模块
import VueRouter from "vue-router";

// 引入组件
import MiddleSection from '@/pages/MiddleSection'
import EditMessage from '@/components/EditMessage'
import PersonPageSection from '@/pages/PersonPageSection'
import FollowPerson from '@/components/FollowPerson'
import PersonPageHome from '@/components/PersonPageHome'
// 创建一个路由实例
const router = new VueRouter({
    routes:[{
        path:'/index',
        component:MiddleSection
    },
    {
        path:'/PersonPageSection',
        component:PersonPageSection,
        children:[
            {
                path:'follow',
                component:FollowPerson
            },
            {
                path:'PersonPageHome',
                component:PersonPageHome 
            }
        ]
    },
    {
        path:'/EditMessage',
        component:EditMessage
    },
    // {
    //     path:'/PersonLog',
    //     component:PersonLog
    // },
    ]
})

router.beforeEach((to,from,next)=>{

    if (to.path === '/PersonPageSection') {
         
        if (window.Vue.$store.state.isLog === 'no') {
            
            window.Vue.$confirm('您还未登录，您要现在登录吗？', {
                distinguishCancelAndClose: true,
                confirmButtonText: '现在登录',
                cancelButtonText: '放弃登录'
            })
            .then(() => {
                alert('现在有点问题哈！')
              })
              .catch(() => {
                window.Vue.$notify.info({
                  title: "取消",
                  message: "取消登录!",
                  position: "bottom-left",
                });
              });
              
        }else{
            window.Vue.$store.commit('changeTitle','主页');
            next();
        }
    }else{
        
        next();
    }
    // 做一下主页面的默认路由
    if (to.path == '/') {
        window.router.push({path:'/index'})
    }





})
window.router = router
export default router




















