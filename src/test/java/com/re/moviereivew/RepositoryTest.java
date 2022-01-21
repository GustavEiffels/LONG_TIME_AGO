package com.re.moviereivew;

import com.re.moviereivew.entity.Member;
import com.re.moviereivew.entity.Movie;
import com.re.moviereivew.entity.MovieImage;
import com.re.moviereivew.entity.Review;
import com.re.moviereivew.repository.MemberRepository;
import com.re.moviereivew.repository.MovieImageRepository;
import com.re.moviereivew.repository.MovieRepository;
import com.re.moviereivew.repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;
import java.util.stream.IntStream;

@SpringBootTest
public class RepositoryTest {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieImageRepository movieImageRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ReviewRepository reviewRepository;


    // 영화 정보  100 개를 삽입하는 method
//    @Test
    @Transactional
    @Commit
    public void insertMethod(){
        Random r = new Random();
        //for(int i = 1 ; i<=100 ;i++) 와 같다
        IntStream.rangeClosed(1,100).forEach(i->{
            Movie movie = Movie.builder()
                    .title("Movie_"+i)
                    .build();
            movieRepository.save(movie);

            int count = r.nextInt(5);
            IntStream.rangeClosed(0,count).forEach(k->{
                MovieImage movieImage = MovieImage.builder()
                        .uuid(UUID.randomUUID().toString())
                        .imgName("test_"+k+".png")
                        .movie(movie)
                        .build();
                movieImageRepository.save(movieImage);
            });
        });
    }


    // Member 100개 삽입하는 테스트
//    @Test
    public void insertMember(){
        IntStream.rangeClosed(1,100).forEach(i->{
            Member member = Member.builder()
                    .email("user"+i+"@naver.com")
                    .pw("password"+(i*12))
                    .nickname("테스트맨"+i)
                    .build();
            memberRepository.save(member);
        });
    }

    //영화 목록 가져오는 method
    @Test
    public void testListPage(){
        PageRequest pageRequest = PageRequest.of(0,10,
                Sort.by("mno").descending());
        Page<Object []> result =movieRepository.getListPage(pageRequest);
        for(Object [] objects : result.getContent()){
            System.out.println(Arrays.toString(objects));
        }
    }

    // Review 데이터를 200 개 삽입하는 method

//    @Test
    public void reviewTest(){
        Random r = new Random();
        IntStream.rangeClosed(1,200).forEach(i->{
            Long mno = (long)(r.nextInt(100)+1);
            Long bno = (long)(r.nextInt(100)+1);


            // Review 는 Member 와 Movie 가 실제로 존재해야 생성이 가능하다.
            Member member = Member.builder()
                    .mid(mno)
                    .build();
            Movie movie = Movie.builder()
                    .mno(mno)
                    .build();
            Review review =Review.builder()
                    .member(member)
                    .movie(movie)
                    .grade(r.nextInt(5)+1)
                    .text("잘봤습니다"+(i))
                    .build();
            reviewRepository.save(review);
        });
    }

}
