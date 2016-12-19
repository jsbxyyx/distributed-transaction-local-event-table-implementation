# 分布式事务本地事件表实现

## 业务场景
用户注册后，给用户生成账户信息

## 流程
### 保存用户流程
1. 用户服务接收到用户请求后，在user表创建一条用户记录，并在event_publish用户创建事件，状态为NEW，payload记录的是事件内容，提交事务
2. 用户模块的定时器查询所有状态为new的事件后，拿到payload信息，发送给mq对应的topic，发送成功后把状态改为PUBLISHED，提交事务.

### 生成账户流程
1. 接受到mq传来的用户创建事件，在event_process表中创建一条status为NEW的记录，payload记录的是事件内容，保存成功，向mq返回成功的消息
2. 账户服务的定时器，开启事务，查询所有event_process中是否有NEW的记录，拿到payload信息后，回调事件处理器，处理生成账户信息，处理成功后修改event_process的status为PROCESSED，最后提交事务

```
create table transaction1;

use transaction1;

CREATE TABLE `account` (
  `user_id` int(11) NOT NULL,
  `amount` int(11) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `event_process` (
  `id` varchar(40) NOT NULL,
  `status` varchar(30) DEFAULT NULL COMMENT '事件状态(待处理NEW，已处理PROCESSED)',
  `payload` text COMMENT '事件内容',
  `create_time` datetime DEFAULT NULL,
  `event_type` varchar(30) DEFAULT NULL COMMENT '事件类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table transaction2;

use transaction2;

CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `event_publish` (
  `id` varchar(40) NOT NULL,
  `status` varchar(30) NOT NULL COMMENT '事件状态(待发布NEW, 已发布PUBLISHED)',
  `payload` text NOT NULL COMMENT '事件内容',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `event_type` varchar(30) NOT NULL COMMENT '事件类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```