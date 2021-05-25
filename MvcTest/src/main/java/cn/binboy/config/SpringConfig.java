package cn.binboy.config;

import com.alibaba.druid.pool.DruidDataSource;
import edu.gdpu.ibatis.spring.SqlSessionFactoryBean;
import edu.gdpu.spring.context.annotation.*;
import edu.gdpu.springmvc.servlet.config.annotation.EnableWebMvc;
import edu.gdpu.springmvc.servlet.config.annotation.ResourceHandlerRegistry;
import edu.gdpu.springmvc.servlet.config.annotation.WebMvcConfigurerSupport;
import edu.gdpu.springmvc.servlet.view.InternalResourceViewResolver;

import javax.sql.DataSource;

/**
 * @author mazebin
 * @date 2021年 05月01日 21:24:01
 * Spring配置类，我的不打算区分Spring上下文和springMvc上下文
 */
@Configuration
@ComponentScan("cn.binboy")
@PropertiesSource("application.properties")
@EnableWebMvc
public class SpringConfig extends WebMvcConfigurerSupport {
    @Value("#{mysql.driver}")
    private String driver;
    @Value("#{mysql.url}")
    private String url;
    @Value("#{mysql.username}")
    private String username;
    @Value("#{mysql.password}")
    private String password;
    @Value("#{mybatis.mapperPackageName}")
    private String mapperPackageName;


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceLocations("/css/,/js/,/images/");
    }

    //注册视图解析器
    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/pages/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    @Bean
    public DataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;

    }
    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(){
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setConfiguration(dataSource());
        sqlSessionFactoryBean.setMapperScanPackageName(mapperPackageName);
        return sqlSessionFactoryBean;
    }




}