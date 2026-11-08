package com.rayen.team.service;

import com.rayen.team.dto.TournamentDto;
import org.springframework.stereotype.Component;

@Component
public class TournamentFallBack implements APIClient {
    @Override
    public TournamentDto getTournamentByCode(String tournamentCode) {
        return null;
    }
}
