package ko.maeng.hateoasexam.basic;

import ko.maeng.hateoasexam.Employee;
import ko.maeng.hateoasexam.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class DatabaseLoader {
    @Bean
    CommandLineRunner init(EmployeeRepository repository) {
        return args -> {
            repository.save(new Employee("Frodo", "Baggins", "ring bearer"));
            repository.save(new Employee("Bilbo", "Baggins", "burglar"));
        };
    }
}
