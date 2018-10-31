// pages/shop/index/index.js
var app=getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    indicatorDots: false,
    autoplay: false,
    interval: 5000,
    duration: 1000
  },
  getAdList() {
    var self=this
    wx.request({
      url: app.globalData.HOST+"api/getAdList", //仅为示例，并非真实的接口地址
      data: {
      
      },
      header: {
        'content-type': 'application/json' // 默认值
      },
      success: function (res) {
        if(res.data.code==0){
          self.setData({
            adList:res.data.data
          })
        }
      }
    })
  },
  getProductList() {
    var that = this
    wx.request({
      url: app.globalData.HOST + "api/getProductPage", //仅为示例，并非真实的接口地址
      data: {
          pageNum:1,
          pageSize: 20,          
      },
      header: {
        'content-type': 'application/json' // 默认值
      },
      success: function (res) {
        if (res.data.code == 0) {
          that.setData({
            productPage: res.data.data
          })
        }
      }
    })
  },
  getNoLCouponList:function(){
    let that=this
    wx.request({
      url: app.globalData.HOST + "api/getNoLCouponList", 
      data: {
        token: app.globalData.userInfo.token,
      },
      header: {
        'content-type': 'application/json' 
      },
      success: function (res) {
        if (res.data.code == 0) {
          that.setData({
            couponList: res.data.data
          })
        }
      }
    })
  },
  lingqu:function(e){
    console.log(e)
    let couponId = e.currentTarget.dataset.id
    
    let that = this
    wx.request({
      url: app.globalData.HOST + "api/lingquCoupon",
      data: {
        token: app.globalData.userInfo.token,
        couponId: couponId
      },
      header: {
        'content-type': 'application/json'
      },
      success: function (res) {
        if (res.data.code == 0) {
          let userCoupon=res.data.data
          let couponList = that.data.couponList
          for (let o in couponList){
            if (couponList[o].id == userCoupon.couponId){
              couponList[o].user_coupon_id = userCoupon.id
            }
          }
          that.setData({
            couponList: couponList
          })
        }
      }
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.getAdList();
    this.getProductList();
    this.getNoLCouponList();
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