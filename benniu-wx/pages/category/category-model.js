/**
 * Created by jimmy on 17/2/26.
 */
import {
  Base
} from '../../utils/base.js';

class Category extends Base {
  constructor() {
    super();
  }

  /*获得所有分类*/
  getCategoryType(callback) {
    var param = {
      url: 'http://127.0.0.1:8090/category/all',
      setUpUrl: false,
      sCallback: function(data) {
        callback && callback(data.data);
      }
    };
    this.request(param);
  }
  /*获得某种分类的商品*/
  getProductsByCategory(categoryId,page, callback) {
    var param = {
      url: 'http://127.0.0.1:8090/product/by_category',
      setUpUrl: false,
      data:{
        id:categoryId,
        page:page,
        size:10
      },
      sCallback: function(data) {
        callback && callback(data);
      }
    };
    this.request(param);
  }
}

export {
  Category
};