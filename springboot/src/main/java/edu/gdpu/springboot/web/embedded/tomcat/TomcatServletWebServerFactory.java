package edu.gdpu.springboot.web.embedded.tomcat;

import edu.gdpu.springboot.autoConfiguration.TomcatConfig;
import edu.gdpu.springboot.web.servlet.server.ServletWebServerFactory;
import edu.gdpu.springboot.web.servlet.server.WebServer;
import org.apache.catalina.Engine;
import org.apache.catalina.Service;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;

/**
 * @author mazebin
 * @date 2021年 05月09日 15:40:06
 */
public class TomcatServletWebServerFactory implements ServletWebServerFactory {
    public static final String DEFAULT_PROTOCOL = "org.apache.coyote.http11.Http11NioProtocol";
    private String protocol = DEFAULT_PROTOCOL;
    private TomcatConfig tomcatConfig ;

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public TomcatServletWebServerFactory(String protocol) {
        this.protocol = protocol;
    }

    public TomcatServletWebServerFactory(TomcatConfig tomcatConfig) {
        this.tomcatConfig = tomcatConfig;
    }

    @Override
    public WebServer getWebServer() {
        Tomcat tomcat = new Tomcat();
        TomcatConfig tomcatConfig = new TomcatConfig();
       // tomcat.setBaseDir(tomcatConfig.getBaseDir());
        Connector connector = new Connector(this.protocol);
        Service service = tomcat.getService();
        service.addConnector(connector);
        Engine engine = tomcat.getEngine();
        engine.setDefaultHost("mylocalhost");
        engine.setName("myTomcat");
        service.setContainer(engine);
        return new TomcatWebServer(tomcat,tomcatConfig);
    }
}