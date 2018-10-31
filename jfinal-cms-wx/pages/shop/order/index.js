// pages/shop/order/index.js
var app = getApp()

function countdown(that) {

  console.log(1)
  let orderPage = that.data.orderPage
  let list = orderPage.list
  for (let o in list) {
    if (list[o].order_status != 1) {
      continue
    }
    let micro_second = 1800 - (new Date() - Date.parse(list[o].createTime))
    if (micro_second < 0) {
      list[o].order_status = 7
      continue
    }
    var second = Math.floor(micro_second / 1000);
    // 天数
    var day = Math.floor(second / 3600 / 24);
    // 总小时
    var hr = Math.floor(second / 3600);
    // 小时位
    var hr2 = hr % 24;
    // 分钟位
    var min = Math.floor((second - hr * 3600) / 60);
    // 秒位
    var sec = (second - hr * 3600 - min * 60);// equal to => var sec = second % 60;
    if (sec < 10) {
      sec = "0" + sec
    }
    list[o].countTime = hr + ":" + min + ":" + sec
  }
  orderPage.list = list
  that.setData({
    orderPage: orderPage
  })
  setTimeout(function () {
    countdown(that)
  }
    , 1000)
}
Page({

  /**
   * 页面的初始数据
   */
  data: {
    swithIndex: 0,
    orderPage: {}
  },
  switchTab: function (e) {
    this.setData({
      swithIndex: e.currentTarget.dataset.index
    })
    this.getOrderList()
  },
  getOrderList(pageNum, pageSize) {
    let index = this.data.swithIndex
    let order_status
    if (index == 0) {
      order_status = ""
    } else {
      order_status = index
    }
    if (pageNum == null) {
      pageNum = 1
    }
    if (pageSize == null) {
      pageSize = 50
    }
    let that = this
    wx.request({
      url: app.globalData.HOST + "api/getOrderList", //仅为示例，并非真实的接口地址
      data: {
        token: app.globalData.userInfo.token,
        pageNum: pageNum,
        pageSize: pageSize,
        order_status: order_status
      },
      header: {
        'content-type': 'application/json' // 默认值
      },
      success: function (res) {
        if (res.data.code == 0) {
          that.setData({
            orderPage: res.data.data
          })
        }
      }
    })
  },
  goPay: function (e) {
    wx.navigateTo({
      url: '/pages/shop/pay/index?id=' + e.currentTarget.dataset.id,
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.getOrderList();
    countdown(this)
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