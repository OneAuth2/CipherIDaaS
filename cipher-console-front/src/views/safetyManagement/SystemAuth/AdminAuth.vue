<template>
  <div class="admin-auth formed-wrap">
    <!-- 显示部分 -->
    <div class="formed-form"
         v-if="!isEdit">
      <div class="formed-form-part"
           v-if="showData.ident_auth">
        <h3 class="formed-form-part-title">身份认证</h3>
        <div class="formed-form-item">
          <span>认证方式：</span>
          <span>用户名+密码</span>
        </div>
        <div class="formed-form-item"
             v-if="showOtherAuthList.length">
          <span>其他认证方式：</span>
          <span>
            <span v-for="(item,index) in showOtherAuthList"
                  :key="index"
                  style="margin-right:16px">
              {{item}}
            </span>
          </span>
        </div>
        <div class="formed-form-item"
             v-if="showData.ident_auth.firstLogin===0">
          <span>首次登陆：</span>
          <span>首次登陆强制修改密码</span>
        </div>
      </div>
      <!-- 显示-绑定认证模块 -->
      <div class="formed-form-part"
           v-if="showData.acco_bind">
        <h3 class="formed-form-part-title">账号绑定</h3>
        <div class="formed-form-item">
          <span>绑定应用：</span>
          <span>
            <div v-if="showData.acco_bind.bindingAppWx===0">
              企业微信认证{{showData.acco_bind.autoBindingWx===0?" （已启用自动绑定）":""}}
            </div>
            <div v-if="showData.acco_bind.bindingAppDd===0">
              钉钉认证{{showData.acco_bind.autoBindingDd===0?" （已启用自动绑定）":""}}
            </div>
            <div v-if="showData.acco_bind.bindingAppDb===0">
              大白认证{{showData.acco_bind.autoBindingDb===0?" （已启用自动绑定）":""}}
            </div>
          </span>
        </div>
        <div class="formed-form-item">
          <span>手机验证：</span>
          <span>
            {{isOpenNumBindIcon.status?"已启用":"未启用"}}
            <Icon :class="isOpenNumBindIcon.class"
                  :type="isOpenNumBindIcon.type" />
          </span>
        </div>
        <div class="formed-form-item">
          <span>邮箱验证：</span>
          <span>
            {{isOpenMailBindIcon.status?"已启用":"未启用"}}
            <Icon :class="isOpenMailBindIcon.class"
                  :type="isOpenMailBindIcon.type" />
          </span>
        </div>
      </div>
    </div>

    <!-- 编辑部分 -->
    <Form :label-width="130"
          class="formed-form"
          v-else>
      <div class="formed-form-part">
        <h3 class="formed-form-part-title">身份认证</h3>
        <FormItem label="认证方式：">
          <span>用户名+密码</span>
        </FormItem>
        <FormItem label="其他认证方式：">
          <CheckboxGroup v-model=" formItem.otherAuth">
            <Checkbox v-for="(item,key) in otherAuthList"
                      :key="key"
                      :label="item.value">
              <span>{{item.label}}</span>
            </Checkbox>
          </CheckboxGroup>
        </FormItem>
        <FormItem label="首次登陆：">
          <Checkbox v-model="formItem.firstLogin">首次登录强制修改密码</Checkbox>
        </FormItem>
      </div>
      <div class="formed-form-part">
        <h3 class="formed-form-part-title">账号绑定</h3>
        <FormItem label="绑定应用：">
          <div>
            <Checkbox v-model="formItem.bindAppWx"
                      @on-change="formItem.bindAppWxAuto=false">企业微信认证</Checkbox>
            <Checkbox v-model="formItem.bindAppWxAuto"
                      :disabled="!formItem.bindAppWx"
                      class="auto">自动绑定（需要配置对接企业微信的接口参数）</Checkbox>
          </div>
          <div>
            <Checkbox v-model="formItem.bindAppDd"
                      @on-change="formItem.bindAppDdAuto=false">钉钉认证</Checkbox>
            <Checkbox v-model="formItem.bindAppDdAuto"
                      :disabled="!formItem.bindAppDd"
                      class="auto">自动绑定（需要配置对接钉钉的接口参数）</Checkbox>
          </div>
          <div>
            <Checkbox v-model="formItem.bindAppDb"
                      @on-change="formItem.bindAppDbAuto=false">大白认证</Checkbox>
            <Checkbox v-model="formItem.bindAppDbAuto"
                      :disabled="!formItem.bindAppDb"
                      class="auto">自动绑定（需要配置对接大白的接口参数）</Checkbox>
          </div>
        </FormItem>
        <FormItem label="手机验证：">
          <RadioGroup v-model="formItem.isOpenNumBind"
                      @on-change="changeisOpenNumBind">
            <Radio v-for="item in switchList"
                   :label="item.value"
                   :key="item.value">
              <span>{{item.label}}</span>
            </Radio>
          </RadioGroup>
        </FormItem>
        <FormItem label="邮箱验证：">
          <RadioGroup v-model="formItem.isOpenMailBind"
                      @on-change="changeisOpenMailBind">
            <Radio v-for="item in switchList"
                   :label="item.value"
                   :key="item.value">
              <span>{{item.label}}</span>
            </Radio>
          </RadioGroup>
        </FormItem>
      </div>
    </Form>
    <div class="formed-footer"
         v-if="isEdit">
      <myButton btnType="info"
                @click="handleSubmit()">保存</myButton>
      <myButton @click="cancel"
                style="margin-left: 8px">取消</myButton>
    </div>
  </div>
