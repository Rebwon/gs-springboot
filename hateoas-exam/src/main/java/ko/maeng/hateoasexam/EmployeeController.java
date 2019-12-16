package ko.maeng.hateoasexam;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

    private final EmployeeRepository repository;
    private final EmployeeResourceAssembler assembler;

    EmployeeController(EmployeeRepository repository, EmployeeResourceAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    // URI 구성은 기본적으로 복수개(employees)로 설정.
    // 단일 엔티티에 대한 경로는 기본적으로 URI + /{id}로 설정

    @GetMapping("/employees")
    public ResponseEntity<CollectionModel<EntityModel<Employee>>> findAll() {
        return ResponseEntity.ok(
                this.assembler.toCollectionModel(this.repository.findAll()));
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<EntityModel<Employee>> findOne(@PathVariable Long id) {
        return this.repository.findById(id)
                        .map(this.assembler::toModel)
                        .map(ResponseEntity::ok)
                        .orElse(ResponseEntity.notFound().build());
    }
}
