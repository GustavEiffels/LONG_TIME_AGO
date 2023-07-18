package com.example.memoserver.service.Board;

import com.example.memoserver.entity.Board;
import com.example.memoserver.repository.BoardRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService
{
    private final BoardRepository boardRepository;

    /** 모든 board 의 list 를 가져오는 method  */
    @Override
    public List<Board> board()
    {
        return boardRepository.getAllBoard();
    }
}
