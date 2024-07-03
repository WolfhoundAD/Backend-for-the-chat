package dev.chat;

import dev.chat.service.Initializer;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);
        Initializer initializer = context.getBean(Initializer.class);
        initializer.initialize();
    }
}

        //docker run -p 9000:9000 -p 9001:9001 minio/minio server /data —console-address ":9001"



