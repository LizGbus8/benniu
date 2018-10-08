/**
 * Created by jimmy on 17/2/26.
 */
import {
  Base
} from '../../../utils/base.js';

class Comment extends Base {
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
}

export {
  Comment
};