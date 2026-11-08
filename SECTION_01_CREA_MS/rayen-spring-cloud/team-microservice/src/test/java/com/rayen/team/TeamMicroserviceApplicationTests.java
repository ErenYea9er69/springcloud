package com.rayen.team;

import com.rayen.team.dto.TeamDto;
import com.rayen.team.service.TeamService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TeamMicroserviceApplicationTests {

    @Autowired
    private TeamService teamService;

    @Test
    void contextLoads() {
        TeamDto dto = teamService.getTeamById(1L);
        assertNotNull(dto);
        assertEquals("Arsenal", dto.getTeamName());
        assertEquals("Mikel Arteta", dto.getCoachName());
    }
}

