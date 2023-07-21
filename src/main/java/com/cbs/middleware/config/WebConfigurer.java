package com.cbs.middleware.config;

import static java.net.URLDecoder.decode;

import com.cbs.middleware.domain.ApplicationLog;
import com.cbs.middleware.domain.IssPortalFile;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Clock;
import io.jsonwebtoken.CompressionCodecResolver;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtHandler;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SigningKeyResolver;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoder;
import io.jsonwebtoken.io.Deserializer;
import io.jsonwebtoken.security.SignatureException;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.server.WebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import tech.jhipster.config.JHipsterProperties;

/**
 * Configuration of web application with Servlet 3.0 APIs.
 */
@Configuration
public class WebConfigurer implements ServletContextInitializer, WebServerFactoryCustomizer<WebServerFactory> {

    private final Logger log = LoggerFactory.getLogger(WebConfigurer.class);

    private final Environment env;

    private final JHipsterProperties jHipsterProperties;

    public WebConfigurer(Environment env, JHipsterProperties jHipsterProperties) {
        this.env = env;
        this.jHipsterProperties = jHipsterProperties;
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        if (env.getActiveProfiles().length != 0) {
            log.info("Web application configuration, using profiles: {}", (Object[]) env.getActiveProfiles());
        }

        log.info("Web application fully configured");
    }

    /**
     * Customize the Servlet engine: Mime types, the document root, the cache.
     */
    @Override
    public void customize(WebServerFactory server) {
        // When running in an IDE or with ./mvnw spring-boot:run, set location of the static web assets.
        setLocationForStaticAssets(server);
    }

    private void setLocationForStaticAssets(WebServerFactory server) {
        if (server instanceof ConfigurableServletWebServerFactory) {
            ConfigurableServletWebServerFactory servletWebServer = (ConfigurableServletWebServerFactory) server;
            File root;
            String prefixPath = resolvePathPrefix();
            root = new File(prefixPath + "target/classes/static/");
            if (root.exists() && root.isDirectory()) {
                servletWebServer.setDocumentRoot(root);
            }
        }
    }

    /**
     * Resolve path prefix to static resources.
     */
    private String resolvePathPrefix() {
        String fullExecutablePath = decode(this.getClass().getResource("").getPath(), StandardCharsets.UTF_8);
        String rootPath = Paths.get(".").toUri().normalize().getPath();
        String extractedPath = fullExecutablePath.replace(rootPath, "");
        int extractionEndIndex = extractedPath.indexOf("target/");
        if (extractionEndIndex <= 0) {
            return "";
        }
        return extractedPath.substring(0, extractionEndIndex);
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = jHipsterProperties.getCors();
        if (!CollectionUtils.isEmpty(config.getAllowedOrigins()) || !CollectionUtils.isEmpty(config.getAllowedOriginPatterns())) {
            log.debug("Registering CORS filter");
            source.registerCorsConfiguration("/api/**", config);
            source.registerCorsConfiguration("/management/**", config);
            source.registerCorsConfiguration("/v3/api-docs", config);
            source.registerCorsConfiguration("/swagger-ui/**", config);
        }
        return new CorsFilter(source);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public IssPortalFile issPortalFile() {
        return new IssPortalFile();
    }

    @Bean
    public ApplicationLog applicationLog() {
        return new ApplicationLog();
    }

    @Bean
    public JwtParser jwtParser() {
        return new JwtParser() {
            @Override
            public JwtParser setSigningKeyResolver(SigningKeyResolver signingKeyResolver) {
                return null;
            }

            @Override
            public JwtParser setSigningKey(Key key) {
                return null;
            }

            @Override
            public JwtParser setSigningKey(String base64EncodedSecretKey) {
                return null;
            }

            @Override
            public JwtParser setSigningKey(byte[] key) {
                return null;
            }

            @Override
            public JwtParser setCompressionCodecResolver(CompressionCodecResolver compressionCodecResolver) {
                return null;
            }

            @Override
            public JwtParser setClock(Clock clock) {
                return null;
            }

            @Override
            public JwtParser setAllowedClockSkewSeconds(long seconds) throws IllegalArgumentException {
                return null;
            }

            @Override
            public JwtParser requireSubject(String subject) {
                return null;
            }

            @Override
            public JwtParser requireNotBefore(Date notBefore) {
                return null;
            }

            @Override
            public JwtParser requireIssuer(String issuer) {
                return null;
            }

            @Override
            public JwtParser requireIssuedAt(Date issuedAt) {
                return null;
            }

            @Override
            public JwtParser requireId(String id) {
                return null;
            }

            @Override
            public JwtParser requireExpiration(Date expiration) {
                return null;
            }

            @Override
            public JwtParser requireAudience(String audience) {
                return null;
            }

            @Override
            public JwtParser require(String claimName, Object value) {
                return null;
            }

            @Override
            public Jwt<Header, String> parsePlaintextJwt(String plaintextJwt)
                throws UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException {
                return null;
            }

            @Override
            public Jws<String> parsePlaintextJws(String plaintextJws)
                throws UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException {
                return null;
            }

            @Override
            public Jwt<Header, Claims> parseClaimsJwt(String claimsJwt)
                throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException {
                return null;
            }

            @Override
            public Jws<Claims> parseClaimsJws(String claimsJws)
                throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException {
                return null;
            }

            @Override
            public <T> T parse(String jwt, JwtHandler<T> handler)
                throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException {
                return null;
            }

            @Override
            public Jwt parse(String jwt) throws ExpiredJwtException, MalformedJwtException, SignatureException, IllegalArgumentException {
                return null;
            }

            @Override
            public boolean isSigned(String jwt) {
                return false;
            }

            @Override
            public JwtParser deserializeJsonWith(Deserializer<Map<String, ?>> deserializer) {
                return null;
            }

            @Override
            public JwtParser base64UrlDecodeWith(Decoder<String, byte[]> base64UrlDecoder) {
                return null;
            }
        };
    }
}
