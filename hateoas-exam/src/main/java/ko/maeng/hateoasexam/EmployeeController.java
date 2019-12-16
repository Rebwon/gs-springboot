package ko.maeng.hateoasexam;

import ko.maeng.hateoasexam.basic.EmployeeResourceAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class EmployeeController {

    private final EmployeeRepository repository;

    EmployeeController(EmployeeRepository repository) {
        this.repository = repository;
    }

    // URI 구성은 기본적으로 복수개(employees)로 설정.
    // 단일 엔티티에 대한 경로는 기본적으로 URI + /{id}로 설정

    @GetMapping("/employees")
    public ResponseEntity<CollectionModel<EntityModel<Employee>>> findAll() {
        List<EntityModel<Employee>> employees = StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(employee -> new EntityModel<>(employee,
                                linkTo(methodOn(EmployeeController.class).findOne(employee.getId())).withSelfRel(),
                                linkTo(methodOn(EmployeeController.class).findAll()).withRel("employees")))
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                new CollectionModel<>(employees,
                        linkTo(methodOn(EmployeeController.class).findAll()).withSelfRel()));
    }

    @PostMapping("/employees")
    public ResponseEntity<?> newEmployee(@RequestBody Employee employee) {
        try{
            Employee savedEmployee = repository.save(employee);

            EntityModel<Employee> employeeResource = new EntityModel<>(savedEmployee,
                    linkTo(methodOn(EmployeeController.class).findOne(savedEmployee.getId())).withSelfRel());

            return ResponseEntity
                            .created(new URI(employeeResource.getRequiredLink(IanaLinkRelations.SELF).getHref()))
                            .body(employeeResource);
        } catch (URISyntaxException e){
            return ResponseEntity.badRequest().body("Unable to create " + employee);
        }
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<EntityModel<Employee>> findOne(@PathVariable Long id) {
        return repository.findById(id)
                    .map(employee -> new EntityModel<>(employee,
                            linkTo(methodOn(EmployeeController.class).findOne(employee.getId())).withSelfRel(),
                            linkTo(methodOn(EmployeeController.class).findAll()).withRel("employees")))
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<?> updateEmployee(@RequestBody Employee employee, @PathVariable Long id) {
        Employee employeeUpdate = employee;
        employeeUpdate.setId(id);
        repository.save(employeeUpdate);

        Link newlyCreatedLink = linkTo(methodOn(EmployeeController.class).findOne(id)).withSelfRel();

        try{
            return ResponseEntity.noContent().location(new URI(newlyCreatedLink.getHref())).build();
        } catch (URISyntaxException e) {
            return ResponseEntity.badRequest().body("Unable to update " + employeeUpdate);
        }
    }
}
