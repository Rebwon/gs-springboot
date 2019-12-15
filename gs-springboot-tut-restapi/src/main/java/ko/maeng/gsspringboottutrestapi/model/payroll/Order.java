package ko.maeng.gsspringboottutrestapi.model.payroll;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "CUSTOMER_ORDER")
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private Status status;

    Order() {}

    Order(String description, Status status) {
        this.description = description;
        this.status = status;
    }
}
