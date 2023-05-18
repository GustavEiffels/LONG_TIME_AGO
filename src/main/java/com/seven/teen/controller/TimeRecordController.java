package com.seven.teen.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.seven.teen.dto.GetTimerDto;
import com.seven.teen.entity.TimeRecord;
import com.seven.teen.service.TimeRecordService;

@RestController
public class TimeRecordController 
{

    @Autowired private TimeRecordService timeRecordService;

    @PostMapping("/stopWatch/saveRecord")
    public List<TimeRecord> handleRecord(@RequestBody GetTimerDto data)
    {
        try {
            TimeRecord t = new TimeRecord();

            t.setRecordDayIdx(data.getNumber());
            t.setRecordTime(data.getTime());
            t.setRecordTitle(data.getTitle());

            timeRecordService.saveTimeRecord(t);
            System.out.println(timeRecordService.getAllUsers());
     
         
             return timeRecordService.getAllUsers();
        } catch (Exception e) {
            
            e.getStackTrace();
        }
        return null;


    }    
}
