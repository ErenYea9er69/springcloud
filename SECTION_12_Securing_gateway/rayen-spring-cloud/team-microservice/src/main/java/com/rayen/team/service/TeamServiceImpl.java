package com.rayen.team.service;

import com.rayen.team.dto.APIResponseDto;
import com.rayen.team.dto.TeamDto;
import com.rayen.team.dto.TournamentDto;
import com.rayen.team.entities.Team;
import com.rayen.team.repos.TeamRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@AllArgsConstructor
public class TeamServiceImpl implements TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired(required = false)
    private APIClient apiClient;

    @Autowired(required = false)
    private WebClient webClient;

    @Override
    public APIResponseDto getTeamById(Long id) {
        Team team = teamRepository.findById(id).orElse(null);
        if (team == null) {
            return null;
        }

        TournamentDto tournamentDto = null;
        if (apiClient != null) {
            try {
                tournamentDto = apiClient.getTournamentByCode(team.getTournamentCode());
            } catch (Exception e) {
                tournamentDto = null;
            }
        } else if (webClient != null) {
            try {
                tournamentDto = webClient.get()
                        .uri("http://localhost:8080/api/tournaments/" + team.getTournamentCode())
                        .retrieve()
                        .bodyToMono(TournamentDto.class)
                        .block();
            } catch (Exception e) {
                tournamentDto = null;
            }
        }

        String tname;
        if (tournamentDto == null) {
            tname = "NOT AVAILABLE";
        } else {
            tname = tournamentDto.getTournamentName();
        }

        TeamDto teamDto = new TeamDto(
                team.getId(),
                team.getTeamName(),
                team.getCoachName(),
                team.getTournamentCode(),
                tname
        );

        APIResponseDto apiResponseDto = new APIResponseDto();
        apiResponseDto.setTeamDto(teamDto);
        apiResponseDto.setTournamentDto(tournamentDto);
        return apiResponseDto;
    }
}
