package com.hojak99.kakaopay.config

import com.zaxxer.hikari.HikariDataSource
import org.apache.ibatis.session.SqlSessionFactory
import org.mybatis.spring.SqlSessionFactoryBean
import org.mybatis.spring.SqlSessionTemplate
import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
@MapperScan(
    value = ["com.hojak99.kakaopay.infrastructure.mapper"],
    sqlSessionFactoryRef = "sqlSessionFactory"
)
class DatabaseConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    fun dataSource() = DataSourceBuilder.create().type(HikariDataSource::class.java).build()!!

    @Bean
    fun sqlSessionFactory(dataSource: DataSource, applicationContext: ApplicationContext): SqlSessionFactory {
        val sqlSessionFactoryBean = SqlSessionFactoryBean()
        sqlSessionFactoryBean.setDataSource(dataSource)
        sqlSessionFactoryBean.setMapperLocations(*applicationContext.getResources("classpath:com/hojak99/kakaopay/infrastructure/mapper/*.xml"))
        return sqlSessionFactoryBean.getObject()!!
    }

    @Bean
    fun sqlSessionTemplate(sqlSessionFactory: SqlSessionFactory) = SqlSessionTemplate(sqlSessionFactory)
}