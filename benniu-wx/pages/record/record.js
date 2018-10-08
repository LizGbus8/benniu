// var recordObj = require('record-model.js');

import {
  Record
} from 'record-model.js';

var record = new Record(); //实例化

Page({
  data: {
    loadingHidden: false,
    userId: ''
  },

  onLoad: function() {
    var that = this;
    record.getUserInfo((data) => {
      that.setData({
        userId : data.openid,
        loadingHidden: true
      });
      
    })
    console.log(that.data.userinfo);
  },

  onMyPublishTap: function(e) {
    var that = this;
    var userId = that.data.userId;
    console.log(userId);
    wx.navigateTo({
      url: '../item/item?userId=' + userId+'&code=1'
    })
  },

  onMySallTap: function (e) {
    var that = this;
    var userId = that.data.userId;
    console.log(userId);
    wx.navigateTo({
      url: '../item/item?userId=' + userId + '&code=2'
    })
  },

  onMyBuyTap: function (e) {
    var that = this;
    var userId = that.data.userId;
    console.log(userId);
    wx.navigateTo({
      url: '../item/item?userId=' + userId + '&code=3'
    })
  }
})