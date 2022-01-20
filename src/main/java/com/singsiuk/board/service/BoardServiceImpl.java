package com.singsiuk.board.service;

import com.singsiuk.board.dto.BoardDTO;
import com.singsiuk.board.dto.PageRequestDTO;
import com.singsiuk.board.dto.PageResultDTO;
import com.singsiuk.board.entity.Board;
import com.singsiuk.board.entity.Member;
import com.singsiuk.board.repository.BoardRepository;
import com.singsiuk.board.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Log4j2
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;

    @Override
    public void modify(BoardDTO dto) {
        //데이터를 조회해서 있으면 수정
        Optional<Board> board =
                    boardRepository.findById(dto.getBon());
        if(board.isPresent()){
            board.get().changeTitle(dto.getTitle());
            board.get().changeContent(dto.getContent());

            boardRepository.save(board.get());
        }
    }

    @Override
    public Long register(BoardDTO dto) {
        // 등록을 위해서 Entity 객체로 변환
        Board board = dtoToEntity(dto);
        boardRepository.save(board);
        return board.getBon();
    }

    @Override
    public PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO dto) {
        // Repository 메서드를 호출해서 결과 가져오기
//        log.info(pageRequestDTO);
//        Page<Object []> result = boardRepository.getBoardWithReplyCount(
//                pageRequestDTO.getPageable(Sort.by("bon").descending())
//        );

        Page<Object []> result = boardRepository.searchPage(
                dto.getType(), dto.getKeyword(),
                dto.getPageable(Sort.by("bon").descending()));

        Function<Object[] ,BoardDTO> fn =
                (en->entityToDTO((Board)en[0],(Member)en[1],(Long)en[2]));

        return new PageResultDTO<>(result, fn);
    }

    @Override
    public BoardDTO get(Long bon) {
        Object result = boardRepository.getBoardByBon(bon);
        Object [] ar = (Object [])result;
        return entityToDTO((Board)ar[0],(Member)ar[1],(Long)ar[2]);
    }

    private final ReplyRepository replyRepository;

    @Override
    @Transactional
    public void removeWithReplies(Long bon) {
        //댓글부터 삭제
        replyRepository.deleteByBon(bon);
        boardRepository.deleteById(bon);
    }
}
