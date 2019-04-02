### 技术选型
- java框架：Spring
- 网络通信：Netty，Nio框架
- 序列化：Protostuff，基于 Protobuf 序列化框架
- 注册中心：ZooKeeper

### 存在问题
- 客户端：每次调用服务启动一次netty导致性能不好
- 服务发现：服务发现基于IP，应该基于方法然后再均衡到不同IP服务器
- 服务注册：节点创建不灵活，服务注册前需要手动创建父亲节点

### 参考
https://my.oschina.net/huangyong/blog/361751