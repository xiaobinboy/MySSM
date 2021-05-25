package edu.gdpu.springboot.web.embedded.tomcat;

import edu.gdpu.springboot.autoConfiguration.TomcatConfig;
import edu.gdpu.springboot.web.servlet.server.WebServer;
import edu.gdpu.springboot.web.servlet.server.WebServerException;
import org.apache.catalina.*;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.StandardHost;
import org.apache.catalina.startup.ContextConfig;
import org.apache.catalina.startup.Tomcat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * @author mazebin
 * @date 2021年 05月09日 15:39:18
 */
public class TomcatWebServer implements WebServer {
    private  final Tomcat tomcat;
    private TomcatConfig tomcatConfig;
private final AtomicInteger containerCounter = new AtomicInteger(-1);
private final Logger logger = LoggerFactory.getLogger(getClass());
    public TomcatWebServer(Tomcat tomcat,TomcatConfig tomcatConfig) {
        this.tomcat = tomcat;
        this.tomcatConfig = tomcatConfig;
        initialize();
    }
    public Map<Service, Connector[]> getServiceConnectors() {
        return serviceConnectors;
    }

    public void setServiceConnectors(Map<Service, Connector[]> serviceConnectors) {
        this.serviceConnectors = serviceConnectors;
    }

    private Map<Service,Connector[]> serviceConnectors = new ConcurrentHashMap<>();
    @Override
    public void start() throws WebServerException {
/*        //真正启动web服务，获取所有wrapper,排序后调用wrapper.load()方法加载所有Servlet
        Container[] children = tomcat.getEngine().findChildren();
        try {
            for (int i = 0; i < children.length; i++) {
                Container child = children[i];
                if(child instanceof Wrapper){
                    Wrapper wrapper = (Wrapper) child;
                    ((Wrapper) child).load();
                }

            }
        } catch ( ServletException e ) {
            e.printStackTrace();
        }*/
// 由于springboot在这方面还写了较为多的类，比如TomcatEmbeddedContext,TomcatStarter等,这些源码方面相对来说较为复杂
//使用addContext方法加载本项目工作目录下的所有class文件,和静态文件，并为之指定contxtPath




    }
private void initialize() throws WebServerException {
    try {
        Host host = tomcat.getHost();
        //设置不自动部署(热部署)
        host.setAutoDeploy(false);
        Engine engine = tomcat.getEngine();
        host.setName("myLocalhost");
        host.setAppBase("webapp");
        engine.addChild(host);
this.tomcat.getConnector().setPort(tomcatConfig.getPort());
        StandardContext context = (StandardContext) tomcat.addContext(host, tomcatConfig.getContextPath(), System.getProperty("user.dir"));

        ////初始化ContextConfig配置
        context.addLifecycleListener(new ContextConfig());
        context.addLifecycleListener((event)->{
            if(context.equals(event.getSource())&&"start".equals(event.getType())){
this.removeServiceConnectors();
            }
        });
        //开启守护线程
        this.tomcat.start();
        logger.info("tomcat Start on,port："+tomcatConfig.getPort()+"contextPath："+this.tomcatConfig.getContextPath());
        startDemoAwaitThread();
    } catch ( LifecycleException e ) {
        e.printStackTrace();
    }

}

private void removeServiceConnectors(){
    Service[] services = this.tomcat.getServer().findServices();
    int length = services.length;
    for (int i = 0; i < length; i++) {
        Service service = services[i];
        Connector[] connectors = service.findConnectors();
        this.getServiceConnectors().put(service,connectors);
        Connector[] connectors1 = connectors;
        for (int j = 0; j < connectors1.length; j++) {
            Connector connector = connectors1[j];
            service.removeConnector(connector);
        }


    }
}
private  void startDemoAwaitThread(){


            //使守护线程处于等待状态，防止服务器断开
            TomcatWebServer.this.tomcat.getServer().await();


    //正式启动tomcat
    //thread.start();

}
private Context findContext(){
    Container[] children = this.tomcat.getHost().findChildren();
     int length =children.length;
    for (int i = 0; i < children.length; i++) {
        Container child = children[i];
        if(child instanceof  Context){
            return (Context) child;
        }
    }
    throw new IllegalStateException("这个主机不包含Context");
}


    @Override
    public void stop() throws WebServerException {
        try {
            this.tomcat.stop();
            this.tomcat.destroy();
        } catch ( LifecycleException e ) {
            e.printStackTrace();
        }
    }

    @Override
    public int getPort() {
        Connector connector = this.tomcat.getConnector();

        return connector ==null?connector.getLocalPort():0;
    }
}