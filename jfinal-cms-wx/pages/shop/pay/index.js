// pages/shop/to-pay/index.js
var app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    realPrice: 0,
    clock: false
  },
  chooseAddress: function (e) {
    let that = this
    wx.chooseAddress({
      success: function (res) {
        let address = res
        wx.request({
          url: app.globalData.HOST + "api/chooseAddress", //提交订单
          data: {
            token: app.globalData.userInfo.token,
            address: JSON.stringify(address)
          },
          header: {
            "Content-Type": "application/x-www-form-urlencoded"  // 默认值
          },
          method: 'POST',
          success: function (res) {
            console.log(res)
            if (res.data.code == 0) {
              let postFee = res.data.data.postFee
              let order = that.data.order
              order.payFee = order.payFee - order.postFee + postFee
              order.postFee = postFee
              order.address = address
              that.setData({
                order: order
              })
            }
          }
        })
      },
      fail: function (res) {
        console.log(res)
      }
    })
  },
  getOrder: function (orderId) {
    let that = this
    wx.request({
      url: app.globalData.HOST + "api/getOrder", 
      data: {
        token: app.globalData.userInfo.token,
        orderId: orderId
      },
      header: {
        'content-type': 'application/json' // 默认值
      },
      method: 'GET',
      success: function (res) {
        console.log(res)
        if (res.data.code == 0) {
          let order = res.data.data
          order.youhuis = JSON.parse(order.youhuis)
          if (order.address != null)
            order.address = JSON.parse(order.address)
          that.setData({
            order: order
          })
        }
      }
    })
  },
 
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.getOrder(options.id)
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