/**
 * Created by jimmy on 17/03/05.
 */
import {
  Base
} from '../../utils/base.js';

class Record extends Base {
  constructor() {
    super();
  };
  getUserInfo(callback) {
    var param = {
      url: 'http://127.0.0.1:8082/token/userinfo',
      setUpUrl: false,
      sCallback: function(res) {
        console.log(res.data)
        callback && callback(res.data);
      }
    };
    this.request(param);
  }

}

export {
  Record
};