let app = getApp();
//替换成开发者后台设置的安全域名
let url = app.globalData.url;
Page({
  data: {
    title: '　　　　　　　　',
    cancel: '关闭',
    disabled: true,
    push1: 'push1.png',
    push2: 'push2.png',
    push3: 'push3.png',
    deviceName: '未知',
    ip: '未知',
    address: '未知',
    loginTime: '未知',
    loginDate: '未知',
    userId: '',
    userName: '',
    corpId: '',
    timeStamp: 1569208450000
  },
  onLoad(query) {
    this.setData({
      corpId: app.globalData.corpId
    })
    // 页面加载
    this.setData({
      deviceName: query.deviceName,
      ip: query.ip,
      address: query.address,
      loginTime: query.loginTime,
      loginDate: query.loginDate,
      timeStamp: query.timeStamp
    })



  },
  onShow() {
    dd.getAuthCode({
      success: (res) => {
        this.setData({
          authCode: res.authCode
        })
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
            console.log('success----', res)
            let userId = res.data.result.userId;
            let userName = res.data.result.userName;
            this.setData({
              userId: userId,
              userName: userName,
            })
            this.changeBtn()
          },
          fail: (res) => {
            console.log("httpRequestFail---", res)
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
  changeBtn() {
    var _this = this
    console.log(Date.parse(new Date()))
    dd.httpRequest({
      url: url + '/push/changeBtn',
      method: 'POST',
      data: {
        timeStamp: _this.data.timeStamp,
        userId: _this.data.userId
      },
      dataType: 'json',
      success: function(res) {
        if (res.data.code === 1) {
          _this.setData({
            title: '登录请求已超时',
            cancel: '关闭',
            disabled: true
          })
          dd.alert({ content: JSON.stringify(res.data.msg) })
        } else if (res.data.code === 2) {
          _this.setData({
            title: '已选择',
            cancel: '关闭',
            disabled: true
          })
        } else if (res.data.code === 3) {
          _this.setData({
            title: '登录请求待确认',
            cancel: '拒绝',
            disabled: false
          })
          _this.changeBtn()
        }
      },
      fail: function(res) {
        dd.alert({ content: JSON.stringify(res) });
      },
      complete: function(res) {
        dd.hideLoading();
      }
    });
  },
  onSubmit() {
    var data = {
      corpId: app.globalData.corpId,
      userId: this.data.userId,
      result: true,
      timeStamp: this.data.timeStamp
    }
    this.agreeOrNo(data)
  },
  onReset() {
    if (this.data.cancel === '拒绝') {
      var data = {
        corpId: app.globalData.corpId,
        userId: this.data.userId,
        result: false,
        timeStamp: this.data.timeStamp
      }
      this.agreeOrNo(data)
    } else if (this.data.cancel === '关闭') {
      dd.redirectTo({ url: '../index/index' })
    }
  },
  agreeOrNo(data) {
    dd.httpRequest({
      url: url + '/push/result',
      method: 'POST',
      data: data,
      dataType: 'json',
      success: function(res) {
        // dd.alert({ content: JSON.stringify(res.data.return_msg) });
        dd.redirectTo({ url: '../index/index' })
      },
      fail: function(res) {
        dd.alert({ content: JSON.stringify(res) });
      },
      complete: function(res) {
        dd.hideLoading();
      }
    });
  }
});
