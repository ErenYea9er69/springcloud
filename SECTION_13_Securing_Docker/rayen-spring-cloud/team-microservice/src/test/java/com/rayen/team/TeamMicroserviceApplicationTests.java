package com.rayen.team;

import com.rayen.team.dto.APIResponseDto;
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
        APIResponseDto response = teamService.getTeamById(1L);
        assertNotNull(response);
        assertNotNull(response.getTeamDto());
        assertEquals("Arsenal", response.getTeamDto().getTeamName());
    }
}
