package ko.maeng.hateoasexam;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ko.maeng.hateoasexam.hypermedia.Manager;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Optional;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
// JSON을 Java로 변환할때 인식할수 없는 속성은 무시한다는 의미.
//@JsonIgnoreProperties(ignoreUnknown = true)
public class Employee {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String role;

    @JsonIgnore
    @OneToOne
    private Manager manager;

    public Employee(String name, String role, Manager manager) {
        this.name = name;
        this.role = role;
        this.manager = manager;
    }

    public Optional<Long> getId() {
        return Optional.ofNullable(this.id);
    }
//
//    // FullName을 통해 이전 버전 API와의 호환성을 유지한다.
//    // 이를 통해 클라이언트를 중단하지 않고도 API가 발전 가능하다.
//    public String getFullName() {
//        return firstName + " " + lastName;
//    }
}
