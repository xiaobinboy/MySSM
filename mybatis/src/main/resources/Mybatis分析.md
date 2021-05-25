# ## mybatis源码分析 ## #
## 1.configuration ##
 获取exector类型   
 获取是否允许缓存  
 获取别名注册器  
 获取扫描包注册器  
 获取自动映射sql  
 获取数据源环境配置
## 2.Environment ##
  DataSource  
 TransactionFactory   
 Id
##SqlSessionFactory.openSession()方法分析
    public class DefaultSqlSessionFactory implements SqlSessionFactory {
     public SqlSession openSession() {
        return this.openSessionFromDataSource(this.configuration.getDefaultExecutorType(), (TransactionIsolationLevel)null, false);
    }
        }
##SqlSessionFactory.openSessionFromDataSource()方法分析
    private SqlSession openSessionFromDataSource(ExecutorType execType, TransactionIsolationLevel level, boolean autoCommit) {
        Transaction tx = null;

        DefaultSqlSession var8;
        try {
            Environment environment = this.configuration.getEnvironment();
            TransactionFactory transactionFactory = this.getTransactionFactoryFromEnvironment(environment);  
    //新建事务  
            tx = transactionFactory.newTransaction(environment.getDataSource(), level, autoCommit);
            Executor executor = this.configuration.newExecutor(tx, execType);
            var8 = new DefaultSqlSession(this.configuration, executor, autoCommit);
        } catch (Exception var12) {
            this.closeTransaction(tx);
            throw ExceptionFactory.wrapException("Error opening session.  Cause: " + var12, var12);
        } finally {
            ErrorContext.instance().reset();
        }

        return var8;
    }
##DefaultSqlSession构造器方法分析
    public class DefaultSqlSession implements SqlSession {
    public DefaultSqlSession(Configuration configuration, Executor executor, boolean autoCommit) {
        this.configuration = configuration;
        this.executor = executor;
        this.dirty = false;
        this.autoCommit = autoCommit;
    }
    }
##SqlSession.getMapper()方法分析
    public class DefaultSqlSession implements SqlSession {
     public <T> T getMapper(Class<T> type) {
        return this.configuration.getMapper(type, this);  
    }  
    }
##Configuration.getmapper()方法分析##
    public class Configuration {
     public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
    return this.mapperRegistry.getMapper(type, sqlSession);
    }
    }
##MapperRegistry.getmapper()方法分析##
    public class MapperRegistry {
    public <T> T getMapper(Class<T> type, SqlSession sqlSession) {  
    //从注册好的map容器中获取mapperProxyFactory
        MapperProxyFactory<T> mapperProxyFactory = (MapperProxyFactory)this.knownMappers.get(type);
        if (mapperProxyFactory == null) {
            throw new BindingException("Type " + type + " is not known to the MapperRegistry.");
        } else {
            try {
                return mapperProxyFactory.newInstance(sqlSession);
            } catch (Exception var5) {
                throw new BindingException("Error getting mapper instance. Cause: " + var5, var5);
            }
        }
    }
    }
