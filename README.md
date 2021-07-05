# Springcloud-boss 1.0 (项目采用Eureka作为注册中心和配置中心)


### 智慧调查，用户，角色，菜单。问卷设计、问卷调查
#### 交流，QQ:892379244


Springcloud 开发平台 作者：camel




![Apache](https://img.shields.io/badge/Apache-2.0-brightgreen)
![cloud版本](https://img.shields.io/badge/Spring%20Cloud-Finchley.RC2-brightgreen)
![boot版本](https://img.shields.io/badge/Spring%20Boot-2.0.3.RELEASE-brightgreen)
![https://img.shields.io/badge/Nacos-1.3-brightgreen](https://img.shields.io/badge/eureka-2.0.3-brightgreen)
![https://img.shields.io/badge/Flowable-6.4.2-brightgreen](https://img.shields.io/badge/oauth-2.0-brightgreen)
![https://img.shields.io/badge/Mybatis%20Plus-3.3.1-brightgreen](https://img.shields.io/badge/Mybatis%20Plus-2.2.0-brightgreen)


- 基于 Spring Cloud Oauth 2.0+ 、Spring Boot 2.2、 zuul 的RBAC权限管理系统  
- 基于vue.js、 jquery、 html，更简洁的页面，实现的前后端分离交互
- 客户端和服务端纯Token交互，接口全部使用restful风格
- 认证服务器与资源服务器分离，方便接入自己的微服务系统
- 功能模块采用插拔方式，使用更简单整洁规范
- 基于flowable的工作流系统，提供完善基于业务的流程系统
- 提供代码生成器、封装Mybatis plus 查询，提高开发效率

 
 ### 如果大家有什么建议或者问题，请在Issues中提交，作者会一一的回复，希望大家一起让这个开源项目变的更好，我的本意是大家一起学习，所以此项目完全开源，完全免费。您的点赞和建议是作者维护这个项目的最大动力，感谢！


### 2021-02-20 更新日志
1. 修改重置密码没有加密的问题。
2. 将bootstrap.yml中指定服务器IP配置转移到配置中心配置中。
3. 修改获取角色权限没有过滤删除菜单的问题。
4. 删除密码json忽略。
5. 更新IP数据库，修改并发导致无法获取IP地址信息的问题。


### 文档地址
 [https://www.kancloud.cn/polaris_wang/spark/1762689](https://www.kancloud.cn/polaris_wang/spark/1762689)
 

注意：
1. 流程测试：
- 这里是列表文本使用admin 账号添加文章，发布，之后，角色是组长的都会接到待办消息。
- 流程流转到主编审核，使用主编1和主编2审核，入口从待办任务中进入。
- 系统判断节点为自动判断，逻辑为 主编审核节点只有当两个主编都审核通过，则为审核流程通过，如果其中有人拒绝，退回到发起人修改，也就是admin
- 发起人admin重新修改数据 提交给组长角色审核，或者关闭当前的流程。
2. 流程测试图
![输入图片说明](https://images.gitee.com/uploads/images/2020/0424/102707_2837dc87_1890906.png "屏幕截图.png")

### 已实现功能
|   用户管理  |  角色管理   |  菜单管理   |  坐席管理   |  字典管理   |  三方项目   |  队列管理   |  微信推荐   |
| --- | --- | --- | --- | --- | --- | --- | --- |
|   区域地址  |  任务面板   |  我的资料   | 代码生成器  | 我的钱包    | 资料管理    |   项目管理  |  问卷管理   |
|   调查工具  |  工作记录   |  班次管理   |  话务管理   |   外呼计划  |  兼职预约   |  财务管理   |  推荐管理   |



### 项目地址
 平台  | spark-platform（后端）|spark-admin（前端）
---|---|---
GitHub | [https://github.com/wangdingfeng/spark-platform](https://github.com/wangdingfeng/spark-platform)|[https://github.com/wangdingfeng/spark-admin](https://github.com/wangdingfeng/spark-admin)
Gitee  | [https://gitee.com/dreamfeng/spark-platform](https://gitee.com/dreamfeng/spark-platform)|[https://gitee.com/dreamfeng/spark-admin](https://gitee.com/dreamfeng/spark-admin)

小程序地址：https://gitee.com/dreamfeng/wx-spark-shop

### 演示地址

演示地址：[http://www.sparkplatform.cn/](http://www.sparkplatform.cn/)

备用演示地址: [http://admin.xiapeiyi.com/](http://admin.xiapeiyi.com/) 不允许对里边的数据进行操作，感谢

演示环境工作流账号密码：
| 账号  | 密码   | 权限               |
| ----- | ------ | ------------------ |
| admin | 123456 | 除删除外所有的权限 |
| zuzhang | 123456 | 工作流权限 |
| zhubian1 | 123456 | 工作流权限 |
| zhubian2 | 123456 | 工作流权限 |

平台账号密码
| 平台  | 账号   | 密码               |
| ----- | ------ | ------------------ |
| Admin监控 | spark | spark |
| 数据库监控 | spark | spark |
| Nacos |  |  |
| Minio|  |  |

依赖 | 版本
---|---
Spring Boot |  2.3.3.RELEASE 
Spring Cloud | Hoxton.SR8   
Nacos | 1.30   
Flowable | 6.4.2
Mybatis Plus | 3.3.1
Spring Boot Admin | 2.2.3
Security Jwt | 1.0.10.RELEASE

#### 模块说明
```
springcloud-boss
├── springcloud-auth-server -- 授权服务 oauth2
├── springcloud-common -- 系统公共模块 
├── springcloud-activiti -- 工作流
├── springcloud-attendance -- 留着
├── springcloud-core -- 核心类
├── springcloud-eureka-server -- 注册中心
├── springcloud-feign -- feign配置模块
├── springcloud-interviewer -- 访员模块
├── springcloud-oa-server -- OA模块
├── springcloud-offline -- 线下调查
├── springcloud-realname -- 实名认证模块
├── springcloud-redis -- Redis模块
├── springcloud-sms-server -- 短信、消息等服务
├── springcloud-stock-operation -- 库存服务
├── springcloud-survey -- 智慧调查
├── springcloud-system -- 系统模块
├── springcloud-zuul-getway -- 网关模块
└── .gitlab-ci.yml -- gitlab CI/CD执行脚本文件
```
 ** :heart: 贡献**
 
 后续开始招收志同道合的仙友一起致力于维护SpringBoot \ springcloud 项目功能的开发和完善，有意愿的小伙伴请私信我。感谢大家的支持！

#### springcloud_oauth2.0
一个基于Spring Cloud+OAuth2.0+Spring Security+Redis的统一认证项目

#### 启动

1. 设置环境变量
```
ACTIVEMQ_HOST： MQ主机IP
APPLICATION_GETWAY_PORT： 项目网关端口
APPLICATION_IP： 项目网关主机IP
AUTH_PORT: 认证服务端口
DATABASE_IP： 数据库IP
DATABASE_PORT： 数据库端口
DATABASE_USER： 数据库用户
DATABASE_PASSWORD: 数据库密码
SURVEY_ENV： 项目启动环境
REDIS_HOST： Redis主机IP
EUREKA_INSTANCE_IP： Eureka 注册中心主机IP
```
2. 打包
```shell script
mvn clean package -Dmaven.test.skip=true -X -U 
```

3. 启动项目
```shell script
// 注册中心
java -jar springcloud-eureka-server-1.0-SNAPSHOT.jar
// 认证服务
java -jar springcloud-auth-server-1.0-SNAPSHOT.jar
// 系统模块
java -jar springcloud-system-1.0-SNAPSHOT.jar
// 库存模块
java -jar springcloud-stock-operation-1.0-SNAPSHOT.jar
// 智慧调查
java -jar springcloud-survey-1.0-SNAPSHOT.jar
// 实名认证
java -jar springcloud-realname-1.0-SNAPSHOT.jar
// 网关模块
java -jar springcloud-zuul-getway-1.0-SNAPSHOT.jar
```

## 问题记录

### 1. 当在控制器方法中注入认证信息 Principal null


> ALTER TABLE `zs_agency`
 ADD COLUMN `max_value`  double(11,2) NOT NULL DEFAULT 300 AFTER `agency_id`;
 
> ALTER TABLE `zs_agency_fee`
ADD COLUMN `state`  int(2) NOT NULL DEFAULT 0 COMMENT '审核状态' AFTER `work_hours`;

> ALTER TABLE `zs_agency_fee`
ADD COLUMN `work_id_num`  varchar(50) NOT NULL DEFAULT '' COMMENT '工作人员身份证号码' AFTER `state`;

