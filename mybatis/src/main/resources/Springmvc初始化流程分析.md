Spring MVC 初始化流程分析
----------
1.HttpServletBean
----------

HttpServletBean 继承自 HttpServlet，它负责将 init-param 中的参数注入到当前 Servlet 实例的属性中，同时也为子类提供了增加 requiredProperties 的能力，需要注意的是 HttpServletBean 并不依赖于 Spring 容器。
大家知道，HttpServlet 的初始化是从 init 方法开始的，所以我们就先从 HttpServletBean 的 init 方法开始看起：

    @Override
    public final void init() throws ServletException {
     // Set bean properties from init parameters.
     PropertyValues pvs = new ServletConfigPropertyValues(getServletConfig(), this.requiredProperties);
     if (!pvs.isEmpty()) {
      try {
       BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(this);
       ResourceLoader resourceLoader = new ServletContextResourceLoader(getServletContext());
       bw.registerCustomEditor(Resource.class, new ResourceEditor(resourceLoader, getEnvironment()));
       initBeanWrapper(bw);
       bw.setPropertyValues(pvs, true);
      }
      catch (BeansException ex) {
       if (logger.isErrorEnabled()) {
    logger.error("Failed to set bean properties on servlet '" + getServletName() + "'", ex);
       }
       throw ex;
      }
     }
     // Let subclasses do whatever initialization they like.
     initServletBean();
    }
在这个方法里，首先获取到 Servlet 的所有配置并转为 PropertyValues，然后通过 BeanWrapper 修改目标 Servlet 的相关属性。BeanWrapper 是 Spring 中提供一个工具，使用它可以修改一个对象的属性，像下面这样：

    public class Main {
    public static void main(String[] args) {
    User user = new User();
    BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(user);
    beanWrapper.setPropertyValue("username", "itboyhub");
    PropertyValue pv = new PropertyValue("address", "www.itboyhub.com");
    beanWrapper.setPropertyValue(pv);
    System.out.println("user = " + user);
      }
    }
最终输出：

user = User{username='itboyhub', address='www.itboyhub.com'}

所以前面的 bw 实际上就代表当前 DispatcherServlet 对象。 通过 BeanWrapper 修改目标 Servlet 的相关属性时，有一个 initBeanWrapper 方法是空方法，开发者如有需要可以在子类中实现该方法，并且完成一些初始化操作。 属性配置完成后，最终调用 initServletBean 方法进行 Servlet 初始化，然而该方法也是一个空方法，在子类中实现。 这就是 HttpServletBean 所做的事情，比较简单，加载 Servlet 相关属性并设置给当前 Servlet 对象，然后调用 initServletBean 方法继续完成 Servlet 的初始化操作。

2.FrameWorkServlet
从前面的介绍可知，FrameworkServlet 初始化的入口方法就是 initServletBean，因此我们就从 FrameworkServlet#initServletBean 方法开始看起：

    @Override
    protected final void initServletBean() throws ServletException {
     //省略...
     try {
      this.webApplicationContext = initWebApplicationContext();
      initFrameworkServlet();
     }
     catch (ServletException | RuntimeException ex) {
      //省略...
     }
    }
这个方法原本挺长的，但是抛开日志打印异常抛出，剩下的核心代码其实就两行： initWebApplicationContext 方法用来初始化 WebApplicationContext。

initFrameworkServlet 方法用来初始化 FrameworkServlet。
但是这个方法是一个空方法，没有具体的实现。本来子类可以重写该方法做一些初始化操作，但是实际上子类并没有重写该方法，所以这个方法我们就暂且忽略之，不去分析了。 那么这里最为重要的其实就是 initWebApplicationContext 方法了，我们一起来看下：   
 
      protected WebApplicationContext initWebApplicationContext() {
    WebApplicationContext rootContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext()); 
      WebApplicationContext wac = null;   
    if (this.webApplicationContext != null)
     { wac = this.webApplicationContext; 
     if (wac instanceof ConfigurableWebApplicationContext) { 
    ConfigurableWebApplicationContext cwac = (ConfigurableWebApplicationContext) wac;  
    if (!cwac.isActive()) { 
     if (cwac.getParent() == null) {  
     cwac.setParent(rootContext); }   
    configureAndRefreshWebApplicationContext(cwac);
     } } } if (wac == null) { 
     wac = findWebApplicationContext(); }  
     if (wac == null) { wac = createWebApplicationContext(rootContext); }   
    if (!this.refreshEventReceived) { synchronized (this.onRefreshMonitor) { onRefresh(wac); } } 
    if (this.publishContext) {  
      String attrName = getServletContextAttributeName(); getServletContext().setAttribute(attrName, wac);} 
    return wac; }   

 这里的逻辑也比较清晰： 首先获取 rootContext。在默认情况下，Spring 会将容器设置为 ServletContext 的一个属性，属性的 key 为 org.springframework.web.context.WebApplicationContext.ROOT，所以根据这个 key 就可以调用 ServletContext#getAttribute 方法获取到 rootContext 了。 获取 WebApplicationContext 实例，也就是给 wac 变量赋值的过程，这里存在三种可能性：1.如果已经通过构造方法给 webApplicationContext 赋值了，则直接将其赋给 wac 变量，同时，如果需要设置 parent 就设置，需要刷新就刷新。这种方式适用于 Servlet3.0 以后的环境，因为从 Servlet3.0 开始，才支持直接调用 ServletContext.addServlet 方法去注册 Servlet，手动注册的时候就可以使用自己提前准备好的 WebApplicationContext 了；2.如果第一步没能成功给 wac 赋值，那么调用 findWebApplicationContext 方法尝试去 ServletContext 中查找 WebApplicationContext 对象，找到了就赋值给 wac；3.如果第二步没能成功给 wac 赋值，那么调用 createWebApplicationContext 方法创建一个 WebApplicationContext 对象并赋值给 wac，一般来说都是通过这种方式创建的 WebApplicationContext。这三套组合拳下来，wac 肯定是有值了。 当 ContextRefreshedEvent 事件没有触发时，调用 onRefresh 方法完成容器刷新（由于第一种和第三种获取 WebApplicationContext 的方式最终都会调用 configureAndRefreshWebApplicationContext 方法，然后发布事件，再将 refreshEventReceived 变量标记为 true，所以实际上只有第二种方式获取 wac 实例的时候，这里才会刷新，具体可以看下文分析）。 最后将 wac 保存到到 ServletContext 中。保存的时候会根据 publishContext 变量的值来决定是否保存，publishContext 可以在 web.xml 中配置 Servlet 时通过 init-param 进行配置，保存的目的是为了方便获取。