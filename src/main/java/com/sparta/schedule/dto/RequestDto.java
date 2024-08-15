package com.sparta.schedule.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RequestDto {

    @NotBlank(message = "할일은 필수 입력값입니다.")
    @Size(max = 200, message = "할일은 최대 200자 이내로 입력해야 합니다.")
    private String schedule;

    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    private String password;

    private int assignee_id;
    private String assignee_name;

    @NotBlank(message = "담당자의 이메일은 필수 입력값입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;
    
    private LocalDateTime registration_date;
    private LocalDateTime modification_date;
}
