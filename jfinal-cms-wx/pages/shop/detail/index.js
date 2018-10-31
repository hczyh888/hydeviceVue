var app = getApp();
var WxParse = require('../../../lib/wxParse/wxParse.js');

// 定义一个总毫秒数，以一天为例
// var total_micro_second = 3600 * 1000*24;//这是一天倒计时
var total_micro_second = 0;//这是10秒倒计时


/* 毫秒级秒杀倒计时 */
function countdown(that) {
  // 渲染倒计时时钟
  that.setData({
    clock: dateformat(total_micro_second)//格式化时间
  });

  if (total_micro_second <= 0) {
    that.setData({
      clock: "秒杀结束"
    });
    // timeout则跳出递归
    return;
  }
  //settimeout实现倒计时效果
  setTimeout(function () {
    // 放在最后--
    total_micro_second -= 1000;
    countdown(that);
  }
    , 1000)//注意毫秒的步长受限于系统的时间频率，于是我们精确到0.01s即10ms
}
// 时间格式化输出，如1天天23时时12分分12秒秒12 。每10ms都会调用一次
function dateformat(micro_second) {
  // 总秒数
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
  // 毫秒位，保留2位
  var micro_sec = Math.floor((micro_second % 1000) / 10);
  return day + "天" + hr2 + "时" + min + "分" + sec + "秒";
}


