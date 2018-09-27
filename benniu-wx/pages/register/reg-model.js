import {
  Base
} from '../../utils/base.js'

class Reg extends Base {
  constructor() {
    super();
  }

  //判断是否注册
  isRegsited(token, cb) {
    var that = this;
    var allParams = {
      url: 'token/user',
      data: {
        token: token
      },
      header: {
        'content-type': 'application/x-www-form-urlencoded',
      },
      type: 'get',
      sCallback: function(data) {
        console.log("data:" + data.code);
        if (data.code == 775){
          typeof cb == "function" && cb(false);
        }else if(data.code == 0){
          typeof cb == "function" && cb(true);
        }
      }
    };
    this.request(allParams);
  }

  //得到用户信息
  getUserInfo(cb) {
    var that = this;
    wx.login({
      success: function() {
        wx.getUserInfo({
          success: function(res) {
            typeof cb == "function" && cb(res.userInfo);

            //将用户昵称 提交到服务器
            if (!that.onPay) {
              that._updateUserInfo(res.userInfo);
            }

          },
          fail: function(res) {
            typeof cb == "function" && cb({
              avatarUrl: '../../imgs/icon/user@default.png',
              nickName: '零食小贩'
            });
          }
        });
      },

    })
  }

  /*更新用户信息到服务器*/
  _updateUserInfo(res) {
    var nickName = res.nickName;
    delete res.avatarUrl; //将昵称去除
    delete res.nickName; //将昵称去除
    var allParams = {
      url: 'user/wx_info',
      data: {
        nickname: nickName,
        extend: JSON.stringify(res)
      },
      type: 'post',
      sCallback: function(data) {}
    };
    this.request(allParams);

  }
}



export {
  Reg
}