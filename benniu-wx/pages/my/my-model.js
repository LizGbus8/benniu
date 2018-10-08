/**
 * Created by jimmy on 17/3/24.
 */
import {Base} from '../../utils/base.js'

class My extends Base{
    constructor(){
        super();
    }

    //得到用户信息
  getUserInfo(callback){
        var that=this;
      var param = {
        url: 'http://127.0.0.1:8082/token/userinfo',
        setUpUrl: false,
        sCallback: function (res) {
          callback && callback(res.data);
        }
      };
      this.request(param);
    }

  getComments(userId,callback) {
      var that = this;
      var param = {
        url: 'http://127.0.0.1:8090/comments/'+userId,
        setUpUrl: false,
        sCallback: function (res) {
          callback && callback(res.data);
        }
      };
      this.request(param);
    }


    /*更新用户信息到服务器*/
    _updateUserInfo(res){
        var nickName=res.nickName;
        delete res.avatarUrl;  //将昵称去除
        delete res.nickName;  //将昵称去除
        var allParams = {
            url: 'user/wx_info',
            data:{nickname:nickName,extend:JSON.stringify(res)},
            type:'post',
            sCallback: function (data) {
            }
        };
        this.request(allParams);

    }
}



export {My}