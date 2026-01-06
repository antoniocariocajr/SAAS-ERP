package com.billerp.infrastructure.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.billerp.infrastructure.constants.OpenApiConstants.*;

import java.util.List;

@Configuration
public class OpenApiConfig {

        @Bean
        public OpenAPI openAPI() {
                return new OpenAPI()
                                .info(buildInfo())
                                .servers(buildServers())
                                .tags(buildGlobalTags())
                                .externalDocs(buildExternalDocs())
                                .addSecurityItem(buildSecurityRequirement())
                                .components(new Components().addSecuritySchemes(SECURITY_SCHEME_NAME,
                                                new SecurityScheme()
                                                                .name(SECURITY_SCHEME_NAME)
                                                                .type(SecurityScheme.Type.HTTP)
                                                                .scheme("bearer")
                                                                .bearerFormat("JWT")));
        }

        private Info buildInfo() {
                return new Info()
                                .title("Bill ERP API")
                                .description("API for Bill ERP Application")
                                .version("1.0")
                                .contact(new Contact()
                                                .name(AUTHOR_NAME)
                                                .url(AUTHOR_URL)
                                                .email(AUTHOR_EMAIL))
                                .license(new License()
                                                .name("Apache 2.0")
                                                .url("https://www.apache.org/licenses/LICENSE-2.0"));
        }

        private List<Server> buildServers() {
                return List.of(
                                new Server().url(DEV_SERVER_URL).description("Development Server"),
                                new Server().url(PROD_SERVER_URL).description("Production Server"));
        }

        private List<Tag> buildGlobalTags() {
                return List.of(
                                new Tag().name("Category").description("Category management"),
                                new Tag().name("User").description("User management"),
                                new Tag().name("Authentication").description("System authentication"));
        }

        private ExternalDocumentation buildExternalDocs() {
                return new ExternalDocumentation()
                                .description("Full Documentation")
                                .url(AUTHOR_URL + "/docs");
        }

        private SecurityRequirement buildSecurityRequirement() {
                return new SecurityRequirement().addList(SECURITY_SCHEME_NAME);
        }

}
