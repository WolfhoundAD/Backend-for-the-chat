package dev.chat;

import dev.chat.service.Initializer;
import io.minio.MinioClient;
import io.minio.errors.MinioException;
import io.minio.messages.Bucket;
import org.apache.maven.plugin.lifecycle.Execution;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);
        Initializer initializer = context.getBean(Initializer.class);
        initializer.initialize();

        MinioClient minioClient = demo();
        try{
            List<Bucket> bList = minioClient.listBuckets();
            System.out.println("Connection success, total buskets: " + bList.size());
        } catch (MinioException e){
            System.out.println("Connection faild: " + e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static MinioClient demo(){
        MinioClient minioClient = MinioClient.builder()
                .endpoint("http://127.0.0.1:9000")
                .credentials("minioadmin", "minioadmin")
                .build();
        return minioClient;
    }
}
