package ru.clevertec.house.configuration;

import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

import java.util.Objects;
import java.util.Properties;

/**
 * Конфигурационный класс для работы с данными в application.yml
 */
@Configuration
@PropertySource(value = "classpath:application.yml")
public class YamlConfig {

    /**
     * Бин для чтения данных из файла application.yml и парсинга файла
     *
     * @return фабрика, в которой хранится информация с конфигруацией приложения
     */
    @Bean
    public BeanFactoryPostProcessor beanFactoryPostProcessor() {
        PropertySourcesPlaceholderConfigurer propertyConfigure = new PropertySourcesPlaceholderConfigurer();
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(new ClassPathResource("application.yml"));
        Properties yamlObject = Objects.requireNonNull(yaml.getObject(), "Not found");
        propertyConfigure.setProperties(yamlObject);
        return propertyConfigure;
    }
}
