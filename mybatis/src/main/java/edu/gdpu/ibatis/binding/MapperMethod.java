package edu.gdpu.ibatis.binding;


import com.sun.org.apache.xalan.internal.xsltc.dom.SortingIterator;
import edu.gdpu.ibatis.exceptions.MapperException;
import edu.gdpu.ibatis.mapping.SqlCommandType;
import edu.gdpu.ibatis.reflection.TypeParameterResolver;
import edu.gdpu.ibatis.session.Configuration;
import edu.gdpu.ibatis.session.SqlSession;


import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

/**
 * @author mazebin
 * @date 2021年 03月24日 21:24:19
 */
public class MapperMethod {
    private SqlCommand sqlCommand;
    private MethodSignature methodSignature;

    public MapperMethod(Class mapperInterface, Method method, Configuration configuration) {
        String id =mapperInterface.getName()+"."+method.getName();
        System.out.println(id);
        sqlCommand = new SqlCommand(id,configuration.getMappedStatement(id).getSqlCommandType());
        methodSignature = new MethodSignature(method,mapperInterface);
    }

    public Object execute(SqlSession sqlSession, Object[] args) {

       Object result = null;
       switch (sqlCommand.getType()){
           case SELECT:{

               if(methodSignature.isReturnMany()&&!methodSignature.isReturnOne()){
                    result =executeForMany(sqlSession,args);
               }
               else if(methodSignature.isReturnOne()&&!methodSignature.isReturnMany()){
                    result = sqlSession.selectOne(sqlCommand.getId(),args);
               }
               break;
           }
           case INSERT:{

               result = rowCountResult(sqlSession.insert(sqlCommand.getId(),args));

               break;
           }
           case UPDATE:{

               result = rowCountResult(sqlSession.update(sqlCommand.getId(),args));
               break;
           }
           case DELETE:
               {

                   result = rowCountResult(sqlSession.delete(sqlCommand.getId(),args));
                   break;
               }

       }
        return result;
    }
    private <E> List<E> executeForMany(SqlSession sqlSession, Object[] args){

        return  sqlSession.selectList(sqlCommand.getId(),args);
    }
    private Object rowCountResult(int rowCount){
          Object result;
        if(methodSignature.isReturnVoid()){
            result = null;
        }else if(Integer.class.equals(methodSignature.getReturnType())||Integer.TYPE.equals(methodSignature.getReturnType())){
            result = rowCount;
        }
        else if (Long.class.equals(methodSignature.getReturnType()) || Long.TYPE.equals(methodSignature.getReturnType())) {
            result = (long)rowCount;
        }else{
            throw  new MapperException("MapperMethod 不支持此返回类型:"+methodSignature.getReturnType());
        }
       return  result;
    }

    public static class  SqlCommand{
    private  String id;
    private  SqlCommandType type;


    public SqlCommand(String id, SqlCommandType type) {
        this.id = id;
        this.type = type;
    }

    public SqlCommand() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SqlCommandType getType() {
        return type;
    }

    public void setType(SqlCommandType type) {
        this.type = type;
    }
}
public static class MethodSignature{
private Class<?> returnType;
private  boolean returnOne = false;
private boolean returnMany = false;
private boolean returnVoid = false;

    public MethodSignature(Method method,Class<?> mapperInterface) {
        Type resolvedReturnType = TypeParameterResolver.resolveReturnType(method, mapperInterface);
        if (resolvedReturnType instanceof Class<?>) {
            this.returnType = (Class<?>) resolvedReturnType;
        } else if (resolvedReturnType instanceof ParameterizedType) {
            this.returnType = (Class<?>) ((ParameterizedType) resolvedReturnType).getRawType();
        } else {
            this.returnType = method.getReturnType();
        }
        if(Collection.class.isAssignableFrom(returnType)){
            returnMany = true;
        }else {
            returnOne = true;
        }
        returnVoid =void.class.equals(returnType);
    }

    public Class<?> getReturnType() {
        return returnType;
    }

    public void setReturnType(Class<?> returnType) {
        this.returnType = returnType;
    }

    public boolean isReturnOne() {
        return returnOne;
    }

    public void setReturnOne(boolean returnOne) {
        this.returnOne = returnOne;
    }

    public boolean isReturnMany() {
        return returnMany;
    }

    public void setReturnMany(boolean returnMany) {
        this.returnMany = returnMany;
    }

    public boolean isReturnVoid() {
        return returnVoid;
    }

    public void setReturnVoid(boolean returnVoid) {
        this.returnVoid = returnVoid;
    }
}
}
