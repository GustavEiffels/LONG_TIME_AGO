package com.example.memoserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MemoServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MemoServerApplication.class, args);
    }

}
