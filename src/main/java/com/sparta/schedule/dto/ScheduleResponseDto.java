package com.sparta.schedule.dto;

import com.sparta.schedule.entity.Assignee;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
public class ScheduleResponseDto {

    private String schedule;
    private String assignee_name;
    private String email;
    private String created_date;
    private String updated_date;

    public ScheduleResponseDto(Assignee assignee) {
        assignee_name = assignee.getAssignee_name();
        email = assignee.getEmail();
        created_date = assignee.getRegistration_date().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        updated_date = assignee.getModification_date().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public ScheduleResponseDto(String schedule, String assignee_name, String email, LocalDateTime created_date, LocalDateTime updated_date) {
        this.schedule = schedule;
        this.assignee_name = assignee_name;
        this.email = email;
        this.created_date = created_date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.updated_date = updated_date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
