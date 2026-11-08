package com.rayen.team.service;

import com.rayen.team.dto.APIResponseDto;
import com.rayen.team.dto.TeamDto;
import com.rayen.team.dto.TeamMessageDto;
import com.rayen.team.dto.TournamentDto;
import com.rayen.team.entities.Team;
import com.rayen.team.exceptions.ResourceNotFoundException;
import com.rayen.team.repos.TeamRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class TeamServiceImpl implements TeamService {

    private static final Logger log = LoggerFactory.getLogger(TeamServiceImpl.class);

    private final TeamRepository teamRepository;
    private final APIClient apiClient;
    private final WebClient webClient;
    private final StreamBridge streamBridge;

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository,
                           @Autowired(required = false) APIClient apiClient,
                           @Autowired(required = false) WebClient webClient,
                           @Autowired(required = false) StreamBridge streamBridge) {
        this.teamRepository = teamRepository;
        this.apiClient = apiClient;
        this.webClient = webClient;
        this.streamBridge = streamBridge;
    }

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
        teamDto.setEmail(team.getEmail());
        teamDto.setMobileNumber(team.getMobileNumber());
        teamDto.setCommunicationDone(team.isCommunicationDone());

        APIResponseDto apiResponseDto = new APIResponseDto();
        apiResponseDto.setTeamDto(teamDto);
        apiResponseDto.setTournamentDto(tournamentDto);
        return apiResponseDto;
    }

    @Override
    public void createTeam(TeamDto teamDto) {
        Team savedTeam = teamRepository.save(Team.builder()
                .teamName(teamDto.getTeamName())
                .coachName(teamDto.getCoachName())
                .tournamentCode(teamDto.getTournamentCode())
                .email(teamDto.getEmail())
                .mobileNumber(teamDto.getMobileNumber())
                .build());

        sendCommunication(savedTeam);
    }

    private void sendCommunication(Team team) {
        if (streamBridge == null) {
            log.warn("StreamBridge is null, skipping sendCommunication");
            return;
        }
        var teamMsgDto = new TeamMessageDto(team.getId(), team.getEmail(), team.getMobileNumber());
        log.info("Sending Communication request for the details: {}", teamMsgDto);
        var result = streamBridge.send("sendCommunication-out-0", teamMsgDto);
        log.info("Is the Communication request successfully triggered ? : {}", result);
    }

    @Override
    public boolean updateCommunicationStatus(Long teamId) {
        boolean isUpdated = false;
        if (teamId != null) {
            Team team = teamRepository.findById(teamId).orElseThrow(
                    () -> new ResourceNotFoundException("Team", "TeamId", teamId.toString())
            );
            team.setCommunicationDone(true);
            teamRepository.save(team);
            isUpdated = true;
        }
        return isUpdated;
    }
}
