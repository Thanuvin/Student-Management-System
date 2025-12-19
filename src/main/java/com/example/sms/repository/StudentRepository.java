package com.example.sms.repository;

import com.example.sms.model.Student;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StudentRepository {

    private final JdbcTemplate jdbcTemplate;

    public StudentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Student> findAll() {
        return jdbcTemplate.query("SELECT * FROM students", new BeanPropertyRowMapper<>(Student.class));
    }

    public Student findById(int id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM students WHERE id = ?",
                    new BeanPropertyRowMapper<>(Student.class), id);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Student> findByYear(String year) {
        return jdbcTemplate.query("SELECT * FROM students WHERE year = ?", new BeanPropertyRowMapper<>(Student.class),
                year);
    }

    public List<Student> findByDepartment(String department) {
        return jdbcTemplate.query("SELECT * FROM students WHERE department = ?",
                new BeanPropertyRowMapper<>(Student.class), department);
    }

    public List<Student> findByGpa(double gpa) {
        return jdbcTemplate.query("SELECT * FROM students WHERE gpa = ?", new BeanPropertyRowMapper<>(Student.class),
                gpa);
    }

    public int save(Student student) {
        return jdbcTemplate.update("INSERT INTO students (id, name, gpa, year, department) VALUES (?, ?, ?, ?, ?)",
                student.getId(), student.getName(), student.getGpa(), student.getYear(), student.getDepartment());
    }

    public int update(Student student) {
        return jdbcTemplate.update("UPDATE students SET name = ?, gpa = ?, year = ?, department = ? WHERE id = ?",
                student.getName(), student.getGpa(), student.getYear(), student.getDepartment(), student.getId());
    }

    public int deleteById(int id) {
        return jdbcTemplate.update("DELETE FROM students WHERE id = ?", id);
    }

    public int count() {
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM students", Integer.class);
        return count != null ? count : 0;
    }
}
