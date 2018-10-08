//app.js
import { Token } from 'utils/token.js';

App({
  onLaunch: function () {
    console.log("app start!")
      var token = new Token();
      token.verify();
  },

  onShow:function(){
  
  },
})