ioc注解设计思路：
1.准备好相关注解
AutoWrited , Bean ,Value ,Controller ,Service ,Repository,ProertySource Configuation,ComponentScan,EnableAspectAopProxy(开启spring的aop支持),
EnableTransactionManagement
2.设计相关注解的后置处理器
3.设计产生相关注解的工厂
   **工厂通过resourceUtils(静态资源工具加载类)加载class对象通过反射创建，存入到map容器中
4.设计相关工具类
@Value:proertiesLoaderUtils （通过resourceUtils获取inputstream,pro.load()）和ExpressionParse（解析value注解的value值）
@CompontScan:PackageScanUtils(递归扫描basepackage,获取所有文件)和fileUtils(获取文件工具类)，对应思考：fileMethoTest
5.相关注解实现和关联
bean注解和value注解，propertiesSource一般是在configuation修饰的类里面出现
autowrited注解一般是在contrller等修饰的类里面出现
bean注解：method.invoke(args... args);
value注解：field.setvisitable(true);
autowirted注解需要注入到具体的实例中，因此需要在ioc容器中找到对应class的实例化对象和获取所修饰的类变量所在的类的实例
configuation注解要注入value和bean,思路：先将new instance对象存入ioc中，然后注入bean和value,（因为bean和value都需要注入到具体的实例中）
componentscan注解要获取包下的所有class，扫描其他注解，执行他们的处理方法




