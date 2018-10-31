//app.js
App({
  onLaunch: function () {
    // 登录
    this.getUserInfo()
  },
  getUserInfo: function (callback) {
    let that = this
    wx.getStorage({
      key: 'userInfo',
      success: function(res) {
        that.globalData.userInfo = res.data
      },fail:function(res){
        
      }
    })
    wx.login({
      success: res => {
        wx.request({
          url: that.globalData.HOST + "api/miniProgram/login", //仅为示例，并非真实的接口地址
          data: {
            code: res.code
          },
          header: {
            'content-type': 'application/json' // 默认值
          },
          success: function (res) {
            if (res.data.code == 0) {
              wx.setStorage({
                key: 'userInfo',
                data: res.data.data
              })
              that.globalData.userInfo = res.data.data
            }
          }
        })
      }
    })
  },
  globalData: {
    userInfo: {toekn:"xxxxxxxxxxxxxxx"},
    HOST:"http://localhost/"
  }
})