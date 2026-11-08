package com.rayen.tournament.repos;

import com.rayen.tournament.entities.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TournamentRepository extends JpaRepository<Tournament, Long> {
    Tournament findByTournamentCode(String code);
}

