package co.istad.productapisimpledemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

// Customize the metadata response of JPA pagination
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
@SpringBootApplication
public class ProductApiSimpleDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductApiSimpleDemoApplication.class, args);
    }

}
