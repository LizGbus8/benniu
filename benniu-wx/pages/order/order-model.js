/**
 * Created by jimmy on 17/2/26.
 */
import {
  Base
} from '../../utils/base.js';

class Order extends Base {
  constructor() {
    super();
  }

  /*改变订单状态*/
  changeOrderStatus(orderId, status,callback) {
    var param = {
      url: 'http://127.0.0.1:8081/order/' + orderId + '/' + status,
      setUpUrl: false,
      type:"put",
      data: {
        orderStatus: status
      },
      sCallback: function (data) {
        callback && callback(data);
      }
    };
    this.request(param);
  }

  /*查看评价*/
  readComment(orderId,callback) {
    var param = {
      url: 'http://127.0.0.1:8090/comment/' + orderId,
      setUpUrl: false,
      sCallback: function (res) {
        callback && callback(res.data);
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
}

export {
  Order
};