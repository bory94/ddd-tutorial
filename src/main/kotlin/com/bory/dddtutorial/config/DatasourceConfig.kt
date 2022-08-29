package com.bory.dddtutorial.config

import com.zaxxer.hikari.HikariDataSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing
import org.springframework.transaction.annotation.EnableTransactionManagement

@Configuration
@EnableJdbcAuditing
@EnableTransactionManagement
class DatasourceConfig(
    private val hikariPoolProperties: HikariPoolProperties
) {
    @Bean
    fun dataSource() = HikariDataSource().apply {
        driverClassName = hikariPoolProperties.driverClassName
        jdbcUrl = hikariPoolProperties.jdbcUrl
        username = hikariPoolProperties.username
        password = hikariPoolProperties.password
        maximumPoolSize = hikariPoolProperties.maximumPoolSize
        minimumIdle = hikariPoolProperties.minimumIdle
    }
}