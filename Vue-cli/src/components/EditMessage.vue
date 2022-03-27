<template>
  <div class="middle">
    <section>
      <el-row>
        <el-col :span="24"
          ><div class="grid-content bg-purple-dark">账号信息设置</div></el-col
        >
      </el-row>
      <el-row>
        <el-col :span="24"
          ><div class="grid-content bg-purple-dark">
            <div class="imgSection">
              <img :src="tempUrl" class="headerAdv" />
            </div>

            <input type="file" @change="getHeaderAdvUrl" multiple />

            <el-dialog title="预览" :visible.sync="dialogTableVisible">
              <img :src="tempUrl" class="previewImg" />
            </el-dialog>
            </div
        ></el-col>
      </el-row>
      <el-divider content-position="center">账号资料</el-divider>
      <el-row>
        <el-col :span="24">
          <div class="grid-content bg-purple-dark">
              <form class="ChangeMessage">
                    <el-col :span="24">
                        <em>
                            <span>
                                昵称:
                            </span>
                            <input type="text" v-model="name">
                            <i>4～30个字符，支持中英文、数字</i>
                        </em>
                        
                    </el-col>
                    <el-col :span="24">
                        <em>
                            <span>
                                简介:
                            </span>
                            <textarea name="introduce" v-model="textarea" placeholder="介绍一下你自己！"></textarea>
                            <i>1～140个字符</i>
                        </em>
                    </el-col>
                    <hr/>
                    <h4>
                        个人基本资料
                    </h4>
                    <el-col :span="24">
                        <em>
                            <span>
                                性别:
                            </span>
                            <div class="radio">
                                <input type="radio" name="sex" id="input" v-model="sex" value="1">男<br>
                                <input type="radio" name="sex" id="input" v-model="sex" value="0">女
                            </div>
                        </em>
                    </el-col>
                    <el-col :span="24">
                        <em>
                            <span>
                                生日:
                            </span>
                            <input type="date" name="" id="" v-model="birthday">

                            <i>1～140个字符</i>
                        </em>
                    </el-col>
                    <hr>
                    <h4>
                        教育经历
                    </h4>
                    <el-col :span="24">
                        <em>
                            <span>
                                学校类型:
                            </span>
                            <select v-model="schoolState">
                                <option value="college">大学及以上</option>
                                <option value="highSchool">高中</option>
                                <option value="technology">中专技校</option>
                                <option value="middleSchool">初中</option>
                                <option value="primarySchool">小学</option>
                            </select>
                        </em>
                    </el-col>
                    <el-col :span="24">
                        <em>
                            <span>
                                学校名称:
                            </span>
                            <input type="text" v-model="school">
                        </em>
                    </el-col>
                    <el-col :span="24">
                        <em>
                            <span>
                                入学时间:
                            </span>
                            <select v-model="schoolTime">
                                <option value="2022">2022</option>
                                <option value="2021">2021</option>
                                <option value="2020">2020</option>
                                <option value="2019">2019</option>
                                <option value="2018">2018</option>
                                <option value="2017">2017</option>
                                <option value="2016">2016</option>
                                <option value="2015">2015</option>
                            </select>
                        </em>
                    </el-col>
                    <button type="submit" class="submitForm" @click="submit">提交</button>
              </form>
          </div>
        </el-col>
      </el-row>
    </section>
  </div>
</template>

<script>
export default {
  name: "EditMessage",
  data() {
    return {
    dialogTableVisible: false,
    binarayUrl: "",

    tempUrl: this.$store.state.HeaderAdvUrl,
    name: this.$store.state.name,
    textarea: this.$store.state.introduce,
    sex: this.$store.state.sex,
    birthday:this.$store.state.birthday,
    schoolState:this.$store.state.schoolState,
    school:this.$store.state.school,
    schoolTime:this.$store.state.schoolTime,
    aa:1
    };
  },
  methods: {
    getHeaderAdvUrl(e) {
      // 先进行文件的转码
      for (let item of e.target.files) {
        if (!/image\/\w+/.test(item.type)) {
          alert("只能选择图片");

          return;
        }

        var _this = this;
        //  创建一个FileReader()对象，它里面有个readAsDataURL方法
        let reader = new FileReader();
        // readAsDataURL方法可以将上传的图片格式转为base64
        reader.readAsDataURL(item);
        //文件读取成功完成时触发
        reader.addEventListener("load", function () {
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
              var b = resolve;
              _this.binarayUrl = b;
            });
          _this.tempUrl = a;
        });
      }
      //   此处写ajax请求

      this.dialogTableVisible = true;
      this.aa += this.aa++; 
    },
    submit(e){
      e.preventDefault();

    }
  },
};
</script>

<style lang='less'>
.imgSection {
  text-align: center;
  .headerAdv {
    width: 120px;
    height: 120px;
    border-radius: 60px;
    text-align: center;
    &:hover {
    }
  }
}
.previewImg {
  width: 100%;
}
hr{
    margin-top: 30px !important;
    
}
.ChangeMessage {
    em{
        display: block;
        margin-top: 10px;
        margin-bottom: 10px;
        span {
            display: inline-block;
            width: 100px;
            text-align: center;
            font-size: 15px;
            color: black;
        }

        i {
            display: block;
            margin-left: 120px;
            font-size: 12px;
            color: #999;
        }
        input {
            // display: block;
            width: 70%;
            height: 30px;
            color: black;
            border-radius: 5px;
            outline: 0;
            border: 1px solid transparent;
            background-color: #f0f1f4;

            &:focus {
                border: 1px solid orange;
            }
        }
        textarea {
            display: inline-block;
            width: 70% !important;
            margin-left: 0 !important;
            font-size: 14px;
            vertical-align: middle;
            &:focus{
                color: black;
            }
        }
        .radio {
            display: inline-block !important;
            width: 200px;
            vertical-align: middle;

            input {
                margin-left: 15px;
                width: 15px !important;
                height: 15px !important;
                vertical-align: middle !important;
                color: black;
            }

        }

        select {
            width: 25%;
            height: 25px;
            border-radius: 5px;
            text-align: center;
            outline: 0;
            &:focus {
                border: 1px solid orange;
            }
        }
    }
    .submitForm {
    width: 200px;
    height: 30px;
    margin-left: 35%;
    border-radius: 8px;
    background-color: rgb(232, 236, 0);
    outline: 0;
    border: 1px solid transparent;
    &:hover{
        border:1px solid orange;
    }
}
}

</style>