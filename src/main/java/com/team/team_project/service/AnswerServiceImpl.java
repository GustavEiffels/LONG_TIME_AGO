package com.team.team_project.service;

import com.team.team_project.dto.AnswerDTO;
import com.team.team_project.entity.Answer;
import com.team.team_project.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;


@Service
@Log4j2
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {
    private final AnswerRepository answerRepository;

    @Override
    public Long selfcheckinggood(AnswerDTO dto) {
        Answer entity = dtoToEntity(dto);
        answerRepository.save(entity);
        return entity.getAno();
    }
}
