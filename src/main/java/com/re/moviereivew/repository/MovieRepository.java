package com.re.moviereivew.repository;

import com.re.moviereivew.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MovieRepository extends JpaRepository<Movie,Long> {

    // 영화 목록 보기를 위한 method
    // Movie 와 Review 를 Join 하고 Movie 로 그룹화해서
    // Movie 정보 와 grade 의 평균 과 Review의 개수를 구해주는 method
    @Query("select m, avg(coalesce(r.grade,0)),count(r) " +
            "from Movie m " +
            "left outer join Review r " +
            "on r.movie=m group by m")
    Page<Object []> getListPage(Pageable pageable);
}
