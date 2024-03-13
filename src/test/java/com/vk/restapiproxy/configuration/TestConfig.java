package com.vk.restapiproxy.configuration;

import com.vk.restapiproxy.IntegrationEnvironment;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import(ServiceConfig.class)
public class TestConfig extends IntegrationEnvironment {
}
