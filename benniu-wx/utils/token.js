// 引用使用es6的module引入和定义
// 全局变量以g_开头
// 私有函数以_开头

import {
  Config
} from 'config.js';

class Token {
  constructor() {
    this.verifyUrl = Config.restUrl + 'token/verify';//verify
  }

  verify() {
    var token = wx.getStorageSync('token');
    if (!token) {
      this.getTokenFromServer();//wxLogin
    } else {
      this._veirfyFromServer(token);//verify
    }
  }

  _veirfyFromServer(token) {
    var that = this;
    wx.request({
      url: that.verifyUrl,
      method: 'POST',
      data: {
        token: token
      },
      header: {
        'content-type': 'application/x-www-form-urlencoded',
      },
      success: function(res) {
        var valid = res.data.isValid;
        if (!valid) {
          that.getTokenFromServer();
        }
      }
    })
  }

  getTokenFromServer(callBack) {
    var that = this;
    wx.login({
      success: function(res) {
        wx.request({
          url: "http://127.0.0.1:8082/token/user",//wxLogin
          method: 'POST',
          data: {
            code: res.code
          },
          header: {
            'content-type': 'application/x-www-form-urlencoded',
          },
          success: function(res) {
            wx.setStorageSync('token', res.data.token);
            console.log(res);
            callBack && callBack(res.data.token);
          }
        })
      }
    })
  }
}

export {
  Token
};