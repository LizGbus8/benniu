/**
 * Created by jimmy on 17/2/26.
 */

// var Base = require('../../utils/base.js').base;
import {
  Base
} from '../../utils/base.js';

class Home extends Base {
  constructor() {
    super();
  }

  /*首页部分商品*/
  getProductorData(callback) {
    var param = {
      url: 'http://127.0.0.1:8084/content/hot',
      type: 'get',
      setUpUrl: false,
      sCallback: function(res) {
        console.log(res.data)
        callback && callback(res.data);
      }
    };
    this.request(param);
  }
};

export {
  Home
};