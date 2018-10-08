import {
  Item
} from 'item-model.js';
var item = new Item(); //实例化 home 的推荐页面
// pages/item/item.js
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
    orderlist:[],
    code:null
  },
  //code 1 我的发布 2 我卖出的 3 我买入的 4 我喜欢的
  onLoad: function(option) {
    var userId = option.userId;
    this.setData({
      userId: userId,
      code: option.code
    });
    this.data.userId = userId;
    var exp = option.code;
    console.log("exp:" + exp);
    switch (exp) {
      case '1':
        this._loadPublishData(); console.log("_loadPublishData");
        break;
      case '2':
        this._loadSallData(); console.log("_loadSallData");
        break;
      case '3':
        this._loadBuyData(); console.log("_loadBuyData");
        break;
      case '4':
        this._loadPublishData(); console.log("_loadPublishData");
        break;
      default:
        console.log("default");
    }
  },

  /*加载我的买入数据*/
  _loadBuyData: function (callback) {
    var that = this;
    var userId = that.data.userId;
    that.getOrdersByUserIdAndType(userId,0,(data)=>{
      console.log("data:" + data)
      that.setData({
        orderlist:data,
        buyorderlist:data,
        loadingHidden:true
      });
      callback && callback();
    });
  },

  /*加载我的卖出数据*/
  _loadSallData: function (callback) {
    var that = this;
    var userId = that.data.userId;
    that.getOrdersByUserIdAndType(userId, 1,(data) => {
      that.setData({
        orderlist: data,
        loadingHidden: true,
        sallorderlist: data
      });
      callback && callback();
    });
  },

  /*加载我的发布数据*/
  _loadPublishData: function(callback) {
    var that = this;
    var userId = that.data.userId;
    that.getProductsByUserId(userId, (data) => {
      that.setData({
        loadingHidden: true,
        contentlist:data
      });
      callback && callback();
    });
  },

  /* 请求订单 */
  getOrdersByUserIdAndType: function (userId, type, callback) {
    var that = this;
    var p = that.data.page;
    item.getOrdersByUserIdAndType(userId, p, type, (res) => {
      var orderlistTem = that.data.orderlist;

      if (res.code == 0) {
        if (that.data.page == 0) {
          orderlistTem = []
        }
        var resultlist = res.data;
        console.log("resultlist:" + resultlist);
        if (resultlist.length < that.data.size) {
          orderlistTem = orderlistTem.concat(resultlist),
          that.setData({
            hasMoreData: false
          })
        } else {
          orderlistTem = orderlistTem.concat(resultlist),
          that.setData({
            hasMoreData: true,
            page: that.data.page + 1
          })
        }
        console.log("orderlistTem:" + orderlistTem);
      } else {
        wx.showToast({
          title: res.showapi_res_error,
        })
      }
      callback && callback(orderlistTem);
    });
  },

  /* 请求商品 */
  getProductsByUserId: function (userId, callback) {
    var that = this;
    var p = that.data.page;
    item.getProductsByUserId(userId, p, (res) => {
      var contentlistTem = that.data.contentlist;
      if (res.code == 0) {
        if (that.data.page == 0) {
          contentlistTem = []
        }
        var resultlist = res.data;
        if (resultlist.length < that.data.size) {
          contentlistTem = contentlistTem.concat(resultlist),
          that.setData({
            hasMoreData: false
          })
        } else {
            contentlistTem = contentlistTem.concat(resultlist),
          that.setData({
            hasMoreData: true,
            page: that.data.page + 1
          })
        }
      } else {
        wx.showToast({
          title: res.showapi_res_error,
        })
      }
      callback && callback(contentlistTem);
    });
  },

  /*跳转到商品详情*/
  onProductsItemTap: function(event) {
    var id = item.getDataSet(event, 'id');
    console.log(id)
    wx.navigateTo({
      url: '../product/product?id=' + id +'&code=1'
    })
  },

  /*下拉刷新页面*/
  onPullDownRefresh: function() {
    // this._loadData(() => {
    //   wx.stopPullDownRefresh()
    // });
  },

  //分享效果
  onShareAppMessage: function() {
    return {
      title: '零食商贩 Pretty Vendor',
      path: 'pages/category/category'
    }
  },

  /**
    * 页面上拉触底事件的处理函数
    */
  onReachBottom: function () {
    console.log("触动");
    var that = this;
    console.log("hasMoreData:" + this.data.hasMoreData);

    if (this.data.hasMoreData) {
      //加载
      wx.showLoading({
        title: '玩命加载中',
      })
      //code 1-我的发布 2-我卖出的 3-我买入的 4-我喜欢的
      var exp = that.data.code;
      console.log("exp:" + exp);
      switch (exp) {
        case '1':
          this._loadPublishData(); console.log("_loadPublishData");
          break;
        case '2':
          this._loadSallData(); console.log("_loadSallData");
          break;
        case '3':
          this._loadBuyData(); console.log("_loadBuyData");
          break;
        case '4':
          this._loadPublishData(); console.log("_loadPublishData");
          break;
        default:
          console.log("default");
      }
      wx.hideLoading();
    } else {
      wx.showToast({
        title: '没有更多数据',
      })
    }
  },

  lower: function(e) {
    

  }
})