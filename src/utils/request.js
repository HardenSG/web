import axios from 'axios'

const request = axios.create({
    timeout:5000
})
request.send = async (Url,Method)=>{
    
    return await axios({
        url:Url,
        method:Method,
    }).then(response=>response.data)
}
export default {
    install(Vue){
        Vue.prototype.$request = request
    }
}