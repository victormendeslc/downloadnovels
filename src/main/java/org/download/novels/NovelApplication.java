package org.download.novels;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class NovelApplication {

    public static void main(String[] args) throws IOException {
        WebDriverManager.chromedriver().setup();
        SpringApplication.run(NovelApplication.class, args);
    }

}
