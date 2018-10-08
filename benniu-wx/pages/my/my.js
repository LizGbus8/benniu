import {My} from '../my/my-model.js';
var my=new My();

Page({
    data: {
        pageIndex:1,
        isLoadedAll:false,
        loadingHidden:true,
        orderArr:[],
        userinfo:null,
        comments:null,
        list: [
          {
            id: 'myinfo',
            name: '个人信息',
            open: false,
            img:'../../imgs/icon/myinfo.png'
          },
          {
            id: 'leaving',
            name: '查看留言',
            open: false,
            img: '../../imgs/icon/atm.png',
            pages: ['article', 'badge', 'flex', 'footer', 'gallery', 'grid', 'icons', 'loadmore', 'panel', 'preview', 'progress']
          },
          {
            id: 'comment',
            name: '我的评价',
            open: false,
            img: '../../imgs/icon/text.png'
          }
        ]
    },
    onLoad:function(){
        this._loadData();
    },

    _loadData:function(){
        var that=this;
        /* 加载个人信息 */
        my.getUserInfo((data)=>{
          console.log("userinfo" +data);
            that.setData({
              userinfo:data
            });

          //加载评价信息
          var userId = this.data.userinfo.openid;
          my.getComments(userId, (data) => {
            console.log("comments" + data);
            that.setData({
              comments: data
            });
          });
        });

      
    },

    /* 查看评价 */
    onCommentTap:function(event){
        var that=this;
        
    },

    /*下拉刷新页面*/
    onPullDownRefresh: function(){
        var that=this;
       
    },

    onReachBottom:function(){
        if(!this.data.isLoadedAll) {
            this.data.pageIndex++;
            this._getOrders();
        }
    },

    /*
     * 提示窗口
     * params:
     * title - {string}标题
     * content - {string}内容
     * flag - {bool}是否跳转到 "我的页面"
     */
    showTips:function(title,content){
        wx.showModal({
            title: title,
            content: content,
            showCancel:false,
            success: function(res) {
            }
        });
    },
    kindToggle: function (e) {
      var id = e.currentTarget.id, list = this.data.list;
      for (var i = 0, len = list.length; i < len; ++i) {
        if (list[i].id == id) {
          list[i].open = !list[i].open
        } else {
          list[i].open = false
        }
      }
      this.setData({
        list: list
      });
     }

})