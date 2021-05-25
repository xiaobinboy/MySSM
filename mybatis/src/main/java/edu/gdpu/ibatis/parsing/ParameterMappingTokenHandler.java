package edu.gdpu.ibatis.parsing;

import edu.gdpu.ibatis.mapping.ParameterMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mazebin
 * @date 2021年 03月26日 15:25:06
 */
public class ParameterMappingTokenHandler implements TokenHandler {
    private List<ParameterMapping> parameterMappings = new ArrayList<ParameterMapping>();

    // context是参数名称 #{id}:id #{username}:username
@Override
    public String handleToken(String content) {
        parameterMappings.add(buildParameterMapping(content));
        return "?";
    }

    private ParameterMapping buildParameterMapping(String content) {
        ParameterMapping parameterMapping = new ParameterMapping(content);

        return parameterMapping;
    }

    public List<ParameterMapping> getParameterMappings() {
        return parameterMappings;
    }

    public void setParameterMappings(List<ParameterMapping> parameterMappings) {
        this.parameterMappings = parameterMappings;
    }


}