package ko.maeng.hateoasexam;

import lombok.Getter;
import lombok.Setter;
import org.springframework.core.GenericTypeResolver;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.LinkBuilder;
import org.springframework.hateoas.server.LinkRelationProvider;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.hateoas.server.core.EvoInflectorLinkRelationProvider;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

public class SimpleIdentifiableRepresentationModelAssembler<T> implements SimpleRepresentationModelAssembler<T> {

    // 링크가 생성될 객체의 Spring MVC 클래스.
    private final Class<?> controllerClass;

    // LinkRelationProvider는 링크의 리소스 경로에 대한 옵션의 이름을 찾는다.
    @Getter private final LinkRelationProvider relProvider;

    // 링크가 될 객체의 유형을 나타낸다.
    @Getter private final Class<?> resourceType;

    // 기본 경로는 비어있게 설정.
    @Getter @Setter private String basePath = "";

    public SimpleIdentifiableRepresentationModelAssembler(Class<T> controllerClass, LinkRelationProvider relProvider) {
        this.controllerClass = controllerClass;
        this.relProvider = relProvider;

        this.resourceType = GenericTypeResolver.resolveTypeArgument(this.getClass(),
                SimpleIdentifiableRepresentationModelAssembler.class);
    }

    // EvoInflectorLinkRelationProvider로 되돌아 가는 대체 생성자.
    public SimpleIdentifiableRepresentationModelAssembler(Class<?> controllerClass) {
        this((Class<T>) controllerClass, new EvoInflectorLinkRelationProvider());
    }

    // 단일 도메인 리소스 타입을 반환할 목적으로 재정의한 메소드
    public void addLinks(EntityModel<T> resource) {
        resource.add(getCollectionLinkBuilder().slash(getId(resource)).withSelfRel());
        resource.add(getCollectionLinkBuilder().withRel(this.relProvider.getCollectionResourceRelFor(this.resourceType)));
    }

    private Object getId(EntityModel<T> resource) {
        Field id = ReflectionUtils.findField(this.resourceType, "id");
        ReflectionUtils.makeAccessible(id);

        return ReflectionUtils.getField(id, resource.getContent());
    }

    // 컬렉션 리소스 타입 엔티티를 반환할 목적으로 재정의한 메소드
    // Add a self link to aggreate root.
    public void addLinks(CollectionModel<EntityModel<T>> resources) {
        resources.add(getCollectionLinkBuilder().withSelfRel());
    }

    // Spring Controller를 사용하여 사용할 URI를 생성한 후 리소스 유형을 변환.
    // EmployeeController가 Employee를 제공하는 것으로 가정함.
    // 인스턴스에서 이 방법을 재정의하거나, addLinks(EntityModel)을 재정의한다.
    protected LinkBuilder getCollectionLinkBuilder() {
        WebMvcLinkBuilder linkBuilder = WebMvcLinkBuilder.linkTo(this.controllerClass);

        for(String pathComponent : (getPrefix() + this.relProvider.getCollectionResourceRelFor(this.resourceType))
                .split("/")) {
            if(!pathComponent.isEmpty()){
                linkBuilder = linkBuilder.slash(pathComponent);
            }
        }
        return linkBuilder;
    }

    // URI 기본 경로를 재정의 할 기회를 제공하는 역할.
    private String getPrefix() {
        return getBasePath().isEmpty() ? "" : getBasePath() + "/";
    }
}
