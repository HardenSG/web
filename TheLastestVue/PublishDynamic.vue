<template>
  <div>
    <section class="publish">
      <form enctype="multipart/form-data" class="pubForm">
        <textarea
          name="publish"
          id=""
          cols="20"
          rows="8"
          placeholder="有什么新鲜事想分享给大家？"
          v-model="dynamicContent"
          ref="textarea"
        ></textarea>
        <div id="preview">
          <img :src="p" v-for="(p, index) in photo" :key="index" alt="" />
        </div>
        <ul class="row">
          <el-row>
            <el-col :span="2" :offset="2" class="icon">
              <span class="iconfont" title="相册">
                &#xe8ba;
                <div class="upload">
                  <input type="file" id="browse" multiple @change="upload($event)" />
                </div>
              </span>
            </el-col>
            <el-col :span="2"
              ><div class="grid-content bg-purple-light">
                <span class="iconfont" title="视频">&#xe600;</span>
              </div></el-col
            >
            <el-col :span="2"
              ><div class="grid-content bg-purple-light">
                <span class="iconfont" @click="addTopic" title="话题"
                  >&#xe8b0;</span
                >
              </div></el-col
            >
            <el-col :span="2"
              ><div class="grid-content bg-purple-light">
                <span class="iconfont" title="资讯">&#xe7a5;</span>
              </div></el-col
            >
            <el-col :span="2" :offset="12"
              ><div class="grid-content bg-purple-light">
                <el-button type="warning" @click="publish">发布微博</el-button>
              </div></el-col
            >
          </el-row>
        </ul>
      </form>
    </section>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "PublishDynamic",
  data() {
    return {
      dynamicContent: "",
      photo: [],        //base64格式用于展示
      picture:''        //url格式用于传递
    };
  },
  // props:['receiveData'],
  methods: {
    upload(e) {
      for (let item of e.target.files) {
        
        if (!/image\/\w+/.test(item.type)) {
          
          alert("只能选择图片");

          return;

        }

        var _this = this;
        //  创建一个FileReader()对象，它里面有个readAsDataURL方法
        let reader = new FileReader();

        reader.readAsDataURL(item);
        //文件读取成功完成时触发
        this.photo = []

        reader.addEventListener("load", function () {
          
          let a = this.result;

          _this.photo.push(a);

        });
      }

      // 临时照片数组
      this.picture = [];

      var length = e.target.files.length;

      var config = {
        headers: {
          "Content-Type": "multipart/form-data",
          'token': this.$store.state.token,
        }
      };

      for (let i = 0; i < e.target.files.length; i++) {
        // 发送请求的formdata对象
        var photo = new FormData();

        photo.append('photo',e.target.files[0]);
       
        axios.post("http://82.157.48.184:5482/user/url", photo, config)

        .then((response) => {
          if (length - i  == 1 ) {
            this.picture += 'http://' + response.data ;
          }else{
            this.picture += 'http://' + response.data + ',';
          }
        })
        .catch((err) => {
          alert('图片上传失败',err)
        });
        // 配置项
      }
    },
    axiosRequest(config){
      axios({ 
            url:'http://111.229.237.78:8080/dynamic',
            method:'POST',
            params:config,
            headers:{
              'token': this.$store.state.token,
            }
            }).then(response=>{
              console.log(response.data);
              this.$emit('publish',response.data)
              alert('发布成功!');
              
            }).catch(()=>{
              alert('发布失败！')
          })
    },
    publish(e) {
      e.preventDefault();
      
      if (this.$store.state.isLog == 'no') {
          alert('你还没登陆呢！');
          return;
      }

      if (this.dynamicContent !== "" && this.photo.length > 0) {
        // 正则表达式取出topic
        const reg = /#.+#/;

        let Topic = this.dynamicContent.match(reg);

        Topic = Topic == null ? null : Topic[0]

        if (Topic !== null ) {
          
          while (Topic.indexOf('#')!== -1) {

            Topic = Topic.replace('#','')

          }

          this.dynamicContent = this.dynamicContent.replace(this.dynamicContent.substr(this.dynamicContent.indexOf('#'),Topic.length + 2),'')

          this.axiosRequest({
              message:this.dynamicContent,
              picture:this.picture,
              topic:Topic
          })
        }
        else if(Topic == null){
          this.axiosRequest({
            message:this.dynamicContent,
            picture:this.picture,
          })
        }
          return;
        }
      if( this.dynamicContent !== "" && this.photo.length == 0) {
        
        const reg = /#.+#/;

        let Topic = this.dynamicContent.match(reg);

        Topic = Topic == null ? null : Topic[0]

        if (Topic !== null ) {
          
          while (Topic.indexOf('#')!== -1) {

            Topic = Topic.replace('#','')

          }

          this.dynamicContent = this.dynamicContent.replace(this.dynamicContent.substr(this.dynamicContent.indexOf('#'),Topic.length + 2),'')
        
          this.axiosRequest({
            message:this.dynamicContent,
            topic:Topic
          })
          return ;
        }
        else if(Topic == null ){
          this.axiosRequest({
            message:this.dynamicContent
          })
        }
      }
      if( this.dynamicContent == "" && this.photo.length > 0) {
        // this.axiosRequest({
        //   picture:this.picture,
        // })
         this.$notify({
          title: "失败",
          message: '不如发点文字再发图片呗！',
          type: "warning",
          position: "bottom-left",
        })
        return;
      }
        
        // 清空输入
        this.dynamicContent = '',
        this.photo = [];
        this.formdata = new FormData();
      
    },
    addTopic() {
      this.dynamicContent = "#" + this.dynamicContent + "#";
      // 获取元素触发焦点
      this.$refs.textarea.focus();
    },
  },
};
</script>

<style lang='less' scoped>
#preview {
  padding-left: 30px;
  padding-top: 10px;
  width: 100%;
}

#preview img {
  // float: left !important;
  width: 100px;
  height: 100px;
  border-radius: 10px;
  margin-left: 10px;
}

.icon {
  position: relative;
}

#browse {
  position: absolute;
  left: -12px;
  top: -10px;
  width: 50px;
  height: 50px;
  z-index: 999;
  opacity: 0;
  cursor: pointer;
}
</style>