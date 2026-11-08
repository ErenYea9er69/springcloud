package com.rayen.team.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamDto {
    private Long id;
    private String teamName;
    private String coachName;
    private String tournamentCode;
    private String tournamentName;
    private String email;
    private String mobileNumber;
    private boolean communicationDone;

    public TeamDto(Long id, String teamName, String coachName, String tournamentCode, String tournamentName) {
        this.id = id;
        this.teamName = teamName;
        this.coachName = coachName;
        this.tournamentCode = tournamentCode;
        this.tournamentName = tournamentName;
    }
}
