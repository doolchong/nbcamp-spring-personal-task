package com.sparta.schedule.repository;

import com.sparta.schedule.dto.ScheduleResponseDto;
import com.sparta.schedule.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;

    public ScheduleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Schedule save(Schedule schedule){

        KeyHolder keyHolder = new GeneratedKeyHolder(); // 기본 키를 반환하기 위한 객체

        String sql = "INSERT INTO schedule (schedule, assignee_name, password, creation_date, update_date) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(con -> {
                    PreparedStatement preparedStatement = con.prepareStatement(sql,
                            Statement.RETURN_GENERATED_KEYS);

                    preparedStatement.setString(1, schedule.getSchedule());
                    preparedStatement.setString(2, schedule.getAssignee_name());
                    preparedStatement.setString(3, schedule.getPassword());
                    preparedStatement.setString(4, schedule.getCreation_date());
                    preparedStatement.setString(5, schedule.getUpdate_date());
                    return preparedStatement;
                },
                keyHolder);

        int id = keyHolder.getKey().intValue();
        schedule.setId(id);

        return schedule;
    }

    public ScheduleResponseDto getScheduleById(int schedule_id){
        String sql = "SELECT * FROM schedule WHERE schedule_id = ?";

        return jdbcTemplate.query(sql, resultSet -> {
            if (resultSet.next()) {
                return new ScheduleResponseDto(resultSet.getString("schedule"),
                        resultSet.getString("assignee_name"),
                        resultSet.getString("creation_date"),
                        resultSet.getString("update_date"));
            } else {
                return null;
            }
        }, schedule_id);
    }
}
