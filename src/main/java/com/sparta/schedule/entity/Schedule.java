package com.sparta.schedule.entity;

import com.sparta.schedule.dto.RequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class Schedule {

    private int schedule_id;
    private String schedule;
    private String password;
    private int assignee_id;
    private LocalDateTime created_date;
    private LocalDateTime updated_date;

    public Schedule(RequestDto requestDto) {
        schedule = requestDto.getSchedule();
        password = requestDto.getPassword();
        created_date = requestDto.getRegistration_date();
        updated_date = requestDto.getModification_date();
    }
}
