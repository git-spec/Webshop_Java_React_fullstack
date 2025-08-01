package org.example.backend.configuration;

import org.slf4j.event.Level;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.paypal.sdk.Environment;
import com.paypal.sdk.PaypalServerSdkClient;
import com.paypal.sdk.authentication.ClientCredentialsAuthModel;


@Configuration
public class AppConfig {

    @Value("${PAYPAL_CLIENT_ID}")
    private String PAYPAL_CLIENT_ID;

    @Value("${PAYPAL_CLIENT_SECRET}")
    private String PAYPAL_CLIENT_SECRET;
   
    @Bean
    public PaypalServerSdkClient paypalClient() {
            return new PaypalServerSdkClient.Builder()
                     .loggingConfig(builder -> builder
                             .level(Level.DEBUG)
                             .requestConfig(logConfigBuilder -> logConfigBuilder.body(true))
                             .responseConfig(logConfigBuilder -> logConfigBuilder.headers(true)))
                     .httpClientConfig(configBuilder -> configBuilder
                             .timeout(0))
                     .environment(Environment.SANDBOX)
                     .clientCredentialsAuth(new ClientCredentialsAuthModel.Builder(
                             PAYPAL_CLIENT_ID, 
                             PAYPAL_CLIENT_SECRET
                             ).build()
                     )
                     .build();
     }
}
