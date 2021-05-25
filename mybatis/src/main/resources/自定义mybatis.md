#自定义mybatis
 首先我想要实现的是注解版，结合spring的mybatis,也就是xmlConfigurationBuilder这个类就不实现了  
 第一步应该是扫描整个包，获取Configuration(environment(transaction和DataSource ),MapperRegistry)  
 这个流程会进行mappedStatement的初始化,这里mybatis使用了建造者模式和助手模式。详情请看[http://https://blog.csdn.net/Lee_0202/article/details/102452019](http://https://blog.csdn.net/Lee_0202/article/details/102452019)  
 第二步 应该是获取sqlSession(executor,configuration)  
前面两步流程的源码相对来说个人觉得较为容易理解，就不详细说明了。  
 第三步应该是getMapper(Class type)  
#第三步流程分为：  
sqlSession.getMapper(Class type)  
->this.getConfiguration.getMapper(Class type)  
->this.MapperRegistry.getMapper(Class type)  
->MapperProxyFactory.newInstance()  
->MapperProxy : MapperProxy<T> mapperProxy = new MapperProxy(sqlSession, this.mapperInterface, this.methodCache);
Sql语句的执行过程(以select进入分析)
mapperProxy.invoke()->  
mapperMethod  
->sqlSession.selectList()   
->executor.query()  
->StatementHandler.query()  
->ParameterHandler.setParameter()  
->ResultSetHandler.handleResultSets()
#SQL语句的解析和获取(这个过程会获得预编译好的SQL语句)
MappedStatement.getBoundSql()  
-> SqlSource.getBoundSql()->  
SqlSourceBuilder.parse()->  
GenericTokenParser.parse()


`        
         使用myMybatis完成crud
         
         //模拟扫描xml,获取全局配置对象configuration
         DruidDataSource dataSource = new DruidDataSource();
         dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
         dataSource.setUrl("jdbc:mysql://localhost:3306/mySSMDemo?serverTimezone=UTC");
         dataSource.setUsername("root");
         dataSource.setPassword("18050910ads");
         Environment environment = new Environment(dataSource);
         Configuration configuration = new Configuration(environment);
         String packageName ="edu.gdpu.mySSM.test.sqlSessionTest";
         //完成mappedStatement的注册
         SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().builder(configuration,packageName);
         SqlSession sqlSession = sqlSessionFactory.openSession();
 
         
         UserDao mapper = sqlSession.getMapper(UserDao.class);
         //System.out.println(mapper.getClass());
        //        List<User> all = mapper.findAll();
      //        for (User user : all) {
      //            System.out.println(user);
     //        }
 
         // User byId = mapper.findByName("binboy");
         //System.out.println(byId);//User{name='binboy', age=11}
        // mapper.saveUser(new User("lzh",13));
         int i = mapper.updateUser(new User("binboy", 34));`
#目前已经完成的功能
1.@Select  
2.@Insert  
3.@Delete  
4.@Update  
5.返回类型为List和自定义类型
#未实现功能：
1. @Param
2. @Results
3. 缓存
4. 动态sql
5. 懒加载
7.一对多映射

