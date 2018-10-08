import {
  Order
} from 'order-model.js';
var order = new Order(); //实例化 home 的推荐页面

// pages/order/order.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    id: '', //订单id
    code: '', //操作码
    tip:'',
    msg:'',
    comment:'',
    loadingHidden: false,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    this.setData({
      id: options.id,
      code: options.code
    });
    console.log("订单操作")
    var code = this.data.code;
    if (code == 1 || code == 3){
      this._changeOrderStatus();
    }else if (code == 4){
      this._readComment();
    }
    
  },
  //交易中-0 关闭交易-1 完成交易-3 查看评价-4
  _changeOrderStatus:function(){
    var that = this;
    var orderId = that.data.id;
    var code = that.data.code;
    order.changeOrderStatus(orderId, code, (data) => {//order 要改变的状态 
      that.setData({
        loadingHidden: true
      });
      console.log("订单操作：" + data)
    });
  },

  /**查看评价 */
  _readComment: function () {
    var that = this;
    var orderId = that.data.id;
    order.readComment(orderId, (data) => {//order 要改变的状态 
      that.setData({
        loadingHidden: true,
        comment:data
      });
      console.log("评价的数据" + data)
    });
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function() {

  },
  onHomeTap: function (e) {
    console.log("onHomeTap")
    //跳转到home
    wx.reLaunch({
      url: '/pages/home/home'
    })
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function() {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {

  }
})