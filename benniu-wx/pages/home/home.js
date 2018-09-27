import {
  Home
} from 'home-model.js';
var home = new Home(); //实例化 首页 对象
Page({
  data: {
    loadingHidden: true
  },
  onLoad: function() {
    this._loadData();
  },

  /*加载所有数据*/
  _loadData: function(callback) {
    var that = this;

    /*获取单品信息*/
    home.getHotData((data) => {
      that.setData({
        hotArr: data
      });
      callback && callback();
    });
  },

  /*跳转到商品详情*/
  onProductsItemTap: function(event) {
    var id = home.getDataSet(event, 'id');
    wx.navigateTo({
      url: '../product/product?id=' + id
    })
  },

  /*下拉刷新页面*/
  onPullDownRefresh: function() {
    this._loadData(() => {
      wx.stopPullDownRefresh()
    });
  },

  //分享效果
  onShareAppMessage: function() {
    return {
      title: '零食商贩 Pretty Vendor',
      path: 'pages/home/home'
    }
  },

  //search
  showInput: function() {
    this.setData({
      inputShowed: true
    });
  },
  hideInput: function() {
    this.setData({
      inputVal: "",
      inputShowed: false
    });
  },
  clearInput: function() {
    this.setData({
      inputVal: ""
    });
  },
  inputTyping: function(e) {
    this.setData({
      inputVal: e.detail.value
    });
  }

})