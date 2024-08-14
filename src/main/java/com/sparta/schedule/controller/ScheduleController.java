package com.sparta.schedule.controller;

import com.sparta.schedule.dto.RequestDto;
import com.sparta.schedule.dto.ScheduleResponseDto;
import com.sparta.schedule.dto.UpdateDto;
import com.sparta.schedule.entity.Schedule;
import com.sparta.schedule.service.ScheduleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping("/schedule")
    public ScheduleResponseDto createSchedule(@RequestBody RequestDto requestDto){
        return scheduleService.create(requestDto);
    }


    @GetMapping("/schedule/inquire/{schedule_id}")
    public ScheduleResponseDto getSchedule(@PathVariable int schedule_id){
        return scheduleService.getSchedule(schedule_id);
    }

    @GetMapping("/schedule/inquire/param")
    public List<ScheduleResponseDto> getSchedules(@RequestParam(required = false) String modification_date, @RequestParam(required = false) String assignee_name) {
        return scheduleService.getSchedules(modification_date, assignee_name);
    }

    @PutMapping("/schedule")
    public ScheduleResponseDto updateSchedule(@RequestBody UpdateDto updateDto){
        return scheduleService.updateSchedule(updateDto);
    }

    @DeleteMapping("/schedule/schedule_id/{schedule_id}/password/{password}")
    public void deleteSchedule(@PathVariable int schedule_id, @PathVariable String password){
        scheduleService.deleteSchedule(schedule_id, password);
    }
}
