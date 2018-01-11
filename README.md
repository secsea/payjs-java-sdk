# 说明

本项目为payjs java版本demo代码，

技术选型：

spring boot  1.5.9.RELEASE

commons-httpclient  3.0.1

fastjson  1.2.39

## 使用说明

拉取代码到本地，修改application.properties配置文件，填写好payjs的商户号和密钥

然后启动PayjsJavaSdkApplication

### 扫码支付

打开 http://localhost:8080/nativePay


### 收银台支付 
收银台支付测试最好部署到服务器上，需要通过微信打开以下地址：

打开 http://localhost:8080/order