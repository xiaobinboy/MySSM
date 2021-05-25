package edu.gdpu.ibatis.mapping;

import edu.gdpu.ibatis.session.Configuration;

import java.util.List;

/**
 * @author mazebin
 * @date 2021年 03月24日 22:05:31
 * 封装sql语句
 * <select id="findById" parameterType="java.lang.Integer" resultMap="userMap">
 *         select * from user  where  id =#{designId};
 *     </select>
 *  @Select("select * from user")
 *     @Results(id="userMap",value ={
 *             @Result(id=true,column = "id",property = "userId"),
 *             @Result(column = "username",property = "userName"),
 *             @Result(column = "address",property = "userAddress"),
 *             @Result(column = "sex",property = "userSex"),
 *             @Result(column = "birthday",property = "userBirthday"),
 *             @Result(property = "accounts" ,column = "id",
 *                     many =@Many(select ="edu.gdpu.dao.AccountDao.findAccountByUid",fetchType = FetchType.LAZY))
 *     })
 * }
 * )
 */
public class MappedStatement {
    private  String id;//方法名

    private String resultType;//获取方法返回值的类型
    private SqlSource sqlSource;//获取boundSql
    private SqlCommandType sqlCommandType;
    private Configuration configuration;

    public Configuration getConfiguration() {
        return configuration;
    }

    public MappedStatement() {
    }

    public MappedStatement(String id, String resultType, SqlCommandType sqlCommandType,SqlSource sqlSource,Configuration configuration) {
        this.id = id;
        this.resultType = resultType;
        this.sqlCommandType =sqlCommandType;
        this.sqlSource = sqlSource;
        this.configuration = configuration;

    }

    public SqlCommandType getSqlCommandType() {
        return sqlCommandType;
    }

    public void setSqlCommandType(SqlCommandType sqlCommandType) {
        this.sqlCommandType = sqlCommandType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public SqlSource getSqlSource() {
        return sqlSource;
    }

    public void setSqlSource(SqlSource sqlSource) {
        this.sqlSource = sqlSource;
    }



    /**
     *
     * @param parameterObject 方法参数
     * @return
     */
    public BoundSql getBoundSql(Object parameterObject){
        return  sqlSource.getBoundSql(parameterObject);
    }
}