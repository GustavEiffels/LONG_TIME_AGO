package com.re.moviereivew.repository;

import com.re.moviereivew.entity.Member;
import com.re.moviereivew.entity.Movie;
import com.re.moviereivew.entity.Review;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {
    // 영화 정보를 가지고 모든 영화의 모든 리뷰를 가져오는 method
    @EntityGraph(attributePaths = {"member"},type = EntityGraph.EntityGraphType.FETCH)
    List<Review> findByMovie(Movie movie);



    // Member가 지워질 때 같이 데이터를 지우는 Method
    void deleteByMember(Member member);

    // Movie 가 지워질 때 같이 데이터를 지우는 method
    void deleteByMovie(Movie movie);
}
