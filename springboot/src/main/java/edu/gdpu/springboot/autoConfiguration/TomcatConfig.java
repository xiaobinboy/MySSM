package edu.gdpu.springboot.autoConfiguration;


import edu.gdpu.spring.context.annotation.Configuration;
import edu.gdpu.spring.context.annotation.PropertiesSource;
import edu.gdpu.spring.context.annotation.Value;

/**
 * @author mazebin
 * @date 2021年 05月10日 16:16:21
 */
@Configuration
@PropertiesSource
public class TomcatConfig {
    @Value("#{tomcat.port}")
    private Integer port = 8080;
    @Value("#{tomcat.contextPath}")
    private  String contextPath = "/spring";

    private String baseDir="src/main/webapp";

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public String getBaseDir() {
        return baseDir;
    }

    public void setBaseDir(String baseDir) {
        this.baseDir = baseDir;
    }
}