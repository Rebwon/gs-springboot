package ko.maeng.hateoasexam.hypermedia;

import ko.maeng.hateoasexam.EmployeeController;
import ko.maeng.hateoasexam.RootController;
import ko.maeng.hateoasexam.basic.SimpleIdentifiableRepresentationModelAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ManagerRepresentationModelAssembler extends SimpleIdentifiableRepresentationModelAssembler<Manager> {

    ManagerRepresentationModelAssembler() {
        super(ManagerController.class);
    }

    @Override
    public void addLinks(EntityModel<Manager> resource) {
        super.addLinks(resource);

        resource.getContent().getId()
                .ifPresent(id -> {
                    // Add custom link to find all managed employees
                    resource.add(linkTo(methodOn(EmployeeController.class).findEmployees(id)).withRel("employees"));
                });
    }

    @Override
    public void addLinks(CollectionModel<EntityModel<Manager>> resources) {
        super.addLinks(resources);

        resources.add(linkTo(methodOn(EmployeeController.class).findAll()).withRel("employees"));
        resources.add(linkTo(methodOn(EmployeeController.class).findAllDetailedEmployees()).withRel("detailedEmployees"));
        resources.add(linkTo(methodOn(RootController.class).root()).withRel("root"));
    }
}
