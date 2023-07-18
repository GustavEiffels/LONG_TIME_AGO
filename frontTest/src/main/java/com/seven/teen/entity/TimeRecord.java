package com.seven.teen.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "timeRecord")
public class TimeRecord 
{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long timerRecordId;

    @Column(name = "timeRecord_DayIndex")
    private Integer recordDayIdx;

    @Column(name = "timeRecord_Title")
    private String recordTitle;

    @Column(name = "timerRecord_Time")
    private String recordTime;

    
}
