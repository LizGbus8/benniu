// var productObj = require('product-model.js');

import {
  Product
} from 'product-model.js';

var product = new Product(); //实例化 商品详情 对象
Page({
  data: {
    loadingHidden: false,
    hiddenSmallImg: true,
    countsArray: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10],
    productCounts: 1,
    currentTabsIndex: 0,
    cartTotalCounts: 0,
    isLike: false,
    code: 0 //0 普通查看（操作我想要） 1 我的发布（我想下架）
  },
  onLoad: function(option) {
    var id = option.id;
    this.data.id = id;
    this.setData({
      code: option.code
    })
    this._loadData();
    this._increaseRead();
  },

  /*加载所有数据*/
  _loadData: function(callback) {
    var that = this;
    product.getDetailInfo(this.data.id, (data) => {
      console.log(data);
      that.setData({
        product: data,
        loadingHidden: true
      });
      callback && callback();
    });
  },

  /* 增加浏览数 */
  _increaseRead: function(callback) {
    var that = this;
    product.increaseRead(this.data.id, (data) => {
      console.log("读了一次");
      //设置已读
      callback && callback();
    });
  },


  // 提交表单
  formSubmit: function(e) {
    var that = this;
    var formData = e.detail.value;
    console.log(e);
    console.log("formId: " + e.detail.formId)
    wx.request({
      url: 'http://127.0.0.1:8081/order',
      data: formData,
      header: {
        'Content-Type': 'application/json',
        'token': wx.getStorageSync('token')
      },
      method: "POST",
      success: function(res) {
        console.log(res.data);
        var data = res.data.data;
        console.log(data);
        console.log(e.detail.formId)
        // 推送消息
        // 买家
        wx.request({
          url: 'https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=' + wx.getStorageSync('token'),
          data: {
            'touser': data.userId,
            'template_id': 'nvWJ0GY4W9oFO3Zw3nkbnrH33ROIH6JHERTgqz_4rKI',
            'page': '/pages/home/home',
            'form_id': e.detail.formId,
            'data': {
              "keyword1": {
                "value": data.productTitle,
                "color": "#173177"
              },
              "keyword2": {
                "value": data.tradersNickName,
                "color": "#173177"
              },
              "keyword3": {
                "value": data.tradersContact,
                "color": "#173177"
              },
              "keyword4": {
                "value": data.productPrice,
                "color": "#173177"
              },
              "keyword5": {
                "value": data.createTime,
                "color": "#173177"
              },
            }
          },
          header: {
            'Content-Type': 'application/json',
            'token': wx.getStorageSync('token')
          },
          method: "POST",
          success: function(res) {
            console.log(res);
            wx.reLaunch({
              url: '/pages/tip/success'
            });
          }
        });
      }
    })
  },

  //选择购买数目
  bindPickerChange: function(e) {
    this.setData({
      productCounts: this.data.countsArray[e.detail.value],
    })
  },

  //切换详情面板
  onTabsItemTap: function(event) {
    var index = product.getDataSet(event, 'index');
    this.setData({
      currentTabsIndex: index
    });
  },

  //点赞
  onLikeTap: function(event) {
    console.log(event);
    this.setData({
      isLike: true
    });
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
      path: 'pages/product/product?id=' + this.data.id
    }
  }

})