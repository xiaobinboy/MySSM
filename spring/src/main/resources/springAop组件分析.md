#SpringAop组件分析
aop的几个核心组件:  
切面：aspect注解，enableAop注解,pointcut注解  
切入点(连接点)：jointPoint接口,signature接口 
通知：几个通知就不一一介绍了
目标对象：targetSource  
代理：proxy   
ClassFilter，用于约束一个Advisor，与指定的targetClass是否匹配。  
只有匹配的前提下，Advisor才能使用其内部持有的Advice对targetClass进行增强。  
Advisor：通知器(pointcut和advice的组合)
MethodMatcher：方法匹配器  

Pointcut：切点，由ClassFilter和MethodMatcher组成
还得了解targetsource和joinpoint，pointcut的区别  

一些官方文档的解释
：一个涉及多个对象的模块化。事务管理是J2EE应用程序中横切关注点的一个很好的例子。在springaop中，方面是使用正则类（基于模式的方法）或用@Aspectannotation（@AspectJ样式）注释的正则类来实现的。

aspect：程序执行过程中的一个点，如方法的执行或异常的处理。在SpringAOP中，连接点*总是*表示方法执行。通过声明类型为的参数，可以在通知正文中获得连接点信息org.aspectj.lang语言.连接点。

joinpoint：方面在特定连接点采取的操作。不同类型的建议包括“周围”、“之前”和“之后”建议。建议类型如下所述。许多AOP框架（包括Spring）将通知建模为拦截器，在连接点周围维护一系列拦截器。

pointcut：匹配连接点的谓词。Advice与切入点表达式相关联，并在与切入点匹配的任何连接点上运行（例如，使用特定名称执行方法）。由切入点表达式匹配的连接点概念是AOP的核心：Spring默认使用AspectJ切入点语言。

introductions：（也称为类型间声明）。代表类型声明其他方法或字段。springaop允许您向任何代理对象引入新的接口（以及相应的实现）。例如，您可以使用介绍让bean实现IsModifiedinterface，以简化缓存。

target：由一个或多个方面建议的对象。也称为建议对象。因为springaop是使用运行时代理实现的，所以这个对象总是一个代理对象。

AOP代理：由AOP框架创建的一个对象，用于实现方面契约（advise方法执行等）。在Spring框架中，AOP代理将是JDK动态代理或CGLIB代理。代理创建对spring2.0中引入的基于模式和@AspectJ样式的方面声明的用户是透明的。

weave：将方面与其他应用程序类型或对象链接起来，以创建一个建议对象。这可以在编译时（例如使用AspectJ编译器）、加载时或运行时完成。springaop与其他纯javaaop框架一样，在运行时执行编织。  
#献上spring中文文档
[https://www.docs4dev.com/docs/zh/spring-framework/5.1.3.RELEASE/reference/core.html#aop-api](https://www.docs4dev.com/docs/zh/spring-framework/5.1.3.RELEASE/reference/core.html#aop-api)