package com.rayen.team.functions;

import com.rayen.team.service.TeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class TeamFunctions {

    private static final Logger log = LoggerFactory.getLogger(TeamFunctions.class);

    @Bean
    public Consumer<Long> updateCommunication(TeamService teamService) {
        return teamId -> {
            log.info("Updating Communication status for the team Id : " + teamId.toString());
            teamService.updateCommunicationStatus(teamId);
        };
    }
}

