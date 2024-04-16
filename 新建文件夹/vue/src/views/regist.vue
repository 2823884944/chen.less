//用户注册界面
<template>
  <div class="login">
    <Row justify="center" align="middle" @keydown.enter.native="submitRegist" style="height:100%">
      <div class="loginUp">
        <div class="loginLeft">
          <img src="../assets/login/tubiao.png" alt="" srcset="">
          <span class="line"></span>
          <span class="title">教评系统</span>
        </div>
      </div>
      <div class="loginMiddle">
          <div class="loginRight">
            <Row v-if="!socialLogining" class="loginRow">
              <Tabs v-model="tabName" @on-click="changeTabName" class="loginTab">
                <TabPane label="用户注册" name="userAndPassword">
                  <Form ref="usernameLoginForm" :model="form" :rules="rules" class="form" style="width: 100%">
                    <FormItem prop="username" class="loginInput">
                      <Row>
                        <Input v-model="form.username" size="large" clearable placeholder="手机号" autocomplete="off">
                          <Icon class="iconfont icon-yonghu" slot="prefix" style="line-height:50px" />
                        </Input>
                      </Row>
                    </FormItem>
                    <FormItem prop="nickname" class="loginInput">
                      <Row>
                        <Input v-model="form.nickname" size="large" clearable placeholder="用户名" autocomplete="off">
                          <Icon class="iconfont icon-yonghu" slot="prefix" style="line-height:50px" />
                        </Input>
                      </Row>
                    </FormItem>
                    <FormItem prop="password">
                      <Input style="height:50px;line-height:50px" type="password" v-model="form.password" size="large" placeholder="用户密码" password autocomplete="off">
                        <Icon class="iconfont icon-mima1" slot="prefix" style="line-height:50px" />
                      </Input>
                    </FormItem>
                    <FormItem prop="code">
                      <Row type="flex" justify="space-between" style="align-items: center;overflow: hidden;">
                        <Col span="16">
                          <Input v-model="form.code" size="large" clearable placeholder="输入图片验证码" style="width:90%" :maxlength="10" class="input-verify" />
                        </Col>
                        <Col span="8">
                          <div class="code-image" style="position:relative;font-size:12px;width:100%">
                            <Spin v-if="loadingCaptcha" fix></Spin>
                            <img :src="captchaImg" @click="getCaptchaImg" alt="加载验证码失败" style="width:110px;cursor:pointer;display:block" />
                          </div>
                        </Col>
                        <Button class="login-btn" type="primary" size="large" :loading="loading" @click="submitRegist" long>
                          <span v-if="!loading" style="letter-spacing:20px; font-weight:bold">注册</span>
                          <span v-else>正在注册</span>
                        </Button>
                        <router-link to="/login">
                          <Button class="login-btn" type="primary" size="large" long>返回登录</Button>
                        </router-link>
                      </Row>
                    </FormItem>
                  </Form>
                 </TabPane>
              </Tabs>
            </Row>
            <div v-if="socialLogining">
              <RectLoading />
            </div>
          </div>
      </div>
      <div class="loginDown">
        <p style="margin-top:10px">帮助 | 隐私 | 条款</p>
        <p>
          <span>Copyright © 2024 - 至今 </span>
          <span style="display:inline-block; width:4px;height:6px"></span>
        </p>
      </div>
    </Row>
  </div>
</template>

<script>
import {
  regist,
  drawCodeImage,
  initCaptcha
} from "@/api/index";
import {
  validateMobile,
  validatePassword
} from "@/libs/validate";
export default {
  components: {
  },
  data() {
    return {
      captchaId: "",
      captchaImg: "",
      error: false,
      loading: false,
      errorCode: "",
      form: {
        username: "",
        password: "",
        mobile: "",
        code: "",
        captchaId: ""
      },
      rules: {
        username: [{
          required: true,
          message: "请输入手机号",
          trigger: "blur"
        },
          {
            validator: validateMobile,
            trigger: "blur"
          }
        ],
        nickname: [{
          required: true,
          message: "请输入用户名",
          trigger: "blur"
        }],
        password: [{
          required: true,
          message: "请输入登陆密码",
          trigger: "blur"
        },
          {
            validator: validatePassword,
            trigger: "blur"
          }
        ]
      }
    };
  },
  methods: {
    getCaptchaImg() {
      this.loadingCaptcha = true;
      initCaptcha().then(res => {
        this.loadingCaptcha = false;
        if (res.success) {
          this.captchaId = res.result;
          this.captchaImg = drawCodeImage + this.captchaId;
        }
      });
    },
    submitRegist() {
      this.form.captchaId = this.captchaId;
      this.form.mobile = this.form.username;
      this.$refs.usernameLoginForm.validate(valid => {
        if (valid) {
          if (!this.form.code) {
            this.errorCode = "验证码不能为空";
            return;
          } else {
            this.errorCode = "";
          }
          this.loading = true;
          regist(this.form).then(res => {
            this.loading = false;
            if (res.success) {
              this.$router.push({
                name: "login"
              });
            }
          });
        }
      });
    }
  },
  mounted() {
    this.getCaptchaImg();
  }
};
</script>

