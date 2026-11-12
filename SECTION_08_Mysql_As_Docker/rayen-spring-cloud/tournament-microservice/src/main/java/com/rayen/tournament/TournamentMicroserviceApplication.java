package com.rayen.tournament;

import com.rayen.tournament.entities.Tournament;
import com.rayen.tournament.repos.TournamentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TournamentMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TournamentMicroserviceApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(TournamentRepository tournamentRepository) {
        return args -> {
            if (tournamentRepository.findByTournamentCode("PL") == null) {
                tournamentRepository.save(Tournament.builder()
                        .tournamentName("Premier League")
                        .tournamentCode("PL")
                        .build());
            }
            if (tournamentRepository.findByTournamentCode("CL") == null) {
                tournamentRepository.save(Tournament.builder()
                        .tournamentName("Champions League")
                        .tournamentCode("CL")
                        .build());
            }
        };
    }
}

