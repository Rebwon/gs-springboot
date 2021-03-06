package ko.maeng.hateoasexam;

import ko.maeng.hateoasexam.hypermedia.Manager;
import ko.maeng.hateoasexam.hypermedia.ManagerRepository;
import ko.maeng.hateoasexam.rest.Order;
import ko.maeng.hateoasexam.rest.OrderRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DatabaseLoader {
//    @Bean
//    CommandLineRunner init(EmployeeRepository employeeRepository, ManagerRepository managerRepository) {
//        return args -> {
//            Manager gandalf = managerRepository.save(new Manager("Gandalf"));
//
//            Employee frodo = employeeRepository.save(new Employee("Frodo", "ring bearer", gandalf));
//            Employee bilbo = employeeRepository.save(new Employee("Bilbo", "burglar", gandalf));
//
//            gandalf.setEmployees(Arrays.asList(frodo, bilbo));
//            managerRepository.save(gandalf);
//
//            Manager saruman = managerRepository.save(new Manager("Saruman"));
//
//            Employee sam = employeeRepository.save(new Employee("Sam", "gardener", saruman));
//
//            saruman.setEmployees(Arrays.asList(sam));
//
//            managerRepository.save(saruman);
//        };
//    }

    @Bean
    CommandLineRunner init(OrderRepository repository){
        return args -> {
            repository.save(new Order("grande mocha"));
            repository.save(new Order("venti hazelnut machiatto"));
        };
    }
}
