package com.glody.glody_platform.config;
import org.apache.catalina.connector.Connector;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpToHttpsRedirectConfig
        implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {

    @Override
    public void customize(TomcatServletWebServerFactory factory) {
        // Tạo connector HTTP lắng nghe cổng 8080
        Connector httpConnector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
        httpConnector.setScheme("http");
        httpConnector.setPort(8080);
        httpConnector.setSecure(false);
        // Khi có request HTTP, redirect sang cổng HTTPS 8443
        httpConnector.setRedirectPort(9876);

        factory.addAdditionalTomcatConnectors(httpConnector);
    }
}
