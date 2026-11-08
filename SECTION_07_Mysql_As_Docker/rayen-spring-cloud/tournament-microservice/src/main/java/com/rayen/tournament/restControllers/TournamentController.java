package com.rayen.tournament.restControllers;

import com.rayen.tournament.config.Configuration;
import com.rayen.tournament.dto.TournamentDto;
import com.rayen.tournament.service.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tournaments")
public class TournamentController {

    private final TournamentService tournamentService;

    @Value("${build.version:1.0}")
    private String buildVersion;

    @Autowired(required = false)
    private Configuration configuration;

    public TournamentController(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    @GetMapping("{code}")
    public ResponseEntity<TournamentDto> getTournamentByCode(@PathVariable("code") String code) {
        return new ResponseEntity<>(tournamentService.getTournamentByCode(code), HttpStatus.OK);
    }

    @GetMapping("/version")
    public ResponseEntity<String> version() {
        return ResponseEntity.status(HttpStatus.OK).body(buildVersion);
    }

    @GetMapping("/author")
    public ResponseEntity<String> retrieveAuthorInfo() {
        if (configuration == null) {
            return ResponseEntity.status(HttpStatus.OK).body("rayen rayenb@yahoo.com");
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(configuration.getName() + " " + configuration.getEmail());
    }
}
