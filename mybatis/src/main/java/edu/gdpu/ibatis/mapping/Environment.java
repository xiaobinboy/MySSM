package edu.gdpu.ibatis.mapping;

import javax.sql.DataSource;

/**
 * @author mazebin
 * @date 2021年 03月24日 10:37:08
 */
public class Environment {
    private DataSource dataSource;

    public Environment(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Environment() {
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }


}