#自定义Spring IOc思路分析
ioc容器设计思路：  
准备两个map容器，一个存放class-string类型，一个存放string-object类型  
 //ioc最终容器  

    private   Map<String,Object> beans = new ConcurrentHashMap<>();  

 //ioc初始化容器：beanFactory.getBean(class<t> cl);  

    private  Map<String,Class> mapping = new ConcurrentHashMap<>();
相关spring知识点：参考![](D:\javaprogram\springboot_quick\MySSM\spring\src\main\resources\iocContainer-2.png) 

目前水平有限，未来我会努力实现beanDefinition这个类  

beanFactory的setBean(String key, Object value)方法需传入已向下转型的object对象
ioc容器初始化思路：对应方法  
beanUtils的getBean方法：通过反射创建空实例（如果是接口，则创建interfaceObject对象）（类要有空构造方法哦）

beanFactory的setBean(String key, Object value)方法需传入已向下转型的object对象，这个value需由beanUtils的getBean方法产生 

interfaceObject：接口的构造方法无法使用，所以创建此类用来在beans中存储接口的字节码对象  

先检查容器中是否存在key(因为key不可以重复)  
没有的话：  
则加入mapping和bean中，
有的话：  
则获取value的class类型，判断是否为interfaceObject类型，是则存储interfaceObject.getaClass()(就是接口的字节码对象),
不是就抛异常  
#ioc注解设计思路：
1.准备好相关注解  
AutoWrited , Bean ,Value ,Controller ,Service ,Repository,ProertySource Configuation,ComponentScan,EnableAop(开启spring的aop支持),
EnableTransaction
2.设计相关注解的后置处理器
3.设计产生相关注解后置处理器的工厂
   该工厂通过resourceUtils(静态资源工具加载类)加载class对象通过反射创建，存入到map容器中
4.设计相关工具类
@Value:proertiesLoaderUtils （通过resourceUtils获取inputstream,pro.load()）和ExpressionParse（解析value注解的value值）  
@CompontScan:PackageScanUtils(递归扫描basepackage,获取所有文件)和fileUtils(获取文件工具类)  

5.相关注解实现和关联  

bean注解和value注解，propertiesSource一般是在configuation修饰的类里面出现  

autowrited注解一般是在contrller等修饰的类里面出现  

bean注解：method.invoke(Object... args);  

value注解：field.setvisitable(true);
autowirted注解需要注入到具体的实例中，因此需要在ioc容器中找到对应class的实例化对象和获取所修饰的类变量所在的类的实例  

configuation注解要注入value和bean,思路：先将new instance对象存入ioc中，然后注入bean和value,（因为bean和value都需要注入到具体的实例中）  

componentscan注解要获取包下的所有class，扫描其他注解，执行他们的处理方法  
