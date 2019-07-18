# springcloud_oauth2.0
一个基于Spring Cloud+OAuth2.0+Spring Security+Redis的统一认证项目

## 问题记录

### 1. 当在控制器方法中注入认证信息 Principal null
--- 这个时候需要考虑下是不是这个对外的接口是不是需要权限。这个接口不能被拦截
，每次有请求的时候要鉴权，访问设置的security.oauth2.resource.user-info-uri,获取用户授权信息

## 功能节点

### Springcloud-auth-server
> sprngcloud auth server 是`oauth2.0`的授权服务器


### Springcloud-bpm
> Springcloud bpm 是一个BPM服务， 包含`ACTIVITI`流程的运行服务。

### Springcloud-common
> springcloud common 包含了开发中常用的类，工具类等。 没有特殊意义

### Springcloud-core
> springcloud core 在命名上可能有歧义，指的是web项目的核心，包含了大部分web开发使用的依赖，公用类，工具类等

### Springcloud-eureka-server
> springcloud eureka 服务注册中心

### Springcloud-redis
> springcloud redis 模块，包含了要使用`redis`所需要使用的大部分依赖，还有一些存储在redis中的自定义类。如果需要使用到redis方面的直接依赖即可。

### Springcloud-system
> springcloud 系统模块，包含用户，角色， 菜单， 日志， 字典等模块， 也是一个资源服务器。

### Springcloud-zuul-getway
> springcloud 基于`zuul`的网关模块，oauth2.0客户端，单点登录模块，也是一个资源服务器，好包含了统一登录界面。

### Springcloud-oa-server 
> springcloud-oa-server 是一个依赖其他模块的一个办公室oa系统，包含流程审批，定制，出差，请假等单据的打印与流转