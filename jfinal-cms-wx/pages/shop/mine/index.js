// pages/shop/mine/index.js
var app=getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    userInfo: {}
  },
  onGotUserInfo: function () {
    this.getAuthorize()
  },

  getAuthorize: function (callback) {
    let that = this
    wx.getSetting({
      success: res => {
        if (res.authSetting['scope.userInfo']) {
          that.setData({
            authorizeShow: false
          })
          wx.getUserInfo({
            success: res => {
              console.log(res)
              wx.request({
                url: app.globalData.HOST + "api/miniProgram/updateUserInfo",
                data: {
                  token: app.globalData.userInfo.token,
                  userInfo: JSON.stringify(res.userInfo)
                },
                header: {
                  "Content-Type": "application/x-www-form-urlencoded"
                },
                method: 'POST',
                success: function (res) {
                  if (res.data.code == 0) {
                    wx.setStorage({
                      key: 'userInfo',
                      data: res.data.data
                    })
                    app.globalData.userInfo = res.data.data
                    that.setData({
                      userInfo: res.data.data
                    })
                  }
                }
              })
            }
          })
        } else {
         
        }

      }
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      userInfo: app.globalData.userInfo
    })
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})