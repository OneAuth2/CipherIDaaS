<template>
  <div class="portal-auth formed-wrap">
    <!-- 显示部分 -->
    <div class="formed-form"
         v-if="!isEdit">
      <!-- 显示-身份认证模块 -->
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
        <div class="formed-form-item"
             v-if="showData.ident_auth.infoCollection===0|| showData.ident_auth.infoCollectionMail===0">
          <span>信息采集：</span>
          <span>
            <div v-if="showData.ident_auth.infoCollection===0">强制录入手机号</div>
            <div v-if="showData.ident_auth.infoCollectionMail===0">强制录入邮箱</div>
          </span>
        </div>
      </div>
      <!-- 显示-注册认证模块 -->
      <div class="formed-form-part"
           v-if="showData.acco_regi">
        <h3 class="formed-form-part-title">账号注册</h3>
        <div class="formed-form-item">
          <span>是否开启注册：</span>
          <span>
            <span>
              {{isOpenRegisterIcon.status?"已启用":"未启用"}}
              <Icon :class="isOpenRegisterIcon.class"
                    :type="isOpenRegisterIcon.type" />
            </span>
          </span>
        </div>
        <div class="formed-form-item"
             v-if="isOpenRegisterIcon.status">
          <span>手机验证：</span>
          <span>
            {{isOpenNumRegIcon.status?showData.acco_regi.isSkipNumReg===0?"已启用  （可跳过）":"已启用":"未启用"}}
            <Icon :class="isOpenNumRegIcon.class"
                  :type="isOpenNumRegIcon.type" />
          </span>
        </div>
        <div class="formed-form-item"
             v-if="isOpenRegisterIcon.status">
          <span>邮箱验证：</span>
          <span>
            {{isOpenMailRegIcon.status?showData.acco_regi.isSkipMailReg===0?"已启用 （可跳过）":"已启用":"未启用"}}
            <Icon :class="isOpenMailRegIcon.class"
                  :type="isOpenMailRegIcon.type" />
          </span>
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
      <!-- 显示-忘记密码认证模块 -->
      <div class="formed-form-part"
           v-if="showData.forg_pass">
        <h3 class="formed-form-part-title">忘记密码</h3>
        <div class="formed-form-item">
          <span>是否开启忘记密码：</span>
          <span>
            {{isOpenForgetPwdIcon.status?"已启用":"未启用"}}
            <Icon :class="isOpenForgetPwdIcon.class"
                  :type="isOpenForgetPwdIcon.type" />
          </span>
        </div>
        <div class="formed-form-item"
             v-if="showData.forg_pass.isOpenForgetPwd===0">
          <span>手机验证：</span>
          <span>
            {{isOpenNumForgetIcon.status?"已启用":"未启用"}}
            <Icon :class="isOpenNumForgetIcon.class"
                  :type="isOpenNumForgetIcon.type" />
          </span>
        </div>
        <div class="formed-form-item"
             v-if="showData.forg_pass.isOpenForgetPwd===0">
          <span>邮箱验证：</span>
          <span>
            {{isOpenMailForgetIcon.status?"已启用":"未启用"}}
            <Icon :class="isOpenMailForgetIcon.class"
                  :type="isOpenMailForgetIcon.type" />
          </span>
        </div>
        <div class="formed-form-item"
             v-if="showSecondAuth.length>0">
          <span>二次认证：</span>
          <span>
            <span v-for="(item,index) in showSecondAuth"
                  :key="index"
                  style="margin-right:16px">{{item}}</span>
          </span>
        </div>
      </div>
    </div>

    <!-- 编辑部分 -->
    <Form :label-width="140"
          class="formed-form"
          v-else
          ref="portalVal">
      <!-- 编辑：身份认证 -->
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
        <FormItem label="信息采集：">
          <div>
            <Checkbox v-model="formItem.infoCollection">强制录入手机号</Checkbox>
          </div>
          <div>
            <Checkbox v-model="formItem.infoCollectionMail">强制录入邮箱</Checkbox>
          </div>
        </FormItem>
      </div>
      <!-- 编辑：注册认证 -->
      <div class="formed-form-part">
        <h3 class="formed-form-part-title">账号注册</h3>
        <FormItem label="是否开启注册：">
          <RadioGroup v-model="formItem.isOpenRegister">
            <Radio v-for="item in switchList"
                   :label="item.value"
                   :key="item.value">
              <span>{{item.label}}</span>
            </Radio>
          </RadioGroup>
        </FormItem>
        <template v-if="formItem.isOpenRegister === 0">
          <FormItem label="手机验证："
                    class="skip-model">
            <RadioGroup v-model="formItem.isOpenNumReg"
                        @on-change="changeisOpenNumReg">
              <Radio v-for="item in switchList"
                     :label="item.value"
                     :key="item.value">
                <span>{{item.label}}</span>
              </Radio>
            </RadioGroup>
            <Checkbox v-model="formItem.isSkipNumReg"
                      :disabled="formItem.isOpenNumReg===1">手机验证可跳过</Checkbox>
          </FormItem>
          <FormItem label="邮箱验证：">
            <RadioGroup v-model="formItem.isOpenMailReg"
                        @on-change="changeisOpenMailReg">
              <Radio v-for="item in switchList"
                     :label="item.value"
                     :key="item.value">
                <span>{{item.label}}</span>
              </Radio>
            </RadioGroup>
            <Checkbox v-model="formItem.isSkipMailReg"
                      :disabled="formItem.isOpenMailReg===1">邮箱验证可跳过</Checkbox>
          </FormItem>
        </template>
      </div>
      <!-- 编辑：绑定认证 -->
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
          <RadioGroup v-model="formItem.isOpenNumBind">
            <Radio v-for="item in switchList"
                   :label="item.value"
                   :key="item.value">
              <span>{{item.label}}</span>
            </Radio>
          </RadioGroup>
        </FormItem>
        <FormItem label="邮箱验证：">
          <RadioGroup v-model="formItem.isOpenMailBind">
            <Radio v-for="item in switchList"
                   :label="item.value"
                   :key="item.value">
              <span>{{item.label}}</span>
            </Radio>
          </RadioGroup>
        </FormItem>
      </div>
      <!-- 编辑：忘记密码认证 -->
      <div class="formed-form-part">
        <h3 class="formed-form-part-title">忘记密码：</h3>
        <FormItem label="是否开启忘记密码：">
          <RadioGroup v-model="formItem.isOpenForgetPwd">
            <Radio v-for="item in switchList"
                   :label="item.value"
                   :key="item.value">
              <span>{{item.label}}</span>
            </Radio>
          </RadioGroup>
        </FormItem>
        <template v-if="formItem.isOpenForgetPwd === 0">
          <FormItem label="手机验证：">
            <RadioGroup v-model="formItem.isOpenNumForget">
              <Radio v-for="item in switchList"
                     :label="item.value"
                     :key="item.value">
                <span>{{item.label}}</span>
              </Radio>
            </RadioGroup>
          </FormItem>
          <FormItem label="邮箱验证：">
            <RadioGroup v-model="formItem.isOpenMailForget">
              <Radio v-for="item in switchList"
                     :label="item.value"
                     :key="item.value">
                <span>{{item.label}}</span>
              </Radio>
            </RadioGroup>
          </FormItem>
          <FormItem label="二次认证：">
            <CheckboxGroup v-model=" formItem.secondAuth">
              <Checkbox v-for="(item,key) in secondAuthList"
                        :key="key"
                        :label="item.value">
                <span>{{item.label}}</span>
              </Checkbox>
            </CheckboxGroup>
          </FormItem>
        </template>
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
      isOpenRegisterIcon: null, // 注册是否开启icon
      isOpenNumRegIcon: null, // 注册-手机验证是否开启icon
      isOpenMailRegIcon: null, // 注册-邮箱验证是否开启icon
      isOpenNumBindIcon: null, // 绑定-手机验证是否开启icon
      isOpenMailBindIcon: null, // 绑定-邮箱验证是否开启icon
      isOpenForgetPwdIcon: null, // 忘记密码是否开启icon
      isOpenNumForgetIcon: null, // 忘记密码手-机验证是否开启icon
      isOpenMailForgetIcon: null, // 忘记密码-手机验证是否开启icon
      isShowBindApp: false, // 是否有绑定应用
      showSecondAuth: [], // 显示页码-忘记密码-二次认证方式
      otherAuthList: [ // 编辑页面-身份认证-二次认证方式checkbox值
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
      ],
      switchList: [ // 编辑页-1关闭radio值
        {
          label: "开启",
          value: 0
        },
        {
          label: "关闭",
          value: 1
        }
      ],
      secondAuthList: [ // 编辑页-忘记密码二次认证
        {
          label: "企业微信扫码",
          value: 1
        },
        {
          label: "钉钉扫码",
          value: 2
        },
        {
          label: "大白扫码",
          value: 3
        }
      ],
      formItem: { // 编辑页
        otherAuth: [], // 身份认证-其他认证方式
        firstLogin: false, // 第一次登陆是否强制修改密码
        infoCollection: false, // 信息录入，强制录入手机
        infoCollectionMail: false, // 信息录入，强制录入邮箱
        isOpenRegister: 0, // 是否开启注册
        isOpenNumReg: null, // 注册手机验证
        isSkipNumReg: false, // 是否可跳过注册手机验证
        isOpenMailReg: null, // 注册邮箱验证
        isSkipMailReg: false, // 是否可跳过注册邮箱验证
        isOpenNumBind: null, // 账号绑定时手机验证
        isOpenMailBind: null, // 账号绑定时邮箱验证
        // bindAppSf: false, // 账号绑定 赛赋认证应用
        // bindAppSfAuto: false, // 账号绑定 赛赋认证应用是否自动绑定
        bindAppWx: false, // 账号绑定 企业微信应用
        bindAppWxAuto: false, // 账号绑定 企业微信应用是否自动绑定
        bindAppDd: false, // 账号绑定 钉钉认证应用
        bindAppDdAuto: false, // 账号绑定 钉钉认证应用是否自动绑定
        bindAppDb: false, // 账号绑定 大白认证应用
        bindAppDbAuto: false, // 账号绑定 大白认证应用是否自动绑定
        isOpenForgetPwd: 0, // 是否开启忘记密码
        isOpenNumForget: null, // 忘记密码手机验证
        isOpenMailForget: null, // 忘记密码邮箱验证
        secondAuth: [], // 忘记密码-二次认证方式
        isSave: false
      }
    };
  },
  mounted () {
    this.getPortalAuthData();
  },
  methods: {
    editStatus () {
      this.getPortalAuthData(); // 重新获取数据
      this.isEdit = true;
      this.$emit("statusChange", {
        isEdit: this.isEdit,
        name: "portal"
      });
    },
    cancel () {
      this.isEdit = false;
      this.$emit("statusChange", {
        isEdit: this.isEdit,
        name: "portal"
      });
    },
    changeisOpenNumReg () {
      if (this.formItem.isOpenNumReg === 1) {
        this.formItem.isSkipNumReg = false;
      }
    },
    changeisOpenMailReg () {
      if (this.formItem.isOpenMailReg === 1) {
        this.formItem.isSkipMailReg = false;
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
      let secondAuth = {
        twoAuthForgetWx: 1,
        twoAuthForgetDd: 1,
        twoAuthForgetDb: 1
      };
      for (let i = 0; i < this.formItem.otherAuth.length; i++) {
        switch (this.formItem.otherAuth[i]) {
          case 1:
            otherAuth.otherAuthSf = 0;
            break;
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
      for (let i = 0; i < this.formItem.secondAuth.length; i++) {
        switch (this.formItem.secondAuth[i]) {
          case 1:
            secondAuth.twoAuthForgetWx = 0;
            break;
          case 2:
            secondAuth.twoAuthForgetDd = 0;
            break;
          case 3:
            secondAuth.twoAuthForgetDb = 0;
            break;
        }
      }

      let params = {
        otherAuthSf: 1,
        otherAuthDd: otherAuth.otherAuthDd,
        otherAuthDb: otherAuth.otherAuthDb,
        otherAuthNum: otherAuth.otherAuthNum,
        otherAuthDt: otherAuth.otherAuthDt,
        otherAuthWx: otherAuth.otherAuthWx,
        firstLogin: this.formItem.firstLogin ? 0 : 1,
        infoCollection: this.formItem.infoCollection ? 0 : 1,
        infoCollectionMail: this.formItem.infoCollectionMail ? 0 : 1,
        isOpenRegister: this.formItem.isOpenRegister,
        isOpenMailReg: this.formItem.isOpenMailReg,
        isSkipMailReg: this.formItem.isSkipMailReg ? 0 : 1,
        isOpenNumReg: this.formItem.isOpenNumReg,
        isSkipNumReg: this.formItem.isSkipNumReg ? 0 : 1,
        // bindingAppSf: this.formItem.bindAppSf ? 0 : 1,
        // autoBindingSf: this.formItem.bindAppSfAuto ? 0 : 1,
        bindingAppWx: this.formItem.bindAppWx ? 0 : 1,
        autoBindingWx: this.formItem.bindAppWxAuto ? 0 : 1,
        bindingAppDd: this.formItem.bindAppDd ? 0 : 1,
        autoBindingDd: this.formItem.bindAppDdAuto ? 0 : 1,
        bindingAppDb: this.formItem.bindAppDb ? 0 : 1,
        autoBindingDb: this.formItem.bindAppDbAuto ? 0 : 1,
        isOpenMailBind: this.formItem.isOpenMailBind,
        isOpenNumBind: this.formItem.isOpenNumBind,
        isOpenForgetPwd: this.formItem.isOpenForgetPwd,
        isOpenMailForget: this.formItem.isOpenMailForget,
        isOpenNumForget: this.formItem.isOpenNumForget,
        //  twoAuthForgetSf: secondAuth.twoAuthForgetSf,
        twoAuthForgetWx: secondAuth.twoAuthForgetWx,
        twoAuthForgetDd: secondAuth.twoAuthForgetDd,
        twoAuthForgetDb: secondAuth.twoAuthForgetDb
      };
      this.axios({
        method: "post",
        data: params,
        url: "/cipher/setting/doorAuth"
      })
        .then(res => {
          if (res.data.return_code === 0) {
            this.$myMessage.success(res.data.return_msg);
            this.cancel();
            this.getPortalAuthData();
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
    getPortalAuthData () {
      this.axios({
        method: "post",
        data: {},
        url: "/cipher/setting/doorAuthEcho"
      })
        .then(res => {
          if (res.data.return_code === 0) {
            this.showData = res.data.return_result;
            this.isOpenRegisterIcon = this.showData.acco_regi.isOpenRegister === 0 ? icon.openIcon : icon.closeIcon; // 显示注册是否开启icon
            this.isOpenNumRegIcon = this.showData.acco_regi.isOpenNumReg === 0 ? icon.openIcon : icon.closeIcon; // 显示注册-手机验证是否开启icon
            this.isOpenMailRegIcon = this.showData.acco_regi.isOpenMailReg === 0 ? icon.openIcon : icon.closeIcon; // 显示注册-邮箱验证是否开启icon
            this.isOpenNumBindIcon = this.showData.acco_bind.isOpenNumBind === 0 ? icon.openIcon : icon.closeIcon; // 显示绑定-手机验证是否开启icon
            this.isOpenMailBindIcon = this.showData.acco_bind.isOpenMailBind === 0 ? icon.openIcon : icon.closeIcon; // 显示绑定-邮箱验证是否开启icon
            this.isOpenForgetPwdIcon = this.showData.forg_pass.isOpenForgetPwd === 0 ? icon.openIcon : icon.closeIcon; // 忘记密码是否开启开启icon
            this.isOpenNumForgetIcon = this.showData.forg_pass.isOpenNumForget === 0 ? icon.openIcon : icon.closeIcon; // 忘记密码-邮手机验证是否开启icon
            this.isOpenMailForgetIcon = this.showData.forg_pass.isOpenMailForget === 0 ? icon.openIcon : icon.closeIcon; // 忘记密码-邮箱验证是否开启icon
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
            if (this.showData.ident_auth.otherAuthWx === 0) {
              this.showOtherAuthList.push("企业微信扫码");
              this.formItem.otherAuth.push(6);
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
            // if (this.showData.ident_auth.otherAuthDingDt === 0) {
            //   this.showOtherAuthList.push("钉钉动态密码");
            //   this.formItem.otherAuth.push(6);
            // }

            this.formItem.firstLogin = this.showData.ident_auth.firstLogin === 0; // 身份认证-第一次登陆是否强制修改密码
            this.formItem.infoCollection = this.showData.ident_auth.infoCollection === 0; // 身份认证-信息收集是否强制录入手机
            this.formItem.infoCollectionMail = this.showData.ident_auth.infoCollectionMail === 0; // 身份认证-信息收集是否强制录入邮箱
            this.formItem.isOpenRegister = this.showData.acco_regi.isOpenRegister; // 注册-是否开启注册
            this.formItem.isOpenNumReg = this.showData.acco_regi.isOpenNumReg; // 注册-手机验证
            this.formItem.isSkipNumReg = this.showData.acco_regi.isSkipNumReg === 0; // 注册-手机验证是否可跳过
            this.formItem.isOpenMailReg = this.showData.acco_regi.isOpenMailReg; // 注册-邮箱验证
            this.formItem.isSkipMailReg = this.showData.acco_regi.isSkipMailReg === 0; // 注册-邮箱验证是否可跳过
            this.formItem.isOpenNumBind = this.showData.acco_bind.isOpenNumBind; // 账号绑定时手机验证
            this.formItem.isOpenMailBind = this.showData.acco_bind.isOpenMailBind; // 账号绑定时邮箱验证
            this.isShowBindApp = this.showData.acco_bind.bindingAppSf === 0 || this.showData.acco_bind.bindingAppDd === 0 || this.showData.acco_bind.bindingAppDb === 0;
            this.formItem.bindAppSf = this.showData.acco_bind.bindingAppSf === 0; // 账号绑定 赛赋认证应用
            this.formItem.bindAppSfAuto = this.showData.acco_bind.autoBindingSf === 0; // 账号绑定 赛赋认证应用是否自动绑定
            this.formItem.bindAppDd = this.showData.acco_bind.bindingAppDd === 0; // 账号绑定 钉钉认证应用
            this.formItem.bindAppDdAuto = this.showData.acco_bind.autoBindingDd === 0;// 账号绑定 钉钉认证应用是否自动绑定
            this.formItem.bindAppDb = this.showData.acco_bind.bindingAppDb === 0; // 账号绑定 大白认证应用
            this.formItem.bindAppDbAuto = this.showData.acco_bind.autoBindingDb === 0;// 账号绑定 大白认证应用是否自动绑定
            this.formItem.isOpenForgetPwd = this.showData.forg_pass.isOpenForgetPwd; // 忘记密码-是否开启忘记密码
            this.formItem.isOpenNumForget = this.showData.forg_pass.isOpenNumForget; // 忘记密码手机验证
            this.formItem.isOpenMailForget = this.showData.forg_pass.isOpenMailForget;// 忘记密码邮箱验证

            this.showSecondAuth = [];
            this.formItem.secondAuth = [];
            // 忘记-二次认证方式，根据返回数据判断放入数组显示
            if (this.showData.forg_pass.twoAuthForgetWx === 0) {
              this.showSecondAuth.push("企业微信扫码");
              this.formItem.secondAuth.push(1);
            }
            if (this.showData.forg_pass.twoAuthForgetDd === 0) {
              this.showSecondAuth.push("钉钉扫码");
              this.formItem.secondAuth.push(2);
            }
            if (this.showData.forg_pass.twoAuthForgetDb === 0) {
              this.showSecondAuth.push("大白扫码");
              this.formItem.secondAuth.push(3);
            }
          } else {
            this.$myMessage.error(res.data.return_msg === 0);
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
