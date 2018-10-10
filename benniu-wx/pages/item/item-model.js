/**
 * Created by jimmy on 17/2/26.
 */
import {
  Base
} from '../../utils/base.js';

class Item extends Base {
  constructor() {
    super();
  }

  /*获得订单列表*/
  getOrdersByUserIdAndType(userId, page,type, callback) {
    var param = {
      url: 'http://127.0.0.1:8081/order/list',
      setUpUrl: false,
      data: {
        userId: userId,
        page: page,
        type:type,
        size: 10
      },
      sCallback: function (res) {
        callback && callback(res);
      }
    };
    this.request(param);
  }

  /*获得某种分类的商品*/
  getProductsByUserId(userId, page, callback) {
    var param = {
      url: 'http://127.0.0.1:8090/product',
      setUpUrl: false,
      data: {
        userId: userId,
        page: page,
        size: 10
      },
      sCallback: function (data) {
        callback && callback(data);
      }
    };
    this.request(param);
  }

  /* 获取搜索的商品列表 */
  getSearchData(keyword, page, callback) {
    var param = {
      url: 'http://127.0.0.1:8085/search',
      setUpUrl: false,
      data: {
        q: keyword,
        page: page,
        size: 10
      },
      sCallback: function (res) {
        console.log("res return data is " + res)
        callback && callback(res);
      }
    };
    this.request(param);
  }
}

export {
  Item
};