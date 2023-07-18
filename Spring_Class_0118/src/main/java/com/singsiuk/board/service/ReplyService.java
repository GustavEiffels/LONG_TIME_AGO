package com.singsiuk.board.service;

import com.singsiuk.board.dto.ReplyDTO;
import com.singsiuk.board.entity.Board;
import com.singsiuk.board.entity.Reply;

import java.util.List;

public interface ReplyService {
    // 데이터 삽입을 위한 method
    // 삽입은 항상 기본키를 return 하도록생성
    public Long register(ReplyDTO replyDTO);

    // 데이터 수정을 위한 method
    public void modify(ReplyDTO replyDTO);

    // 데이터 삭제를 위한 method
    public void remove(Long rno);

    // 댓글 목록을 가져오기
    // 글 번호가 필요
    public List<ReplyDTO> getList(Long bon);

    // ReplyDTO를 reply Entity로 변환해주는 method
    default Reply dtoEntity(ReplyDTO replyDTO){
        Board board = Board.builder().bon(replyDTO.getBon()).build();

        Reply reply = Reply.builder()
                .rno(replyDTO.getRno())
                .text(replyDTO.getText())
                .replyer(replyDTO.getReplyer())
                .board(board)
                .build();
        return reply;
    }
    // Reply Entity 를  ReplyDto로 변환해주는 method
    default ReplyDTO entityToDTO(Reply reply){
        ReplyDTO replyDTO = ReplyDTO.builder()
                .rno(reply.getRno())
                .text(reply.getText())
                .replyer(reply.getReplyer())
                .regDate(reply.getRegDate())
                .modDate(reply.getModDate())
                .build();

        return  replyDTO;
    }
}
