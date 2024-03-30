package dev.vorstu.controller.v1;

import dev.vorstu.entity.Student;
import dev.vorstu.entity.User;
import dev.vorstu.enums.Role;
import dev.vorstu.repository.StudentRepository;
import dev.vorstu.repository.UserRepository;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
//@CrossOrigin(origins = "*")
@RequestMapping("api/base")
public class BaseController {
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;

    @Autowired
    public BaseController(StudentRepository studentRepository, UserRepository userRepository) {
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("students")
    public List<Student> getAllStudents(){
        return StreamSupport.stream(studentRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @PostMapping(value = "students", produces = MediaType.APPLICATION_JSON_VALUE)
    public Student createStudent(@RequestBody Student newStudent){
        return studentRepository.save(newStudent);
    }

    @PutMapping(value = "students", produces = MediaType.APPLICATION_JSON_VALUE)
    public Student changeStudent(@RequestBody Student changingStudent){
        if (changingStudent.getId() == null) {
            throw new RuntimeException("id of changing student cannot be null");
        }
        return studentRepository.save(changingStudent);
    }


    @DeleteMapping(value = "students/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteStudent(@PathVariable("id") Long id){
        studentRepository.deleteById(id);
    }
    @GetMapping(value =  "students/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Student getStudentById(@PathVariable("id") Long id){
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("student with id " + id + " was not found"));
    }

    @PutMapping(value = "students/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Student updateStudent(@PathVariable Long id, @RequestBody Student updatedStudent){
        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));

        // Update the existing student with new data
        existingStudent.setFio(updatedStudent.getFio());
        existingStudent.setGroup(updatedStudent.getGroup());
        existingStudent.setPhoneNumber(updatedStudent.getPhoneNumber());

        return studentRepository.save(existingStudent);
    }
}
