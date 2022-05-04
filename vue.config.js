const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true
})
module.exports = {
  lintOnSave: false, // eslint-loader 是否在保存的时候检查
  pages:{
    index:{
      entry:'src/main.js'
    },
  },
  // 开启代理服务器，，方式一
  // devServer:{
  //   proxy:'http://127.0.0.1:8000'
  // }
  // 开启代理服务器，，方式二
  devServer:{
    proxy:{
      '/photo':{
        target:'http://127.0.0.1:8000'
      }
    }
  }
}
