package com.rayen.message.functions;

import com.rayen.message.dto.TeamMessageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class MessageFunctions {

    private static final Logger log = LoggerFactory.getLogger(MessageFunctions.class);

    @Bean
    public Function<TeamMessageDto, TeamMessageDto> email() {
        return teamMsgDto -> {
            log.info("Sending email with the details : " + teamMsgDto.toString());
            return teamMsgDto;
        };
    }

    @Bean
    public Function<TeamMessageDto, Long> sms() {
        return teamMsgDto -> {
            log.info("Sending sms with the details : " + teamMsgDto.toString());
            return teamMsgDto.teamId();
        };
    }
}

