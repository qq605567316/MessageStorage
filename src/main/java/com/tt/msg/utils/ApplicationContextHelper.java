package com.tt.msg.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @ClassName ApplicationContextHelper
 * @Description 其它类引用它以操作spring容器及其中的Bean实例，让job类通过该接口去调用spring管理的service
 * @Author tanjiang
 * @CreateTime 2019/3/8 16:05
 * @Version 1.0
 **/

public class ApplicationContextHelper implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context=applicationContext;
    }

    public static Object getBean(String beanName){return context.getBean(beanName);}

    public static <T> T getBean(Class<T> tClass){return context.getBean(tClass);}
}
