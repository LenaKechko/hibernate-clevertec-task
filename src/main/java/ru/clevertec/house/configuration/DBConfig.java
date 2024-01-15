package ru.clevertec.house.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Конфигурационный класс для работы с базой данных
 */
@Configuration
@PropertySource(value = "classpath:application.yml")
public class DBConfig {

    /**
     * Зависимость для работы с данными из application.yml
     */
    @Autowired
    private BeanFactoryPostProcessor beanFactoryPostProcessor;
    /**
     * Имя пользователя для подключение к БД
     */
    @Value("${spring.datasource.username}")
    private String userName;
    /**
     * Пароль пользователя для подключение к БД
     */
    @Value("${spring.datasource.password}")
    private String password;
    /**
     * Путь к БД
     */
    @Value("${spring.datasource.url}")
    private String url;

    /**
     * Драйвер для подключения к БД
     */
    @Value("${spring.datasource.driver-class-name}")
    private String driver;

    /**
     * Бин для подключения к БД
     *
     * @return возвращается DataSource для дальнейшей работы с БД
     */
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);
        return dataSource;
    }

    /**
     * Бин для JDBCTemplate
     */
    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    /**
     * Бин для конфигурации hibernate
     */
    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("ru.clevertec.house.entity");

        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        hibernateProperties.setProperty("hibernate.show_sql", "true");

        sessionFactory.setHibernateProperties(hibernateProperties);
        return sessionFactory;
    }

    /**
     * Настройка транзакций
     */
    @Bean
    public HibernateTransactionManager transactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();

        transactionManager.setSessionFactory(sessionFactory().getObject());

        return transactionManager;
    }
}
