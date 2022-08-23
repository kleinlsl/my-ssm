package com.tujia.myssm.web.annotation;

import java.util.Objects;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 *
 * @author: songlinl
 * @create: 2021/12/31 17:34
 */
public class MyRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        BeanRegistrationUtil.registerBeanDefinitionIfNotExists(registry, MyAnnotationProcessor.class.getName(),
                                                               MyAnnotationProcessor.class);
    }

    static class BeanRegistrationUtil {
        public static boolean registerBeanDefinitionIfNotExists(BeanDefinitionRegistry registry, String beanName,
                                                                Class<?> beanClass) {
            if (registry.containsBeanDefinition(beanName)) {
                return false;
            }

            String[] candidates = registry.getBeanDefinitionNames();

            for (String candidate : candidates) {
                BeanDefinition beanDefinition = registry.getBeanDefinition(candidate);
                if (Objects.equals(beanDefinition.getBeanClassName(), beanClass.getName())) {
                    return false;
                }
            }

            BeanDefinition annotationProcessor = BeanDefinitionBuilder.genericBeanDefinition(beanClass)
                                                                      .getBeanDefinition();
            registry.registerBeanDefinition(beanName, annotationProcessor);

            return true;
        }
    }
}
