package com.fastcampus.jpa.bookmanager.support;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

// entity listener에서는 스프링 빈을 주입받지 못하는 문제를 해결하기 위한 클래스이다.
@Component //빈 표시
public class BeanUtils implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        BeanUtils.applicationContext = applicationContext;

    }

    public static <T> T getBean(Class<T> clazz){
        return applicationContext.getBean(clazz);
    }
}
