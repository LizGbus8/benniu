/**
 * Created by jimmy on 17/2/26.
 */

import {
  Base
} from '../../utils/base.js';

class Product extends Base {
  constructor() {
    super();
  }

  increaseRead(id, callback) {
    var param = {
      url: 'http://127.0.0.1:8084/content/' + id,
      setUpUrl: false,
      type:'put',
      sCallback: function(data) {
        callback && callback(data.data);
      }
    };
    this.request(param);
  }

  getDetailInfo(id, callback) {
    var param = {
      url: 'http://127.0.0.1:8084/product/' + id,
      setUpUrl: false,
      sCallback: function (data) {
        callback && callback(data.data);
      }
    };
    this.request(param);
  }

  //获取卖家信息  我想要
  contactSaller(userId, callback) {
    var param = {
      url: 'http://127.0.0.1:8084/product/contact/' + id,
      setUpUrl: false,
      sCallback: function(data) {
        callback && callback(data.data);
      }
    };
    this.request(param);
  }

  _notifyBuyer(e,data1, callback) {
    console.log("show : " + data1)
    var param = {
      url: 'https://api.weixin.qq.com/cgi-bin/wxopen/template/library/list?access_token='+wx.getStorageSync('token'),
      setUpUrl: false,
      type:'POST',
      data:{
        'touser': data1.userId,
        'template_id':'nvWJ0GY4W9oFO3Zw3nkbnrH33ROIH6JHERTgqz_4rKI',
        'page':'/pages/home/home',
        'form_id':e.detail.formId,
        'data':{
          "keyword1": { "value": data1.productTitle, "color": "#173177" },
          "keyword2": { "value": data1.tradersNickName, "color": "#173177" },
          "keyword3": { "value": data1.tradersContact, "color": "#173177" },
          "keyword4": { "value": data1.productPrice, "color": "#173177" },
          "keyword5": { "value": data1.createTime, "color": "#173177" },
        }
      },
      sCallback: function (data) {
        callback && callback(data.errcode);
      }
    };
    this.request(param);
  }

  _notifySaller(e, data, callback) {
    console.log(data)
    var param = {
      url: 'https://api.weixin.qq.com/cgi-bin/wxopen/template/library/list?access_token=' + wx.getStorageSync('token'),
      setUpUrl: false,
      type: 'POST',
      data: {
        'touser': data.tradersId,
        'template_id': 'nvWJ0GY4W9oFO3Zw3nkbnrH33ROIH6JHERTgqz_4rKI',
        'page': '/pages/home/home',
        'form_id': e.detail.formId,
        'data': {
          "keyword1.DATA": { "value": data.productTitle, "color": "#173177" },
          "keyword2.DATA": { "value": data.nickName, "color": "#173177" },
          "keyword3.DATA": { "value": data.contact, "color": "#173177" },
          "keyword4.DATA": { "value": data.productPrice, "color": "#173177" },
          "keyword5.DATA": { "value": data.createTime, "color": "#173177" },
        }
      },
      sCallback: function (data) {
        callback && callback(data.errmsg);
      }
    };
    console.log(param);
    this.request(param);
  }

};

export {
  Product
}