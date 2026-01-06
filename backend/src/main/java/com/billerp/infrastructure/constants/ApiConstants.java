package com.billerp.infrastructure.constants;

public final class ApiConstants {

    private ApiConstants() {
    }

    public static final String API_PREFIX = "/api/v1";
    public static final String CATEGORY_PATH = API_PREFIX + "/categories";
    public static final String ID_PARAMETER = "/{id}";

    public static final String[] AUTH_WHITELIST = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html"
    };

    public static final String ISSUER = "bill-erp";
}
