package ko.maeng.gsspringboothateoas;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;

public class Greeting extends ResourceSupport {
    // SpringBoot Hateoas 1.0.1 버전에서는
    // ResourceSupport -> RepresentationModel로 이름 변경
    // Resource -> EntityModel
    // Resources -> CollectionModel
    // PagedResources -> PagedModel
    private final String content;

    @JsonCreator
    public Greeting(@JsonProperty("content") String content){
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
