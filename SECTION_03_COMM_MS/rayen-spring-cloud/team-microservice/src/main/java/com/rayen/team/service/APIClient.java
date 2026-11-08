package com.rayen.team.service;

import com.rayen.team.dto.TournamentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "http://localhost:8080", value = "TOURNAMENT")
public interface APIClient {
    @GetMapping("api/tournaments/{tournament-code}")
    TournamentDto getTournamentByCode(@PathVariable("tournament-code") String tournamentCode);
}

