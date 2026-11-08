package com.rayen.tournament;

import com.rayen.tournament.dto.TournamentDto;
import com.rayen.tournament.service.TournamentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TournamentMicroserviceApplicationTests {

    @Autowired
    private TournamentService tournamentService;

    @Test
    void contextLoads() {
        TournamentDto dto = tournamentService.getTournamentByCode("PL");
        assertNotNull(dto);
        assertEquals("Premier League", dto.getTournamentName());
    }
}

