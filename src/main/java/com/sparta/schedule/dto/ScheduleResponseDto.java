package com.sparta.schedule.dto;

import com.sparta.schedule.entity.Schedule;
import lombok.Getter;

@Getter
public class ScheduleResponseDto {

    private String schedule;
    private String assignee_name;
    private String creation_date;
    private String update_date;

    public ScheduleResponseDto(Schedule schedule) {
        this.schedule = schedule.getSchedule();
        this.assignee_name = schedule.getAssignee_name();
        this.creation_date = schedule.getCreation_date();
        this.update_date = schedule.getUpdate_date();
    }

    public ScheduleResponseDto(String schedule, String assignee_name, String creation_date, String update_date) {
        this.schedule = schedule;
        this.assignee_name = assignee_name;
        this.creation_date = creation_date;
        this.update_date = update_date;
    }
}
