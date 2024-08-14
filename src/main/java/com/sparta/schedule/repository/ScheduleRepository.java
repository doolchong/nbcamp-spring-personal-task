package com.sparta.schedule.repository;

import com.sparta.schedule.dto.ScheduleResponseDto;
import com.sparta.schedule.dto.UpdateDto;
import com.sparta.schedule.entity.Assignee;
import com.sparta.schedule.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;

    public ScheduleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean isExist(int assignee_id){
        String sql = "SELECT * FROM assignee WHERE assignee_id = ?";

        return Boolean.TRUE.equals(jdbcTemplate.query(sql, resultSet -> {
            if (resultSet.next()) {
                return false;
            } else {
                return true;
            }
        }, assignee_id));
    }

    public void saveAssignee(Assignee assignee) {

        KeyHolder keyHolder = new GeneratedKeyHolder(); // 기본 키를 반환하기 위한 객체

        String sql = "INSERT INTO assignee (assignee_name, email, registration_date, modification_date) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(con -> {
                    PreparedStatement preparedStatement = con.prepareStatement(sql,
                            Statement.RETURN_GENERATED_KEYS);

                    preparedStatement.setString(1, assignee.getAssignee_name());
                    preparedStatement.setString(2, assignee.getEmail());
                    preparedStatement.setTimestamp(3, Timestamp.valueOf(assignee.getRegistration_date()));
                    preparedStatement.setTimestamp(4, Timestamp.valueOf(assignee.getModification_date()));
                    return preparedStatement;
                },
                keyHolder);

        int id = keyHolder.getKey().intValue();
        assignee.setAssignee_id(id);
    }

    public Schedule saveSchedule(Schedule schedule) {

        KeyHolder keyHolder = new GeneratedKeyHolder(); // 기본 키를 반환하기 위한 객체

        String sql = "INSERT INTO schedule (schedule, password, assignee_id, created_date, updated_date) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(con -> {
                    PreparedStatement preparedStatement = con.prepareStatement(sql,
                            Statement.RETURN_GENERATED_KEYS);

                    preparedStatement.setString(1, schedule.getSchedule());
                    preparedStatement.setString(2, schedule.getPassword());
                    preparedStatement.setInt(3, schedule.getAssignee_id());
                    preparedStatement.setTimestamp(4, Timestamp.valueOf(schedule.getCreated_date()));
                    preparedStatement.setTimestamp(5, Timestamp.valueOf(schedule.getUpdated_date()));
                    return preparedStatement;
                },
                keyHolder);

        int id = keyHolder.getKey().intValue();
        schedule.setSchedule_id(id);

        return schedule;
    }

    public ScheduleResponseDto getScheduleById(int schedule_id) {
        String sql = "SELECT * FROM schedule AS s JOIN assignee AS a ON s.assignee_id = s.assignee_id WHERE s.schedule_id = ?";

        return jdbcTemplate.query(sql, resultSet -> {
            if (resultSet.next()) {
                return new ScheduleResponseDto(resultSet.getString("s.schedule") ,
                        resultSet.getString("a.assignee_name"),
                        resultSet.getString("a.email"),
                        resultSet.getTimestamp("s.created_date").toLocalDateTime(),
                        resultSet.getTimestamp("s.updated_date").toLocalDateTime());
            } else {
                return null;
            }
        }, schedule_id);

    }

    public List<ScheduleResponseDto> getSchedulesList(String modification_date, String assignee_name) {
        String sql = "SELECT * FROM schedule AS s JOIN assignee AS a ON s.assignee_id = a.assignee_id";
        List<Object> params = new ArrayList<>();

        // 조건에 따라 SQL 쿼리와 파라미터를 설정
        if (modification_date != null) {
            sql += " WHERE DATE_FORMAT(a.modification_date, '%Y-%m-%d') = ?";
            params.add(modification_date);
        }
        if (assignee_name != null) {
            // 이미 WHERE 절이 추가된 경우 AND를 붙임
            if (params.isEmpty()) {
                sql += " WHERE a.assignee_name = ?";
            } else {
                sql += " AND a.assignee_name = ?";
            }
            params.add(assignee_name);
        }

        return jdbcTemplate.query(sql, params.toArray(), new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                String schedule = rs.getString("s.schedule");
                String assignee_name = rs.getString("a.assignee_name");
                String email = rs.getString("a.email");
                LocalDateTime created_date = rs.getTimestamp("s.created_date").toLocalDateTime();
                LocalDateTime updated_date = rs.getTimestamp("s.updated_date").toLocalDateTime();
                return new ScheduleResponseDto(schedule, assignee_name, email, created_date, updated_date);
            }
        });
    }

    public ScheduleResponseDto updateById(UpdateDto updateDto) {
        String sql = "UPDATE schedule AS s JOIN assignee AS a ON s.assignee_id = a.assignee_id SET s.schedule = ?, a.assignee_name = ?, s.updated_date = ?, a.modification_date = ? WHERE schedule_id = ? AND password = ?";
        jdbcTemplate.update(sql, updateDto.getSchedule(), updateDto.getAssignee_name(), updateDto.getUpdated_date(), updateDto.getModification_date(), updateDto.getSchedule_id(), updateDto.getPassword());

        sql = "SELECT * FROM schedule AS s JOIN assignee AS a ON s.assignee_id = a.assignee_id WHERE s.schedule_id = ? AND s.password = ?";
        return jdbcTemplate.query(sql, resultSet -> {
            if (resultSet.next()) {
                return new ScheduleResponseDto(resultSet.getString("s.schedule"),
                        resultSet.getString("a.assignee_name"), resultSet.getString("a.email"),
                        resultSet.getTimestamp("s.created_date").toLocalDateTime(), updateDto.getUpdated_date());
            } else {
                return null;
            }
        }, updateDto.getSchedule_id(), updateDto.getPassword());
    }

    public void deleteById(int schedule_id, String password) {
        String sql = "DELETE FROM schedule WHERE schedule_id = ? AND password = ?";
        jdbcTemplate.update(sql, schedule_id, password);
    }
}
