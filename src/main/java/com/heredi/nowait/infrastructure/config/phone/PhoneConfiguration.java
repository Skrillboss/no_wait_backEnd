package com.heredi.nowait.infrastructure.config.phone;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:phone.properties")
@Getter
public class PhoneConfiguration {

    @Value("${phone.sid}")
    private String ACCOUNT_SID;

    @Value("${phone.token}")
    private String AUTH_TOKEN;

    @Value("${phone.host}")
    private String phoneHost;
}
