package com.singsiuk.board;

import com.singsiuk.board.entity.Board;
import com.singsiuk.board.entity.Member;
import com.singsiuk.board.entity.Reply;
import com.singsiuk.board.repository.BoardRepository;
import com.singsiuk.board.repository.MemberRepository;
import com.singsiuk.board.repository.ReplyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.Random;

@SpringBootTest
public class RepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ReplyRepository replyRepository;

//    @Test
    public void insertMembers(){
        for(int i =1 ; i<=100;i++){
            Member member = Member.builder()
                    .email("user"+i+"@gmail.com")
                    .password("123"+i+"98@")
                    .name("오소리"+i)
                    .build();
            memberRepository.save(member);
        }
    }

//    @Test
    public void insertBoards(){
        for(int i =1 ; i<=100;i++){
            Member member = Member.builder()
                    .email("user"+i+"@gmail.com")
                    .password("123"+i+"98@")
                    .name("오소리"+i)
                    .build();
            Board board = Board.builder()
                    .title("제목 : "+"인생"+i)
                    .content("내용 : "+"날개다람쥐"+i)
                    .writer(member)
                    .build();
            boardRepository.save(board);

        }
    }

    @Test
    public void insertReplies(){
        Random r = new Random();
        for(long i =1 ; i<=300;i++){
            Board board = Board.builder()
                    .bon((long)(r.nextInt(100)+1))
                    .build();
            Reply reply = Reply.builder()
                    .rno(i)
                    .text("ㅎ"+i)
                    .board(board)
                    .replyer("Customer")
                    .build();
            replyRepository.save(reply);
        }
    }

    // 게시물 1 개를 가져오는 method 테스트
    @Test
    public void eagerLoading(){
        Optional<Board> board = boardRepository.findById(100L);
        if(board.isPresent()){
            System.out.println(board.get());
            System.out.println(board.get().getWriter());
        }
    }
}