##MapperProxyFactory.newInstance(SqlSession)方法分析##

    public class MapperProxyFactory<T> {
    public class MapperProxy<T> implements InvocationHandler, Serializable{ 
     public T newInstance(SqlSession sqlSession) {
        MapperProxy<T> mapperProxy = new MapperProxy(sqlSession, this.mapperInterface, this.methodCache);
        return this.newInstance(mapperProxy);
    }
    }
     protected T newInstance(MapperProxy<T> mapperProxy) {  
    //通过JDK动态代理产生代理类
        return Proxy.newProxyInstance(this.mapperInterface.getClassLoader(), new Class[]{this.mapperInterface}, mapperProxy);
    }
##代理对象如何执行方法分析##
    public class MapperProxy<T> implements InvocationHandler, Serializable {

      private static final long serialVersionUID = -6424540398559729838L;
      private final SqlSession sqlSession;
      private final Class<T> mapperInterface;
      private final Map<Method, MapperMethod> methodCache;
    
      @Override
      public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    try {
      if (Object.class.equals(method.getDeclaringClass())) {
        //如果调用的是Object的方法，就直接调用他，不用太关注
        return method.invoke(this, args);
      } else if (method.isDefault()) {
        //对默认方法的处理，不用太关注
        return invokeDefaultMethod(proxy, method, args);
      }
    } catch (Throwable t) {
      throw ExceptionUtil.unwrapThrowable(t);
    }


    //如果缓存中存在这个方法，从缓存中取，
    //如果没有，就从Configuration中获取相关信息，查询其方法和对应的sql语句建立，封装成MapperMethod类
    final MapperMethod mapperMethod = cachedMapperMethod(method);
    //执行方法，重点
    return mapperMethod.execute(sqlSession, args);  
    }
## Class MapperMethod ##
    ublic class MapperMethod {

      private final SqlCommand command;//接口方法和对应的sql语句
      private final MethodSignature method;
    
      public MapperMethod(Class<?> mapperInterface, Method method, Configuration config) {
    this.command = new SqlCommand(config, mapperInterface, method);
    this.method = new MethodSignature(config, mapperInterface, method);
     }
      //args是传入的参数
      public Object execute(SqlSession sqlSession, Object[] args) {
    Object result;
    //command.getType()是sql语句的类型，如select，insert等
    switch (command.getType()) {
      case INSERT: { //insert语句，进入这个
        Object param = method.convertArgsToSqlCommandParam(args);
        result = rowCountResult(sqlSession.insert(command.getName(), param));
        break;
      }
      case UPDATE: { //update语句，进入这个
        Object param = method.convertArgsToSqlCommandParam(args);
        result = rowCountResult(sqlSession.update(command.getName(), param));
        break;
      }
      case DELETE: { //delete 语句，进入这个
        Object param = method.convertArgsToSqlCommandParam(args);
        result = rowCountResult(sqlSession.delete(command.getName(), param));
        break;
      }
      case SELECT: //select 语句，进入这个
        if (method.returnsVoid() && method.hasResultHandler()) {
         // 如果返回void 并且方法参数有ResultHandler
          executeWithResultHandler(sqlSession, args);
          result = null;
        } else if (method.returnsMany()) {
          //如果返回多行结果
          result = executeForMany(sqlSession, args);
        } else if (method.returnsMap()) {
           //如果返回MAP
          result = executeForMap(sqlSession, args);
        } else if (method.returnsCursor()) {
           //如果返回Cursor
          result = executeForCursor(sqlSession, args);
        } else {
          //否则就是查询单个对象
          //返回方法对应参数信息和其值
          Object param = method.convertArgsToSqlCommandParam(args);
          //执行方法，本文重点研究这个方法
          result = sqlSession.selectOne(command.getName(), param);
          if (method.returnsOptional()
              && (result == null || !method.getReturnType().equals(result.getClass()))) {
            result = Optional.ofNullable(result);
          }
        }
        break;
      case FLUSH:
        result = sqlSession.flushStatements();
        break;
      default: 
        throw new BindingException("Unknown execution method for: " + command.getName());
    }
    if (result == null && method.getReturnType().isPrimitive() && !method.returnsVoid()) {
      throw new BindingException("Mapper method '" + command.getName()
  +    ` " attempted to return null from a method with a primitive return type (" + method.getReturnType() + ").");`
    }
    return result;
      }
    /*方法参数解析过程
    Mybatis是通过method.convertArgsToSqlCommandParam(args);获取方法参数及其值,其底层是MethodSignature的convertArgsToSqlCommandParam方法*/
    
    //MethodSignature是MapperMethod的静态内部类
    public static class MethodSignature {
    
    private final boolean returnsMany;
    private final boolean returnsMap;
    private final boolean returnsVoid;
    private final boolean returnsCursor;
    private final boolean returnsOptional;
    private final Class<?> returnType;
    private final String mapKey;
    private final Integer resultHandlerIndex;
    private final Integer rowBoundsIndex;
    private final ParamNameResolver paramNameResolver;

    public MethodSignature(Configuration configuration, Class<?> mapperInterface, Method method) {
      .......
      //解析@Param注解
      this.paramNameResolver = new ParamNameResolver(configuration, method);
    }

    public Object convertArgsToSqlCommandParam(Object[] args) {
      return paramNameResolver.getNamedParams(args);
    }
    }


/********************************分界线***************************************************/
 
    public class ParamNameResolver {
    
      private static final String GENERIC_NAME_PREFIX = "param";
    
      private final SortedMap<Integer, String> names;
    
      private boolean hasParamAnnotation;
      // 解析@Param注解，把解析结果放到names中，key是位置从0开始，vaule是@Param值，
      // 如果没有标注@Param注解，vaule就是arg+"key"：如arg0
      public ParamNameResolver(Configuration config, Method method) {
    final Class<?>[] paramTypes = method.getParameterTypes();
    final Annotation[][] paramAnnotations = method.getParameterAnnotations();
    final SortedMap<Integer, String> map = new TreeMap<>();
    int paramCount = paramAnnotations.length;
   
    for (int paramIndex = 0; paramIndex < paramCount; paramIndex++) {
      if (isSpecialParameter(paramTypes[paramIndex])) {
        continue;
      }
      String name = null;
      for (Annotation annotation : paramAnnotations[paramIndex]) {
        if (annotation instanceof Param) {
          hasParamAnnotation = true;
          name = ((Param) annotation).value();
          break;
        }
      }
      if (name == null) {
        // @Param was not specified.
        if (config.isUseActualParamName()) {
          name = getActualParamName(method, paramIndex);
        }
        if (name == null) {
          // use the parameter index as the name ("0", "1", ...)
          // gcode issue #71
          name = String.valueOf(map.size());
        }
      }
      map.put(paramIndex, name);
    }
    names = Collections.unmodifiableSortedMap(map);
      }
    
      public Object getNamedParams(Object[] args){
    final int paramCount = names.size();
    //没有参数，直接返回
    if (args == null || paramCount == 0) {
      return null;
    //没有标注@Param，且参数格式为1
    } else if (!hasParamAnnotation && paramCount == 1) {
      return args[names.firstKey()];
    } else {
      //多个参数的情况
      final Map<String, Object> param = new ParamMap<>();
      int i = 0;
      //循环遍历names
      for (Map.Entry<Integer, String> entry : names.entrySet()) {
        //如果标注@Param了，key就是@Param值，否则就是args0 args1 等
        //值是入参值
        param.put(entry.getValue(), args[entry.getKey()]);
        //从新添加新的key值规则，如param1  param2...
        final String genericParamName = GENERIC_NAME_PREFIX + String.valueOf(i + 1);
        if (!names.containsValue(genericParamName)) {
          param.put(genericParamName, args[entry.getKey()]);
        }
        i++;
      }
      return param;
    }
      }
    }
[https://blog.csdn.net/qq_41071876/article/details/105471701](https://blog.csdn.net/qq_41071876/article/details/105471701 "mybatis如何生成代理对象和代理对象执行方法")
Mybatis缓存的一些思考：
一级缓存：对应SqlSession  
二级缓存：对应SqlSessionFactory  
这也是为何执行器是在SqlSessionFactory.openSession中创建