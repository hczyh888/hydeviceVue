// pages/shop/category/index.js
var app=getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    selectedId:''
  },
  getCategoryList() {
    var self = this
    wx.request({
      url: app.globalData.HOST + "api/getCategorys", //仅为示例，并非真实的接口地址
      data: {
        token: app.globalData.userInfo.token
      },
      header: {
        'content-type': 'application/json' // 默认值
      },
      success: function (res) {
        if (res.data.code == 0) {
          let categoryList=res.data.data
          if (categoryList.length>0){
            categoryList[0].selected=true
          }
          self.setData({
            categoryList: categoryList,
            selectedId: categoryList[0].id
          })
        }
      }
    })
  },
  selectedCategory:function(e){
   let selectedId=e.target.dataset.id
    this.setData({
      selectedId: selectedId
    })
  },
  bindchange:function(e){

    this.setData({
      selectedId: e.detail.currentItemId
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.getCategoryList()
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