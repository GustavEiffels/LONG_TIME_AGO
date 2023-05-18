package com.seven.teen.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seven.teen.entity.TimeRecord;
import com.seven.teen.repository.TimeRecordRepository;

@Service
public class TimeRecordService 
{
    @Autowired private TimeRecordRepository timeRecordRepository;
    
    public List<TimeRecord> getAllUsers() {
        return timeRecordRepository.findAll();
    }

    public void saveTimeRecord(TimeRecord timeRecord)
    {
        timeRecordRepository.save(timeRecord);
    
    }
}
