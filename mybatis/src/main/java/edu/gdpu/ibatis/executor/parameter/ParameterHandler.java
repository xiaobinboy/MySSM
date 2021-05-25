package edu.gdpu.ibatis.executor.parameter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author mazebin
 * @date 2021年 03月24日 14:36:18
 */
public interface ParameterHandler {
    Object getParameterObject();

    void setParameters(PreparedStatement ps)
            throws SQLException;
}