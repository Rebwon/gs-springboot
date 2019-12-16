package ko.maeng.hateoasexam.hypermedia;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ko.maeng.hateoasexam.Employee;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@Entity
@NoArgsConstructor
public class Manager {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "manager")
    private List<Employee> employees = new ArrayList<>();

    public Manager(String name) {
        this.name = name;
    }

    public Optional<Long> getId() {
        return Optional.ofNullable(this.id);
    }
}
