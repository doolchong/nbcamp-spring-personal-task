package com.sparta.schedule.entity;

import com.sparta.schedule.dto.ScheduleRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Schedule {

    private int id;
    private String schedule;
    private String assignee_name;
    private String password;
    private String creation_date;
    private String update_date;

    public Schedule(ScheduleRequestDto requestDto) {
        this.schedule = requestDto.getSchedule();
        this.assignee_name = requestDto.getAssignee_name();
        this.password = requestDto.getPassword();
        this.creation_date = requestDto.getCreation_date();
        this.update_date = requestDto.getUpdate_date();
    }

    public void update(ScheduleRequestDto requestDto){
        this.schedule = requestDto.getSchedule();
        this.assignee_name = requestDto.getAssignee_name();
        this.password = requestDto.getPassword();
        this.creation_date = requestDto.getCreation_date();
        this.update_date = requestDto.getUpdate_date();
    }
}
