package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.core.jackson.ModelResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({
        "org.example",
        "my.plugin"
})
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
    
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
    
    @Bean
    public ModelResolver customModelResolver(ObjectMapper objectMapper) {
        return new CustomModelResolver(objectMapper);
    }
}
