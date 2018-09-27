import {
  Base
} from '../../utils/base.js';

class Publish extends Base {
  constructor() {
    super();
  }

  /*提交发布的表单*/
  submitForm(data, callback) {
    var url = "http://127.0.0.1:8084/content";
    wx.request({
      url: url, 
      data:data,
      header: {
        'content-type': 'application/json', // 默认值
        'token': wx.getStorageSync('token')
      },
      method: "POST",
      success: function (res) {
        console.log(res.data)
      }
    })
};
}
export {
  Publish
};