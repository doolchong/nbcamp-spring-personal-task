package com.sparta.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UpdateDto {

    private int schedule_id;

    @NotBlank(message = "할일은 필수 입력값입니다.")
    @Size(max = 200, message = "할일은 최대 200자 이내로 입력해야 합니다.")
    private String schedule;

    private String assignee_name;

    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    private String password;

    private LocalDateTime modification_date;
    private LocalDateTime updated_date;
}