Page({
  data: {
    id: 0,
    indicatorDots: true,
    autoplay: false,
    interval: 5000,
    duration: 1000,
    switchshowindex: 1,
    goods: {},
    selectedImg: "",
    specificationsShow: false,
    selectedCount:0,
    selectedSpecificationsStr:'',
    maxCount:0
  },
  getGoodsInfo: function () {
    let self = this;
    wx.request({
      url: app.globalData.HOST + "api/getProduct",
      data: {
        id: self.data.id
      },
      header: {
        'content-type': 'application/json' // 默认值
      },
      success: function (res) {
        if (res.data.code == 0) {
          let goods = res.data.data
          goods.parameterValue = JSON.parse(goods.parameterValue)
          goods.pictures.push(goods.thumbnail_temp)
          if (goods.productPromotion != null && goods.productPromotion.type==0) {
              goods.price =  goods.productPromotion.lastPrice
          }
          self.setData({
            goods: goods,
            maxCount: goods.stock
          })
          if (res.data.data.introduction!=null)
          WxParse.wxParse('goodsDetail', 'html', res.data.data.introduction, self);
          self.getSpecification()
          if (goods.productPromotion!=null){
            total_micro_second = Date.parse(goods.productPromotion.endDate) - Date.parse(new Date())
            countdown(self)
          }
        }
      }
    })
  },
  getSpecification: function () {
    let self = this;
    wx.request({
      url: app.globalData.HOST + "api/getSpecification", //仅为示例，并非真实的接口地址
      data: {
        productId: self.data.id
      },
      header: {
        'content-type': 'application/json' // 默认值
      },
      success: function (res) {
        if (res.data.code == 0) {
          let checkedAttributes = res.data.data.checkedAttributes
          let specifications = res.data.data.specifications
          let goods = self.data.goods
          for (let o of specifications) {
            let flag = false
            if (o.thumbnail_temp != null) {
              for (let img of goods.pictures) {
                if (img == o.thumbnail_temp) {
                  flag = true
                  break;
                }
              }
              if (flag == false) {
                goods.pictures.push(o.thumbnail_temp)
              }
            }
          }
          self.setData({
            goods: goods,
            checkedAttributes: checkedAttributes,
            specifications: specifications
          })
        }
      }
    })
  },
  addCart: function () {
    if (this.data.specificationsShow == false) {
      this.setData({
        specificationsShow: true
      })
      if (this.data.specifications.length > 0)
      this.checkLackSpecifications(0)
      return
    } else if (this.data.selectedCount > 0) {
      console.log(11)
      let cartGoods = {}
      cartGoods.goods = this.data.goods
      cartGoods.count = this.data.selectedCount
      cartGoods.selectedSpecificationsStr = this.data.selectedSpecificationsStr
      cartGoods.currentSpecifications = this.data.currentSpecifications
      cartGoods.thumbnail = this.data.goods.thumbnail_temp
      if (this.data.currentSpecifications!=null){
        cartGoods.price = this.data.currentSpecifications.realPrice ? this.data.currentSpecifications.realPrice : this.data.currentSpecifications.price
        if (this.data.currentSpecifications.thumbnail_temp!=null){
          cartGoods.thumbnail = this.data.currentSpecifications.thumbnail_temp
        }
      } else if (cartGoods.goods.productPromotion!=null){
        cartGoods.price = cartGoods.goods.productPromotion.lastPrice
      }else{
        cartGoods.price = cartGoods.goods.price
      }

      wx.getStorage({
        key: 'cartData',
        success: function (res) {
          console.log(res)
          let cartData = res.data
          let flag=false
          for(let o in cartData){
            if (cartData[o].goods.id == cartGoods.goods.id && (cartData[o].currentSpecifications == null || cartData[o].currentSpecifications.SKU == cartGoods.currentSpecifications.SKU)){
              flag=true
              cartData[o].count = cartGoods.count
            }
          }
          if(flag==false)
          cartData.push(cartGoods)
          wx.setStorage({
            key: "cartData",
            data: cartData
          })
          wx.showToast({
            title: '加入购物车成功',
          })
        },
        fail(res){
          let cartData = []
          cartData.push(cartGoods)
          wx.setStorage({
            key: "cartData",
            data: cartData
          })
          wx.showToast({
            title: '加入购物车成功',
          })
        }
      })
    }
  },
  buy: function () {
    if (this.data.specificationsShow == false) {
      this.setData({
        specificationsShow: true
      })
      if(this.data.specifications.length>0)
      this.checkLackSpecifications(0)
      return;
    }else if(this.data.selectedCount>0){
        let cartGoods={}
        cartGoods.goods=this.data.goods
        cartGoods.count=this.data.selectedCount
        cartGoods.currentSpecifications = this.data.currentSpecifications
    }
  },

  checkLackSpecifications(index){
      console.log(index)
      let checkedAttributes = this.data.checkedAttributes
      let attributeValues = checkedAttributes[index].attributeValues
      let specifications = this.data.specifications
      for (let i in attributeValues){
      let flag=true
      for (let o of specifications){
        if(o.attributeValues[index].value ==attributeValues[i].value){
          flag=false
          break
        }
      }
      attributeValues[i].lack = flag
      console.log(attributeValues[i])
      }
      checkedAttributes[index].attributeValues = attributeValues
      this.setData({
        checkedAttributes: checkedAttributes
      })
  },
  selectTag:function(e){
    let attributeId=e.currentTarget.dataset.attributeid
    let id = e.currentTarget.dataset.id
    let lack = e.currentTarget.dataset.lack
    if(lack==true)return
    let checkedAttributes = this.data.checkedAttributes
    for (let o in checkedAttributes){
      if (checkedAttributes[o].id == attributeId){
        let attributeValues = checkedAttributes[o].attributeValues
        for (let i in attributeValues){
          if (attributeValues[i].id==id){
            attributeValues[i].selected=true
            if (o < checkedAttributes.length-1){
              this.checkLackSpecifications(parseInt(o)+1)
            }
            else if (o > 0 && o == checkedAttributes.length - 1){
              this.checkLackSpecifications(parseInt(o) -1)
            }
          }else{
            attributeValues[i].selected = false
          }
        }
        checkedAttributes[o].attributeValues = attributeValues
        this.setData({
          checkedAttributes: checkedAttributes
        })
        break
      }
    }
this.currentSpecifications()
  },
  currentSpecifications:function(){ 
    let SKU='SKU'
    let selectedSpecificationsStr=''
    let checkedAttributes = this.data.checkedAttributes
    let flag=0
    for (let o in checkedAttributes) {
        let attributeValues = checkedAttributes[o].attributeValues
        for (let attr of attributeValues) {
          if (attr.selected){
            SKU += attr.id
            flag++
            selectedSpecificationsStr = selectedSpecificationsStr + attr.value+" "
            break
          }
        }
    }

    if (flag < checkedAttributes.length){
      return
    }
    let currentSpecifications= this.data.specifications.find(function (o) {
      return o.SKU == SKU
    });
    if (currentSpecifications!=null&&currentSpecifications.stock>0){
      if (currentSpecifications != null && this.data.goods.productPromotion){
        if (this.data.goods.productPromotion.status==0){
          currentSpecifications.realPrice = currentSpecifications.price * this.data.goods.productPromotion.discount
        }else{
          currentSpecifications.realPrice = currentSpecifications.price - this.data.goods.productPromotion.reduceMoney
        }
      }

    this.setData({
      currentSpecifications: currentSpecifications,
      maxCount:currentSpecifications.stock,
      selectedCount:1,
      selectedSpecificationsStr: selectedSpecificationsStr
    })
    }else{
      this.setData({
        currentSpecifications: null,
        maxCount: 0,
        selectedCount: 0,
        selectedSpecificationsStr:''
      })
    }
  },
  //基本操作
  switchShow: function (e) {
    this.setData({
      switchshowindex: e.currentTarget.dataset.switchshowindex
    })
  },
  specificationsClose: function (e) {
    console.log(e)
    if (e.target.id == 'specifications' || e.target.id == 'close-btn') {
      this.setData({
        specificationsShow: false
      })
    }
  },
  minus: function () {
    this.setData({
      selectedCount: this.data.selectedCount - 1
    })
  },
  plus:function(){
    this.setData({
      selectedCount: this.data.selectedCount+1
    })
  },
  goHome:function(){
    wx.switchTab({
      url: '/pages/shop/index/index'
    })
  },



  onLoad: function (options) {
    // 页面初始化 options为页面跳转所带来的参数
    this.setData({
      id: parseInt(options.id)
    });
    this.getGoodsInfo();
  },
  onReady: function () {
    // 页面渲染完成

  },
  onShow: function () {
    // 页面显示

  },
  onHide: function () {
    // 页面隐藏

  },
  onUnload: function () {
    // 页面关闭

  }
})