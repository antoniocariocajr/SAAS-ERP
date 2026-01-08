package com.billerp.infrastructure.constants;

public final class ApiConstants {

    private ApiConstants() {
    }

    public static final String API_PREFIX = "/api/v1";
    public static final String CATEGORY_PATH = API_PREFIX + "/categories";
    public static final String PRODUCT_PATH = API_PREFIX + "/products";
    public static final String INVOICE_PATH = API_PREFIX + "/invoices";
    public static final String CUSTOMER_PATH = API_PREFIX + "/customers";
    public static final String USER_PATH = API_PREFIX + "/users";
    public static final String REPORT_PATH = API_PREFIX + "/reports";
    public static final String AUTH_PATH = "/auth";
    public static final String ID_PARAMETER = "/{id}";
    public static final String AUTH_LOGIN = "/login";

    public static final String CORS_ORIGIN = "http://localhost:4200";

    public static final String[] AUTH_WHITELIST = {
            "/",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html"
    };

    public static final String ISSUER = "bill-erp";
}
