package ru.practicum.ewm.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import ru.practicum.ewm.statsclient.StatClient;

@SpringBootApplication
@ComponentScan(value = {"ru.practicum.ewm.statsclient","ru.practicum.ewm.main"})
public class MainService {
    public static void main(String[] args) {
        //ConfigurableApplicationContext context =
                SpringApplication.run(MainService.class, args);
        // StatClient sc = context.getBean(StatClient.class);
    }
}
