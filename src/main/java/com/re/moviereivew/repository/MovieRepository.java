package com.re.moviereivew.repository;

import com.re.moviereivew.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie,Long> {

    // 영화 목록 보기를 위한 method
    // Movie 와 Review 를 Join 하고 Movie 로 그룹화해서
    // Movie 정보 와 grade 의 평균 과 Review의 개수를 구해주는 method
    @Query("select m, max(mi), avg(coalesce(r.grade,0)),count(distinct r) " +
            "from Movie m left outer join MovieImage mi on mi.movie = m " +
            "left outer join Review r on r.movie=m " +
            "group by m")
    Page<Object []> getListPage(Pageable pageable);

//    @Query("select m, avg(coalesce(r.grade,0)),count(r) " +
//            "from Movie m " +
//            "left outer join Review r " +
//            "on r.movie=m group by m")
//


    // 특정 영화에 대한 정보를 가져오는 method
    // 영화 정보 (Movie ) , 영화 이미지 정보(MovieImage) , 리뷰정보(grade 의 평균 , 개수)



    @Query("select m, mi, avg(coalesce(r.grade,0)), count(r) " +
            "from Movie m left outer join MovieImage mi on mi.movie = m " +
            "left outer join Review r on r.movie=m" +
            " where m.mno =:mno " +
            " group by mi")
    List<Object []> getMovieWithAll(Long mno);


}
