package com.team.team_project.service;

import com.team.team_project.dto.CheckListDTO;
import com.team.team_project.entity.CheckList;
import com.team.team_project.repository.CheckListRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
@Service
@Log4j2
@RequiredArgsConstructor
public class CheckListServiceImpl implements CheckListService {
    private final CheckListRepository checkListRepository;

    @Override
    public Long showCheckList(CheckListDTO dto) {
        CheckList entity = dtoToEntity(dto);
        checkListRepository.save(entity);
        return entity.getCno();
    }
}