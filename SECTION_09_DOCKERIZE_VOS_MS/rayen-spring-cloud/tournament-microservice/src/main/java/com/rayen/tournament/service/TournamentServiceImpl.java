package com.rayen.tournament.service;

import com.rayen.tournament.dto.TournamentDto;
import com.rayen.tournament.entities.Tournament;
import com.rayen.tournament.repos.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TournamentServiceImpl implements TournamentService {

    @Autowired
    private TournamentRepository tournamentRepository;

    @Override
    public TournamentDto getTournamentByCode(String code) {
        Tournament tournament = tournamentRepository.findByTournamentCode(code);
        if (tournament == null) {
            return null;
        }
        return new TournamentDto(
                tournament.getId(),
                tournament.getTournamentName(),
                tournament.getTournamentCode()
        );
    }
}

