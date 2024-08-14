package com.sparta.schedule.entity;

import com.sparta.schedule.dto.RequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class Assignee {

    private int assignee_id;
    private String assignee_name;
    private String email;
    private LocalDateTime registration_date;
    private LocalDateTime modification_date;

    public Assignee(RequestDto requestDto){
        assignee_id = requestDto.getAssignee_id();
        assignee_name = requestDto.getAssignee_name();
        email = requestDto.getEmail();
        registration_date = requestDto.getRegistration_date();
        modification_date=requestDto.getModification_date();
    }
}
