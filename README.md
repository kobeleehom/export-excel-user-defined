
# mybatis-plus-autoGenerator util

mybatis-plus 自动生成代码工具包
1.解放双手，专注于具体的业务
2.支持全库所有表或输入表名后自动生成对应表相关代码
3.可自由方便的引入到自己正在用的工程

## 技术栈

- springboot
- 使用MyBatisPlus作为持久层

## 环境

- JDK1.8
- mysql5.7 及以上

## 启动

- 创建数据库导入表结构
```bash
在项目根目录中有sys_user.sql文件，在mysql数据库中创建sys_user数据库，并导入该sql文件即可。
```
- 后端启动服务

```bash

# 使用IDEA导入项目，选择maven导入

# 找到auto-generator中 myBatisPlus.properties 修改对应的配置
# 找到auto-generator中 AutoGenerator.java 类，执行main方法即可
# 执行完成后再 generator-demo 中会生成对应的代码

```