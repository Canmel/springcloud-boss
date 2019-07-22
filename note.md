## oauth2 
### 配置
####  1. `ResourceServerConfigurerAdapter` 资源配置服务器
> 配置资源服务器的关键信息
> - 1. HttpSecurity配置, 配置csrf,认证方式等
```java
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                .authorizeRequests()
                .antMatchers("/session/**").authenticated().antMatchers("/session/me").permitAll()
                .and()
                .httpBasic();
    }
``` 


####  2 `AuthorizationServerConfigurerAdapter` 授权服务器配置
> 主要是配置服务器授权中的关键信息。如：
> - 1. 认证行为
```java
    @Override
     public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
         security
                 .allowFormAuthenticationForClients()
                 .tokenKeyAccess("permitAll()")
                 .checkTokenAccess("isAuthenticated()");
     }
```
     
> - 2. 配置客户端信息。只有指定的客户端才能得到授权.
    以及各个客户端的授权认证方式：1. 授权码 2.简化 3. 密码 4. 客户端模式

```java
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("android")
                .scopes("read")
                .secret("android")
                .authorizedGrantTypes("password", "authorization_code", "refresh_token")
                .and()
                .withClient("webapp")
                .scopes("read")
                .authorizedGrantTypes("implicit")
                .and()
                .withClient("browser")
                .authorizedGrantTypes("refresh_token", "password")
                .scopes("read");
    }
```
> - 3. 配置用户详情服务， 获取客户端发来的用户信息，进行匹配的服务.token存储管理。

```java
 @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore())
                .userDetailsService(userDetailService)
                .authenticationManager(authenticationManager);
        endpoints.tokenServices(defaultTokenServices());
        //认证异常翻译
        endpoints.exceptionTranslator(webResponseExceptionTranslator());
    }
```

> - 4 配置接口中只有四个方法。除了构造方法，还有3个配置包含：
```java
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
    }

    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    }

    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    }
``` 






