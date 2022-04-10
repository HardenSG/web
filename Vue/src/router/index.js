// 引入路由模块
import VueRouter from "vue-router";

// 引入组件
// 主页面middlesction组件路由
import MiddleSection from '@/pages/MiddleSection'

// 编辑页面组件路由
import EditMessage from '@/pages/EditMessage'

// 个人界面的组件路由
import PersonPageSection from '@/pages/PersonPageSection'
import FollowPerson from '@/components/FollowPerson'
import PersonPageHome from '@/components/PersonPageHome'

// 登录组件路由
import PersonLog from '@/pages/PersonLog'

// 热搜组件路由
import HotNews from '@/pages/HotNews'
import HotNewsMain from '@/components/HotNewsMain'
import HotTopic from '@/components/HotTopic'

// 信息组件路由
import MessageAlarm from '@/pages/MessageAlarm'
import CommentMain from '@/components/CommentMain'
import ChatMain from '@/components/ChatMain'
import NoFound from '@/components/NoFound'

// 动态详细信息的组件
import DetailMessage from '@/components/DetailMessage'


// 创建路由实例
const router = new VueRouter({
    routes:[{
        path:'/index',
        component:MiddleSection,
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
    {
        path:'/PersonLog',
        component:PersonLog
    },
    {
        path:'/HotNews',
        component:HotNews,
        children:[
            {
                path:'HotTopic',
                component:HotTopic
            },
            {
                path:'HotNewsMain',
                component:HotNewsMain
            }
        ]
    },
    {
        path:'/MessageAlarm',
        component:MessageAlarm,
        children:[
        {
            path:'CommentMain',
            component:CommentMain
        },
        {
            path:'ChatMain',
            component:ChatMain
        },
        {
            path:'NoFound',
            component:NoFound
        }
        ]
    },
    {
        path:'/DetailMessage',
        component:DetailMessage
    }
    ]
})

router.beforeEach((to,from,next)=>{
    
    next();    
    
    if (to.path == '/') {

        window.router.push({path:'/index'})
        
    }
    
    window.Vue.$store.commit('changeHttpFlag',false);

})
window.router = router

export default router




















