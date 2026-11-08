package com.rayen.team.restControllers;

import com.rayen.team.dto.APIResponseDto;
import com.rayen.team.service.TeamService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/teams")
@AllArgsConstructor
public class TeamController {

    private TeamService teamService;

    @GetMapping("{id}")
    public ResponseEntity<APIResponseDto> getTeamById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(teamService.getTeamById(id), HttpStatus.OK);
    }
}
