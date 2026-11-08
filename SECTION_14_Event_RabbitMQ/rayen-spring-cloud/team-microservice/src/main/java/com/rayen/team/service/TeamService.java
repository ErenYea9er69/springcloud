package com.rayen.team.service;

import com.rayen.team.dto.APIResponseDto;
import com.rayen.team.dto.TeamDto;

public interface TeamService {
    APIResponseDto getTeamById(Long id);
    void createTeam(TeamDto teamDto);
    boolean updateCommunicationStatus(Long teamId);
}
