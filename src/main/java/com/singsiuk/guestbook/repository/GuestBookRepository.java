package com.singsiuk.guestbook.repository;

import com.singsiuk.guestbook.Entity.GuestBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

//간단한 Crud 작업 가능
public interface GuestBookRepository extends JpaRepository<GuestBook,Long>
, QuerydslPredicateExecutor<GuestBook> {

}
