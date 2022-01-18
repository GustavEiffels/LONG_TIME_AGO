package com.singsiuk.board.repository;

import com.singsiuk.board.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,String> {
}
