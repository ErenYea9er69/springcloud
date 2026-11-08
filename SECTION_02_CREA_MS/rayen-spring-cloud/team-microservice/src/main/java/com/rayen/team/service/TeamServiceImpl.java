package com.rayen.team.service;

import com.rayen.team.dto.TeamDto;
import com.rayen.team.entities.Team;
import com.rayen.team.repos.TeamRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class TeamServiceImpl implements TeamService {

    private TeamRepository teamRepository;

    @Override
    public TeamDto getTeamById(Long id) {
        Team team = teamRepository.findById(id).orElse(null);
        if (team == null) {
            return null;
        }
        return new TeamDto(
                team.getId(),
                team.getTeamName(),
                team.getCoachName(),
                team.getTournamentCode()
        );
    }
}

