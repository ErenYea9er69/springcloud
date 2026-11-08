package com.rayen.tournament.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TournamentDto {
    private Long id;
    private String tournamentName;
    private String tournamentCode;
}

