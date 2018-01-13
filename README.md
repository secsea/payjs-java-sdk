# 说明

本项目为payjs java版本demo代码，

技术选型：

spring boot  1.5.9.RELEASE

commons-httpclient  3.0.1

fastjson  1.2.39

## 使用说明

拉取代码到本地，修改application.properties配置文件，填写好payjs的商户号和密钥

然后启动PayjsJavaSdkApplication

### 支付测试

打开 http://localhost:8080/

您将会看到如下

![支付首页](https://github.com/secsea/payjs-java-sdk/blob/master/img/index.png)

根据您的选择，进行不同的支付测试，扫码支付如下图：

![扫码支付](https://github.com/secsea/payjs-java-sdk/blob/master/img/nativePay.png)

收银台支付如下图：

![收银台支付](https://github.com/secsea/payjs-java-sdk/blob/master/img/cashierPay.png)

请注意：收银台支付需要在微信中进行测试 
