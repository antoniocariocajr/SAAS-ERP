package com.billerp.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.NoOpDbRefResolver;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class MongoConfig {

    private final MongoDatabaseFactory factory;
    private final MongoMappingContext context;

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(factory, new MappingMongoConverter(NoOpDbRefResolver.INSTANCE, context));
    }
}
