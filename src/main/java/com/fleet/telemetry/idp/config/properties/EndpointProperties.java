package com.fleet.telemetry.idp.config.properties;

/**
 * Holder for properties of a endpoint.
 */
public class EndpointProperties {

    private String baseUrl;

    private String path;

    private Integer timeoutMillis;

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(final String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getPath() {
        return path;
    }

    public void setPath(final String path) {
        this.path = path;
    }

    public Integer getTimeoutMillis() {
        return timeoutMillis;
    }

    public void setTimeoutMillis(final Integer timeoutMillis) {
        this.timeoutMillis = timeoutMillis;
    }

}