</template>

<script>
import icon from "@/util/icon.js";
export default {
  data () {
    return {
      isEdit: false, // 是否编辑状态
      showData: {}, // 显示页面所有数据
      showOtherAuthList: [], // 显示页面-身份认证-其他认证方式列表
      isOpenNumBindIcon: null, // 绑定-手机验证是否开启icon
      isOpenMailBindIcon: null, // 绑定-邮箱验证是否开启icon
      isShowBindApp: false, // 是否有绑定应用
      otherAuthList: [
        // {
        //   label: "赛赋扫码",
        //   value: 1
        // },
        {
          label: "钉钉扫码",
          value: 2
        },
        {
          label: "企业微信扫码",
          value: 6
        },
        {
          label: "大白扫码",
          value: 3
        },
        {
          label: "手机随机码",
          value: 4
        },
        {
          label: "动态密码",
          value: 5
        }
        // {
        //   label: "钉钉动态密码",
        //   value: 6
        // }
      ],
      switchList: [
        {
          label: "开启",
          value: 0
        },
        {
          label: "关闭",
          value: 1
        }
      ],
      formItem: {
        otherAuth: [],
        firstLogin: false,
        isOpenNumBind: null,
        isOpenMailBind: null,
        bindAppWx: false,
        bindAppWxAuto: false,
        bindAppDd: false,
        bindAppDdAuto: false,
        bindAppDb: false,
        bindAppDbAuto: false
      }
    };
  },
  mounted () {
    this.getAdminAuthData();
  },
  methods: {
    editStatus () {
      this.getAdminAuthData(); // 编辑重新获取数据
      this.isEdit = true;
      this.$emit("statusChange", {
        isEdit: this.isEdit,
        name: "admin"
      });
    },
    cancel () {
      this.isEdit = false;
      this.$emit("statusChange", {
        isEdit: this.isEdit,
        name: "admin"
      });
    },
    changeisOpenNumBind () {
      if (!this.formItem.isOpenNumBind === 0) {
        this.formItem.isOpenNumBindSkip = false;
      }
    },
    changeisOpenMailBind () {
      if (!this.formItem.isOpenMailBind === 0) {
        this.formItem.isOpenMailBindSkip = false;
      }
    },
    handleSubmit (name) {
      let otherAuth = {
        otherAuthSf: 1,
        otherAuthDd: 1,
        otherAuthDb: 1,
        otherAuthNum: 1,
        otherAuthDt: 1,
        otherAuthWx: 1
      };
      for (var i = 0; i < this.formItem.otherAuth.length; i++) {
        switch (this.formItem.otherAuth[i]) {
          // case 1:
          //   otherAuth.otherAuthSf = 0;
          //   break;
          case 2:
            otherAuth.otherAuthDd = 0;
            break;
          case 3:
            otherAuth.otherAuthDb = 0;
            break;
          case 4:
            otherAuth.otherAuthNum = 0;
            break;
          case 5:
            otherAuth.otherAuthDt = 0;
            break;
          case 6:
            otherAuth.otherAuthWx = 0;
            break;
        }
      }

      let params = {
        otherAuthWx: otherAuth.otherAuthWx,
        otherAuthDd: otherAuth.otherAuthDd,
        otherAuthDb: otherAuth.otherAuthDb,
        otherAuthNum: otherAuth.otherAuthNum,
        otherAuthDt: otherAuth.otherAuthDt,
        firstLogin: this.formItem.firstLogin ? 0 : 1,
        bindingAppWx: this.formItem.bindAppWx ? 0 : 1,
        autoBindingWx: this.formItem.bindAppWxAuto ? 0 : 1,
        bindingAppDd: this.formItem.bindAppDd ? 0 : 1,
        autoBindingDd: this.formItem.bindAppDdAuto ? 0 : 1,
        bindingAppDb: this.formItem.bindAppDb ? 0 : 1,
        autoBindingDb: this.formItem.bindAppDbAuto ? 0 : 1,
        isOpenMailBind: this.formItem.isOpenMailBind,
        isOpenNumBind: this.formItem.isOpenNumBind

      };
      this.axios({
        method: "post",
        data: params,
        url: "/cipher/setting/extManAuth"
      })
        .then(res => {
          if (res.data.return_code === 0) {
            this.$myMessage.success(res.data.return_msg);
            this.cancel();
            this.getAdminAuthData();
          } else {
            this.$myMessage.error(res.data.return_msg);
          }
        })
        .catch(function (error) {
          this.$Notice.warning({
            title: "网络未知错误！",
            desc: error
          });
        });
    },
    getAdminAuthData () {
      this.axios({
        method: "post",
        data: {},
        url: "/cipher/setting/extManAuthEcho"
      })
        .then(res => {
          if (res.data.return_code === 0) {
            this.showData = res.data.return_result;
            this.isOpenNumBindIcon = this.showData.acco_bind.isOpenNumBind === 0 ? icon.openIcon : icon.closeIcon; // 显示绑定-手机验证是否开启icon
            this.isOpenMailBindIcon = this.showData.acco_bind.isOpenMailBind === 0 ? icon.openIcon : icon.closeIcon; // 显示绑定-邮箱验证是否开启icon
            this.showOtherAuthList = [];
            this.formItem.otherAuth = [];
            // 身份认证-其他认证方式，根据返回数据判断放入数组显示
            // if (this.showData.ident_auth.otherAuthSf === 0) {
            //   this.showOtherAuthList.push("赛赋扫码");
            //   this.formItem.otherAuth.push(1);
            // }
            if (this.showData.ident_auth.otherAuthDd === 0) {
              this.showOtherAuthList.push("钉钉扫码");
              this.formItem.otherAuth.push(2);
            }
            if (this.showData.ident_auth.otherAuthDb === 0) {
              this.showOtherAuthList.push("大白扫码");
              this.formItem.otherAuth.push(3);
            }
            if (this.showData.ident_auth.otherAuthNum === 0) {
              this.showOtherAuthList.push("手机随机码");
              this.formItem.otherAuth.push(4);
            }
            if (this.showData.ident_auth.otherAuthDt === 0) {
              this.showOtherAuthList.push("动态密码");
              this.formItem.otherAuth.push(5);
            }
            if (this.showData.ident_auth.otherAuthWx === 0) {
              this.showOtherAuthList.push("企业微信扫码");
              this.formItem.otherAuth.push(6);
            }

            this.formItem.firstLogin = this.showData.ident_auth.firstLogin === 0; // 身份认证-第一次登陆是否强制修改密码
            this.formItem.isOpenNumBind = this.showData.acco_bind.isOpenNumBind; // 账号绑定时手机验证
            this.formItem.isOpenMailBind = this.showData.acco_bind.isOpenMailBind; // 账号绑定时邮箱验证
            this.isShowBindApp = this.showData.acco_bind.bindingAppSf === 0 || this.showData.acco_bind.bindingAppDd === 0 || this.showData.acco_bind.BindingDb === 0;
            this.formItem.bindAppWx = this.showData.acco_bind.bindingAppWx === 0; // 账号绑定 企业维信认证应用
            this.formItem.bindAppWxAuto = this.showData.acco_bind.autoBindingWx === 0; // 账号绑定 企业维信应用是否自动绑定
            this.formItem.bindAppDd = this.showData.acco_bind.bindingAppDd === 0; // 账号绑定 钉钉认证应用
            this.formItem.bindAppDdAuto = this.showData.acco_bind.autoBindingDd === 0;// 账号绑定 钉钉认证应用是否自动绑定
            this.formItem.bindAppDb = this.showData.acco_bind.bindingAppDb === 0; // 账号绑定 大白认证应用
            this.formItem.bindAppDbAuto = this.showData.acco_bind.autoBindingDb === 0;// 账号绑定 大白认证应用是否自动绑定
          } else {
            this.$myMessage.error(res.data.return_msg);
          }
        })
        .catch(function (error) {
          this.$Notice.warning({
            title: "网络未知错误！",
            desc: error
          });
        });
    }
  }
};
</script>

<style lang="less" scoped>
@import "~@/assets/styles/formStyle.less";
</style>
