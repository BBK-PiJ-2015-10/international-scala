package org.tc.rs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.tc.rs.helper.MyTester;
import org.tc.rs.helper.Tester;

@SpringBootApplication
public class RsApplication {


    public static void main(String[] args) {

        System.out.println("BURITA");
        SpringApplication.run(RsApplication.class, args);

    }

    @Bean
    public CommandLineRunner demo(MyTester myTester) {
        return (args) -> {

            myTester.execute();
        };
    }

}
