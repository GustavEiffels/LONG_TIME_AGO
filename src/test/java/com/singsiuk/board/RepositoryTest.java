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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
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

//    @Test
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
//    @Test
    public void eagerLoading(){
        Optional<Board> board = boardRepository.findById(100L);
        if(board.isPresent()){
            System.out.println(board.get());
            System.out.println(board.get().getWriter());
        }
    }
//    @Test
    @Transactional
    public void lazyLoading(){
        Optional<Board> board = boardRepository.findById(100L);
        if(board.isPresent()){
            System.out.println(board.get());
            System.out.println(board.get().getWriter());
        }
    }

    // Board 와Member 를 join 하는 method
//    @Test
    public void testJoin(){
        Object result = boardRepository.getBoardWithWriter(100L);
        // 배열로 Return 되기 때문
        Object [] arr = (Object [])result;
        System.out.println(Arrays.toString(arr));
    }

//    @Test
//    public void testJoin2(){
//        List<Object[]> result =boardRepository.getBoardWithReply(90L);
//                for(Object [] ar:result){
//                    System.out.println(Arrays.toString(ar));
//                }
//    }

//    @Test
//    public void testBoardList(){
        // 페이징 조건 생성
        // 0 page 에서 10개의 데이터를
        // bon 의 내림차순으로 가져오기
//        Pageable pageable = PageRequest.of(0,10,
//                Sort.by("bon").descending());
//
//        Page<Object []> result = boardRepository.getBoardWithReplyCount(pageable);
//        result.get().forEach(row -> {
//            Object [] ar =(Object [])row;
//            System.out.println(Arrays.toString(ar));
//        });
//    }

//    @Test
//    public void testBoardList(){
//        Object result = boardRepository.getBoardByBon(90L);
//        Object [] ar = (Object[]) result;
//        System.out.println(Arrays.toString())
//    }


//    @Test
    public void testQuerydls(){
//        boardRepository.search();
        Pageable pageable = PageRequest.of(0,10,Sort.by("bon").descending()
                .and(Sort.by("title").ascending()));

        Page<Object []> result =
                    boardRepository.searchPage("t","1",pageable);
        System.out.println(result);
    }
//    @Test
    public void testReplyList(){
        List<Reply> list =
                    replyRepository.getRepliesByBoardOrderByRno(
                            Board.builder().bon(68L).build());
        System.out.println(list);
    }
    @Test
    public void testReplyList2(){
        List<Reply> list =
                replyRepository.getRepliesByBoardOrderByRno(
                        Board.builder().bon(70L).build());
        System.out.println(list);
    }
}
