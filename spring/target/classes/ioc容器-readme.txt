ioc容器设计思路：
准备两个map容器，一个存放class-string类型，一个存放string-object类型
 //ioc最终容器
    private   Map<String,Object> beans = new ConcurrentHashMap<>();
 //ioc初始化容器：beanFactory.getBean(class<t> cl);
    private  Map<String,Class> mapping = new ConcurrentHashMap<>();
相关spring知识点：参考iocContainer-2.png
目前水平有限，未来我会努力实现beanDefinition这个类的
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
