package com.sparta.schedule.controller;

import com.sparta.schedule.dto.RequestDto;
import com.sparta.schedule.dto.ScheduleResponseDto;
import com.sparta.schedule.dto.UpdateDto;
import com.sparta.schedule.exception.CustomException;
import com.sparta.schedule.service.ScheduleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Validated
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping("/schedule")
    public ResponseEntity<ScheduleResponseDto> createSchedule(@Valid @RequestBody RequestDto requestDto) {
        ScheduleResponseDto scheduleResponseDto = scheduleService.create(requestDto);
        if (scheduleResponseDto == null) {
            return new ResponseEntity<ScheduleResponseDto>(HttpStatus.CREATED);
        } else {
            return ResponseEntity.ok().body(scheduleResponseDto);
        }
    }


    @GetMapping("/schedule/inquire/{schedule_id}")
    public ScheduleResponseDto getSchedule(@PathVariable int schedule_id) {
        return scheduleService.getSchedule(schedule_id);
    }

    @GetMapping("/schedule/inquire/param")
    public List<ScheduleResponseDto> getSchedulePage(@RequestParam(required = false) String modification_date, @RequestParam(required = false) String assignee_name) {
        return scheduleService.getSchedules(modification_date, assignee_name);
    }

    @PutMapping("/schedule")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(@Valid @RequestBody UpdateDto updateDto) {
        ScheduleResponseDto scheduleResponseDto = scheduleService.updateSchedule(updateDto);
        if (scheduleResponseDto == null) {
            return new ResponseEntity<ScheduleResponseDto>(HttpStatus.CREATED);
        } else {
            return ResponseEntity.ok().body(scheduleResponseDto);
        }
    }

    @DeleteMapping("/schedule/schedule_id/{schedule_id}/password/{password}")
    public void deleteSchedule(@PathVariable int schedule_id, @PathVariable String password) {
        scheduleService.deleteSchedule(schedule_id, password);
    }

    @GetMapping("/schedule/inquire/pagination/param")
    public List<ScheduleResponseDto> getSchedulesPage(@RequestParam int page, @RequestParam int size) {
        return scheduleService.getSchedulesPage(page, size);
    }
}
