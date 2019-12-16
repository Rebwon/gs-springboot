package ko.maeng.hateoasexam;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Optional;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
// JSON을 Java로 변환할때 인식할수 없는 속성은 무시한다는 의미.
@JsonIgnoreProperties(ignoreUnknown = true)
public class Employee {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String role;

    public Employee(String firstName, String lastName, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    public Optional<Long> getId() {
        return Optional.ofNullable(this.id);
    }

    // FullName을 통해 이전 버전 API와의 호환성을 유지한다.
    // 이를 통해 클라이언트를 중단하지 않고도 API가 발전 가능하다.
    public String getFullName() {
        return firstName + " " + lastName;
    }
}
