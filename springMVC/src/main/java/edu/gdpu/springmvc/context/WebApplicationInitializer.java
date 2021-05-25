package edu.gdpu.springmvc.context;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * @author mazebin
 * spring mvc 启动加载,实现这个接口可以取代web.xml的作用
 */
public interface WebApplicationInitializer  {
    /**
     * spring mvc启动加载，注册监听
     * @param servletContext
     * @throws ServletException
     */
    public  void onStartup(ServletContext servletContext) throws ServletException;
    /**
     * 献上springMVC源码
     * public abstract class AbstractContextLoaderInitializer implements WebApplicationInitializer {
     *     protected final Log logger = LogFactory.getLog(this.getClass());
     *
     *     public AbstractContextLoaderInitializer() {
     *     }
     *
     *     public void onStartup(ServletContext servletContext) throws ServletException {
     *         this.registerContextLoaderListener(servletContext);
     *     }
     *
     *     protected void registerContextLoaderListener(ServletContext servletContext) {
     *         WebApplicationContext rootAppContext = this.createRootApplicationContext();
     *         if (rootAppContext != null) {
     *             ContextLoaderListener listener = new ContextLoaderListener(rootAppContext);
     *             listener.setContextInitializers(this.getRootApplicationContextInitializers());
     *             servletContext.addListener(listener);
     *         } else {
     *             this.logger.debug("No ContextLoaderListener registered, as createRootApplicationContext() did not return an application context");
     *         }
     *
     *     }
     *
     *     @Nullable
     *     protected abstract WebApplicationContext createRootApplicationContext();
     *
     *     @Nullable
     *     protected ApplicationContextInitializer<?>[] getRootApplicationContextInitializers() {
     *         return null;
     *     }
     * }
     */
}
