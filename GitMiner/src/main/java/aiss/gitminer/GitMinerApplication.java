package aiss.gitminer;

import graphql.kickstart.spring.web.boot.GraphQLExtendedScalarsInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GitMinerApplication {

    public static void main(String[] args) {
        SpringApplication.run(GitMinerApplication.class, args);
    }

}
