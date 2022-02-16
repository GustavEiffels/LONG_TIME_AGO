package com.team.team_project.service;

import com.team.team_project.dto.ShareDTO;
import com.team.team_project.entity.Plan;
import com.team.team_project.entity.Share;
import com.team.team_project.entity.User;

public interface ShareService {
    default Share dtoToEntity(ShareDTO dto){
        User user = User.builder()
                .code(dto.getCode())
                .build();
        Plan plan = Plan.builder()
                .pno(dto.getPno())
                .build();
        Share entity = Share.builder()
                .sno(dto.getSno())
                .code(user)
                .pno(plan)
                .build();
        return entity;
    }
    default ShareDTO entityToDto(Share share, User user, Plan plan){
        ShareDTO dto = ShareDTO.builder()
                .code(user.getCode())
                .pno(plan.getPno())
                .sno(share.getSno())
                .build();
        return dto;
    }
    public Long shareTest(ShareDTO dto);
}
