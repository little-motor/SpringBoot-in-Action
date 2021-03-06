package cn.littlemotor.web;

import cn.littlemotor.web.interceptor.DeCROSInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.stereotype.Indexed;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 此处是对整个应用进行一些必要的附加设置
 * @author littlemotor
 * @date 19.3.12
 */
@Indexed
@SpringBootApplication(scanBasePackages = {"cn.littlemotor.web.*"})
//定义Mabatis的dao接口位置
@MapperScan(
        //指定扫描包
        basePackages = "cn.littlemotor.web.model.dao.*",
        //指定sqlSessionFactory
        sqlSessionFactoryRef = "sqlSessionFactory",
        //指定标记的注解
        annotationClass = Repository.class
)
public class WebApplication extends SpringBootServletInitializer implements WebMvcConfigurer{

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

    @Autowired
    BeanFactory beanFactory;

    //注册拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册拦截器到Spring MVC机制，然后返回一个InterceptorRegistration
        //此处添加的是防止浏览器提示CROS的拦截器
        InterceptorRegistration deCROSInterceptorRegistration = registry.addInterceptor(new DeCROSInterceptor());
        //指定拦截器匹配模式
        deCROSInterceptorRegistration.addPathPatterns("/**");

    }

//    //覆盖configure方法，用于部署war文件
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//        return builder.sources(WebApplication.class);
//    }

//    @Override
//    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
//        //converters.add(0,new StringToUserHttpMessageConverter());
//    }
}

