package com.sparta.schedule.service;

import com.sparta.schedule.dto.RequestDto;
import com.sparta.schedule.dto.ScheduleResponseDto;
import com.sparta.schedule.dto.UpdateDto;
import com.sparta.schedule.entity.Assignee;
import com.sparta.schedule.entity.Schedule;
import com.sparta.schedule.exception.CustomException;
import com.sparta.schedule.repository.ScheduleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public ScheduleResponseDto create(RequestDto requestDto) {
        LocalDateTime registration_date = LocalDateTime.now();
        requestDto.setRegistration_date(registration_date);
        requestDto.setModification_date(registration_date);

        boolean isExist = scheduleRepository.isExist(requestDto.getAssignee_id());
        Assignee assignee = new Assignee(requestDto);
        ScheduleResponseDto responseDto = new ScheduleResponseDto(assignee);

        if (isExist) {
            scheduleRepository.saveAssignee(assignee);
        }

        Schedule schedule = new Schedule(requestDto);
        schedule.setAssignee_id(assignee.getAssignee_id());
        responseDto.setSchedule(scheduleRepository.saveSchedule(schedule).getSchedule());

        return responseDto;
    }

    public ScheduleResponseDto getSchedule(int scheduleId) {
        ScheduleResponseDto scheduleResponseDto = scheduleRepository.getScheduleById(scheduleId);
        if (scheduleResponseDto == null) {
            throw new CustomException("HTTP 상태 코드: " + HttpStatus.FORBIDDEN.value() + "\n입력하신 ID와 일치하는 일정 정보가 없어 조회할 수 없습니다.");
        }
        return scheduleResponseDto;
    }

    public List<ScheduleResponseDto> getSchedules(String modification_date, String assignee_name) {
        List<ScheduleResponseDto> scheduleResponseDtoList = scheduleRepository.getSchedulesList(modification_date, assignee_name);
        if (scheduleResponseDtoList.toString().equals("[]")) {
            throw new CustomException("HTTP 상태 코드: " + HttpStatus.FORBIDDEN.value() + "\n입력하신 이름이나 수정한 날짜와 일치하는 일정 정보가 없어 조회할 수 없습니다.");
        }
        return scheduleResponseDtoList;
    }

    public ScheduleResponseDto updateSchedule(UpdateDto updateDto) throws CustomException {
        LocalDateTime registration_date = LocalDateTime.now();
        updateDto.setModification_date(registration_date);
        updateDto.setUpdated_date(registration_date);
        return scheduleRepository.updateById(updateDto);
    }

    public void deleteSchedule(int schedule_id, String password) {
        scheduleRepository.deleteById(schedule_id, password);
    }

    public List<ScheduleResponseDto> getSchedulesPage(int page, int size) {
        return scheduleRepository.getSchedulesPage(page, size);
    }

}
