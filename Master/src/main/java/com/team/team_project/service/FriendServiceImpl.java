package com.team.team_project.service;

import com.team.team_project.dto.FriendDTO;
import com.team.team_project.entity.Friend;
import com.team.team_project.repository.FriendRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService{
    private final FriendRepository friendRepository;


    @Override
    public Long beAFriend(FriendDTO dto) {
        Friend entity = dtoToEntity(dto);
        friendRepository.save(entity);
        return entity.getFno();
    }
}
