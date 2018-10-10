import {
  Home
} from 'home-model.js';
var home = new Home(); //实例化 首页 对象
Page({
  data: {
    loadingHidden: false,
    page: 0, //当前页码
    size: 10, //页数据大小
    hasMoreData: true, //是否有更多数据
    contentlist: [],
    sallorderlist: [],
    buyorderlist: [],
    userId: '',
    orderlist: [],
    code: null
  },
  onLoad: function() {
    this._loadData();
  },

  /*加载所有数据*/
  _loadData: function(callback) {
    var that = this;

    /*获取单品信息*/
    home.getProductorData((data) => {
      console.log("data==" + data);
      that.setData({
        productsArr: data,
        loadingHidden: true
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
  search:function(){
    var that = this;
    var keyword = that.data.inputVal;
    //跳转到search
    wx.navigateTo({
      url: '../item/item?keyword=' + keyword + '&code=5'
    })
  },
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