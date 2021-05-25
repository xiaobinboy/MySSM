#自定义SpringAop
![](D:\javaprogram\springboot_quick\MySSM\spring\src\main\resources\aop分析.jpg)
## 我的组件： ##

    /**管理切面的通知器
     * @author mazebin
     * @date 2021年 03月15日 17:32:23
     */
    public class Advisor {
    //结合pointcut和advice
    //获取通知注解的类型，生成对应的advice
    private Advice advice;
    //获取pointcut注解的value
    private Pointcut pointcut;


算了直接看源码吧，想学习Spring源码，自己实现一个穷人版的spring,怎么可能看不懂我写的呢  
直接上测试Demo  
#测试类  

     public class Client {
     @Test
    public  void test() throws  Exception{
    AnnotationPostProcessor componentScanPostProcessor = AnnoationPostProcessorFactory.
    getPostProcessor("ComponentScanPostProcessor");
    Class<?> aClass = Class.forName("edu.gdpu.MySSM.test.iocAop.config.TestConfiguration");
    componentScanPostProcessor.handle(aClass);
    MyApplicationContext context = MyApplicationContextFactory.getMyApplicationContext();
    //context.printBeans();
    TestService testservice =context.getBean("testservice");
    //context.printBeans();
       //System.out.println("生成的代理类型："+testservice.getClass().getName());
     testservice.print();
    }
    }
#切面类  

    @Component
    //@Aspect
    public class MyAspect {
    @Pointcut("edu.gdpu.MySSM.test.iocAop.service.impl.*.*()")
    public void pt1(){
    
    }
    //@Before("pt1()")
    //public void before(){
    //System.out.println("myAspect before");
    //}
    //@After("pt1()")
    //public void after(){
    //System.out.println("myAspect after");
    //}
    //@AfterReturn("pt1()")
    //public void afterReturn(){
    //System.out.println("myAspect afterReturn");
    //}
    //@AfterThrow("pt1()")
    //public void afterThrowing(){
    //System.out.println("myAspect afterThrow");
    //}
    
    @Around("pt1()")
    public void around(JoinPoint joinPoint){
    System.out.println("环绕通知-begin");
    try {
    joinPoint.proceed();
    } catch ( Exception e ) {
    e.printStackTrace();
    }
    System.out.println("环绕通知-end");
    
    }
    }
#环境配置类
    @PropertiesSource("test.properties")
    @Configuration
    @EnableTransaction
    @ComponentScan("edu.gdpu.MySSM.test.iocAop")
    //@EnableAopProxy(useCglib = false)
    public class TestConfiguration {
    @Value("#{test.user}")
    String user;

    @Bean
    public MyBean MyBean() {
        MyBean myBean = new MyBean();
        return myBean;
    }
    @Bean
    public DataSource dataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/eesy?serverTimezone=UTC");
        dataSource.setUsername("root");
        dataSource.setPassword("18050910ads");
    return dataSource;
    }
    @Bean
    public ConnectionManager connectionManager(){
        //类似于@Qualifer注解，不想实现
        DataSource dataSource = dataSource();
        ConnectionManager.setDataSource(dataSource);
        ConnectionManager manager = new ConnectionManager();
        return manager;
    }
    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager(){
        DataSourceTransactionManager manager = new DataSourceTransactionManager();
        manager.setConnection();
        return manager;
        }
    }
# Controller  
    @Controller
    public class TestController {
    @AutoWrited
    private TestService testService;

    public void print() {
        System.out.println("TestController print");
        System.out.println( testService.print());
        }
    }
#service
    @Service("testservice")
    @Transactional
    public class TestServiceImpl implements TestService {

    @Override
    public String print() {
        System.out.println("testService print");
        return "testService print";
        }
    }
#事务
这一部分是基于Aop实现的，个人觉得没啥好讲，毕竟缺少具体场景，对事务的理解还不是很深，就实现得相对比较简单。
 **！！！要保证事务 能够获取同一个connnection 可以使用ThreadLocal**