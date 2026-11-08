package com.rayen.team;

import com.rayen.team.entities.Team;
import com.rayen.team.repos.TeamRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@EnableFeignClients
public class TeamMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeamMicroserviceApplication.class, args);
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }

    @Bean
    CommandLineRunner commandLineRunner(TeamRepository teamRepository) {
        return args -> {
            teamRepository.save(Team.builder()
                    .teamName("Arsenal")
                    .coachName("Mikel Arteta")
                    .tournamentCode("PL")
                    .build());
        };
    }
}
