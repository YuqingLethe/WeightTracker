package yoki.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
public class WeighttrackerApplication {

    public static void main(String[] args) {
        System.out.println("main");
        SpringApplication.run(WeighttrackerApplication.class, args);
    }

    @RequestMapping("/")
    public String toIndex() {
        return "index";
    }

}