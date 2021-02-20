#### 代码目录结构

​	thinking-in-jdbc   JDBC 有关作业简单编写了 JDBC 原生接口和 PrepareStatement 操作 批处理，整合 Hikari 数据库连接池  

​			｜—pojo 实体包

​			｜— util 工具类包 获取数据库连接，初始化连接池，释放连接

​			｜—test 操作数据库方法

​	thinking-in-spring  spring 有关作业

​			｜— aop 基于 cglib 实现的简单的 aop

​			｜— proxy 基于 jdk 和 cglib 的动态代理

​			｜— springbean 代码实现 springbean 的多种装配方式

​			｜— springxml springxml 自定义配置，装配 student/Klass/school

​	thinking-in-spring-entity 实体类包

​	thinking-in-spring-starter 自动装配student/Klass/school 的starter，目前装配参数是写死的，通过properties 或者 yaml 文件装配还在学习中。。。
