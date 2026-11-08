package com.rayen.tournament.service;

import com.rayen.tournament.dto.TournamentDto;

public interface TournamentService {
    TournamentDto getTournamentByCode(String code);
}

