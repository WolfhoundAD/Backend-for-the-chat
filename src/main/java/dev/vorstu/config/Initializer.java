package dev.vorstu.config;

import dev.vorstu.entity.Password;
import dev.vorstu.entity.Student;
import dev.vorstu.entity.User;
import dev.vorstu.enums.Role;
import dev.vorstu.repository.StudentRepository;
import dev.vorstu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class Initializer {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void initial(){
        // Добавление обычного студента
        Student student1 = new Student("Alex Popovoch", "PO", "+79105559980");
        studentRepository.save(student1);

        User studentUser1 = new User(
                "student1",
                Role.STUDENT,
                new Password("1234"),
                true
        );
        studentUser1.setStudent(student1);
        userRepository.save(studentUser1);

        // Добавление администратора
        Student adminStudent = new Student("Admin", "Admin Group", "+79999999999");
        studentRepository.save(adminStudent);

        User adminUser = new User(
                "admin",
                Role.ADMIN,
                new Password("admin123"),
                true
        );
        adminUser.setStudent(adminStudent);
        userRepository.save(adminUser);
    }
}
