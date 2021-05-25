package edu.gdpu.mySSM.test.sqlParseTest;

import edu.gdpu.ibatis.parsing.GenericTokenParser;
import edu.gdpu.ibatis.parsing.ParameterMappingTokenHandler;
import org.junit.Test;

/**
 * @author mazebin
 * @date 2021年 03月29日 23:50:24
 */
public class TestSqlParse {
    @Test
    public void testSqlParse(){
        ParameterMappingTokenHandler handler = new ParameterMappingTokenHandler();
        GenericTokenParser parser = new GenericTokenParser("#{", "}", handler);
        String sql = parser.parse(" select * from user  where  id =#{designId} and name = #{designName}");

        System.out.println(sql);
    }
}