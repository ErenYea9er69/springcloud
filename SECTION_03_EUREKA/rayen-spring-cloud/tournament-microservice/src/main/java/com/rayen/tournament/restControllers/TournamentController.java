package com.rayen.tournament.restControllers;

import com.rayen.tournament.dto.TournamentDto;
import com.rayen.tournament.service.TournamentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tournaments")
@AllArgsConstructor
public class TournamentController {

    private TournamentService tournamentService;

    @GetMapping("{code}")
    public ResponseEntity<TournamentDto> getTournamentByCode(@PathVariable("code") String code) {
        return new ResponseEntity<>(tournamentService.getTournamentByCode(code), HttpStatus.OK);
    }
}

