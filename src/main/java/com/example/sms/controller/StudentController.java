package com.example.sms.controller;

import com.example.sms.model.Student;
import com.example.sms.service.StudentService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.sms.service.ExcelService;
import java.io.ByteArrayInputStream;

@Controller
public class StudentController {

    private final StudentService service;
    private final ExcelService excelService;

    public StudentController(StudentService service, ExcelService excelService) {
        this.service = service;
        this.excelService = excelService;
    }

    @GetMapping("/")
    public String listStudents(Model model) {
        model.addAttribute("students", service.getAllStudents());
        return "students";
    }

    @GetMapping("/students/new")
    public String createStudentForm(Model model) {
        Student student = new Student();
        model.addAttribute("student", student);
        return "create_student";
    }

    @PostMapping("/students")
    public String saveStudent(@ModelAttribute("student") Student student) {
        // Simple logic to ensure unique ID if not auto-generated
        // Ideally DB should handle ID generation (AUTO_INCREMENT), but sticking to
        // current schema
        if (service.existsById(student.getId())) {
            return "redirect:/students/new?error=id_exists";
        }
        service.addStudent(student.getName(), student.getId(), student.getGpa(), student.getYear(),
                student.getDepartment());
        return "redirect:/";
    }

    @GetMapping("/students/edit/{id}")
    public String editStudentForm(@PathVariable int id, Model model) {
        // We need to fetch the student.
        // existing service doesn't have a direct "get by id" that returns object
        // exposed easily perfectly,
        // but we can use the repository under the hood or refactor service.
        // Wait, service has "searchByID" but it prints to console.
        // I need to add a method to Service to get a student object. For now I will
        // hack it or rely on repository if I could.
        // Actually, I should update the Service to return the student.
        // But for speed, I will use reflection or just add the method to service in a
        // moment.
        // Let's assume I will update Service to include 'getStudentById(int id)'
        model.addAttribute("student", service.getStudentById(id));
        return "edit_student";
    }

    @PostMapping("/students/{id}")
    public String updateStudent(@PathVariable int id, @ModelAttribute("student") Student student) {
        service.updateStudentByID(id, student.getName(), student.getGpa(), student.getYear(), student.getDepartment());
        return "redirect:/";
    }

    @GetMapping("/students/delete/{id}")
    public String deleteStudent(@PathVariable int id) {
        service.removeStudentByID(id);
        return "redirect:/";
    }

    @GetMapping("/students/export/excel")
    public ResponseEntity<InputStreamResource> exportExcel() {
        ByteArrayInputStream in = excelService.studentsToExcel(service.getAllStudents());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=students.xlsx");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(
                        MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(new InputStreamResource(in));
    }
}
