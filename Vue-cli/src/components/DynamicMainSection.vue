<template>
  <ul class="infinite-list list">
    <section class="infinite-list-item" v-for="p1 in datap" :key="p1.id">
      <a href="javascript:;">
        <img src="../assets/preview.jpg" class="headerAdv" />
        <span class="Name">SG</span>
      </a>
      <i class="time">2022-1-5</i>
      <!-- <button class="follow" v-if="this.$store.state.email === '@qq.com'"><span class="iconfont">&#xe60c;</span></button> -->
      <el-row>
        <el-col :span="24">
          <div class="grid-content bg-purple-dark">
            1111111111111111111111<br />
          </div>
        </el-col>
        <el-col :span="24" v-if="1">
          <div class="grid-content bg-purple-dark" v-for="p in 6" :key="p">
            <img src="../assets/preview.jpg" />
          </div>

        </el-col>
        <hr v-if="0" />
        <el-col :span="24" v-if="0">
          <div class="grid-content bg-purple-dark">
            <a href="#"> SG </a>
          </div>
        </el-col>
        <el-col :span="24" v-if="0">
          <div class="grid-content bg-purple-dark" v-for="p in 6" :key="p">
            <img src="../assets/preview.jpg" />
          </div>
        </el-col>
      </el-row>
      <hr />
      <el-row class="tools">
        <el-col :span="8">
          <el-badge :value="2" class="item" type="warning">
            <span class="iconfont" @click="aplay(p1.id)"> &#xe602; </span>
          </el-badge>
        </el-col>
        <el-col :span="8">
          <el-badge :value="5" class="item" type="warning">
            <el-collapse accordion class="commentDropList">
              <el-collapse-item>
                <template slot="title">
                  <span class="iconfont"> &#xe6ad; </span>
                </template>
                <!-- @click="open(p1.id)" -->
                <div
                  v-for="item in comment"
                  :key="item.id"
                  class="commentDropListCotent"
                >
                  <a href="javascript:;" @click="open(item.id)">
                    {{ item.msg }}
                  </a>
                </div>
                <div class="commentDropListCotent" v-if="comment.length > 5">
                  <a href="javascript:;"> 查看更多 </a>
                </div>
              </el-collapse-item>
            </el-collapse>
          </el-badge>
        </el-col>
        <el-col :span="8">
          <el-badge :value="2" class="item" type="warning">
            <span class="iconfont" @click="like(p1.id)"> &#xe644; </span>
          </el-badge>
        </el-col>
      </el-row>
    </section>
    <section>
      <el-skeleton :rows="6" animated v-show="1" />
    </section>
  </ul>
</template>

<script>
// 引入一下nanoid生成随机，先关闭报错，后期没用删除掉
import {nanoid} from 'nanoid'
export default {
  name: "DynamicMainSection",
  props: [],
  data() {
    return {
      count: 0,
      //展示图片的变量
      dialogImageUrl: "",
      dialogVisible: true,
      disabled: false,
      comment: [
        {
          id: nanoid(),
          msg: "我是一楼",
        },
        {
          id: nanoid(),
          msg: "我是二楼",
        },
        {
          id: "002",
          msg: "我是三楼",
        },
        {
          id: nanoid(),
          msg: "我是四楼",
        },
        {
          id: nanoid(),
          msg: "我是五楼",
        },
      ],
      datap: [
        {
          id: "001",
        },
        {
          id: "002",
        },
        {
          id: "003",
        },
        {
          id: "004",
        },
      ],
      // comment:''
    };
  },
  methods: {
    // 用于异步ajax请求
    load() {
      this.datap.push({
        id: nanoid(),
      });
    },
    // 滚动条位置
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
    // dialog框展示图片
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    // 评论内容
    open(id) {
      this.$prompt("请输入评论内容", "评论", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
      })
        .then(({ value }) => {
          this.$notify({
            title: "成功",
            message: "输入内容" + value + "---" + id,
            // 此处书写ajax代码
            type: "success",
            position: "bottom-left",
          });
        })
        .catch(() => {
          this.$notify.info({
            title: "取消",
            message: "取消评论!",
            position: "bottom-left",
          });
        });
    },
    // 点赞
    like(id) {
      new Promise((resolve, reject) => {
        // 此处书写ajax代码
        reject();
      })
        .then(({ value }) => {
          this.$notify({
            title: "成功",
            message: "点赞成功!",
            // 此处书写ajax代码
            type: "success",
            position: "bottom-left",
          });
        })
        .catch(() => {
          this.$notify({
            title: "失败",
            message: "点赞失败,我也不知道为什么!",
            type: "warning",
            position: "bottom-left",
          });
        });
    },
    // 转发
    aplay(id) {
      new Promise((resolve, reject) => {
        // 此处书写ajax代码
        reject();
      })
        .then(({ value }) => {
          this.$notify({
            title: "成功",
            message: "转发成功!",
            // 此处书写ajax代码
            type: "success",
            position: "bottom-left",
          });
        })
        .catch(() => {
          this.$notify({
            title: "失败",
            message: "转发失败,我也不知道为什么!",
            type: "warning",
            position: "bottom-left",
          });
        });
    },
  },
  //挂载后给window注册滚动事件，调用上方判断是否到达底部进行加载，
  // 考虑到ajax是异步任务，所以要处理一下如何在加载时使页面不可动
  mounted() {
    window.addEventListener(
      "scroll",
      () => {
        if (this.touchScroll() < 10) {
          this.load();
        }
      },
      true
    );
  },
  //VC销毁前注销事件
  beforeDestroy() {
    window.removeEventListener("scroll", this.mounted);
  },
};
</script>

<style lang='less' scoped>
.list {
  overflow: auto;
}
.headerAdv {
  width: 30px !important;
  height: 30px !important;
  border-radius: 15px !important;
}
.Name {
  font-size: 20px !important;
  vertical-align: middle;
}
.time {
  margin-left: 15px;
  font-size: 12px;
  vertical-align: bottom;
}
.follow {
  float: right;
  width: 50px;
  height: 30px;
  border-radius: 15px;
  border:2px solid transparent;
  cursor: pointer;
  &:active{
    border-color:orange;
    color: orange;
  }
}
.grid-content {
  word-wrap: break-word;
  word-break: normal;
  margin-top: 10px;
  img {
    float: left;
    margin-right: 10px;
    width: 100px;
    height: 100px;
    border-radius: 5px;
  }
}
hr {
  margin-top: 30px;
}

.tools {
  text-align: center;
  span {
    cursor: pointer;
    &:hover {
      color: orange;
    }
  }
  .item {
    user-select: none;
  }
  .commentDropList {
    border: 0 !important;
  }
  .commentDropListCotent {
    a {
      width: 100%;
      color: orange;
    }
  }
}
</style>