package dev.vorstu.config;
import dev.vorstu.entity.Password;
import dev.vorstu.entity.Student;
import dev.vorstu.entity.User;
import dev.vorstu.enums.Role;
import dev.vorstu.repository.StudentRepository;
import dev.vorstu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Initializer {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepository;

    public void initial(){
        studentRepository.save(new Student("Alex Popovoch", "PO", "+79105559980"));
        studentRepository.save(new Student("Dobrinya Nikitich", "AP", "+79104443322"));
        studentRepository.save(new Student("Ilya Myrometc", "PO", "+79108439933"));

        User student = new User(
                null,
                "student",
                Role.STUDENT,
                new Password("1234"), // Удалили кодирование пароля
                true
        );
        userRepository.save(student);
    }
}