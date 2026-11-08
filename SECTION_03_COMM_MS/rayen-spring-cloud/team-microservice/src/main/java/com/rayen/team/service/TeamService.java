package com.rayen.team.service;

import com.rayen.team.dto.APIResponseDto;

public interface TeamService {
    APIResponseDto getTeamById(Long id);
}
