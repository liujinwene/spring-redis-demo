bb-server
======================================  

### 1. 启动类  
com.example.base.core.CoreServerApp  

### 2. redis的使用  
src/main/resources/META-INF/redis.xml  
com.example.order.service.OrderServiceImpl.listOrder  
com.example.order.service.OrderServiceImpl.clearListOrder  
测试类  
com.example.order.OrderTest  

### 3. Mysql Lock  
com.example.base.lock.MysqlNamedLock  
com.example.base.lock.dao.LockDaoImpl  
测试类  
com.example.lock.LockTest.lock  

### 4. Spring Hibernate声明式事务  
src/main/resources/META-INF/hibernate.xml 中的 transactionTemplate/transactionManager  
com.example.lock.LockTest.lock2  
注意  
Spring Hibernate中,Criteria和insert/update/delete操作,需要使用事务.  
Mysql的getLock和releaseLock需要session,session需要事务.  
由事务的影响,导致Hibernate不能使用Mysql锁.  
另外需要再研究下,Hibernate是否可以不使用事务.  
