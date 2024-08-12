package com.sparta.schedule.repository;

import com.sparta.schedule.dto.ScheduleResponseDto;
import com.sparta.schedule.dto.ScheduleUpdateDto;
import com.sparta.schedule.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;

    public ScheduleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Schedule save(Schedule schedule) {

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

    public ScheduleResponseDto getScheduleById(int schedule_id) {
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

    public List<ScheduleResponseDto> getSchedulesList(String update_date, String assignee_name) {
        String sql = "SELECT * FROM schedule";
        List<Object> params = new ArrayList<>();

        // 조건에 따라 SQL 쿼리와 파라미터를 설정
        if (update_date != null) {
            sql += " WHERE update_date = ?";
            params.add(update_date);
        }
        if (assignee_name != null) {
            // 이미 WHERE 절이 추가된 경우 AND를 붙임
            if (params.isEmpty()) {
                sql += " WHERE assignee_name = ?";
            } else {
                sql += " AND assignee_name = ?";
            }
            params.add(assignee_name);
        }

        return jdbcTemplate.query(sql, params.toArray(), new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                String schedule = rs.getString("schedule");
                String assignee_name = rs.getString("assignee_name");
                String creation_date = rs.getString("creation_date");
                String update_date = rs.getString("update_date");
                return new ScheduleResponseDto(schedule, assignee_name, creation_date, update_date);
            }
        });
    }

    public ScheduleResponseDto updateById(ScheduleUpdateDto updateDto) {
        String sql = "UPDATE schedule SET schedule = ?, assignee_name = ?, update_date = ? WHERE schedule_id = ? AND password = ?";
        jdbcTemplate.update(sql, updateDto.getSchedule(), updateDto.getAssignee_name(), updateDto.getUpdate_date(), updateDto.getSchedule_id(), updateDto.getPassword());
        sql = "SELECT * FROM schedule WHERE schedule_id = ?";
        return jdbcTemplate.query(sql, resultSet -> {
            if (resultSet.next()) {
                return new ScheduleResponseDto(resultSet.getString("schedule"),
                        resultSet.getString("assignee_name"),
                        resultSet.getString("creation_date"), updateDto.getUpdate_date());
            } else {
                return null;
            }
        }, updateDto.getSchedule_id());
    }

    public void deleteById(int schedule_id, String password) {
        String sql = "DELETE FROM schedule WHERE schedule_id = ? AND password = ?";
        jdbcTemplate.update(sql, schedule_id, password);
    }
}
