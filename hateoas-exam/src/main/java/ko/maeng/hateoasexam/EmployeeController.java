package ko.maeng.hateoasexam;

import ko.maeng.hateoasexam.hypermedia.EmployeeRepresentationModelAssembler;
import ko.maeng.hateoasexam.hypermedia.EmployeeWithManager;
import ko.maeng.hateoasexam.hypermedia.EmployeeWithManagerResourceAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@RestController
public class EmployeeController {

    private final EmployeeRepository repository;
    private final EmployeeRepresentationModelAssembler assembler;
    private final EmployeeWithManagerResourceAssembler employeeWithManagerResourceAssembler;

    EmployeeController(EmployeeRepository repository, EmployeeRepresentationModelAssembler assembler,
                       EmployeeWithManagerResourceAssembler employeeWithManagerResourceAssembler) {
        this.repository = repository;
        this.assembler = assembler;
        this.employeeWithManagerResourceAssembler = employeeWithManagerResourceAssembler;
    }

    // URI 구성은 기본적으로 복수개(employees)로 설정.
    // 단일 엔티티에 대한 경로는 기본적으로 URI + /{id}로 설정

    @GetMapping("/employees")
    public ResponseEntity<CollectionModel<EntityModel<Employee>>> findAll() {
        return ResponseEntity.ok(assembler.toCollectionModel(repository.findAll()));
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<EntityModel<Employee>> findOne(@PathVariable Long id) {
        return repository.findById(id)
                    .map(assembler::toModel)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/managers/{id}/employees")
    public ResponseEntity<CollectionModel<EntityModel<Employee>>> findEmployees(@PathVariable Long id) {
        return ResponseEntity.ok(assembler.toCollectionModel(repository.findByManagerId(id)));
    }

    @GetMapping("/employees/detailed")
    public ResponseEntity<CollectionModel<EntityModel<EmployeeWithManager>>> findAllDetailedEmployees() {
        return ResponseEntity.ok(
                    employeeWithManagerResourceAssembler.toCollectionModel(
                            StreamSupport.stream(repository.findAll().spliterator(), false)
                                            .map(EmployeeWithManager::new)
                                            .collect(Collectors.toList())));
    }

    @GetMapping("/employees/{id}/detailed")
    public ResponseEntity<EntityModel<EmployeeWithManager>> findDetailedEmployee(@PathVariable Long id) {
        return repository.findById(id)
                .map(EmployeeWithManager::new)
                .map(employeeWithManagerResourceAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
