package ko.maeng.hateoasexam.hypermedia;

import org.springframework.data.repository.CrudRepository;

public interface ManagerRepository extends CrudRepository<Manager, Long> {
    Manager findByEmployeesId(Long id);
}
