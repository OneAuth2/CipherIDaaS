let app = getApp();

let url = app.globalData.url;
Page({
  data: {
    corpId: '',
    authCode: '',
    userId: '',
    userName: '　　　　　　　　',
    indexImg: 'index1.png'
  },
  onLoad() {

    let _this = this;

    this.setData({
      corpId: app.globalData.corpId
    })
  },

  onShow() {
    dd.getAuthCode({
      success: (res) => {
        this.setData({
          authCode: res.authCode
        });
        //dd.alert({content: "step1"});
        dd.httpRequest({
          url: url + '/login',
          method: 'POST',
          data: {
            authCode: res.authCode
          },
          dataType: 'json',
          success: (res) => {
            // dd.alert({content: "step2"});
            console.log('success----', res);
            let userId = res.data.result.userId;
            let userName = res.data.result.userName;
            dd.alert(userName)
            this.setData({
              userId: userId,
              userName: userName
            })
          },
          fail: (res) => {
            console.log("httpRequestFail---", res);
            dd.alert({ content: JSON.stringify(res) });
          },
          complete: (res) => {
            dd.hideLoading();
          }

        });
      },
      fail: (err) => {
        // dd.alert({content: "step3"});
        dd.alert({
          content: JSON.stringify(err)
        })
      }
    })

  },
  
   onSubmit() {
    //location.href="http://portal.cipherchina.cn:9999/cipher/getSsoLoginUrl?param=2FB65AF25967AA098B231FA8672DE04E8F1945E96AC378D5CA0E04486A80A87E8249BE595BC60F08C7289C5B5280023A0942A7E717FFC93B9FCA8A9A9FD7C50301D446DC160A117225705C9102EE7708B11FD79EA03F982E77C674B9B8ECB90379CF9C6F0C0DFD4336E1910BACE1732800C3892145FED11458805E35BF51DC65&clientId=bNIL3t";
    dd.navigateTo({           // 保留当前页面，跳转到应用内的某个指定页面
  url: 'http://portal.cipherchina.cn:9999/cipher/getSsoLoginUrl?param=2FB65AF25967AA098B231FA8672DE04E8F1945E96AC378D5CA0E04486A80A87E8249BE595BC60F08C7289C5B5280023A0942A7E717FFC93B9FCA8A9A9FD7C50301D446DC160A117225705C9102EE7708B11FD79EA03F982E77C674B9B8ECB90379CF9C6F0C0DFD4336E1910BACE1732800C3892145FED11458805E35BF51DC65&clientId=bNIL3t'
})
  },
});
