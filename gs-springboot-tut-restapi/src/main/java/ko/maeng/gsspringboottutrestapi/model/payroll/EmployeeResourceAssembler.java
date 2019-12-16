package ko.maeng.gsspringboottutrestapi.model.payroll;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class EmployeeResourceAssembler implements ResourceAssembler<Employee, Resource<Employee>> {
    // ResourceAssembler를 통해 Employee객체를 Resource로 변환시켜줌.
    // 이렇게 굳이 toResource를 통해 두개의 링크를 생성해야 하는 이유는
    // RestAPI가 시간이 지남에 따라 기존 링크는 유지하고 새 링크를 추가할 수 있게 하기 위함이다.
    // 이로써 레거시 클라이언트는 기존 링크를 사용하고, 새로운 클라이언트는 새 링크를 사용하면 된다.
    // 링크 구조가 유지되기 때문에 여전히 서비스는 상호작용이 가능하다.
    @Override
    public Resource<Employee> toResource(Employee employee) {
        // linkTo() 링크를 생성
        // methodOn() 링크에 특정 메소드를 가리킨다.
        return new Resource<>(employee,
                linkTo(methodOn(EmployeeController.class).one(employee.getId())).withSelfRel(), // localhost:8080/employees/{id} 링크를 생성
                linkTo(methodOn(EmployeeController.class).all()).withRel("employees")); // localhost:8080/employees 링크를 생성.
    }
}
