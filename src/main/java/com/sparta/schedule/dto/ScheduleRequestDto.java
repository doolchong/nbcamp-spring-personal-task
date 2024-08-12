package com.sparta.schedule.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleRequestDto {

    private String schedule;
    private String assignee_name;
    private String password;
    private String creation_date;
    private String update_date;
}
