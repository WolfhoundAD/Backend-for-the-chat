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

import java.security.Principal;
import java.util.List;

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
    public List<Student> getAllStudents(Principal principal) {
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() != Role.ADMIN) {
            throw new RuntimeException("Access denied");
        }

        Iterable<Student> studentsIterable = studentRepository.findAll();
        return IterableUtils.toList(studentsIterable);
    }

    @PostMapping(value = "students", produces = MediaType.APPLICATION_JSON_VALUE)
    public Student createStudent(@RequestBody Student newStudent, Principal principal) {
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() != Role.ADMIN) {
            throw new RuntimeException("Access denied");
        }

        return studentRepository.save(newStudent);
    }

    @PutMapping(value = "students", produces = MediaType.APPLICATION_JSON_VALUE)
    public Student changeStudent(@RequestBody Student changingStudent, Principal principal) {
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() != Role.ADMIN) {
            throw new RuntimeException("Access denied");
        }

        if (changingStudent.getId() == null) {
            throw new RuntimeException("id of changing student cannot be null");
        }

        return studentRepository.save(changingStudent);
    }

    @DeleteMapping(value = "students/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteStudent(@PathVariable("id") Long id, Principal principal) {
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() != Role.ADMIN) {
            throw new RuntimeException("Access denied");
        }

        studentRepository.deleteById(id);
    }
}
