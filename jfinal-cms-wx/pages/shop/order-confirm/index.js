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
  getOrderData: function (orderData) {
    let that = this
    wx.request({
      url: app.globalData.HOST + "api/getOrderData", //提交订单
      data: {
        token: app.globalData.userInfo.token,
        orderData: JSON.stringify(orderData)
      },
      header: {
        "Content-Type": "application/x-www-form-urlencoded"  // 默认值
      },
      method: 'POST',
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
  createOrder: function (orderData) {
    let that = this
    let clock = that.data.clock
    if (clock) {
      return
    }
    if (that.data.order.address == null || that.data.order.address == '') {
      wx.showToast({
        title: '请选择收货地址'
      })
      return;
    }
    this.setData({
      clock: true
    })

    wx.request({
      url: app.globalData.HOST + "api/createOrder", //提交订单
      data: {
        token: app.globalData.userInfo.token,
        order: JSON.stringify(that.data.order)
      },
      header: {
        "Content-Type": "application/x-www-form-urlencoded"  // 默认值
      },
      method: 'POST',
      success: function (res) {
        if (res.data.code == 0) {
          let order = res.data.data
          order.youhuis = JSON.parse(order.youhuis)
          if (order.address != null)
            order.address = JSON.parse(order.address)
          that.setData({
            order: order
          })
          let cartData = wx.getStorageSync('cartData')
          let cartDataResult = []
          for (let o of cartData) {
            if (o.checked) {
              o.checked = false
              cartDataResult.push(o)
            }
          }
          wx.setStorage({
            key: 'cartData',
            data: cartDataResult
          })
          wx.switchTab({
            url: '/pages/shop/order/index',
          })
        }else{

        }
      }
    })
  },
  bindRemarkInput: function (e) {
    let order = this.data.order
    order.remark = e.detail.value
    this.setData({
      order: order
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let that = this
    wx.getStorage({
      key: 'goodsData',
      success: function (res) {
        that.getOrderData(res.data)
      },
      fail(res) {

      }
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