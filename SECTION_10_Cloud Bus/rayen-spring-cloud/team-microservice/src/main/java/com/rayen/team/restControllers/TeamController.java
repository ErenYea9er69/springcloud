package com.rayen.team.restControllers;

import com.rayen.team.config.Configuration;
import com.rayen.team.dto.APIResponseDto;
import com.rayen.team.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    private final TeamService teamService;

    @Autowired(required = false)
    private Configuration configuration;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping("{id}")
    public ResponseEntity<APIResponseDto> getTeamById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(teamService.getTeamById(id), HttpStatus.OK);
    }

    @GetMapping("/author")
    public ResponseEntity<String> retrieveAuthorInfo() {
        if (configuration == null) {
            return ResponseEntity.status(HttpStatus.OK).body("rayen team rayenb_team@yahoo.com");
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(configuration.getName() + " " + configuration.getEmail());
    }
}
