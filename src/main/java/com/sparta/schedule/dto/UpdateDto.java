package com.sparta.schedule.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UpdateDto {

    private int schedule_id;
    private String schedule;
    private String assignee_name;
    private String password;
    private LocalDateTime modification_date;
    private LocalDateTime updated_date;
}
