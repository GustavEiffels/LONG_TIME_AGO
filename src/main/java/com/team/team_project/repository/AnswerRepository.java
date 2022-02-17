package com.team.team_project.repository;

import com.team.team_project.entity.Answer;
import com.team.team_project.entity.Question;
import com.team.team_project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import javax.transaction.Transactional;


public interface AnswerRepository extends JpaRepository<Answer,Long> {

    @Query(value="select a.qno from Answer a where a.answer = :answer")
    Question getQnoByUseContext(@Param("answer") String answer);

    @Query(value="select a.code from Answer a where a.qno = :qno")
    User getCodeByUseQno(@Param("qno") Question qno);


    @Query(value="select a.answer from Answer a where a.code = :code")
    String getAnswerForEditInfo(@Param("code")User code);


    // User Code 를 입력하면 qno 가 출력되도록 설정
    @Query(value="select a.qno from Answer a where a.code =:code")
    Question getQnoForEditInfo(@Param("code")User code);

    // User Code 를 입력하면 ano 가 출력 되도록 설정
    @Query(value = "select ano from Answer where code=:code")
    Long anoByCode(@Param("code")User code);

    // User Code를 입력하면 Answer Table 의 answer 를 수정할 수 있도록 생성
    @Modifying
    @Transactional
    @Query(value = "update Answer set answer=:answer where ano=:ano")
    int updateUserAnswer(@Param("ano") Long ano, @Param("answer")String answer);
}
