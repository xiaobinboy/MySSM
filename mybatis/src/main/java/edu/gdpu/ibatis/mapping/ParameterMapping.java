package edu.gdpu.ibatis.mapping;

/**
 * @author mazebin
 * @date 2021年 03月26日 15:24:25
 */
public class ParameterMapping {
    //sql语句的参数
    private String property;

    public ParameterMapping() {
    }



    public ParameterMapping(String property) {
        this.property = property;
    }

    public String getProperty() {
        return this.property;
    }

    public void setProperty(String context) {
        this.property= context;
    }

    @Override
    public String toString() {
        return "ParameterMapping{" +
                "property='" + property;
    }
}