<style lang="less">
html,body{
  font-family: Microsoft YaHei;
  font-weight: 400;;
}
a{
  font-family: Microsoft YaHei;
  color: #ed3f14;
}
input::-webkit-input-placeholder {
  font-size: 14px;
}
.ivu-checkbox-wrapper.ivu-checkbox-large{
  font-size: 14px;
}
a:hover{
  font-family: Microsoft YaHei;
  color:#ed3f14;
}


.login {
  height: 100%;
  background-color: #ffffff;

  .ivu-tabs-nav-container {
    line-height: 2;
    font-size: 17px;
    box-sizing: border-box;
    white-space: nowrap;
    overflow: hidden;
    position: relative;
    zoom: 1;
  }
  .loginUp{
    width: 100%;
    height: 70px;
    background-color:#ffffff;
    margin-left: 0;

  }
  .loginLeft{
    margin-top: 20px;
    height: 50px;
    display: flex;
  }
  .line {
    display: inline-block;
    width: 2px;
    height: 25px;
    background: url(../assets/login/line.png);
    margin: 0px 10px;
    margin-top: 15px;
  }

  .title{
    line-height: 58px;
    font-size: 18px;
    font-family: Microsoft YaHei;
    font-weight: 500;
    color: #444444;
  }
  .loginMiddle{
    width: 100%;
    height: 780px;
    margin: 0 auto;
    background-image: url(../assets/login/bg2.jpg);
    overflow: hidden;
  }
  .loginRight{
    width: 450px;
    height: 550px;
    background-color: rgba(255,255,255,.4);
    border: 1px solid #619fe7;
    box-shadow: 0 2px 15px 1px rgba(0, 0, 0, 0.1);
    border-radius: 9px;
    margin-top: 115px;
    position: relative;
    margin-left:700px;

  }
  .loginRow{
    padding: 0 30px;
  }
  .loginDown{
    width: 1200px;
    height: auto;
    margin: 0 auto;

  }
  .loginTab{
    margin-top: auto;
  }
  .ivu-tabs-tab{
    color: #333333;
    font-size: 18px;
    font-family: Microsoft YaHei;
    font-weight: bold;
  }
  .ivu-tabs-nav .ivu-tabs-tab{
    padding: 8px 42px;
    margin-right: 0;
  }
  .ivu-tabs-ink-bar{
    height: 4px;
    width: 86px !important;
    border-radius: 2px;
    margin: 0 42px;
    background-color: #83C9C7;
  }
  .ivu-tabs-nav .ivu-tabs-tab-active,.ivu-tabs-nav .ivu-tabs-tab:hover{
    color: #333333;
  }
  .loginInput{
    font-size: 18px;
    font-family: Microsoft YaHei;
    font-weight: bold;
    color: #333333;
  }

  .ivu-tabs-bar{
    border-bottom: 0;
  }
  .login-btn{
    width: 390px;
    height: 50px;
    background: radial-gradient(circle, #77C8C6, #50C7C4);
    border: 2px solid #61C8C5;
    box-shadow: 0 2px 6px 0 rgba(0, 0, 0, 0.21);
    border-radius: 4px;
  }
  .login .login-btn, .login .other-login{
    margin-top: 0;
  }
  .loginBottom{
    width: 448px;
    height: 60px;
    background:  #9ea7b4;
    border-radius: 0px 0px 5px 5px;
    padding: 0px;
    position: absolute;
    bottom: 0px;
    font-size: 16px;
    font-weight: bold;
    color: #f9f9f9;
    text-align: center;
    line-height: 60px;
  }
  .loginDown p{
    text-align: center;
    font-size: 12px;
    font-family: Microsoft YaHei;
    color: #777777;
    line-height: 22px;
  }
  .ivu-checkbox-checked .ivu-checkbox-inner{
    background-color: #83c9c7;
    border-color: #83c9c7;
  }
  .ivu-form-item{
    margin-bottom: 18px;
  }
  .ivu-input-wrapper-large .ivu-input-icon{
    line-height: 50px;
  }
  .loginInput input:nth-of-type(1){
    height: 50px;
    font-size: 18px;
    font-weight: bold;
    font-family: Microsoft YaHei;
    color: #333333;
    line-height: 50px;
  }
  .ivu-input-large{
    height: 50px;
    color:#CFCFCF;
    line-height: 50px;
  }
  .ivu-input-large{
    font-size: 14px;
  }
  .ivu-btn-large{
    height: 50px;
  }
  .form {
    padding-top: 2vh;

    .input-verify {
      width: 67%;
    }
  }

  .code-image {
    .ivu-spin-fix .ivu-spin-main {
      height: 20px;
    }
  }

  .forget-pass,
  .other-way {
    font-size: 14px;
  }

  .login-btn,
  .other-login {
    margin-top: 40px;
  }

  .icons {
    display: flex;
    align-items: center;
  }

  .other-icon {
    cursor: pointer;
    margin-left: 10px;
    display: flex;
    align-items: center;
    color: rgba(0, 0, 0, .2);

    :hover {
      color: #2d8cf0;
    }
  }

  .layout {
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    width: 368px;
    height: 100%;


  }
}
</style>
