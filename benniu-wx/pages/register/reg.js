import {
  Reg
} from '../register/reg-model.js';
var reg = new Reg();

Page({
  data: {
    showTopTips: false,

    radioItems: [{
        name: '男',
        value: '1',
        checked: true
      },
      {
        name: '女',
        value: '2'
      }
    ],
    checkboxItems: [{
        name: '计算机',
        value: '0',
        checked: true
      },
      {
        name: '英文',
        value: '1'
      },
      {
        name: '教材',
        value: '2'
      },
      {
        name: '考研',
        value: '3'
      },
      {
        name: '考公',
        value: '4'
      },
      {
        name: '管理',
        value: '5'
      },
      {
        name: '电子商务',
        value: '6'
      }
    ],
    countryCodes: ["手机号", "微信", "QQ", "邮箱"],
    countryCodeIndex: 0,

    countries: ["华南农业大学", "华南理工大学", "华南师范大学"],
    countryIndex: 0,

    accounts: ["微信号", "QQ", "Email"],
    accountIndex: 0,

    isAgree: false,
    isReg: false
  },
  showTopTips: function() {
    var that = this;
    this.setData({
      showTopTips: true
    });
    setTimeout(function() {
      that.setData({
        showTopTips: false
      });
    }, 3000);
  },
  radioChange: function(e) {
    console.log('radio发生change事件，携带value值为：', e.detail.value);

    var radioItems = this.data.radioItems;
    for (var i = 0, len = radioItems.length; i < len; ++i) {
      radioItems[i].checked = radioItems[i].value == e.detail.value;
    }

    this.setData({
      radioItems: radioItems
    });
  },
  checkboxChange: function(e) {
    console.log('checkbox发生change事件，携带value值为：', e.detail.value);

    var checkboxItems = this.data.checkboxItems,
      values = e.detail.value;
    for (var i = 0, lenI = checkboxItems.length; i < lenI; ++i) {
      checkboxItems[i].checked = false;

      for (var j = 0, lenJ = values.length; j < lenJ; ++j) {
        if (checkboxItems[i].value == values[j]) {
          checkboxItems[i].checked = true;
          break;
        }
      }
    }

    this.setData({
      checkboxItems: checkboxItems
    });
  },
  bindDateChange: function(e) {
    this.setData({
      date: e.detail.value
    })
  },
  bindTimeChange: function(e) {
    this.setData({
      time: e.detail.value
    })
  },
  bindCountryCodeChange: function(e) {
    console.log('picker country code 发生选择改变，携带值为', e.detail.value);

    this.setData({
      countryCodeIndex: e.detail.value
    })
  },
  bindCountryChange: function(e) {
    console.log('picker country 发生选择改变，携带值为', e.detail.value);

    this.setData({
      countryIndex: e.detail.value
    })
  },
  bindAccountChange: function(e) {
    console.log('picker account 发生选择改变，携带值为', e.detail.value);

    this.setData({
      accountIndex: e.detail.value
    })
  },
  formSubmit: function(e) {
    var that = this;
    var formData = e.detail.value;
    console.log(e);
    wx.request({
      url: '',
      data: formData,
      header: {
        'Content-Type': 'application/json'
      },
      success: function(res) {
        console.log(res.data);
      }
    })
  },
  onLoad: function(options) { // 页面初始化 options为页面跳转所带来的参数
    var that = this;
    var token = wx.getStorageSync('token');
    reg.isRegsited(token, (data) => {
      that.setData({
        isReg: data
      })
      console.log("isReg:" + that.data.isReg)
      if (that.data.isReg) {
        console.log("isReg")
        wx.reLaunch({
          url: '/pages/home/home'
        })
      }
      wx.reLaunch({
        url: '/pages/home/home'
      })
    });
  },
  onReady: function() { // 页面渲染完成  
  },
  onShow: function() { // 页面显示  
  },
  onHide: function() { // 页面隐藏  
  },
  onUnload: function() { // 页面关闭 
  }

});