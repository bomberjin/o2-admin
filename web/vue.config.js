const path = require('path')

function resolve(dir) {
    return path.join(__dirname, '.', dir)
}

module.exports = {
    publicPath: './',
    indexPath: 'index.html',
    configureWebpack:  {
      externals:  {
      'AMap': 'AMap'  
      }
    },
    chainWebpack: config => {
      config
        .plugin('html')
        .tap(args => {
          args[0].title= '搭把手管理平台'
          return args
        })
              // 删除默认配置中处理svg
      const svgRule = config.module.rule('svg')

      // 清除已有的所有 loader。
      // 如果你不这样做，接下来的 loader 会附加在该规则现有的 loader 之后。
      svgRule.uses.clear()
      
      config.module
          .rule('svg-sprite-loader')
          .test(/\.svg$/)
          .include
          .add(resolve('src/icons')) // 处理svg目录,根据实际情况修改
          .end()
          .use('svg-sprite-loader')
          .loader('svg-sprite-loader')
    },
    devServer: {
        overlay: {
          warnings: true,
          errors: true
        }
    },
    lintOnSave: false
}