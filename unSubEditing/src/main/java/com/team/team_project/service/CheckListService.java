package com.team.team_project.service;

import com.team.team_project.dto.CheckListDTO;
import com.team.team_project.entity.CheckList;
import com.team.team_project.entity.Plan;
import com.team.team_project.entity.User;

public interface CheckListService {
    default CheckList dtoToEntity(CheckListDTO dto){
        User user = User.builder()
                .code(dto.getCode())
                .build();
        Plan plan = Plan.builder()
                .pno(dto.getPno())
                .build();
        CheckList checkList = CheckList.builder()
                .plan(plan)
                .user(user)
                .done(dto.getDone())
                .todo(dto.getTodo())
                .build();
        return checkList;
    }

    default CheckListDTO EntityToDto(CheckList checkList,User user, Plan plan){
        CheckListDTO checkListDTO = CheckListDTO.builder()
                .code(user.getCode())
                .pno(plan.getPno())
                .done(checkList.getDone())
                .todo(checkList.getTodo())
                .build();
        return checkListDTO;
    }

    public Long showCheckList(CheckListDTO checkListDTO);
}

