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
                  <input type="file" id="browse" multiple @change="upload" />
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
                <span class="iconfont" title="话题">&#xe8b0;</span>
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
      photo: [],
      binarayCode: [],
    };
  },
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
        // readAsDataURL方法可以将上传的图片格式转为base64,然后在存入到图片路径,
        //这样就可以上传电脑任意位置的图片
        reader.readAsDataURL(item);
        //文件读取成功完成时触发
        reader.addEventListener("load", function () {
          //只有在读取操作完成后，此属性才有效，返回的数据的格式取决于是使用哪种读取方法来执行读取操作的。
          //给数组添加这个文件也就是图片的内容

          let a = this.result;

          // 转码为二进制
          function dataURLtoBlob(dataurl) {
            var arr = dataurl.split(","),
              mime = arr[0].match(/:(.*?);/)[1],
              bstr = atob(arr[1]),
              n = bstr.length,
              u8arr = new Uint8Array(n);
            while (n--) {
              u8arr[n] = bstr.charCodeAt(n);
            }
            return new Blob([u8arr], {
              type: mime,
            });
          }

          // console.log(dataURLtoBlob(a).arrayBuffer());
          dataURLtoBlob(a)
            .arrayBuffer()
            .then((resolve) => {
              _this.binarayCode.push(resolve);
            });

          _this.photo.push(a);
        });
      }
    },
    publish(e) {
      e.preventDefault();

      if (this.dynamicContent !== "" || this.photo.length > 0) {
        const formdata = new FormData();

        formdata.append("content", this.dynamicContent);
        // 将二进制拿出来
        var a = new Int8Array(this.binarayCode[0]);

        formdata.append("adv", a);

        // console.log(formdata.get('content'));
        // console.log(formdata.get('adv'),'length');

        let cancel = null;

        if (cancel !== null) {
          cancel();
          return;
        }

        axios({
          method: "POST",
          url: "http://127.0.0.1:8000/photo",
          cancelToken: new axios.CancelToken(function (c) {
            cancel = c;
          }),
          data: {
            formdata,
          },
          headers: {
            "Content-Type": "multipart/form-data",
            token: localStorage.getItem("token"),
          },
        }).then((resolve) => {
          console.log(resolve);

          cancel = null;
        });
      }
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