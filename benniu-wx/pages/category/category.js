import {
  Category
} from 'category-model.js';
var category = new Category(); //实例化 home 的推荐页面
Page({
  data: {
    transClassArr: ['tanslate0', 'tanslate1', 'tanslate2', 'tanslate3', 'tanslate4', 'tanslate5'],
    currentMenuIndex: 0,
    loadingHidden: false,

    category_id:1,

    page: 0, //当前页码
    size: 10, //页数据大小
    hasMoreData: true, //是否有更多数据
    contentlist: []
  },

  onLoad: function() {
    this._loadData();
  },

  /*加载所有数据*/
  _loadData: function(callback) {
    var that = this;
    category.getCategoryType((categoryData) => {
      console.log(categoryData);
      that.setData({
        categoryTypeArr: categoryData,
        loadingHidden: true
      });

      that.getProductsByCategory(categoryData[0].categoryId, (data) => {
        that.setData({
          loadingHidden: true,
        });
        callback && callback();
      });
    });
  },

  /*切换分类*/
  changeCategory: function(event) {
    var that = this;
    
    //初始化分页
    this.setData({
      page: 0, //当前页码
      size: 10, //页数据大小
      hasMoreData: true, //是否有更多数据
      contentlist: []
    });

    var index = category.getDataSet(event, 'index');
    var categoryId = category.getDataSet(event, 'id');
    this.setData({
      currentMenuIndex: index,
      category_id: categoryId
    });

    that.getProductsByCategory(categoryId, (data) => {
      that.setData({
        loadingHidden: true,
      });
    });
  },

  /* 根据分类，请求商品列表 */
  getProductsByCategory: function(categoryId, callback) {
    var that = this;
    var p = that.data.page;
    category.getProductsByCategory(categoryId, p, (res) => {
      var contentlistTem = that.data.contentlist;
      console.log("category:" + categoryId);
      console.log("p:" + p);
      console.log("contentlistTem:" + contentlistTem);
      if (res.code == 0) {
        if (that.data.page == 0) {
          contentlistTem = []
        }
        var resultlist = res.data; 
        console.log("resultlist:" + resultlist);
        if (resultlist.length < that.data.size) {
          that.setData({
            contentlist: contentlistTem.concat(resultlist),
            hasMoreData: false
          })
        } else {
          that.setData({
            contentlist: contentlistTem.concat(resultlist),
            hasMoreData: true,
            page: that.data.page + 1
          })
        }
        console.log("contentlist:" + that.data.contentlist);
      } else {
        wx.showToast({
          title: res.showapi_res_error,
        })
      }
      
      callback && callback(res);
    });
  },

  /*跳转到商品详情*/
  onProductsItemTap: function(event) {
    var id = category.getDataSet(event, 'id');
    console.log(id)
    wx.navigateTo({
      url: '../product/product?id=' + id + '&code=0'
    })
  },

  /*下拉刷新页面*/
  onPullDownRefresh: function() {
    this._loadData(() => {
      wx.stopPullDownRefresh()
    });
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {
    if (this.data.hasMoreData) {
      this.getMusicInfo('加载更多数据')
    } else {
      wx.showToast({
        title: '没有更多数据',
      })
    }
  },

  //分享效果
  onShareAppMessage: function() {
    return {
      title: '零食商贩 Pretty Vendor',
      path: 'pages/category/category'
    }
  },

  lower: function (e) {
    var that = this;
    var list = that.contentlist;
    if (this.data.hasMoreData) {
      //加载
      var categoryId = that.data.category_id;
      wx.showLoading({
        title: '玩命加载中',
      })
      that.getProductsByCategory(categoryId, (res) => {
        wx.hideLoading();
      });
    } else {
      wx.showToast({
        title: '没有更多数据',
      })
    }
  
  }

})