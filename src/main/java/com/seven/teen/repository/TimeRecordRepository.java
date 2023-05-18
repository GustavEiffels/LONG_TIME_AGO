package com.seven.teen.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.seven.teen.entity.TimeRecord;

public interface TimeRecordRepository extends JpaRepository<TimeRecord,Long> 
{
    
}
