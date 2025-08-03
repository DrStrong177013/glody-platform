package com.glody.glody_platform.config;

import reactor.netty.http.Http11SslContextSpec;
import reactor.netty.http.server.HttpServer;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.KeyManagerFactory;
import java.io.ByteArrayInputStream;
import java.security.KeyStore;
import java.util.Base64;

@Configuration
public class SslConfig {

    @Bean
    public WebServerFactoryCustomizer<NettyReactiveWebServerFactory> nettySslCustomizer() {
        return factory -> factory.addServerCustomizers(this::configureSsl);
    }

    private HttpServer configureSsl(HttpServer httpServer) {
        // 1) Đọc Base64 + password từ env
        String base64 = System.getenv("SSL_KEYSTORE_BASE64");
        char[] pwd = System.getenv("SSL_KEYSTORE_PASSWORD").toCharArray();

        // 2) Tạo KeyManagerFactory từ dữ liệu in-memory
        KeyManagerFactory kmf = createKeyManagerFactory(base64, pwd);

        // 3) Đóng gói thành Http11SslContextSpec
        Http11SslContextSpec sslSpec = Http11SslContextSpec.forServer(kmf);

        // 4) Bật TLS cho Reactor Netty
        return httpServer.secure(spec -> spec.sslContext(sslSpec));
    }

    private KeyManagerFactory createKeyManagerFactory(String base64, char[] pwd) {
        try {
            byte[] data = Base64.getDecoder().decode(base64);
            KeyStore ks = KeyStore.getInstance("PKCS12");
            try (var in = new ByteArrayInputStream(data)) {
                ks.load(in, pwd);
            }
            KeyManagerFactory kmf = KeyManagerFactory
                    .getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(ks, pwd);
            return kmf;
        } catch (Exception ex) {
            throw new IllegalStateException("Không tải được keystore từ biến env", ex);
        }
    }
}
