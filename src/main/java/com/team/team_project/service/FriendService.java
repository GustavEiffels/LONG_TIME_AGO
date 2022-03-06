package com.team.team_project.service;

import com.team.team_project.dto.FriendDTO;
import com.team.team_project.entity.Friend;
import com.team.team_project.entity.User;

public interface FriendService {
    default Friend dtoToEntity(FriendDTO dto){
        User request = User.builder()
                .code(dto.getRequest())
                .build();
        User response = User.builder()
                .code(dto.getResponse())
                .build();
        Friend friend =Friend.builder()
                .fno(dto.getFno())
                .response(response)
                .request(request)
                .status(dto.getStatus())
                .build();
        return friend;
    }
    default FriendDTO entityToDto(Friend friend, User request, User response){
        FriendDTO dto = FriendDTO.builder()
                .fno(friend.getFno())
                .status(friend.getStatus())
                .regDate(friend.getRegDate())
                .modDate(friend.getModDate())
                .request(request.getCode())
                .response(response.getCode())
                .build();
        return dto;
    }
    public Long beAFriend(FriendDTO dto);
}
