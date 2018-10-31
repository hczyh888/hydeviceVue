# 更新说明

> 2018-07-17

完成小程序下单，登录，获取用户信息，客服，个人中心，优惠券领取，订单列表等功能，完善后台商品列表，订单列表等功能


> 2018-07-12

上传小程序源码，下一步完成下单和完善首页商品浏览
﻿


# QQ交流群
![输入图片说明](https://images.gitee.com/uploads/images/2018/0712/231130_2598d82b_722346.png "jfinal-cms-shop交流群群二维码.png")

群号：699760941

# jfinal-cms-shop

#### 项目介绍
基于jfinal+element ui+vue的开源商城管理系统
演示地址：http://gongzhen.site


#### 软件架构
软件架构说明
后台采用java语言，jfinal开源框架 数据库 采用 mysql 5.7

#### 安装教程

1. 导入数据库脚本到mysql
2. 更改项目数据库连接  用户名和密码
3. 运行项目后台，直接用IntelliJ IDEA打开jfinal-cms.iml,或者用eclipse打开,启动项目。eclipse打开教程https://blog.csdn.net/leimenghongchen1/article/details/80830344
4. 用vue构建工具启动vue-admin-web


#### 使用说明

1. 数据库及图片存储路径设置文件：a_little_config.txt  

有些路径要配置
2. cms-model模块，配置主机地址Constant.java    HOST_PATH = "http://192.168.199.175";//主机地址可以为域名
3. 图片加载路径Constant.java  ，FILE_PATH=HOST_PATH+"/";//图片文件预览下载地址，'/'表示cms-admin项目根路径，如为admin则此值为HOST_PATH+"/admin/"
4. 修改vue-admin-web\config\index.js的/api和uploadPath路径
5. 打包部署时修改vue-admin-web\main.js  的Vue.prototype.HOST和Vue.prototype.UPLAODPATH


```
Vue.prototype.HOST=process.env.NODE_ENV === 'development'?"/api":"http://192.168.1.150"//部署后的后台服务地址，如项目根路径为admin则为http://192.168.1.150/admin
Vue.prototype.UPLAODPATH=process.env.NODE_ENV === 'development'?"/uploadPath":"http://192.168.1.150/file/upload"//部署后的后台文件上传地址，如项目根路径为admin则为http://192.168.1.150/admin/file/upload
```


#### 参与贡献

1. Fork 本项目
2. 新建 Feat_xxx 分支
3. 提交代码
4. 新建 Pull Request


#### 项目介绍

 **功能列表** 
1. 商品管理：
商品列表
订单管理
商品类目
商品规格
2. 文章管理：
美妆推荐
今日快报
3. 推广管理：
广告管理
促销
4. 素材管理
5. 用户管理
6. 管理员
7. 栏目管理

 **项目展示** 
![输入图片说明](https://gitee.com/uploads/images/2018/0625/180041_9e6f9e94_722346.png "屏幕截图.png")
![输入图片说明](https://gitee.com/uploads/images/2018/0625/180142_9d532164_722346.png "屏幕截图.png")
![输入图片说明](https://gitee.com/uploads/images/2018/0625/180200_dbf7acc0_722346.png "屏幕截图.png")
![输入图片说明](https://gitee.com/uploads/images/2018/0625/180211_5f083ad3_722346.png "屏幕截图.png")
![输入图片说明](https://gitee.com/uploads/images/2018/0625/180225_d10ef43c_722346.png "屏幕截图.png")
![输入图片说明](https://gitee.com/uploads/images/2018/0625/180245_43ab47ac_722346.png "屏幕截图.png")
![输入图片说明](https://gitee.com/uploads/images/2018/0625/180303_d4c12327_722346.png "屏幕截图.png")
![输入图片说明](https://gitee.com/uploads/images/2018/0625/180325_864111d6_722346.png "屏幕截图.png")
![输入图片说明](https://gitee.com/uploads/images/2018/0625/180336_1e47382b_722346.png "屏幕截图.png")
![输入图片说明](https://gitee.com/uploads/images/2018/0625/180345_27618d71_722346.png "屏幕截图.png")
![输入图片说明](https://gitee.com/uploads/images/2018/0625/180357_d2854e2d_722346.png "屏幕截图.png")
![输入图片说明](https://gitee.com/uploads/images/2018/0625/180421_0f99d77b_722346.png "屏幕截图.png")
![输入图片说明](https://gitee.com/uploads/images/2018/0625/180436_1b204438_722346.png "屏幕截图.png")
![输入图片说明](https://gitee.com/uploads/images/2018/0625/180453_2dd32aba_722346.png "屏幕截图.png")
![输入图片说明](https://images.gitee.com/uploads/images/2018/0717/182506_0df927a8_722346.png "屏幕截图.png")

 **小程序端** 
![输入图片说明](https://gitee.com/uploads/images/2018/0626/161510_b580d819_722346.jpeg "微信图片_20180625181100.jpg")
![输入图片说明](https://gitee.com/uploads/images/2018/0626/161613_35250b7d_722346.png "屏幕截图.png")
![输入图片说明](https://gitee.com/uploads/images/2018/0626/161639_adf4dbb6_722346.png "屏幕截图.png")
![输入图片说明](https://gitee.com/uploads/images/2018/0627/173550_aed64b9c_722346.png "屏幕截图.png")
![输入图片说明](https://gitee.com/uploads/images/2018/0627/173416_dd9736c7_722346.png "屏幕截图.png")
![输入图片说明](https://gitee.com/uploads/images/2018/0627/173515_3d0da25d_722346.png "屏幕截图.png")
![输入图片说明](https://gitee.com/uploads/images/2018/0628/221714_ae90b806_722346.png "屏幕截图.png")
![输入图片说明](https://images.gitee.com/uploads/images/2018/0712/225351_b3bf8231_722346.png "屏幕截图.png")
![输入图片说明](https://images.gitee.com/uploads/images/2018/0712/225408_ca99d27f_722346.png "屏幕截图.png")
![输入图片说明](https://images.gitee.com/uploads/images/2018/0717/181902_bba6269e_722346.png "屏幕截图.png")
![输入图片说明](https://images.gitee.com/uploads/images/2018/0717/181918_6403fd1a_722346.png "屏幕截图.png")
