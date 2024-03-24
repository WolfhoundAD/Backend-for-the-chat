package dev.vorstu.controller.v1;

import dev.vorstu.entity.Student;
import dev.vorstu.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
//@CrossOrigin(origins = "*")
@RequestMapping("api/base")
public class BaseController {
    private final StudentRepository studentRepository;

    @Autowired
    public BaseController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
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

    @GetMapping("students")
    public List<Student> getAllStudents(){
        return StreamSupport.stream(studentRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @GetMapping(value =  "students/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Student getStudentById(@PathVariable("id") Long id){
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("student with id " + id + " was not found"));
    }

    @GetMapping("check")
    public String greetJava(){
        return "Hello word" + new Date();
    }
}
