package dev.vorstu;

import dev.vorstu.config.Initializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VorstuApplication {

    private  static Initializer initiator;

    @Autowired
    public void setInitialLoader(Initializer initiator){
        VorstuApplication.initiator = initiator;
    }

    public static void main(String[] args) {
        SpringApplication.run(VorstuApplication.class, args);
        initiator.initial();
    }
}
