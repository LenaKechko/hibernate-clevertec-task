package ru.clevertec.house.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.nio.charset.StandardCharsets;
import java.util.List;


/**
 * Конфигурационный класс для приложения
 */
@EnableWebMvc
@Configuration
@EnableTransactionManagement
@Import({YamlConfig.class, DBConfig.class})
@ComponentScan(basePackages = "ru.clevertec.house")
@PropertySource(value = "classpath:application.yml")
public class AppConfig {

    /**
     * Бин для настройки ответа от сервера в нужном формате
     */
    @Bean
    public HttpHeaders header() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        httpHeaders.setAcceptCharset(List.of(StandardCharsets.UTF_8));
        return httpHeaders;
    }

}
