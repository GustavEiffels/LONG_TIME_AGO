package com.example.memoserver.service;

import com.example.memoserver.dto.BoardDto;
import com.example.memoserver.entity.Board;

import java.util.List;

public interface BoardService
{
    default Board dtoToEntity(BoardDto dto)
    {
        Board board = Board.builder()
                .board_name(dto.getBoard_name())
                .build();

        return board;
    }

    default BoardDto entityToDto(Board board)
    {
        BoardDto boardDto = BoardDto.builder()
                .board_name(board.getBoard_name())
                .build();
        return boardDto;
    }

    List<Board> getAllBoard();
}
