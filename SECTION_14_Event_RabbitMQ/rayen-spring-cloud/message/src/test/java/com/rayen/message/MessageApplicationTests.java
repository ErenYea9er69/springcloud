package com.rayen.message;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
        "spring.cloud.stream.function.autostart=false",
        "spring.rabbitmq.listener.simple.auto-startup=false"
})
class MessageApplicationTests {

    @Test
    void contextLoads() {
    }
}
