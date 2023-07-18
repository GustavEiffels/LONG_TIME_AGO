package com.team.team_project.service;

import com.team.team_project.dto.ShareDTO;
import com.team.team_project.entity.Share;
import com.team.team_project.repository.ShareRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Log4j2
public class ShareServiceImpl implements ShareService{
    private final ShareRepository shareRepository;

    @Override
    public Long shareTest(ShareDTO dto) {
        Share entity = dtoToEntity(dto);
        shareRepository.save(entity);
        return entity.getSno();
    }
}

