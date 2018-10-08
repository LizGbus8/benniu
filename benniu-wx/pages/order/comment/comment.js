import {
  Comment
} from 'comment-model.js';
var comment = new Comment(); //实例化 home 的推荐页面

Page({
  /**
   * 页面的初始数据
   */
  data: {
    id: '', //订单id
    title: '', //操作码
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      id: options.id,
      title: options.productTitle
    });
    console.log("订单操作")
    var code = this.data.code;
  },

  /* 提交评价表单 */
  formSubmit: function (e) {
    var that = this;
    var formData = e.detail.value;
    console.log("e:" + e);
    wx.request({
      url: 'http://127.0.0.1:8090/comment',
      data: {
        orderId:that.data.id,
        content:formData.content,
        type:formData.type
      },
      header: {
        'Content-Type': 'application/json',
        'token': wx.getStorageSync('token')
      },
      method: "POST",
      success: function (res) {
        var orderId = res.data.data.orderId;
        /* 评价提交成功，返回 */
        //1.改变订单状态  交易中-0 关闭交易-1 完成交易-3 查看评价-4
        comment.changeOrderStatus(orderId, 4, (data) => {//order 要改变的状态 
          that.setData({
            loadingHidden: true
          });
          console.log("订单操作：" + data)
          //2.页面跳转
          wx.reLaunch({
            url: '/pages/tip/success'
          })
        });
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