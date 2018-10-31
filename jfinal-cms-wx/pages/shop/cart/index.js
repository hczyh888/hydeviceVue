// pages/shop/cart/index.js
var app=getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    cartData:[],
    isSelectAll:false
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    
  },
  selectGoods:function(e){
    let index=e.currentTarget.dataset.index
    let cartData = this.data.cartData
    let totalPrice = 0
    cartData[index].checked = !cartData[index].checked
    let isSelectAll=true
    for (let o in cartData) {
      if (cartData[o].checked){
        totalPrice += cartData[o].price * cartData[o].count
      }else{
        isSelectAll=false
      }
    }
    this.setData({
      cartData: cartData,
      isSelectAll: isSelectAll,
      totalPrice: totalPrice
    })
  },
  selectAll:function(){
    let cartData = this.data.cartData
    let isSelectAll = !this.data.isSelectAll
    let totalPrice=0
    for(let o in cartData){
      cartData[o].checked = isSelectAll
      if (isSelectAll)
        totalPrice += cartData[o].price * cartData[o].count
    }
    this.setData({
      totalPrice: totalPrice,
      isSelectAll: isSelectAll,
      cartData:cartData
    })

  },
goDetail:function(e){
  let index = e.currentTarget.dataset.index
  let cartData = this.data.cartData
  wx.redirectTo({
    url: '/pages/shop/detail/index?id=' + cartData[index].goods.id
  })
},
goOrderConfirm:function(e){
  let that=this
  let cartData = this.data.cartData
let selectedGoods=[]
  for(let o of cartData){
    if(o.checked==true)
    selectedGoods.push(o)
  }
  let orderData={}
  orderData.totalPrice = this.data.totalPrice
  orderData.goods = selectedGoods
  wx.setStorage({
    key: 'goodsData',
    data: orderData
  })
  wx.navigateTo({
    url: '/pages/shop/order-confirm/index'
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
    let that = this
    wx.getStorage({
      key: 'cartData',
      success: function (res) {
        console.log(res.data)
        that.setData({
          cartData: res.data,
          isSelectAll:false,
          totalPrice:0
        })
      },
      fail(res) {

      }
    })

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