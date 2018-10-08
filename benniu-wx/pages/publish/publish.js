import {
  Publish
} from 'publish-model.js';
var publish = new Publish(); //实例化 首页 对象
// pages/publish/publish.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    files: [], 
    images: [],
    categorys: ["计算机", "英文", "教材", "考研", "考公", "管理", "电子商务"],
    categorysIndex: 0,

    contactCodes: ["手机号", "微信", "QQ", "邮箱"],
    contactCodeIndex: 0,

    schools: ["华南农业大学", "华南理工大学", "华南师范大学"],
    schoolsIndex: 0,
    baseRestUrl: 'http://127.0.0.1:8082/'
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {

  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function() {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {

  },

  //分类
  bindCategorysChange: function(e) {
    console.log('picker country 发生选择改变，携带值为', e.detail.value);

    this.setData({
      categorysIndex: e.detail.value
    })
  },

  //联系方式
  bindContactCodeChange: function(e) {
    console.log('picker country code 发生选择改变，携带值为', e.detail.value);

    this.setData({
      contactCodeIndex: e.detail.value
    })
  },

  //学校
  bindSchoolChange: function(e) {
    console.log('picker country 发生选择改变，携带值为', e.detail.value);

    this.setData({
      schoolsIndex: e.detail.value
    })
  },

  chooseImage: function(e) {
    var that = this;
    wx.chooseImage({
      count: 1,
      sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
      sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
      success: function(res) {
        // 返回选定照片的本地文件路径列表，tempFilePath可以作为img标签的src属性显示图片
        that.setData({
          files: that.data.files.concat(res.tempFilePaths)
        });
        var tempFilePaths = res.tempFilePaths; 

        wx.uploadFile({
          url: 'http://127.0.0.1:8084/content/img',
          filePath: tempFilePaths[0],
          name: 'img',
          header: {
            "Content-Type": "multipart/form-data",
            'accept': 'application/json',
            'token': wx.getStorageSync('token')    //s若有token，此处换上你的token，没有的话省略          
          },
          success: function (res) {
            var data = res.data;
            that.setData({
              images: that.data.images.concat(data)
            });
            console.log(that.data.images);
          },
          fail: function (res) {
            console.log('fail');
          }
        })
        //
        //
        console.log(that.data.files);
        
      }
    })
  },
  previewImage: function(e) {
    wx.previewImage({
      current: e.currentTarget.id, // 当前显示图片的http链接
      urls: this.data.files // 需要预览的图片http链接列表
    })
  },

  formSubmit: function(e) {
    var that = this;
    var formData = e.detail.value;

    var data = {
      categoryName: formData.categoryName,
      productTitle: formData.productTitle,
      productDesc: formData.productDesc,
      productImg1: that.data.images[0],
      productImg2: that.data.images[1],
      productImg3: that.data.images[2],
      productPrice: formData.productPrice,
      productStock: formData.productStock,
      contactType: formData.contactType,
      contactNum: formData.contactNum,
      schoolName: formData.schoolName
    };
    console.log(data);
      publish.submitForm(data,(data)=>{
      console.log(data);
    });
    
  },

})