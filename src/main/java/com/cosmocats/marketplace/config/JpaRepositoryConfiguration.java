package com.cosmocats.marketplace.config;

import com.cosmocats.marketplace.repository.impl.NaturalIdRepositoryImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaAuditing
@EnableJpaRepositories(
        basePackages = "com.cosmocats.marketplace.repository",
        repositoryBaseClass = NaturalIdRepositoryImpl.class)
public class JpaRepositoryConfiguration {
}
