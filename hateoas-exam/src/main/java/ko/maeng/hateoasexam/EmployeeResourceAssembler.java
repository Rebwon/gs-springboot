package ko.maeng.hateoasexam;

import org.springframework.stereotype.Component;

@Component
public class EmployeeResourceAssembler extends SimpleIdentifiableRepresentationModelAssembler<Employee> {

    // EmployeeController와 Employee 객체와 연결해서 사용한다.
    // SimpleIdentifiableRepresentationModelAssembler는 EntityModel과 CollectionModel의 생성을 요청한다.
    EmployeeResourceAssembler() {
        super(EmployeeController.class);
    }
}
