package com.chatbot.springbootchatbot.loader;

import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

@Component
public class ElasticsearchLoader {
    private static final Logger LOG = LoggerFactory.getLogger(ElasticsearchLoader.class);
    @Value("${elasticsearch.indices.package}")
    private String packageName;

    @Autowired
    private RestHighLevelClient client;

    @EventListener
    public void handleContextRefresh(ContextRefreshedEvent event) {

        final ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(true);
        scanner.addIncludeFilter(new AssignableTypeFilter(Object.class));
        final Set<BeanDefinition> bds = scanner.findCandidateComponents(packageName);

        bds.forEach(bd -> {
            try {
                createAndConfigureElasticIndex(Class.forName(bd.getBeanClassName()), client);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });


    }

    private void createAndConfigureElasticIndex(Class<? extends Object> c, RestHighLevelClient client) throws InstantiationException, IllegalAccessException {
        Object object = c.newInstance();
        Class[] argTypes = new Class[]{RestHighLevelClient.class};
        try {
            Method m = c.getDeclaredMethod("createIndex", argTypes);
            m.invoke(object, client);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


}
