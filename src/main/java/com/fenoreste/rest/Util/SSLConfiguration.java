package com.fenoreste.rest.Util;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.springframework.context.annotation.Bean;


public class SSLConfiguration {
/*
private static final String CONNECTOR_CLASS = "org.apache.coyote.http11.Http11NioProtocol";
	 
    private static final String HTTPS = "https";
 
    private static final Integer PORT = 8443;
 
    private static final String KEYSTORE_FILE = "classpath:keystore.p12";
 
    private static final String KEYSTORE_PASSWORD = "12345678";
 
    @Bean
    public EmbeddedServletContainerFactory servletContainer() throws KeyManagementException, NoSuchAlgorithmException, InterruptedException, IOException {
        final TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
        tomcat.addAdditionalTomcatConnectors(createSslConnector());
        return tomcat;
    }
 
    private Connector createSslConnector() throws InterruptedException, IOException {
        final Connector connector = new Connector(CONNECTOR_CLASS);
        final Http11NioProtocol protocol = (Http11NioProtocol)connector.getProtocolHandler();
        connector.setScheme(HTTPS);
        connector.setSecure(true);
        connector.setPort(PORT);
        protocol.setSSLEnabled(true);
        protocol.setKeystoreFile(KEYSTORE_FILE);
        protocol.setKeystorePass(KEYSTORE_PASSWORD);
        return connector;
    }*/
}
