package com.sparta.schedule.service;

import com.sparta.schedule.dto.ScheduleRequestDto;
import com.sparta.schedule.dto.ScheduleResponseDto;
import com.sparta.schedule.entity.Schedule;
import com.sparta.schedule.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public ScheduleResponseDto create(ScheduleRequestDto requestDto){
        LocalDate createAt = LocalDate.now();
        requestDto.setCreation_date(String.valueOf(createAt));
        requestDto.setUpdate_date(String.valueOf(createAt));
        Schedule schedule = new Schedule(requestDto);

        Schedule saveSchedule = scheduleRepository.save(schedule);

        ScheduleResponseDto scheduleResponseDto = new ScheduleResponseDto(schedule);

        return scheduleResponseDto;
    }

    public ScheduleResponseDto getSchedule(int scheduleId) {
        return scheduleRepository.getScheduleById(scheduleId);
    }

    public List<ScheduleResponseDto> getSchedules(String update_date, String assignee_name) {
        return scheduleRepository.getSchedulesList(update_date, assignee_name);
    }

}
