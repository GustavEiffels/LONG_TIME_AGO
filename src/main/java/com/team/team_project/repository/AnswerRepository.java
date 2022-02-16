package com.team.team_project.repository;

import com.team.team_project.entity.Answer;
import com.team.team_project.entity.Question;
import com.team.team_project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
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

    @Query(value="select a.qno from Answer a where a.code =:code")
    Question getQnoForEditInfo(@Param("code")User code);


}
