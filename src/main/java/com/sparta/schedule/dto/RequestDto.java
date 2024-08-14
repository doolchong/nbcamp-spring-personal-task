package com.sparta.schedule.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RequestDto {

    private String schedule;
    private String password;
    private int assignee_id;
    private String assignee_name;
    private String email;
    private LocalDateTime registration_date;
    private LocalDateTime modification_date;
}
