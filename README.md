# 用户中心项目

## 基于Ant Design Pro和Spring Boot的一站式用户管理系统
## 可以独立运行，也可以嵌入到其他项目当中，方便用户管理、提供个性化服务

### Q：这个项目采用了哪些技术选型？
### A：- 前端部分：
### Ant Design Pro：基于Ant Design和React的快速开发框架
### UmiJS：基于React的快速开发框架

### - 后端部分：
### Spring Boot 后端框架
### MyBatis + Mybatis Plus 框架
### JUnit 单元测试框架

### Q：其中可能涉及的安全问题？
### A：- 针对密码泄露问题，我们采用了AES加密算法加密敏感信息，然后存储到数据库中（由于是简单项目，没有采用密钥管理系统，故统一采用作者设置的本地密钥和向量）；
### - 针对可能的SQL注入问题，我们在注册接口和登录接口添加了敏感字符校验；
### - 针对可能的登录态泄露问题，我们采用了脱敏技术，使用某些字段替换敏感信息，保障传输层安全；
### - 对于一些敏感功能，我们设置了鉴权机制，防止非管理员用户滥用操作；
