/**
 * Created by jimmy on 17/2/26.
 */

// var Base = require('../../utils/base.js').base;
import {Base} from '../../utils/base.js';

class Home extends Base{
    constructor(){
        super();
    }

    /*首页部分商品*/
    getProductorData(callback){
        var token = wx.getStorageSync("token");
        var param={
            url: 'content/hot',
            data: { token: token},
            type: 'get',
            sCallback:function(res){
                callback && callback(res.data);
            }
        };
        this.request(param);
    }
};

export {Home};